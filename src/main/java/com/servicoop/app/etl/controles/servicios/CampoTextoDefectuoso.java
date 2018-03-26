/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.controles.servicios;

import java.util.ArrayList;
import java.util.List;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un repositorio de alarmas que no superaron alguna de las
 * fabricas abstractas (metodo
 * etl.controles.ETL1Transformar.transformarAlarmas(...)) durante la segunda
 * fase del proceso ETL (transformacion).
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, acompa�o el proceso descrito en la clase
 * etl.controles.ETL1Transformar.
 * 
 * si durante el procesamiento del campo TEXT del objeto ArchAlarma que se est�
 * analizando, se detecta algun tipo de anomalia (no identifica un determinado
 * campo, o es ambiguo) me notifica. en otro caso no participo del ETL.
 * 
 * LO QUE CONOZCO, la lista de textos que no pudieron concretarse en alguno de
 * los subtipos que ofrecen las fabricas concretas (descritas en el paquete
 * comunes.fabrica.*)
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, comunes.fabrica.FabricaAbstracta
 * 
 * COMO INTERACTUO CON MI COLABORADOR, me provee de un mecanismo para que las
 * fabricas concretas puedan localizarme llamando a su super clase, en donde
 * piden mi instancia.
 *
 * @author hdonato
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

	public void agregarNuevaAlarma(String clase_propietaria, String motivo) {

		String mensaje = clase_propietaria + ", " + motivo;

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
}