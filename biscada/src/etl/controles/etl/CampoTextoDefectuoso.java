/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.etl;

import java.util.ArrayList;
import java.util.List;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * gestiona las alarmas rechazadas por el proceso etl. una unica instancia de
 * este objeto es compartida por las tres fabricas que obtienen sus objetos
 * analizando el campo "texto" del archivo dbf
 * 
 * @author hugo
 * 
 */
public class CampoTextoDefectuoso {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private List<String> textos_rechazados;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CampoTextoDefectuoso() {
		textos_rechazados = new ArrayList<String>();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public synchronized void agregarNuevaAlarma(String clase_propietaria, String motivo, String campo_texto) {

		String mensaje = clase_propietaria + ", " + motivo + " -> " + campo_texto;

		if (!yaExiste(mensaje))
			textos_rechazados.add(mensaje);
	}

	public boolean estaVacia() {
		return textos_rechazados.isEmpty();
	}

	@Override
	public String toString() {

		String retorno = "\n";

		for (String alarma_actual : textos_rechazados)
			retorno += alarma_actual + "\n";
		return retorno;
	}

	private boolean yaExiste(String campo_texto) {

		return textos_rechazados.contains(campo_texto);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}