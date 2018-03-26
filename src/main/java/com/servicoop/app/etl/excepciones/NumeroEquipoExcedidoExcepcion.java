package main.java.com.servicoop.app.etl.excepciones;

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
 * soy de utilidad cuando se asigna un numero de equipo para la clase
 * comunes.modelo.EquipoEnSitio, que es mayor al esperado para ese TipoDeEquipo.
 * pueden consultarse los numeros esperados para cada subclases que hereda de
 * TipoDeEquipo, en las implementaciones que ellos hacen de getNumero(...)
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, soy lanzado al momento que se descubre mi excepcion
 * 
 * LO QUE CONOZCO, mi descripcion interna que ser� de utilidad para debug en
 * log.txt o por pantalla
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es la herramienta de logs (log4j)
 * 
 * COMO INTERACTUO CON MI COLABORADOR, este atrapa mi instancia, pide mi
 * descripcion interna y la saca conjuntamente por pantalla y archivo log.txt.
 * posteriormente se puede usar esta info para debug.
 *
 * @author hdonato
 * 
 */
public class NumeroEquipoExcedidoExcepcion extends Exception {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	static final long serialVersionUID = 1;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public NumeroEquipoExcedidoExcepcion(String descripcion_excepcion) {
		super("numero excedido -> " + descripcion_excepcion);
	}
}
