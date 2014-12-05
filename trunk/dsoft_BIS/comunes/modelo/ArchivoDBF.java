/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.PrivateOwned;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@Entity
@Table(name = "archivo_dbf")
@NamedQueries({
		@NamedQuery(name = "ArchivoDBF.buscTodos", query = "SELECT tabla FROM ArchivoDBF tabla"),
		@NamedQuery(name = "ArchivoDBF.buscRuta", query = "SELECT tabla FROM ArchivoDBF tabla WHERE tabla.ruta = :ruta"), })
public class ArchivoDBF implements Comparable<ArchivoDBF> {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_ARCHIVO_DBF")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_archivo_dbf")
	@TableGenerator(name = "gen_archivo_dbf", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "RUTA", nullable = false)
	private String ruta;

	@Column(name = "COMIENZO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar comienzo; // instante en que comenzo el tratamiento del archivo

	@Column(name = "FIN", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fin; // instante en que finalizo el tratamiento del archivo

	@Column(name = "VALIDO")
	private Boolean valido;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "archivo_propietario")
	@PrivateOwned
	private Collection<Alarma> lista_alarmas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ArchivoDBF() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int compareTo(ArchivoDBF entidad) {

		return this.getRuta().compareTo(entidad.getRuta());
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof ArchivoDBF)) {
			return false;
		}
		ArchivoDBF archivo_a_comparar = (ArchivoDBF) object;
		if (this.ruta.compareTo(archivo_a_comparar.getRuta()) == 0)
			return true;

		return false;
	}

	/**
	 * Los objetos que son iguales deben tener el mismo codigo hash. Esto no implica Objetos desiguales tengan diferente
	 * hash, como asi tampoco que dos ojetos con el mismo codigo hash deben ser iguales.
	 * 
	 * +) Siempre que se implemente equals, se debe implementar hashCode. +) ¡hashcode no es clave!, pueden suceder
	 * colisiones. +) No usar en aplicaciones distribuidas.
	 * 
	 * En general, para un uso correcto de colecciones, los objetos que iteractuen con ellas deben implementar hascode.
	 * 
	 * @return el numero hash asociado al objeto.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return ruta.substring(ruta.lastIndexOf("\\") + 1);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Calendar getComienzo() {
		return comienzo;
	}

	public Calendar getFin() {
		return fin;
	}

	public Integer getId() {
		return id;
	}

	public String getRuta() {
		return ruta;
	}

	public Boolean getValido() {
		return valido;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setId(Integer id) {
		this.id = id;
	}

	public void setComienzo(Calendar comienzo) {
		this.comienzo = comienzo;
	}

	public void setFin(Calendar fin) {
		this.fin = fin;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}
}
