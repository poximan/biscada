package main.java.com.servicoop.app.comunes.fabrica;

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una interfaz para facilitar el polimorfismo de tipos obtenidos
 * desde fabricas
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO soy la interfaz polimorfica de las clases Familia, Sitio, Suceso,
 * entre otras
 * 
 * LO QUE CONOZCO todas las clases obtenidas desde fabricas
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL ES las fabricas concretas que piden nuevas intancias
 * de las clases que conocen.
 * 
 * COMO INTERACTUO CON MI COLABORADOR brindo una interfaz polimorfica para que,
 * sin importar si se trata de una fabrica de familias, sitios u otra cosa,
 * todas tengan la misma interfaz
 * 
 * @author hdonato
 *
 */
public interface TipoDatoFabricable {
}
