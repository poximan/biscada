/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.vistas;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz implementada unicamente por componentes visuales
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, asocio los subcomponentes de un componente visual a un manejador
 * de eventos
 * 
 * LO QUE CONOZCO, los subcomponentes que deben darse de alta y el responsable
 * de resolver los eventos
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, el responsable de resolver los eventos
 * 
 * bi.vistas.eventos.EventoComponenteMenuDimension
 * bi.vistas.eventos.EventoComponenteConsulta.EventoComponenteConsulta
 * bi.vistas.eventos.EventoConsultaSimple
 * bi.vistas.eventos.EventoConsultaCompuesta etl.vistas.EventoETL
 * propiedades.eventos.EventoPropiedades
 * 
 * COMO INTERACTUO CON MI COLABORADOR, creo una instancia del manejador de
 * eventos y se la agrego al ActionListener de todos los componentes que deseo
 * estar notificado
 * 
 * @author hdonato
 *
 */
public interface EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void configEventos();
}
