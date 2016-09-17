/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.CampoTextoDefectuoso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * parte de la implementacion del patron AbstractFactory para resolver la
 * instancia de subclases de objetos de una clase variable.
 * 
 * @author hdonato
 *
 */
public abstract class FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private CampoTextoDefectuoso alarma_rechazada;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaAbstracta(CampoTextoDefectuoso alarma_rechazada) {
		this.alarma_rechazada = alarma_rechazada;
	}
	
	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public CampoTextoDefectuoso getAlarma_rechazada() {
		return alarma_rechazada;
	}
	
	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public abstract TipoDatoFabricable getInstancia(String discriminante);
}