/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@Entity
@Table(name = "familia")
@NamedQueries({//
		@NamedQuery(name = "Familia.buscTodos", query = "SELECT tabla FROM Familia tabla"),
		@NamedQuery(name = "Familia.buscDescripcion", query = "SELECT tabla FROM Familia tabla WHERE tabla.descripcion = :descripcion"),
		@NamedQuery(name = "Familia.consulta2", query = "SELECT tabla FROM Familia tabla"), })
public class Familia {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_FAMILIA")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_familia")
	@TableGenerator(name = "gen_familia", initialValue = 1, allocationSize = 1)
	private Integer id;

	private String descripcion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Familia() {
	}

	public Familia(String descripcion) {
		this.descripcion = descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Familia)) {
			return false;
		}
		Familia familia_a_comparar = (Familia) object;
		if (this.descripcion.compareTo(familia_a_comparar.getDescripcion()) == 0)
			return true;

		return false;
	}

	public String getDescripcion() {
		return descripcion;
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

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return descripcion;
	}
}
