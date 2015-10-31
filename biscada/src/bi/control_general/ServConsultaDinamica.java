/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.control_general;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

import comunes.control_general.EMFSingleton;
import comunes.modelo.Alarma;
import comunes.modelo.CalendarNumber;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServConsultaDinamica {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServConsultaDinamica.class);

	private CriteriaBuilder crit_builder;
	private CriteriaQuery<Alarma> crit_query;

	private TypedQuery<Alarma> typed_query;
	private Root<Alarma> root_alarmas;

	private List<Predicate> criteria;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConsultaDinamica() {

		crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();
		crit_query = crit_builder.createQuery(Alarma.class);

		typed_query = EMFSingleton.getInstanciaEM().createQuery(crit_query);
		typed_query.setHint("javax.persistence.query.timeout", 5000);

		root_alarmas = crit_query.from(Alarma.class);

		crit_query.select(root_alarmas);

		criteria = new ArrayList<Predicate>();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public List<Alarma> buscAlarma(Calendar calendarDesde, JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck,
			JRadioButton rbtnDesdeFin, Calendar calendarHasta, JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck,
			JRadioButton rbtnHastaFin, Familia familia, Sitio sitio, TipoDeEquipo tipo_de_equipo, Suceso suceso,
			Integer duracion_minima, Integer duracion_maxima, boolean incluir_ini_incompleta,
			boolean incluir_ack_incompleta, boolean incluir_fin_incompleta) {

		log.trace("...   ...   ...   ...   ...   ...   ...   ...   ...   ...");

		try {
			buscarRangoFechas(calendarDesde, rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin, calendarHasta,
					rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin);

			buscarTipos(familia, sitio, tipo_de_equipo, suceso);

			buscarDuracionAlarma(duracion_minima, duracion_maxima);

		} catch (OutOfMemoryError excepcion) {
		}

		List<Alarma> resultado;

		try {
			resultado = typed_query.getResultList();
		} catch (QueryTimeoutException e) {
			log.error("tiempo excedido en ejecucion de consulta");
			resultado = null;
		}

		if (resultado == null)
			resultado = new ArrayList<Alarma>();

		log.trace("consulta a bd -> " + resultado.size() + " aciertos\n");

		descartarIncompletas(resultado, incluir_ini_incompleta, incluir_fin_incompleta, incluir_ack_incompleta);

		log.trace("...   ...   ...   ...   ...   ...   ...   ...   ...   ...\n");

		return resultado;
	}

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no
	 * poseen fecha las fechas indicadas
	 * 
	 * @param lista_alarmas
	 * @param incluir_ack_incompleta
	 * @param incluir_fin_incompleta
	 * @param incluir_ini_incompleta
	 */
	private void descartarIncompletas(List<Alarma> lista_alarmas, boolean incluir_ini_incompleta,
			boolean incluir_fin_incompleta, boolean incluir_ack_incompleta) {

		int total_alarmas = lista_alarmas.size();
		for (Iterator<Alarma> iterator = lista_alarmas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if ((!incluir_ini_incompleta && alarma_actual.getFecha_inicio() == null) || //
					(!incluir_fin_incompleta && alarma_actual.getFecha_finalizacion() == null) || //
					(!incluir_ack_incompleta && alarma_actual.getFecha_ack() == null))
				iterator.remove();
		}
		log.trace("se extrajeron " + (total_alarmas - lista_alarmas.size())
				+ " alarmas que les faltaba alguna fecha solicitada");
	}

	private void buscarDuracionAlarma(Integer duracion_minima, Integer duracion_maxima) {

		// -------------------------------------
		//
		// llegada dinamica de parametros
		// -------------------------------------

		if (duracion_minima != null)
			criteria.add(agregarPredicadoRuidoMinimo("fecha_inicio", "fecha_finalizacion"));

		if (duracion_maxima != null)
			criteria.add(agregarPredicadoRuidoMaximo("fecha_inicio", "fecha_finalizacion"));

		// -------------------------------------
		//
		// construir consulta
		// -------------------------------------

		if (criteria.size() == 1)
			crit_query.where(criteria.get(0));
		else
			crit_query.where(crit_builder.and(criteria.toArray(new Predicate[0])));

		// -------------------------------------
		//
		// pasar parametros
		// -------------------------------------

		if (duracion_minima != null)
			typed_query.setParameter("duracion_minima", duracion_minima);

		if (duracion_maxima != null)
			typed_query.setParameter("duracion_maxima", duracion_maxima);

		mostrarQuery(typed_query);
	}

	private void buscarRangoFechas(Calendar calendarDesde, JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck,
			JRadioButton rbtnDesdeFin, Calendar calendarHasta, JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck,
			JRadioButton rbtnHastaFin) {

		// -------------------------------------
		//
		// llegada dinamica de parametros
		// -------------------------------------

		if (calendarDesde != null)
			agregarPredicadoFechaDesde(criteria, calendarDesde, rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin);

		if (calendarHasta != null)
			agregarPredicadoFechaHasta(criteria, calendarHasta, rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin);

		// -------------------------------------
		//
		// construir consulta
		// -------------------------------------

		if (criteria.size() == 1)
			crit_query.where(criteria.get(0));
		else
			crit_query.where(crit_builder.and(criteria.toArray(new Predicate[0])));

		// -------------------------------------
		//
		// pasar parametros
		// -------------------------------------

		if (calendarDesde != null) {
			if (rbtnDesdeInicio.isSelected())
				typed_query.setParameter("fecha_inicio", calendarDesde);

			if (rbtnDesdeAck.isSelected())
				typed_query.setParameter("fecha_ack", calendarDesde);

			if (rbtnDesdeFin.isSelected())
				typed_query.setParameter("fecha_finalizacion", calendarDesde);
		}
		if (calendarHasta != null) {
			if (rbtnHastaInicio.isSelected())
				typed_query.setParameter("fecha_inicio", calendarHasta);

			if (rbtnHastaAck.isSelected())
				typed_query.setParameter("fecha_ack", calendarHasta);

			if (rbtnHastaFin.isSelected())
				typed_query.setParameter("fecha_finalizacion", calendarHasta);
		}

		mostrarQuery(typed_query);
	}

	private void buscarTipos(Familia familia, Sitio sitio, TipoDeEquipo tipo_de_equipo, Suceso suceso) {

		if (familia != null)
			criteria.add(agregarPredicadoFamilia("familia"));

		if (sitio != null)
			criteria.add(agregarPredicadoSitio("sitio"));

		if (tipo_de_equipo != null)
			criteria.add(agregarPredicadoTipoDeEquipo("tipo_de_equipo"));

		if (suceso != null)
			criteria.add(agregarPredicadoSuceso("suceso"));

		// -------------------------------------
		//
		// construir consulta
		// -------------------------------------

		if (criteria.size() == 1)
			crit_query.where(criteria.get(0));
		else
			crit_query.where(crit_builder.and(criteria.toArray(new Predicate[0])));

		// -------------------------------------
		//
		// pasar parametros
		// -------------------------------------

		if (familia != null)
			typed_query.setParameter("familia", familia);

		if (sitio != null)
			typed_query.setParameter("sitio", sitio);

		if (tipo_de_equipo != null)
			typed_query.setParameter("tipo_de_equipo", tipo_de_equipo);

		if (suceso != null)
			typed_query.setParameter("suceso", suceso);

		mostrarQuery(typed_query);
	}

	/* ............................................. */
	/* ............................................. */
	/* ....... AUXILIARES CONSULTA FILTRADA ........ */
	/* ............................................. */

	private void mostrarQuery(TypedQuery<Alarma> typed_query) {

		Session sesion = EMFSingleton.getInstanciaEM().unwrap(JpaEntityManager.class).getActiveSession();
		DatabaseQuery consulta = ((EJBQueryImpl<Alarma>) typed_query).getDatabaseQuery();
		consulta.prepareCall(sesion, new DatabaseRecord());
		String sql_string = consulta.getSQLString();

		log.trace(sql_string);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario habilit� la opcion de filtrar las alarmas por tiempo minimo
	 * de vida, para descartar de esta manera alarmas que duren menos que un
	 * periodo de tiempo indicado en segundos, este metodo ser� ejecutado.
	 * 
	 * @param criteria
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * @param duracion_minima
	 */
	private Predicate agregarPredicadoRuidoMinimo(String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_minima = crit_builder.parameter(Integer.class, "duracion_minima");

		Expression<CalendarNumber> diferencia_fechas = crit_builder.diff(
				root_alarmas.get(fecha_finalizacion).as(CalendarNumber.class),
				root_alarmas.get(fecha_inicio).as(CalendarNumber.class));

		Predicate predicado_minima = crit_builder.ge(diferencia_fechas, param_minima);
		return predicado_minima;
	}

	/**
	 * 
	 * creador de parametro dinamico.
	 * 
	 * si el usuario habilit� la opcion de filtrar las alarmas por tiempo maximo
	 * de vida, para descartar de esta manera alarmas que duren mas que un
	 * periodo de tiempo indicado en segundos, este metodo ser� ejecutado.
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * @return
	 */
	private Predicate agregarPredicadoRuidoMaximo(String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_maxima = crit_builder.parameter(Integer.class, "duracion_maxima");

		Expression<CalendarNumber> diferencia_fechas = crit_builder.diff(
				root_alarmas.get(fecha_finalizacion).as(CalendarNumber.class),
				root_alarmas.get(fecha_inicio).as(CalendarNumber.class));

		Predicate predicado_maxima = crit_builder.le(diferencia_fechas, param_maxima);
		return predicado_maxima;
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especific� una "fecha desde" en particular, se traeran solo
	 * alarmas con fecha >= para el campo especificado [fecha inicio, ack, fin]
	 * 
	 * @param criteria
	 * 
	 * @param calendarDesde
	 * @param rbtnDesdeInicio
	 * @param rbtnDesdeAck
	 * @param rbtnDesdeFin
	 */
	private void agregarPredicadoFechaDesde(List<Predicate> criteria, Calendar calendarDesde,
			JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck, JRadioButton rbtnDesdeFin) {

		if (rbtnDesdeInicio.isSelected())
			criteria.add(agregarParametroDesde(crit_builder, root_alarmas, "fecha_inicio", calendarDesde));

		if (rbtnDesdeAck.isSelected())
			criteria.add(agregarParametroDesde(crit_builder, root_alarmas, "fecha_ack", calendarDesde));

		if (rbtnDesdeFin.isSelected())
			criteria.add(agregarParametroDesde(crit_builder, root_alarmas, "fecha_finalizacion", calendarDesde));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especific� una "fecha hasta" en particular, se traeran solo
	 * alarmas con fecha <= para el campo especificado [fecha inicio, ack, fin]
	 * 
	 * @param criteria
	 * 
	 * @param calendarHasta
	 * @param rbtnHastaInicio
	 * @param rbtnHastaAck
	 * @param rbtnHastaFin
	 */
	private void agregarPredicadoFechaHasta(List<Predicate> criteria, Calendar calendarHasta,
			JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck, JRadioButton rbtnHastaFin) {

		if (rbtnHastaInicio.isSelected())
			criteria.add(agregarParametroHasta(crit_builder, root_alarmas, "fecha_inicio", calendarHasta));

		if (rbtnHastaAck.isSelected())
			criteria.add(agregarParametroHasta(crit_builder, root_alarmas, "fecha_ack", calendarHasta));

		if (rbtnHastaFin.isSelected())
			criteria.add(agregarParametroHasta(crit_builder, root_alarmas, "fecha_finalizacion", calendarHasta));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especifico una familia de alarmas en particular, se traeran
	 * solo alarmas cuya familia sea = a la del parametro especificado.
	 * 
	 * @param atributo
	 * @return
	 */
	private Predicate agregarPredicadoFamilia(String atributo) {

		ParameterExpression<Familia> p = crit_builder.parameter(Familia.class, atributo);
		return crit_builder.equal(root_alarmas.get(atributo), p);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param atributo
	 * @return
	 */
	private Predicate agregarPredicadoSitio(String atributo) {

		ParameterExpression<Sitio> p = crit_builder.parameter(Sitio.class, atributo);
		return crit_builder.equal(root_alarmas.get(atributo), p);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param atributo
	 * @return
	 */
	private Predicate agregarPredicadoTipoDeEquipo(String atributo) {

		ParameterExpression<TipoDeEquipo> p = crit_builder.parameter(TipoDeEquipo.class, atributo);
		return crit_builder.equal(root_alarmas.get("equipo_en_sitio").get(atributo), p);
	}

	/**
	 * 
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param atributo
	 * @return
	 */
	private Predicate agregarPredicadoSuceso(String atributo) {

		ParameterExpression<Suceso> p = crit_builder.parameter(Suceso.class, atributo);
		return crit_builder.equal(root_alarmas.get(atributo), p);
	}

	/**
	 *
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaDesde cuando encuentra una seleccion
	 * para el tipo desde (que debe ser del enumerado [inicio, ack, fin])
	 * 
	 * @param crit_builder
	 * @param root_alarmas
	 * @param fecha_usada
	 * @param calendarDesde
	 * @return
	 */
	private Predicate agregarParametroDesde(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas, String fecha_usada,
			Calendar calendarDesde) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		return crit_builder.greaterThanOrEqualTo(pathParaInterpretarCalendar, calendarDesde);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaHasta cuando encuentra una seleccion
	 * para el tipo hasta (que debe ser del enumerado [inicio, ack, fin])
	 * 
	 * @param crit_builder
	 * @param root_alarmas
	 * @param fecha_usada
	 * @param calendarHasta
	 * @return
	 */
	private Predicate agregarParametroHasta(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas, String fecha_usada,
			Calendar calendarHasta) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		return crit_builder.lessThanOrEqualTo(pathParaInterpretarCalendar, calendarHasta);
	}
}