/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimSitio;
import bi.controles.servicios.dimensiones.ServDimTiempoDespeje;
import bi.vistas.eventos.EventoDimTiempoDespeje;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO la vista del segundo nivel de evaluacion
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO extender de la clase VistaDimAbstractSimple que contiene las
 * propiedas de la vista
 * 
 * LO QUE CONOZCO
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, EventoConsultaSimple
 * 
 * COMO INTERACTUO CON MI COLABORADOR, cargando los componentes graficos
 *
 */
public class VistaDimTiempoDespejeSimple extends VistaDimAbstractSimple {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public VistaDimTiempoDespejeSimple(List<Alarma> consultas) {

		super(new ServDimTiempoDespeje(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTiempoDespeje(this));
	}
}