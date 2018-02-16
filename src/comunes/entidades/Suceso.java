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
 * YO REPRESENTO, un POJO de Sucesos
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, doy representacion en Objetos del Suceso ocurrido
 * 
 * LO QUE CONOZCO, mi identidifcador interno, un String que dice quien soy.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es etl.controles.ETL1Transformar
 * 
 * COMO INTERACTUO CON MI COLABORADOR, el usa una fabrica abstracta para obtener
 * el Suceso del que se trata la alarma que se esta procesando. cuando lo
 * encuentra crea una instancia con uno de mis subtipos, alojados en
 * etl.partes_alarma.sucesos
 * 
 * se habla de suceso y no alarma porque alarma es un dato mas complejo que
 * entre sus partes posee la descripcion de lo que paso, el suceso
 * 
 * @author hdonato
 *
 */
@Entity
@Table(name = "suceso")
@NamedQueries({ @NamedQuery(name = "Suceso.buscTodos", query = "SELECT tabla FROM Suceso tabla"),
		@NamedQuery(name = "Suceso.buscDescripcion", query = "SELECT tabla FROM Suceso tabla WHERE tabla.descripcion = :descripcion"), })
public class Suceso implements TipoDatoFabricable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_SUCESO")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_suceso")
	@TableGenerator(name = "gen_suceso", initialValue = 1, allocationSize = 1)
	private Integer id;

	private String descripcion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Suceso() {
	}

	public Suceso(String descripcion) {
		this.descripcion = descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Suceso))
			return false;

		Suceso suceso_a_comparar = (Suceso) object;
		return this.descripcion.equals(suceso_a_comparar.getDescripcion());
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