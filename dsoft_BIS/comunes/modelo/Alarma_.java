package modelo;

import java.util.Calendar;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2014-11-27T19:31:03.735-0300")
@StaticMetamodel(Alarma.class)
public class Alarma_ {
	public static volatile SingularAttribute<Alarma, Integer> id;
	public static volatile SingularAttribute<Alarma, ArchivoDBF> archivo_propietario;
	public static volatile SingularAttribute<Alarma, Calendar> fecha_inicio;
	public static volatile SingularAttribute<Alarma, Calendar> fecha_ack;
	public static volatile SingularAttribute<Alarma, Calendar> fecha_finalizacion;
	public static volatile SingularAttribute<Alarma, String> ack_usuario;
	public static volatile SingularAttribute<Alarma, Integer> severidad;
	public static volatile SingularAttribute<Alarma, Long> clase;
	public static volatile SingularAttribute<Alarma, Integer> zona;
	public static volatile SingularAttribute<Alarma, Integer> atributo;
	public static volatile SingularAttribute<Alarma, String> identificacion;
	public static volatile SingularAttribute<Alarma, Familia> familia;
	public static volatile SingularAttribute<Alarma, Sitio> sitio;
	public static volatile SingularAttribute<Alarma, EquipoEnSitio> equipo_en_sitio;
	public static volatile SingularAttribute<Alarma, Suceso> suceso;
	public static volatile SingularAttribute<Alarma, Integer> id_estacion;
}
