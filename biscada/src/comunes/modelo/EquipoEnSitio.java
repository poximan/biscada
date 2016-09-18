/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import comunes.fabrica.TipoDatoFabricable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@Entity
@Table(name = "equipo_en_sitio", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_SITIO", "ID_TIPO_DE_EQUIPO",
		"NUMERO_EQUIPO" }))
@NamedQueries({ @NamedQuery(name = "EquipoEnSitio.buscTodos", query = "SELECT tabla FROM EquipoEnSitio tabla"), })
public final class EquipoEnSitio extends TipoDatoFabricable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	@Column(name = "ID_EQUIPO_EN_SITIO")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_equipo_en_sitio")
	@TableGenerator(name = "gen_equipo_en_sitio", initialValue = 1, allocationSize = 1)
	private Integer id;

	@ManyToOne(optional = true /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_SITIO", referencedColumnName = "ID_SITIO", nullable = true)
	private Sitio sitio;

	@ManyToOne(optional = true /* JPA lo resuelve en tiempo de ejecucion */)
	@JoinColumn(name = "ID_TIPO_DE_EQUIPO", referencedColumnName = "ID_TIPO_DE_EQUIPO", nullable = true)
	private TipoDeEquipo tipo_de_equipo;

	@Column(name = "NUMERO_EQUIPO", nullable = true)
	private Integer id_equipo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EquipoEnSitio() {
	}

	public EquipoEnSitio(TipoDeEquipo tipo_de_equipo, Integer id_equipo) {

		this.tipo_de_equipo = tipo_de_equipo;
		this.id_equipo = id_equipo;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof EquipoEnSitio))
			return false;

		EquipoEnSitio equipo_a_comparar = (EquipoEnSitio) object;

		if (this.sitio.equals(equipo_a_comparar.getSitio())
				&& this.tipo_de_equipo.equals(equipo_a_comparar.getTipo_de_equipo()))
			return true;

		return false;
	}

	public Integer getId() {
		return id;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Integer getId_equipo() {
		return id_equipo;
	}

	public Sitio getSitio() {
		return sitio;
	}

	public TipoDeEquipo getTipo_de_equipo() {
		return tipo_de_equipo;
	}

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

	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}

	public void setTipo_de_equipo(TipoDeEquipo tipo_de_equipo) {
		this.tipo_de_equipo = tipo_de_equipo;
	}

	@Override
	public String toString() {
		return tipo_de_equipo.getDescripcion() + " " + id_equipo;
	}
}