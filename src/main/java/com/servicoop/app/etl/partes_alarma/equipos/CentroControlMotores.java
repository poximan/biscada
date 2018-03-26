/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.partes_alarma.equipos;

import main.java.com.servicoop.app.comunes.entidades.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, la implementacion concreta de la super clase
 * comunes.modelo.TipoDeEquipo
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, expongo una instancia de mi si la fabrica concreta
 * comunes.fabrica.TipoDeEquipoFactory, concluye que mi expresion regular
 * estatica (antes de la instancia) es un buen definidor del discriminante que
 * est� leyendo.
 *
 * LO QUE CONOZCO, la expresion regular que me define, y mi descripcion para
 * mostrar en componentes visuales
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL,
 *
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hdonato
 *
 */
public class CentroControlMotores extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "FALTA (DE )?(FASE|RED)"//
			+ "|FALTA  ?220V"//
			+ "|RFF"//
			+ "|BARRA SIN TENSION"//
			+ "|FALLA DE AC"//
			+ "|PARADA\\s(DE\\s)?EMERGENCIA"//
			+ "|PARADA EMG EN"//
			+ "|PULSADOR DE EMERGENCIA"//
			+ "|SELECTORA ESTADO"//
			+ "|SELECTORA TAB.-RTU"//
			+ "|INTERRUPTOR (PPAL|GENERAL)"//
			+ "|INTERRUPTOR LADO LINEA"//
			+ "|Selecci.n CONTROL AUTOM.TICO"//
			+ "|REPOSICION DE ALARMAS"//
			+ "|RECTIFICADOR"//
			+ "|Selecci.n CONTROL MANUAL"//
			+ "|FALLA ALIMENTACI.N DE RED"//
			+ "|SGCA"//
			+ "|Alarma CA"//
			+ "|Falla cargador"//
			+ "|ACEPTACION DE ALARMAS TPyM"//
			+ "|Falta de fase"//
			+ "|FALLA ALIM. 220";

	private static String descripcion = "CCM del sitio";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public static String getExpresion_regular() {
		return expresion_regular;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public CentroControlMotores() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Integer getNumero(String discriminante) {
		return new Integer(1);
	}

	@Override
	public String toString() {
		return descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
