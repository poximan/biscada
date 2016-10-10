package comunes.entidades;

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz que deben implementar los sitios concretos en
 * etl.partes_alarma.sitios por herencia desde comunes.modelo.Sitio
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, devuelvo la familia de alarmas a la que pertenece un Sitio
 * 
 * LO QUE CONOZCO, a traves de las implementaciones en sitios concretos conozco
 * la Familia de alarmas a la que pertenece.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es etl.controles.ETL1Transformar en el metodo
 * "usarAbstractFactory(...)"
 * 
 * COMO INTERACTUO CON MI COLABORADOR, a veces la fabrica abstracta de Familias
 * no logra dar con la Familia si esta no fue agregada en el campo TEXT del dbf.
 * en esos casos, puedo preguntarle al Sitio a que Familia pertenece
 * 
 * @author hdonato
 *
 */
public interface FamiliaPorDefecto {

	public abstract Familia getFamiliaPorDefecto();
}
