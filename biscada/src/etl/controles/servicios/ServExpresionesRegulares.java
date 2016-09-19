package etl.controles.servicios;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import comunes.fabrica.Constantes;
import comunes.fabrica.TipoDatoFabricable;
import etl.equipos.Bomba;
import etl.equipos.CamaraAspiracion;
import etl.equipos.Plc;
import etl.equipos.CentroControlMotores;
import etl.equipos.Cisterna;
import etl.equipos.Edificio;
import etl.equipos.Valvula;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.UsarPrimerOcurrenciaExcepcion;
import etl.excepciones.UsarSegundaOcurrenciaExcepcion;
import etl.sucesos.NivelAlto;
import etl.sucesos.NivelRebalse;

public class ServExpresionesRegulares {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServExpresionesRegulares.class);

	private Class<?> nueva_clase;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public TipoDatoFabricable asociar(TipoDatoFabricable dato_fabricado, String discriminante,
			String nombre_canonico_clase) throws CampoTextoAmbiguoExcepcion {

		String expresion_regular = getExpresion_regular(nombre_canonico_clase);
		TipoDatoFabricable nuevo_valor = dato_fabricado;

		if (discriminante.matches(Constantes.ABRE_EXP_REG + expresion_regular + Constantes.CIERRA_EXP_REG)) {

			try {
				if (dato_fabricado != null) {

					salvarAmbiguedad(dato_fabricado, nueva_clase);

					throw new CampoTextoAmbiguoExcepcion(discriminante + " [1er ocurrencia: " + dato_fabricado.getClass().getSimpleName() + " - "
							+ "2da ocurrencia: " + nueva_clase.getSimpleName() + " ]");
				} else
					throw new UsarSegundaOcurrenciaExcepcion();

			} catch (UsarPrimerOcurrenciaExcepcion e) {
			}

			catch (UsarSegundaOcurrenciaExcepcion e1) {

				try {
					nuevo_valor = (TipoDatoFabricable) nueva_clase.newInstance();
				} catch (InstantiationException | IllegalAccessException e2) {
					log.error("ERROR: no se pudo pedir nueva instancia de clase " + nueva_clase.getSimpleName());
				}
			}
		}
		return nuevo_valor;
	}

	private void salvarAmbiguedad(TipoDatoFabricable pri_ocurrencia, Class<?> seg_ocurr)
			throws UsarPrimerOcurrenciaExcepcion, UsarSegundaOcurrenciaExcepcion {

		TipoDatoFabricable seg_ocurrencia = null;

		try {
			seg_ocurrencia = (TipoDatoFabricable) seg_ocurr.newInstance();

			if (pri_ocurrencia instanceof Cisterna && seg_ocurrencia instanceof Plc)
				throw new UsarSegundaOcurrenciaExcepcion();
			
			if (pri_ocurrencia instanceof NivelAlto && seg_ocurrencia instanceof NivelRebalse)
				throw new UsarSegundaOcurrenciaExcepcion();
			
			if (pri_ocurrencia instanceof Cisterna && seg_ocurrencia instanceof Edificio)
				throw new UsarSegundaOcurrenciaExcepcion();
		
			if (pri_ocurrencia instanceof Bomba && seg_ocurrencia instanceof CentroControlMotores)
				throw new UsarSegundaOcurrenciaExcepcion();

			if ((pri_ocurrencia instanceof Plc || pri_ocurrencia instanceof CamaraAspiracion) && seg_ocurrencia instanceof Valvula)
				throw new UsarPrimerOcurrenciaExcepcion();

		} catch (InstantiationException | IllegalAccessException e) {
			log.error("ERROR: no se pudo pedir nueva instancia de clase " + seg_ocurr.getSimpleName());
		}
	}

	private String getExpresion_regular(String nombre_canonico_clase) {

		String expresion_regular = null;

		try {
			nueva_clase = Class.forName(nombre_canonico_clase);
			Method nuevo_metodo = nueva_clase.getMethod("getExpresion_regular");
			expresion_regular = (String) nuevo_metodo.invoke(nueva_clase);

		} catch (ClassNotFoundException e /* nueva clase */) {
			log.error("ERROR: clase no encontrada");
		} catch (NoSuchMethodException | SecurityException e /* nuevo metodo */) {
			log.error("ERROR: metodo no encontrado");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e /* invocar */) {
			log.error("ERROR: metodo no pudo ser invocado (ejecutado)");
		}
		return expresion_regular;
	}
}
