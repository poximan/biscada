/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_etl;

import java.util.regex.Matcher;

import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * implementada por la clase responsable de dividir en partes identificables a
 * un campo de texto. este campo no es cualquiera, sino que se trata
 * exlusivamente del denominado "texto" en el archivo dbf. alli conviven tres
 * datos diferentes que servir�n de guia para instanciar tres objetos
 * diferentes
 * 
 * @author hugo
 * 
 */
public interface TextoDiferenciable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * cada clase interesada en este campo pretende una informacion diferente.
	 * visto que todo esta contenido en el mismo campo texto, es necesario
	 * identidicar divisiones internas.
	 * 
	 * @param campo_texto
	 *            extraido directamente del archivo dbf, en su interiro conviven
	 *            tres datos bien diferenciables: sitio, tipo de alarma y equipo
	 *            asociado
	 */
	public void prepararExpresionRegular(String campo_texto);

	public void crearPropietario(String campo_texto, Matcher matcher)
			throws CampoTextoNoEncontradoExcepcion, CampoTextoAmbiguoExcepcion;

	public Object getPropietario();
}
