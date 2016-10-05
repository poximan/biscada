
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles;

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

import org.apache.log4j.Logger;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

import bi.modelo.DatosConsulta;
import comunes.controles.EMFSingleton;
import comunes.modelo.Alarma;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO la clase para realizar consultas dinamicas a la BD
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO construyo predicados con los argumento que me entrega mi cliente,
 * para usar en consulta SQL de argumento variable
 * 
 * LO QUE CONOZCO al framework JPA para mapeo ORM
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL es JPA
 * 
 * COMO INTERACTUO CON MI COLABORADOR , pido una instancia de CriteriaBuilder y
 * le entrego los predicados que construi
 * 
 * @author hdonato
 *
 */
public class ServConsultaDinamica {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServConsultaDinamica.class);

	private CriteriaBuilder crit_builder;

	private CriteriaQuery<Alarma> crit_query;
	private Root<Alarma> root_alarmas;
	private List<Predicate> predicados;

	private TypedQuery<Alarma> typed_query;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConsultaDinamica() {

		try {
			crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();
			crit_query = crit_builder.createQuery(Alarma.class);

			root_alarmas = crit_query.from(Alarma.class);
			crit_query.select(root_alarmas);

		} catch (NullPointerException e) {
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 *
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaDesde cuando encuentra una seleccion
	 * para el tipo desde (que debe ser del enumerado [inicio, ack, fin])
	 * 
	 */
	private Predicate agregarParametroDesde(String fecha_usada, Calendar calendarDesde) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		return crit_builder.greaterThanOrEqualTo(pathParaInterpretarCalendar, calendarDesde);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * utilizado por agregarPredicadoFechaHasta cuando encuentra una seleccion
	 * para el tipo hasta (que debe ser del enumerado [inicio, ack, fin])
	 * 
	 * @param fecha_usada
	 * @param calendarHasta
	 * @return
	 */
	private Predicate agregarParametroHasta(String fecha_usada, Calendar calendarHasta) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		return crit_builder.lessThanOrEqualTo(pathParaInterpretarCalendar, calendarHasta);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especifico una familia de alarmas en particular, se traeran
	 * solo alarmas cuya familia sea = a la del parametro especificado.
	 * 
	 */
	private Predicate agregarPredicadoFamilia(String atributo) {

		ParameterExpression<Familia> p = crit_builder.parameter(Familia.class, atributo);
		return crit_builder.equal(root_alarmas.get(atributo), p);
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especific� una "fecha desde" en particular, se traeran
	 * solo alarmas con fecha mayor igual para el campo especificado [fecha inicio, ack,
	 * fin]
	 * 
	 * @param calendarDesde
	 * @param rbtnDesdeInicio
	 * @param rbtnDesdeAck
	 * @param rbtnDesdeFin
	 * @return
	 */
	private List<Predicate> agregarPredicadoFechaDesde(Calendar calendarDesde, boolean rbtnDesdeInicio,
			boolean rbtnDesdeAck, boolean rbtnDesdeFin) {

		List<Predicate> predicados = new ArrayList<>();

		if (rbtnDesdeInicio)
			predicados.add(agregarParametroDesde("fecha_inicio", calendarDesde));

		if (rbtnDesdeAck)
			predicados.add(agregarParametroDesde("fecha_ack", calendarDesde));

		if (rbtnDesdeFin)
			predicados.add(agregarParametroDesde("fecha_finalizacion", calendarDesde));

		return predicados;
	}

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario especific� una "fecha hasta" en particular, se traeran
	 * solo alarmas con fecha menor igual para el campo especificado [fecha inicio, ack,
	 * fin]
	 * 
	 * @param calendarHasta
	 * @param rbtnHastaInicio
	 * @param rbtnHastaAck
	 * @param rbtnHastaFin
	 * @return
	 */
	private List<Predicate> agregarPredicadoFechaHasta(Calendar calendarHasta, boolean rbtnHastaInicio,
			boolean rbtnHastaAck, boolean rbtnHastaFin) {

		List<Predicate> predicados = new ArrayList<>();

		if (rbtnHastaInicio)
			predicados.add(agregarParametroHasta("fecha_inicio", calendarHasta));

		if (rbtnHastaAck)
			predicados.add(agregarParametroHasta("fecha_ack", calendarHasta));

		if (rbtnHastaFin)
			predicados.add(agregarParametroHasta("fecha_finalizacion", calendarHasta));

		return predicados;
	}

	/**
	 * 
	 * creador de parametro dinamico.
	 * 
	 * si el usuario habilit� la opcion de filtrar las alarmas por tiempo
	 * maximo de vida, para descartar de esta manera alarmas que duren mas que
	 * un periodo de tiempo indicado en segundos, este metodo ser� ejecutado.
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * @return
	 */
	private Predicate agregarPredicadoRuidoMaximo(String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_maxima = crit_builder.parameter(Integer.class, "duracion_maxima");

		Expression<Float> diferencia_fechas = crit_builder.diff(root_alarmas.get(fecha_finalizacion).as(Float.class),
				root_alarmas.get(fecha_inicio).as(Float.class));

		Predicate predicado_maxima = crit_builder.le(diferencia_fechas, param_maxima);
		return predicado_maxima;
	}

	/* ............................................. */
	/* ............................................. */
	/* ....... AUXILIARES CONSULTA FILTRADA ........ */
	/* ............................................. */

	/**
	 * creador de parametro dinamico.
	 * 
	 * si el usuario habilit� la opcion de filtrar las alarmas por tiempo
	 * minimo de vida, para descartar de esta manera alarmas que duren menos que
	 * un periodo de tiempo indicado en segundos, este metodo ser� ejecutado.
	 * 
	 * @param fecha_inicio
	 * @param fecha_finalizacion
	 * 
	 */
	private Predicate agregarPredicadoRuidoMinimo(String fecha_inicio, String fecha_finalizacion) {

		ParameterExpression<Integer> param_minima = crit_builder.parameter(Integer.class, "duracion_minima");

		Expression<Float> diferencia_fechas = crit_builder.diff(root_alarmas.get(fecha_finalizacion).as(Float.class),
				root_alarmas.get(fecha_inicio).as(Float.class));

		Predicate predicado_minima = crit_builder.ge(diferencia_fechas, param_minima);
		return predicado_minima;
	}

	private void agregarPredicados(DatosConsulta datos_consulta) {

		predicados = new ArrayList<Predicate>();

		if (datos_consulta.getCalendar_desde() != null)
			predicados.addAll(agregarPredicadoFechaDesde(datos_consulta.getCalendar_desde(),
					datos_consulta.getDesde_inicio(), datos_consulta.getDesde_ack(), datos_consulta.getDesde_fin()));

		if (datos_consulta.getCalendar_hasta() != null)
			predicados.addAll(agregarPredicadoFechaHasta(datos_consulta.getCalendar_hasta(),
					datos_consulta.getHasta_inicio(), datos_consulta.getHasta_ack(), datos_consulta.getHasta_fin()));

		if (datos_consulta.getFamilia_elegida() != null)
			predicados.add(agregarPredicadoFamilia("familia"));

		if (datos_consulta.getSitio_elegido() != null)
			predicados.add(agregarPredicadoSitio("sitio"));

		if (datos_consulta.getTipo_de_equipo_elegido() != null)
			predicados.add(agregarPredicadoTipoDeEquipo("tipo_de_equipo"));

		if (datos_consulta.getSuceso_elegido() != null)
			predicados.add(agregarPredicadoSuceso("suceso"));

		if (datos_consulta.getDuracion_minima() != null)
			predicados.add(agregarPredicadoRuidoMinimo("fecha_inicio", "fecha_finalizacion"));

		if (datos_consulta.getDuracion_maxima() != null)
			predicados.add(agregarPredicadoRuidoMaximo("fecha_inicio", "fecha_finalizacion"));
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

	public List<Alarma> buscAlarma(DatosConsulta datos_consulta) {

		log.info("...   ...   ...   ...   ...   ...   ...   ...   ...   ...");

		log.info("agregando predicados...");
		agregarPredicados(datos_consulta);

		log.info("agregando condiciones consulta...");
		construirCondicionesConsulta();

		log.info("estableciendo valores de parametros...");
		establecerParametros(datos_consulta);

		mostrarQuery();

		List<Alarma> resultado = null;

		try {
			resultado = typed_query.getResultList();
		} catch (QueryTimeoutException e) {
			log.error("tiempo excedido en ejecucion de consulta");
			resultado = null;
		} catch (OutOfMemoryError e) {
			log.error("la consulta sobrepaso los recursos asignados del JRE (ambiente de ejecucion java)");
			log.error("reintentar la consulta con algunos filtros y rango de fechas mas reducido");
		}

		if (resultado == null)
			resultado = new ArrayList<Alarma>();

		log.info("consulta finalizada. se trajeron " + resultado.size() + " resultados\n");

		descartarIncompletas(resultado, datos_consulta);

		log.info("...   ...   ...   ...   ...   ...   ...   ...   ...   ...\n");

		return resultado;
	}

	private void construirCondicionesConsulta() {

		if (predicados.size() == 1)
			crit_query.where(predicados.get(0));
		else
			crit_query.where(crit_builder.and(predicados.toArray(new Predicate[0])));

		typed_query = EMFSingleton.getInstanciaEM().createQuery(crit_query);
		typed_query.setHint("javax.persistence.query.timeout", 5);
	}

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no
	 * poseen fecha las fechas indicadas
	 * 
	 * @param lista_alarmas
	 * @param datos_consulta
	 */
	private void descartarIncompletas(List<Alarma> lista_alarmas, DatosConsulta datos_consulta) {

		int total_alarmas = lista_alarmas.size();
		for (Iterator<Alarma> iterator = lista_alarmas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if ((!datos_consulta.isIncluir_ini_incompleta() && alarma_actual.getFecha_inicio() == null) || //
					(!datos_consulta.isIncluir_fin_incompleta() && alarma_actual.getFecha_finalizacion() == null) || //
					(!datos_consulta.isIncluir_ack_incompleta() && alarma_actual.getFecha_ack() == null))
				iterator.remove();
		}
		log.info("se extrajeron " + (total_alarmas - lista_alarmas.size())
				+ " alarmas que les faltaba alguna fecha solicitada");
	}

	private void establecerParametros(DatosConsulta datos_consulta) {

		if (datos_consulta.getCalendar_desde() != null) {
			if (datos_consulta.getDesde_inicio())
				typed_query.setParameter("fecha_inicio", datos_consulta.getCalendar_desde());

			if (datos_consulta.getDesde_ack())
				typed_query.setParameter("fecha_ack", datos_consulta.getCalendar_desde());

			if (datos_consulta.getDesde_fin())
				typed_query.setParameter("fecha_finalizacion", datos_consulta.getCalendar_desde());
		}

		if (datos_consulta.getCalendar_hasta() != null) {
			if (datos_consulta.getHasta_inicio())
				typed_query.setParameter("fecha_inicio", datos_consulta.getCalendar_hasta());

			if (datos_consulta.getHasta_ack())
				typed_query.setParameter("fecha_ack", datos_consulta.getCalendar_hasta());

			if (datos_consulta.getHasta_fin())
				typed_query.setParameter("fecha_finalizacion", datos_consulta.getCalendar_hasta());
		}

		if (datos_consulta.getFamilia_elegida() != null)
			typed_query.setParameter("familia", datos_consulta.getFamilia_elegida());

		if (datos_consulta.getSitio_elegido() != null)
			typed_query.setParameter("sitio", datos_consulta.getSitio_elegido());

		if (datos_consulta.getTipo_de_equipo_elegido() != null)
			typed_query.setParameter("tipo_de_equipo", datos_consulta.getTipo_de_equipo_elegido());

		if (datos_consulta.getSuceso_elegido() != null)
			typed_query.setParameter("suceso", datos_consulta.getSuceso_elegido());

		if (datos_consulta.getDuracion_minima() != null)
			typed_query.setParameter("duracion_minima", datos_consulta.getDuracion_minima());

		if (datos_consulta.getDuracion_maxima() != null)
			typed_query.setParameter("duracion_maxima", datos_consulta.getDuracion_maxima());
	}

	private void mostrarQuery() {

		Session sesion = EMFSingleton.getInstanciaEM().unwrap(JpaEntityManager.class).getActiveSession();
		DatabaseQuery consulta = ((EJBQueryImpl<Alarma>) typed_query).getDatabaseQuery();
		consulta.prepareCall(sesion, new DatabaseRecord());
		String sql_string = consulta.getSQLString();

		log.info(sql_string);
	}
}