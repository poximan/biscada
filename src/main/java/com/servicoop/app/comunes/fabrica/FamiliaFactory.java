/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import main.java.com.servicoop.app.etl.controles.servicios.CampoTextoDefectuoso;
import main.java.com.servicoop.app.etl.controles.servicios.ServExpresionesRegulares;
import main.java.com.servicoop.app.etl.excepciones.CampoTextoAmbiguoExcepcion;
import main.java.com.servicoop.app.etl.excepciones.CampoTextoNoEncontradoExcepcion;
import main.java.com.servicoop.app.etl.partes_alarma.familias.BackupSCADA;
import main.java.com.servicoop.app.etl.partes_alarma.familias.Cloacal;
import main.java.com.servicoop.app.etl.partes_alarma.familias.ErrorComunicacion;
import main.java.com.servicoop.app.etl.partes_alarma.familias.Login;
import main.java.com.servicoop.app.etl.partes_alarma.familias.Logout;
import main.java.com.servicoop.app.etl.partes_alarma.familias.Potable;
import main.java.com.servicoop.app.etl.partes_alarma.familias.Reuso;
import main.java.com.servicoop.app.etl.partes_alarma.familias.WizconLang;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO la implementacion concreta de una fabrica abstracta. patron de
 * dise�o AbstractFactory
 *
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO devuelvo instancias concretas de la super clase para la que fui
 * creado.
 *
 * LO QUE CONOZCO el identificador de cada sub clase concreta y su constructor
 *
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL ES quien precise una instancia de los subtipos que
 * conozco
 *
 * COMO INTERACTUO CON MI COLABORADOR este conoce el discriminante y yo lo que
 * debo devolver nos comunicacmos a traves del mensaje getInstancia(String
 * discriminante)
 *
 * @author hdonato
 *
 */
public class FamiliaFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServExpresionesRegulares serv_exp_reg;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FamiliaFactory(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		serv_exp_reg = new ServExpresionesRegulares();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		TipoDatoFabricable dato_fabricado = null;

		try {
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, BackupSCADA.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Cloacal.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ErrorComunicacion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Login.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Logout.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Potable.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Reuso.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, WizconLang.class.getCanonicalName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FamiliaFactory.class.getSimpleName(),
					excepcion.getMessage());
		}

		return dato_fabricado;
	}
}
