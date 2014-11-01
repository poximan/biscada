/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dbf;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/**
 * Participa en la etapa mas primitiva de recuperacion de alarmas. cuando el ETL comienza a leer un archivo, cada fila
 * es levantada como un strem de texto; ese strem es dividido en secciones (seccion 1 entre indice [0-3], seccion 2
 * [2-5] .... seccion n [m-ultimo indice]). Luego, una instancia de esta clase recepciona cada seccion en un atributo
 * diferente.
 * 
 * @author hugo
 * 
 */
public class ArchAlarma {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String inicio_segundo; // 10
	private String inicio_milisegundo; // 3
	private String fin_segundo; // 10
	private String fin_milisegundo; // 3
	private String ack_segundo; // 10
	private String ack_nombre; // 9
	private String severidad; // 5
	private String clase; // 10
	private String zona; // 5
	private String atributo; // 10
	private String identificacion; // 8
	private String nombre; // 16
	private String texto; // 61
	private String id_estacion; // 3

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ArchAlarma() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public String getAck_nombre() {
		return ack_nombre;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public String getAck_segundo() {
		return ack_segundo;
	}

	public String getAtributo() {
		return atributo;
	}

	public String getClase() {
		return clase;
	}

	public String getFin_milisegundo() {
		return fin_milisegundo;
	}

	public String getFin_segundo() {
		return fin_segundo;
	}

	public String getId_estacion() {
		return id_estacion;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public String getInicio_milisegundo() {
		return inicio_milisegundo;
	}

	public String getInicio_segundo() {
		return inicio_segundo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getSeveridad() {
		return severidad;
	}

	public String getTexto() {
		return texto;
	}

	public String getZona() {
		return zona;
	}

	public void setAck_nombre(String ack_nombre) {
		this.ack_nombre = ack_nombre;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setAck_segundo(String ack_segundo) {
		this.ack_segundo = ack_segundo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public void setFin_milisegundo(String fin_milisegundo) {
		this.fin_milisegundo = fin_milisegundo;
	}

	public void setFin_segundo(String fin_segundo) {
		this.fin_segundo = fin_segundo;
	}

	public void setId_estacion(String id_estacion) {
		this.id_estacion = id_estacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setInicio_milisegundo(String inicio_milisegundo) {
		this.inicio_milisegundo = inicio_milisegundo;
	}

	public void setInicio_segundo(String inicio_segundo) {
		this.inicio_segundo = inicio_segundo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSeveridad(String severidad) {
		this.severidad = severidad;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	@Override
	public String toString() {

		return "\n" + //
				"Ini -> " + "seg: " + inicio_segundo + " / " + "mili: " + inicio_milisegundo + "\n" + //
				"Fin -> " + "seg: " + fin_segundo + " / " + "mili: " + fin_milisegundo + "\n" + //
				"Ack -> " + "seg: " + ack_segundo + " / " + "nombre: " + ack_nombre + "\n" + //
				"Severidad: " + severidad + "\n" + //
				"Clase: " + clase + "\n" + //
				"Zona: " + zona + "\n" + //
				"Atributo: " + atributo + "\n" + //
				"Identificacion: " + identificacion + "\n" + //
				"Nombre: " + nombre + "\n" + //
				"Texto: " + texto + "\n" + //
				"Id estacion: " + id_estacion + "\n"//
		;
	}
}
