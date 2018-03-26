package main.java.com.servicoop.app.etl.partes_alarma.equipos;

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz que deben implementar todos los sub tipos de
 * TipoDeEquipo concretos
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, soy un metodo de acceso unificado para todos los que implementan
 * 
 * LO QUE CONOZCO, a traves de las implementaciones en equipos concretos conozco
 * el numero que debe asignarse para un comunes.modelo.EquipoEnSitio
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es comunes.fabrica.EquipoEnSitioFactory
 * 
 * COMO INTERACTUO CON MI COLABORADOR, luego de definir el TipoDeEquipo es
 * necesario saber que numero de instancia de Equipo corresponde, segun el Sitio
 * que se trate y la descripcion de la alarma que se est� procesando.
 * 
 * @author hdonato
 *
 */
public interface NumeroEquipoIdentificable {

	public Integer getNumero(String discriminante);
}
