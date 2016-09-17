/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class Constantes {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/**
	 * de utilidad para las fabricas concretas al momento de responder con una instancia
	 */
	public static final String ABRE_EXP_REG = ".*(";
	
	/**
	 * de utilidad para las fabricas concretas al momento de responder con una instancia 
	 */
	public static final String CIERRA_EXP_REG = ").*";
	
	/**
	 * de utilidad para indicarle a la fabrica abstracta que fabrica desea
	 */
	public static final String FABRICA_EQUIPO_EN_SITIO = "equipo en sitio";

	/**
	 * de utilidad para indicarle a la fabrica abstracta que fabrica desea
	 */
	public static final String FABRICA_FAMILIA = "familia";

	/**
	 * de utilidad para indicarle a la fabrica abstracta que fabrica desea
	 */
	public static final String FABRICA_SITIO = "sitio";

	/**
	 * de utilidad para indicarle a la fabrica abstracta que fabrica desea
	 */
	public static final String FABRICA_SUCESO = "suceso";

	/**
	 * de utilidad para indicarle a la fabrica abstracta que fabrica desea
	 */
	public static final String FABRICA_TIPO_DE_EQUIPO = "tipo de equipo";
}