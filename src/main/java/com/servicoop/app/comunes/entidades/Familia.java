/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import main.java.com.servicoop.app.comunes.fabrica.TipoDatoFabricable;

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, un POJO de Familia de alarmas, como POTABLE, CLOACAL o REUSO
 * entre otras.
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, doy representacion en Objetos de una Familia de alarmas.
 *
 * LO QUE CONOZCO, mi identidifcador interno, un String que dice quien soy.
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, es etl.controles.ETL1Transformar
 *
 * COMO INTERACTUO CON MI COLABORADOR, el usa una fabrica abstracta para obtener
 * la familia concreta de la alarma que se esta procesando. cuando la encuentra
 * crea una instancia de uno de mis subtipos, alojados en
 * etl.partes_alarma.familias
 *
 * @author hdonato
 *
 */
/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@Entity
@Table(name = "familia")
@NamedQueries({ @NamedQuery(name = "Familia.buscTodos", query = "SELECT tabla FROM Familia tabla"),
		@NamedQuery(name = "Familia.buscDescripcion", query = "SELECT tabla FROM Familia tabla WHERE tabla.descripcion = :descripcion"), })
public class Familia implements TipoDatoFabricable {

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

		if (!(object instanceof Familia))
			return false;

		Familia familia_a_comparar = (Familia) object;
		return this.descripcion.equals(familia_a_comparar.getDescripcion());
	}

	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Los objetos que son iguales deben tener el mismo codigo hash. Esto no implica
	 * Objetos desiguales tengan diferente hash, como asi tampoco que dos ojetos con
	 * el mismo codigo hash deben ser iguales.
	 *
	 * +) Siempre que se implemente equals, se debe implementar hashCode. +)
	 * �hashcode no es clave!, pueden suceder colisiones. +) No usar en aplicaciones
	 * distribuidas.
	 *
	 * En general, para un uso correcto de colecciones, los objetos que iteractuen
	 * con ellas deben implementar hascode.
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
