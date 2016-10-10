package etl.excepciones;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, la especializacion de una excepcion generica
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, mi proposito es enviar un mensaje (interpretar no como algo
 * inesperado, una excepcion, sino como un mensaje, algo esperado) mas abajo en
 * la pila de contextos de ejecucion, para decirle a
 * etl.controles.servicios.ServExpresionesRegulares que alternativa debe usar.
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, etl.controles.servicios.ServExpresionesRegulares
 * 
 * COMO INTERACTUO CON MI COLABORADOR, cuando se detecta una ambigüedad de
 * instancias (una misma expresion regular resulta en mas de una coincidencia)
 * soy usado para notificar que la primer instancia es preferible a la segunda
 *
 * @author hdonato
 * 
 */
public class UsarPrimerOcurrenciaExcepcion extends Exception {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	static final long serialVersionUID = 1;
}
