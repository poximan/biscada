/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.modelo;

import java.util.Calendar;

import javax.swing.JRadioButton;

import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * envuelve todos los datos de una nueva consulta en un unico objeto de intercambio.
 * facilita el pasaje de parametros.
 * 
 * @author donat
 *
 */
public class DatosConsulta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Calendar calendar_desde;
	private Boolean desde_inicio, desde_ack, desde_fin;

	private Calendar calendar_hasta;
	private Boolean hasta_inicio, hasta_ack, hasta_fin;

	private Familia familia_elegida;
	private Sitio sitio_elegido;
	private TipoDeEquipo tipo_de_equipo_elegido;
	private Suceso suceso_elegido;

	private Integer duracion_minima, duracion_maxima;
	private boolean incluir_ini_incompleta, incluir_ack_incompleta, incluir_fin_incompleta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public DatosConsulta(//
			Calendar calendar_desde, //
			JRadioButton rbtn_desde_inicio, JRadioButton rbtn_desde_ack, JRadioButton rbtn_desde_fin,
			Calendar calendar_hasta, //
			JRadioButton rbtn_hasta_inicio, JRadioButton rbtn_hasta_ack, JRadioButton rbtn_hasta_fin,

			Familia familia_elegida, Sitio sitio_elegido, TipoDeEquipo tipo_de_equipo_elegido, Suceso suceso_elegido,
			Integer duracion_minima, Integer duracion_maxima, //
			boolean incluir_ini_incompleta, boolean incluir_ack_incompleta, boolean incluir_fin_incompleta) {

		this.calendar_desde = calendar_desde;
		this.desde_inicio = rbtn_desde_inicio.isSelected();
		this.desde_ack = rbtn_desde_ack.isSelected();
		this.desde_fin = rbtn_desde_fin.isSelected();

		this.calendar_hasta = calendar_hasta;
		this.hasta_inicio = rbtn_hasta_inicio.isSelected();
		this.hasta_ack = rbtn_hasta_ack.isSelected();
		this.hasta_fin = rbtn_hasta_fin.isSelected();

		this.familia_elegida = familia_elegida;
		this.sitio_elegido = sitio_elegido;
		this.tipo_de_equipo_elegido = tipo_de_equipo_elegido;
		this.suceso_elegido = suceso_elegido;

		this.duracion_minima = duracion_minima;
		this.duracion_maxima = duracion_maxima;

		this.incluir_ini_incompleta = incluir_ini_incompleta;
		this.incluir_ack_incompleta = incluir_ack_incompleta;
		this.incluir_fin_incompleta = incluir_fin_incompleta;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* GET's ....................................... */
	/* ............................................. */

	public Calendar getCalendar_desde() {
		return calendar_desde;
	}

	public Boolean getDesde_inicio() {
		return desde_inicio;
	}

	public Boolean getDesde_ack() {
		return desde_ack;
	}

	public Boolean getDesde_fin() {
		return desde_fin;
	}

	public Calendar getCalendar_hasta() {
		return calendar_hasta;
	}

	public Boolean getHasta_inicio() {
		return hasta_inicio;
	}

	public Boolean getHasta_ack() {
		return hasta_ack;
	}

	public Boolean getHasta_fin() {
		return hasta_fin;
	}

	public Familia getFamilia_elegida() {
		return familia_elegida;
	}

	public Sitio getSitio_elegido() {
		return sitio_elegido;
	}

	public TipoDeEquipo getTipo_de_equipo_elegido() {
		return tipo_de_equipo_elegido;
	}

	public Suceso getSuceso_elegido() {
		return suceso_elegido;
	}

	public Integer getDuracion_minima() {
		return duracion_minima;
	}

	public Integer getDuracion_maxima() {
		return duracion_maxima;
	}

	public boolean isIncluir_ini_incompleta() {
		return incluir_ini_incompleta;
	}

	public boolean isIncluir_ack_incompleta() {
		return incluir_ack_incompleta;
	}

	public boolean isIncluir_fin_incompleta() {
		return incluir_fin_incompleta;
	}
}
