/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.vistas;

import javax.swing.JList;

import comunes.entidades.ArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una especializacion de javax.swing.JList para manejo de
 * comunes.entidades.ArchivoDBF
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, a traves de mi como especializacion de JList, quien pregunta
 * usando instanceof sabe si se trata de mi o de un par mio. si no existieran mi
 * par y yo, las preguntas por JList siempre darían verdadero
 * 
 * esto es de utilidad en el metodo etl.vistas.EventoETL.valueChanged(...). alli
 * se actualizan los contadores al pie de cada lista (lista de archivos
 * disponibles y procesados)
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es etl.vistas.JListDisponible
 * 
 * COMO INTERACTUO CON MI COLABORADOR, no lo hago directamente, pero necesito
 * que exista para que la logica de comparacion implementada en
 * etl.vistas.EventoETL.valueChanged(...) sepa a que especializacion de JList
 * refiere el objeto que se esta comparando
 *
 * @author hdonato
 * 
 */
public class JListProcesado extends JList<ArchivoDBF> {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	// para uso de reflexion durante el manejo de eventos generados por la lista
	public JListProcesado() {
	}

	public JListProcesado(ListModelOrdenada model_disponibles) {
		super(model_disponibles);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
