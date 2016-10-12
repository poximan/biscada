/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimSitio;
import bi.vistas.eventos.EventoDimSitio;
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
public class VistaDimSitioSimple extends VistaDimAbstractSimple {

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

	public VistaDimSitioSimple(List<Alarma> consultas) {

		super(new ServDimSitio(), consultas);
		configEventos(new EventoDimSitio(this));
	}
}