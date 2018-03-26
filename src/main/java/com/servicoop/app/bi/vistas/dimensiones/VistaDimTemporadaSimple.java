/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.dimensiones;

import java.util.List;

import main.java.com.servicoop.app.bi.controles.servicios.dimensiones.ServDimSitio;
import main.java.com.servicoop.app.bi.controles.servicios.dimensiones.ServDimTemporada;
import main.java.com.servicoop.app.bi.vistas.eventos.EventoDimTemporada;
import main.java.com.servicoop.app.comunes.entidades.Alarma;

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
public class VistaDimTemporadaSimple extends VistaDimAbstractSimple {

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

	public VistaDimTemporadaSimple(List<Alarma> consultas) {

		super(new ServDimTemporada(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTemporada(this));
	}
}
