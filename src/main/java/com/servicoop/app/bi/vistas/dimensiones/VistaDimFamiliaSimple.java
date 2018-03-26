/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.dimensiones;

import java.util.List;

import main.java.com.servicoop.app.bi.controles.servicios.dimensiones.ServDimFamilia;
import main.java.com.servicoop.app.bi.controles.servicios.dimensiones.ServDimSitio;
import main.java.com.servicoop.app.bi.vistas.eventos.EventoDimFamilia;
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
public class VistaDimFamiliaSimple extends VistaDimAbstractSimple {

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

	public VistaDimFamiliaSimple(List<Alarma> consultas) {

		super(new ServDimFamilia(), new ServDimSitio(), consultas);
		configEventos(new EventoDimFamilia(this));
	}
}
