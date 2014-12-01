package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2014-11-28T12:24:38.754-0300")
@StaticMetamodel(EquipoEnSitio.class)
public class EquipoEnSitio_ {
	public static volatile SingularAttribute<EquipoEnSitio, Integer> id;
	public static volatile SingularAttribute<EquipoEnSitio, Sitio> sitio;
	public static volatile SingularAttribute<EquipoEnSitio, TipoDeEquipo> tipo_de_equipo;
	public static volatile SingularAttribute<EquipoEnSitio, Integer> numero_equipo;
}
