/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * util para optimizar insercion o eliminacion de archivos. los objetos que lo implementen manejaran de forma dinamica
 * el uso o no de transacciones a nivel de lote o de archivo.
 * 
 * @author hugo
 */
public abstract class Transaccion {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void limpiarCache() {
		EMFSingleton.getInstanciaEM().clear();
	}

	public void enviarCacheHaciaBD() {
		EMFSingleton.getInstanciaEM().flush();
	}

	public abstract void beginBULK();

	public abstract void commitBULK();

	public abstract void beginArchivo();

	public abstract void commitArchivo();
}
