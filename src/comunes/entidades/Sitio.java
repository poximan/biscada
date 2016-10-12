/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import comunes.fabrica.TipoDatoFabricable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un POJO de Sitios
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, doy representacion en Objetos del Sitio donde ocurrio la alarma
 * 
 * LO QUE CONOZCO, mi identidifcador interno, un String que dice quien soy.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es etl.controles.ETL1Transformar
 * 
 * COMO INTERACTUO CON MI COLABORADOR, el usa una fabrica abstracta para obtener
 * el Sitio concreto donde ocurrio la alarma que se esta procesando. cuando lo
 * encuentra crea una instancia con uno de mis subtipos, alojados en
 * etl.partes_alarma.sitios
 * 
 * @author hdonato
 *
 */
@Entity
@Table(name = "sitio")
@NamedQueries({ @NamedQuery(name = "Sitio.buscTodos", query = "SELECT tabla FROM Sitio tabla"),
		@NamedQuery(name = "Sitio.buscDescripcion", query = "SELECT tabla FROM Sitio tabla WHERE tabla.descripcion = :descripcion"), })
public class Sitio implements TipoDatoFabricable, FamiliaPorDefecto {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_SITIO")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_sitio")
	@TableGenerator(name = "gen_sitio", initialValue = 1, allocationSize = 1)
	private Integer id;

	private String descripcion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Sitio() {
	}

	public Sitio(String descripcion) {
		this.descripcion = descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Sitio))
			return false;

		Sitio sitio_a_comparar = (Sitio) object;
		return this.descripcion.equals(sitio_a_comparar.getDescripcion());
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public Familia getFamiliaPorDefecto() {
		return null;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/**
	 * Los objetos que son iguales deben tener el mismo codigo hash. Esto no
	 * implica Objetos desiguales tengan diferente hash, como asi tampoco que
	 * dos ojetos con el mismo codigo hash deben ser iguales.
	 * 
	 * +) Siempre que se implemente equals, se debe implementar hashCode. +)
	 * �hashcode no es clave!, pueden suceder colisiones. +) No usar en
	 * aplicaciones distribuidas.
	 * 
	 * En general, para un uso correcto de colecciones, los objetos que
	 * iteractuen con ellas deben implementar hascode.
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
	/* SET'S ....................................... */
	/* ............................................. */

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {

		return descripcion;
	}
}
