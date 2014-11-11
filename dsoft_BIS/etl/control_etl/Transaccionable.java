/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * util para optimizar insercion o eliminacion de archivos. los objetos que lo implementen manejaran de forma dinamica
 * el uso o no de tranacciones a nivel de lote o de archivo.
 * 
 * @author hugo
 */
public interface Transaccionable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void beginBULK();

	public void commitBULK();

	public void limpiarCacheBULK();

	public void beginArchivo();

	public void commitArchivo();
}
