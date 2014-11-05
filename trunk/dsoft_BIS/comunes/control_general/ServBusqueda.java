/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.log4j.Logger;

import modelo.Alarma;
import modelo.CalendarExtendido;
import modelo.Familia;
import modelo.Sitio;
import modelo.Suceso;
import modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServBusqueda implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServBusqueda.class);

	private CriteriaBuilder crit_builder;

	private Root<Alarma> root_alarmas;

	private List<Predicate> criteria;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServBusqueda() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * del set de alarmas devuelto por la consulta, extrae aquellas que no poseen fecha de inicio, fin o ninguna de las
	 * dos. se resuleve creando una nueva lista, por el momento parace funcionar, pero habria que realizar mas pruebas
	 * hacia la ventana anterior, porque en este paso se pierde la lista observable que se habia creado en la pantalla
	 * de consultas.
	 * 
	 * @param lista_alarmas_un_sitio
	 * @return
	 */
	public static List<Alarma> descartarFechasIncompletas(List<Alarma> lista_alarmas_un_sitio) {

		List<Alarma> alarmas_filtradas = new ArrayList<Alarma>(lista_alarmas_un_sitio);

		for (Iterator<Alarma> iterator = alarmas_filtradas.iterator(); iterator.hasNext();) {

			Alarma alarma_actual = iterator.next();

			if (alarma_actual.getFecha_inicio() == null || alarma_actual.getFecha_finalizacion() == null)
				iterator.remove();
		}
		return alarmas_filtradas;
	}

	@SuppressWarnings("unchecked")
	public static List<TipoDeEquipo> getListaEquipos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("TipoDeEquipo.buscTodos").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Familia> getListaFamilia() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Familia.buscTodos").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Sitio> getListaSitios() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Sitio.buscTodos").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Suceso> getListaSucesos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Suceso.buscTodos").getResultList();
	}

	public List<Alarma> buscAlarma(Calendar calendarDesde, JRadioButton rbtnDesdeInicio, JRadioButton rbtnDesdeAck,
			JRadioButton rbtnDesdeFin, Calendar calendarHasta, JRadioButton rbtnHastaInicio, JRadioButton rbtnHastaAck,
			JRadioButton rbtnHastaFin, Familia familia, Sitio sitio, TipoDeEquipo tipo_de_equipo, Suceso suceso,
			Integer ruido_maximo) {

		liberarObjetos();

		crit_builder = EMFSingleton.getInstanciaEM().getCriteriaBuilder();
		CriteriaQuery<Alarma> crit_query = crit_builder.createQuery(Alarma.class);

		root_alarmas = crit_query.from(Alarma.class);
		crit_query.select(root_alarmas);
		crit_query.distinct(true);

		criteria = new ArrayList<Predicate>();

		// -------------------------------------
		//
		// inicio crear parametros
		// -------------------------------------

		if (calendarDesde != null)
			agregarPredicadoFechaDesde(calendarDesde, rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin);

		if (calendarHasta != null)
			agregarPredicadoFechaHasta(calendarHasta, rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin);

		if (familia != null)
			agregarPredicadoFamilia("familia");

		if (sitio != null)
			agregarPredicadoSitio("sitio");

		if (tipo_de_equipo != null)
			agregarPredicadoTipoDeEquipo("tipo_de_equipo");

		if (suceso != null)
			agregarPredicadoSuceso("suceso");

		if (ruido_maximo != null)
			agregarPredicadoRuido("fecha_inicio", "fecha_finalizacion", ruido_maximo);

		// -------------------------------------
		//
		// inicio construir consulta
		// -------------------------------------

		if (criteria.size() == 1)
			crit_query.where(criteria.get(0));
		else
			crit_query.where(crit_builder.and(criteria.toArray(new Predicate[0])));

		// -------------------------------------
		//
		// inicio pasar parametros
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
		if (familia != null) {
			typed_query.setParameter("familia", familia);
		}
		if (sitio != null) {
			typed_query.setParameter("sitio", sitio);
		}
		if (tipo_de_equipo != null) {
			typed_query.setParameter("tipo_de_equipo", tipo_de_equipo);
		}
		if (suceso != null) {
			typed_query.setParameter("suceso", suceso);
		}

		if (ruido_maximo != null)
			typed_query.setParameter("ruido_maximo", ruido_maximo);

		return typed_query.getResultList();
	}

	private void agregarPredicadoRuido(String fecha_inicio, String fecha_finalizacion, Integer ruido_maximo) {

		if (ruido_maximo != null)
			return;

		// primer aproximacion
		Path<Calendar> pathParaInterpretarCalendarFechaInicio = root_alarmas.get(fecha_inicio);
		Path<Calendar> pathParaInterpretarCalendarFechaFin = root_alarmas.get(fecha_finalizacion);

		// long h = pathParaInterpretarCalendarFechaInicio.get("milis");

		// otra aproximacion
		ParameterExpression<Integer> p = crit_builder.parameter(Integer.class, "ruido_maximo");

		Expression<CalendarExtendido> diferencia_fechas = crit_builder.diff(
				root_alarmas.get("fecha_inicio").as(CalendarExtendido.class), root_alarmas.get("fecha_finalizacion")
						.as(CalendarExtendido.class));

		criteria.add(crit_builder.ge(p, diferencia_fechas));
	}

	private void agregarPredicadoFechaDesde(Calendar calendarDesde, JRadioButton rbtnDesdeInicio,
			JRadioButton rbtnDesdeAck, JRadioButton rbtnDesdeFin) {

		if (rbtnDesdeInicio.isSelected())
			agregarParametroDesde("fecha_inicio", calendarDesde);

		if (rbtnDesdeAck.isSelected())
			agregarParametroDesde("fecha_ack", calendarDesde);

		if (rbtnDesdeFin.isSelected())
			agregarParametroDesde("fecha_finalizacion", calendarDesde);
	}

	private void agregarPredicadoFechaHasta(Calendar calendarHasta, JRadioButton rbtnHastaInicio,
			JRadioButton rbtnHastaAck, JRadioButton rbtnHastaFin) {

		if (rbtnHastaInicio.isSelected())
			agregarParametroHasta("fecha_inicio", calendarHasta);

		if (rbtnHastaAck.isSelected())
			agregarParametroHasta("fecha_ack", calendarHasta);

		if (rbtnHastaFin.isSelected())
			agregarParametroHasta("fecha_finalizacion", calendarHasta);
	}

	private void agregarPredicadoFamilia(String atributo) {

		ParameterExpression<Familia> p = crit_builder.parameter(Familia.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	private void agregarPredicadoSitio(String atributo) {

		ParameterExpression<Sitio> p = crit_builder.parameter(Sitio.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	private void agregarPredicadoTipoDeEquipo(String atributo) {

		ParameterExpression<TipoDeEquipo> p = crit_builder.parameter(TipoDeEquipo.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get("equipo_en_sitio").get(atributo), p));
	}

	private void agregarPredicadoSuceso(String atributo) {

		ParameterExpression<Suceso> p = crit_builder.parameter(Suceso.class, atributo);
		criteria.add(crit_builder.equal(root_alarmas.get(atributo), p));
	}

	private void agregarParametroDesde(String fecha_usada, Calendar calendarDesde) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		criteria.add(crit_builder.greaterThanOrEqualTo(pathParaInterpretarCalendar, calendarDesde));
	}

	private void agregarParametroHasta(String fecha_usada, Calendar calendarHasta) {

		Path<Calendar> pathParaInterpretarCalendar = root_alarmas.get(fecha_usada);
		criteria.add(crit_builder.lessThanOrEqualTo(pathParaInterpretarCalendar, calendarHasta));
	}

	@Override
	public void liberarObjetos() {

		EMFSingleton.getInstanciaEM().clear();
		System.gc();
	}
}