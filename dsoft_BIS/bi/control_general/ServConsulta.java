/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JRadioButton;

import modelo.Alarma;
import modelo.CalendarNumber;
import modelo.Familia;
import modelo.Sitio;
import modelo.Suceso;
import modelo.TipoDeEquipo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServConsulta implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServConsulta.class);

	private List<Alarma> resultado_fechas;
	private List<Alarma> resultado_tipos;
	private List<Alarma> resultado_duracion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConsulta() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no poseen fecha de inicio
	 * 
	 * @param lista_alarmas
	 */
	private void descartarIniIncompleta(List<Alarma> lista_alarmas) {

		int total_alarmas = lista_alarmas.size();
		for (Iterator<Alarma> iterator = lista_alarmas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if (alarma_actual.getFecha_inicio() == null)
				iterator.remove();
		}
		log.trace("de lo anterior, se extrajeron " + (total_alarmas - lista_alarmas.size()) + " (ini)");
	}

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no poseen fecha de ack
	 * 
	 * @param lista_alarmas
	 */
	private void descartarAckIncompleta(List<Alarma> lista_alarmas) {

		int total_alarmas = lista_alarmas.size();
		for (Iterator<Alarma> iterator = lista_alarmas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if (alarma_actual.getFecha_ack() == null)
				iterator.remove();
		}
		log.trace("de lo anterior, se extrajeron " + (total_alarmas - lista_alarmas.size()) + " (ack)");
	}

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no poseen fecha de fin
	 * 
	 * @param lista_alarmas
	 */
	private void descartarFinIncompleta(List<Alarma> lista_alarmas) {

		int total_alarmas = lista_alarmas.size();
		for (Iterator<Alarma> iterator = lista_alarmas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if (alarma_actual.getFecha_finalizacion() == null)
				iterator.remove();
		}
		log.trace("de lo anterior, se extrajeron " + (total_alarmas - lista_alarmas.size()) + " (fin)");
	}

	@SuppressWarnings({ "unchecked" })
	public static List<TipoDeEquipo> getListaEquipos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("TipoDeEquipo.buscTodos").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Familia> getListaFamilia() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Familia.buscTodos").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Sitio> getListaSitios() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Sitio.buscTodos").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Suceso> getListaSucesos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Suceso.buscTodos").getResultList();
	}

	/* ............................................. */
	/* ............................................. */
	/* ....... CONSULTA FILTRADA ................... */
	/* ............................................. */

	public List<Alarma> buscAlarma(Calendar calendarDesde, JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck,
			JRadioButton rbtnDesdeFin, Calendar calendarHasta, JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck,
			JRadioButton rbtnHastaFin, Familia familia, Sitio sitio, TipoDeEquipo tipo_de_equipo, Suceso suceso,
			Integer duracion_minima, Integer duracion_maxima, boolean incluir_ini_incompleta,
			boolean incluir_ack_incompleta, boolean incluir_fin_incompleta) {

		log.trace("...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...");

		try {
			resultado_fechas = buscarRangoFechas(calendarDesde, rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin,
					calendarHasta, rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin);

			resultado_tipos = buscarTipos(familia, sitio, tipo_de_equipo, suceso);

			resultado_duracion = buscarDuracionAlarma(duracion_minima, duracion_maxima);
		}
		catch (OutOfMemoryError excepcion) {
		}

		List<Alarma> resultado_join = Join(resultado_fechas, resultado_tipos, resultado_duracion);

		if (!incluir_ini_incompleta)
			descartarIniIncompleta(resultado_join);
		if (!incluir_ack_incompleta)
			descartarAckIncompleta(resultado_join);
		if (!incluir_fin_incompleta)
			descartarFinIncompleta(resultado_join);

		log.trace("...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...\n");

		liberarObjetos();

		return resultado_join;
	}

	private List<Alarma> Join(List<Alarma> resultado_fechas, List<Alarma> resultado_tipos,
			List<Alarma> resultado_duracion) {

		Collection<Alarma> resultado_parcial = CollectionUtils.intersection(resultado_fechas, resultado_tipos);
		Collection<Alarma> resultado_final = CollectionUtils.intersection(resultado_parcial, resultado_duracion);

		if (resultado_final == null)
			resultado_final = new ArrayList<Alarma>();

		log.trace("consulta: join subconsultas -> " + resultado_final.size() + " aciertos\n");
		return new ArrayList<Alarma>(resultado_final);
	}

	private List<Alarma> buscarDuracionAlarma(Integer duracion_minima, Integer duracion_maxima) {

		CriteriaBuilder crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();

		CriteriaQuery<Alarma> crit_query = crit_builder.createQuery(Alarma.class);
		Root<Alarma> root_alarmas = crit_query.from(Alarma.class);

		crit_query.select(root_alarmas);
		List<Predicate> criteria = new ArrayList<Predicate>();

		// -------------------------------------
		//
		// llegada dinamica de parametros
		// -------------------------------------

		if (duracion_minima != null)
			agregarPredicadoRuidoMinimo(crit_builder, root_alarmas, criteria, "fecha_inicio", "fecha_finalizacion");

		if (duracion_maxima != null)
			agregarPredicadoRuidoMaximo(crit_builder, root_alarmas, criteria, "fecha_inicio", "fecha_finalizacion");

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

		TypedQuery<Alarma> typed_query = EMFSingleton.getInstanciaEM().createQuery(crit_query);

		if (duracion_minima != null)
			typed_query.setParameter("duracion_minima", duracion_minima);

		if (duracion_maxima != null)
			typed_query.setParameter("duracion_maxima", duracion_maxima);

		mostrarQuery(typed_query);

		List<Alarma> resultado = typed_query.getResultList();
		if (resultado == null)
			resultado = new ArrayList<Alarma>();

		log.trace("consulta: duracion alarma -> " + resultado.size() + " aciertos\n");

		return resultado;
	}

	private List<Alarma> buscarRangoFechas(Calendar calendarDesde, JRadioButton rbtnDesdeInicio,
			JRadioButton rbtnDesdeAck, JRadioButton rbtnDesdeFin, Calendar calendarHasta, JRadioButton rbtnHastaInicio,
			JRadioButton rbtnHastaAck, JRadioButton rbtnHastaFin) {

		CriteriaBuilder crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();

		CriteriaQuery<Alarma> crit_query = crit_builder.createQuery(Alarma.class);
		Root<Alarma> root_alarmas = crit_query.from(Alarma.class);

		crit_query.select(root_alarmas);
		List<Predicate> criteria = new ArrayList<Predicate>();

		// -------------------------------------
		//
		// llegada dinamica de parametros
		// -------------------------------------

		if (calendarDesde != null)
			agregarPredicadoFechaDesde(crit_builder, root_alarmas, criteria, calendarDesde, rbtnDesdeInicio,
					rbtnDesdeAck, rbtnDesdeFin);

		if (calendarHasta != null)
			agregarPredicadoFechaHasta(crit_builder, root_alarmas, criteria, calendarHasta, rbtnHastaInicio,
					rbtnHastaAck, rbtnHastaFin);

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

		TypedQuery<Alarma> typed_query = EMFSingleton.getInstanciaEM().createQuery(crit_query);

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

		List<Alarma> resultado = typed_query.getResultList();
		if (resultado == null)
			resultado = new ArrayList<Alarma>();

		log.trace("consulta: rango de fechas -> " + resultado.size() + " aciertos\n");

		return resultado;
	}

	private List<Alarma> buscarTipos(Familia familia, Sitio sitio, TipoDeEquipo tipo_de_equipo, Suceso suceso) {

		CriteriaBuilder crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();

		CriteriaQuery<Alarma> crit_query = crit_builder.createQuery(Alarma.class);
		Root<Alarma> root_alarmas = crit_query.from(Alarma.class);

		crit_query.select(root_alarmas);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (familia != null)
			agregarPredicadoFamilia(crit_builder, root_alarmas, criteria, "familia");

		if (sitio != null)
			agregarPredicadoSitio(crit_builder, root_alarmas, criteria, "sitio");

		if (tipo_de_equipo != null)
			agregarPredicadoTipoDeEquipo(crit_builder, root_alarmas, criteria, "tipo_de_equipo");

		if (suceso != null)
			agregarPredicadoSuceso(crit_builder, root_alarmas, criteria, "suceso");

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

		TypedQuery<Alarma> typed_query = EMFSingleton.getInstanciaEM().createQuery(crit_query);

		if (familia != null)
			typed_query.setParameter("familia", familia);

		if (sitio != null)
			typed_query.setParameter("sitio", sitio);

		if (tipo_de_equipo != null)
			typed_query.setParameter("tipo_de_equipo", tipo_de_equipo);

		if (suceso != null)
			typed_query.setParameter("suceso", suceso);

		mostrarQuery(typed_query);

		List<Alarma> resultado = typed_query.getResultList();
		if (resultado == null)
			resultado = new ArrayList<Alarma>();

		log.trace("consulta: coincidencia de tipos -> " + resultado.size() + " aciertos\n");

		return resultado;
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
	 * si el usuario habilitó la opcion de filtrar las alarmas por tiempo minimo de vida, para descartar de esta manera
	 * alarmas que duren menos que un periodo de tiempo indicado en segundos, este metodo será ejecutado.
	 * 
	 * @param criteria
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * @param duracion_minima
	 */
	private void agregarPredicadoRuidoMinimo(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_minima = crit_builder.parameter(Integer.class, "duracion_minima");

		Expression<CalendarNumber> diferencia_fechas = crit_builder.diff(
				root_alarmas.get(fecha_finalizacion).as(CalendarNumber.class),
				root_alarmas.get(fecha_inicio).as(CalendarNumber.class));

		Predicate predicado_minima = crit_builder.ge(diferencia_fechas, param_minima);
		criteria.add(predicado_minima);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario habilitó la opcion de filtrar las alarmas por tiempo maximo de vida, para descartar de esta manera
	 * alarmas que duren mas que un periodo de tiempo indicado en segundos, este metodo será ejecutado.
	 * 
	 * @param criteria
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * @param duracion_minima
	 * @param duracion_maxima
	 */
	private void agregarPredicadoRuidoMaximo(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_maxima = crit_builder.parameter(Integer.class, "duracion_maxima");

		Expression<CalendarNumber> diferencia_fechas = crit_builder.diff(
				root_alarmas.get(fecha_finalizacion).as(CalendarNumber.class),
				root_alarmas.get(fecha_inicio).as(CalendarNumber.class));

		Predicate predicado_maxima = crit_builder.le(diferencia_fechas, param_maxima);
		criteria.add(predicado_maxima);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especificó una "fecha desde" en particular, se traeran solo alarmas con fecha >= para el campo
	 * especificado [fecha inicio, ack, fin]
	 * 
	 * @param criteria
	 * 
	 * @param calendarDesde
	 * @param rbtnDesdeInicio
	 * @param rbtnDesdeAck
	 * @param rbtnDesdeFin
	 */
	private void agregarPredicadoFechaDesde(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, Calendar calendarDesde, JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck,
			JRadioButton rbtnDesdeFin) {

		if (rbtnDesdeInicio.isSelected())
			agregarParametroDesde(crit_builder, root_alarmas, criteria, "fecha_inicio", calendarDesde);

		if (rbtnDesdeAck.isSelected())
			agregarParametroDesde(crit_builder, root_alarmas, criteria, "fecha_ack", calendarDesde);

		if (rbtnDesdeFin.isSelected())
			agregarParametroDesde(crit_builder, root_alarmas, criteria, "fecha_finalizacion", calendarDesde);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especificó una "fecha hasta" en particular, se traeran solo alarmas con fecha <= para el campo
	 * especificado [fecha inicio, ack, fin]
	 * 
	 * @param criteria
	 * 
	 * @param calendarHasta
	 * @param rbtnHastaInicio
	 * @param rbtnHastaAck
	 * @param rbtnHastaFin
	 */
	private void agregarPredicadoFechaHasta(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, Calendar calendarHasta, JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck,
			JRadioButton rbtnHastaFin) {

		if (rbtnHastaInicio.isSelected())
			agregarParametroHasta(crit_builder, root_alarmas, criteria, "fecha_inicio", calendarHasta);

		if (rbtnHastaAck.isSelected())
			agregarParametroHasta(crit_builder, root_alarmas, criteria, "fecha_ack", calendarHasta);

		if (rbtnHastaFin.isSelected())
			agregarParametroHasta(crit_builder, root_alarmas, criteria, "fecha_finalizacion", calendarHasta);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especifico una familia de alarmas en particular, se traeran solo alarmas cuya familia sea = a la
	 * del parametro especificado.
	 * 
	 * @param criteria
	 * 
	 * @param atributo
	 */
	private void agregarPredicadoFamilia(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String atributo) {

		ParameterExpression<Familia> p = crit_builder.parameter(Familia.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param criteria
	 * 
	 * @param atributo
	 */
	private void agregarPredicadoSitio(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String atributo) {

		ParameterExpression<Sitio> p = crit_builder.parameter(Sitio.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param criteria
	 * 
	 * @param atributo
	 */
	private void agregarPredicadoTipoDeEquipo(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String atributo) {

		ParameterExpression<TipoDeEquipo> p = crit_builder.parameter(TipoDeEquipo.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get("equipo_en_sitio").get(atributo), p));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * analogo a campo familia, referirse a su javadoc
	 * 
	 * @param criteria
	 * 
	 * @param atributo
	 */
	private void agregarPredicadoSuceso(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String atributo) {

		ParameterExpression<Suceso> p = crit_builder.parameter(Suceso.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaDesde cuando encuentra una seleccion para el tipo desde (que debe ser del
	 * enumerado [inicio, ack, fin])
	 * 
	 * @param criteria
	 * 
	 * @param fecha_usada
	 * @param calendarDesde
	 */
	private void agregarParametroDesde(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String fecha_usada, Calendar calendarDesde) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		criteria.add(crit_builder.greaterThanOrEqualTo(pathParaInterpretarCalendar, calendarDesde));
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaHasta cuando encuentra una seleccion para el tipo hasta (que debe ser del
	 * enumerado [inicio, ack, fin])
	 * 
	 * @param criteria
	 * 
	 * @param fecha_usada
	 * @param calendarHasta
	 */
	private void agregarParametroHasta(CriteriaBuilder crit_builder, Root<Alarma> root_alarmas,
			List<Predicate> criteria, String fecha_usada, Calendar calendarHasta) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		criteria.add(crit_builder.lessThanOrEqualTo(pathParaInterpretarCalendar, calendarHasta));
	}

	@Override
	public void liberarObjetos() {

		resultado_fechas.clear();
		resultado_tipos.clear();
		resultado_duracion.clear();

		System.gc();
	}
}