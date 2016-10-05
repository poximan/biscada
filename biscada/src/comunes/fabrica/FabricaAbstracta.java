/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.servicios.CampoTextoDefectuoso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO la abstraccion de todas las fabricas concretas del sistema
 * 
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO permito que puedan tratar a las fabricas mediante polimorfismo
 * 
 * LO QUE CONOZCO todas las alarmas dbf que no pudieron convertirse a objeto
 * 
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL ES las instancias concretas que heredan de mi
 * 
 * COMO INTERACTUO CON MI COLABORADOR a traves del metodo getInstancia() cuya
 * implementacion no conozco, delegando a las clases concretas.
 * 
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