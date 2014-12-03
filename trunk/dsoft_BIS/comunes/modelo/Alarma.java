/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "alarma")
public final class Alarma implements Comparable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_ALARMA")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_alarma")
	@TableGenerator(name = "gen_alarma", initialValue = 1, allocationSize = 1)
	private Integer id;

	@ManyToOne(optional = false /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_ARCHIVO_DBF", referencedColumnName = "ID_ARCHIVO_DBF", nullable = false)
	private ArchivoDBF archivo_propietario;

	@Column(name = "FECHA_INICIO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Index
	private Calendar fecha_inicio; // ex inicio_segundo + inicio_milisegundo

	@Column(name = "FECHA_ACK", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Index
	private Calendar fecha_ack; // ex ack_segundo

	@Column(name = "FECHA_FIN", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Index
	private Calendar fecha_finalizacion; // ex fin_segundo + fin_milisegundo

	@Column(name = "ACK_USUARIO", nullable = true)
	private String ack_usuario; // ex ack_name

	@Column(name = "SEVERIDAD", nullable = true)
	private Integer severidad;

	@Column(name = "CLASE", nullable = true)
	private Long clase;

	@Column(name = "ZONA", nullable = true)
	private Integer zona;

	@Column(name = "ATRIBUTO", nullable = true)
	private Integer atributo;

	@Column(name = "IDENTIFICACION", nullable = true)
	private String identificacion;

	@ManyToOne(optional = false /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID_FAMILIA", nullable = false)
	@Index
	private Familia familia; // ex nombre

	@ManyToOne(optional = false /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_SITIO", referencedColumnName = "ID_SITIO", nullable = false)
	@Index
	private Sitio sitio; // ex primera parte de texto

	@ManyToOne(optional = true /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_EQUIPO_EN_SITIO", referencedColumnName = "ID_EQUIPO_EN_SITIO", nullable = true)
	@Index
	private EquipoEnSitio equipo_en_sitio; // ex segunda parte de texto

	@ManyToOne(optional = false /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_SUCESO", referencedColumnName = "ID_SUCESO", nullable = false)
	@Index
	private Suceso suceso; // ex tercera parte de texto

	@Column(name = "ID_ESTACION", nullable = true)
	private int id_estacion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Alarma() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@PostLoad
	protected void repair() {

		if (ack_usuario != null)
			ack_usuario = ack_usuario.intern();
		if (identificacion != null)
			identificacion = identificacion.intern();
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Alarma)) {
			return false;
		}
		Alarma alarma_actual = (Alarma) object;
		if (this.id.compareTo(alarma_actual.id) == 0)
			return true;

		return false;
	}

	@Override
	public int compareTo(Object entidad) {

		Alarma alarma_actual = (Alarma) entidad;
		return this.fecha_inicio.compareTo(alarma_actual.getFecha_inicio());
	}

	@Override
	public String toString() {

		return "\n" + //
				"Id BD: " + id + "\n"
				+ //
				"Ini: " + ((fecha_inicio != null) ? fecha_inicio.getTime().toString() : "desconocida") + "\n"
				+ //
				"Fin: " + ((fecha_finalizacion != null) ? fecha_finalizacion.getTime().toString() : "desconocida")
				+ "\n" + //
				"Ack: " + ((fecha_ack != null) ? fecha_ack.getTime().toString() : "desconocida") + "\n" + //
				"Usuario: " + ack_usuario + "\n" + //
				"Severidad: " + severidad + "\n" + //
				"Clase: " + clase + "\n" + //
				"Zona: " + zona + "\n" + //
				"Atributo: " + atributo + "\n" + //
				"Identificacion: " + identificacion + "\n" + //
				"Familia: " + familia.toString() + "\n" + //
				"Sitio: " + sitio.toString() + "\n" + //
				"Equipo: " + ((equipo_en_sitio != null) ? equipo_en_sitio.toString() : "sin equipo") + "\n" + //
				"Suceso: " + suceso.toString() + "\n" + //
				"Id estacion: " + id_estacion + "\n" //
		;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public ArchivoDBF getArchivo_propietario() {
		return archivo_propietario;
	}

	public Calendar getFecha_inicio() {
		return fecha_inicio;
	}

	public Calendar getFecha_ack() {
		return fecha_ack;
	}

	public Calendar getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public Familia getFamilia() {
		return familia;
	}

	public Sitio getSitio() {
		return sitio;
	}

	public EquipoEnSitio getEquipo_en_sitio() {
		return equipo_en_sitio;
	}

	public Suceso getSuceso() {
		return suceso;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setArchivo_propietario(ArchivoDBF archivo_propietario) {
		this.archivo_propietario = archivo_propietario;
	}

	public void setFecha_inicio(Calendar fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public void setFecha_ack(Calendar fecha_ack) {
		this.fecha_ack = fecha_ack;
	}

	public void setFecha_finalizacion(Calendar fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}

	public void setAck_usuario(String ack_usuario) {
		this.ack_usuario = ack_usuario;
	}

	public void setSeveridad(Integer severidad) {
		this.severidad = severidad;
	}

	public void setClase(Long clase) {
		this.clase = clase;
	}

	public void setZona(Integer zona) {
		this.zona = zona;
	}

	public void setAtributo(Integer atributo) {
		this.atributo = atributo;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}

	public void setEquipo_en_sitio(EquipoEnSitio equipo_en_sitio) {
		this.equipo_en_sitio = equipo_en_sitio;
	}

	public void setSuceso(Suceso suceso) {
		this.suceso = suceso;
	}

	public void setId_estacion(int id_estacion) {
		this.id_estacion = id_estacion;
	}
}
