package etl.controles.servicios;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import comunes.fabrica.Constantes;
import comunes.fabrica.TipoDatoFabricable;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.UsarPrimerOcurrenciaExcepcion;
import etl.excepciones.UsarSegundaOcurrenciaExcepcion;
import etl.partes_alarma.equipos.Bomba;
import etl.partes_alarma.equipos.CamaraAspiracion;
import etl.partes_alarma.equipos.CentroControlMotores;
import etl.partes_alarma.equipos.Cisterna;
import etl.partes_alarma.equipos.InstrumentoCampo;
import etl.partes_alarma.equipos.Plc;
import etl.partes_alarma.equipos.Pozo;
import etl.partes_alarma.equipos.Valvula;
import etl.partes_alarma.sucesos.ActuadoParadaEmergencia;
import etl.partes_alarma.sucesos.ComandoParada;
import etl.partes_alarma.sucesos.NivelAlto;
import etl.partes_alarma.sucesos.NivelRebalse;

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un servicio capaz de obtener la expresion regular de una
 * entidad y devolver la instancia que corresponde
 * 
 * soy de utilidad durante la etapa de comparacion de todas las fabricas
 * concretas que necesitan saber si una frase dada se parece (o no) a una clase
 * objetivo
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, dados el nombre canonico de una clase y un discriminante, puedo
 * reconstruir la clase del nombre canonico (por refleccion) y obtener su
 * expresion regular para compararla con el discriminante.
 * 
 * si la comparacion da coincidente, pido una instancia de la clase reflejada.
 * 
 * ademas recibo una referencia "TipoDatoFabricable dato_fabricado" que debe ser
 * nula. caso contrario una iteracion previa de mi mismo ha encontrado una
 * coincidencia, lo que implica ambig�edad. para tratar de salvar este problema
 * uso el metodo salvarAmbiguedad(...) que posee una serie de recetas del tipo
 * "si la ambig�edad se parece a X, aplicar solucion Y"
 * 
 * LO QUE CONOZCO, el nombre canonico de la clase a analizar, el discriminante
 * conque debo resolver la coincidencia y la referencia donde debo crear la
 * nueva instancia (el TipoDatoFabricable dato_fabricado que debe ser nulo)
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, java.lang.reflect
 * 
 * COMO INTERACTUO CON MI COLABORADOR, en el metodo getExpresion_regular()
 * concentro mi interaccion con la biblioteca reflect. obtengo el nombre de la
 * clase partiendo del canonico (Class.forName(...)). luego pido un metodo
 * estatico cuyo nombre ya conozco (getMethod("getExpresion_regular") e
 * nuevo_metodo.invoke(...)). si las expresiones coinciden pido una instancia
 * (nueva_clase.newInstance())
 * 
 * @author hdonato
 * 
 */
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
				/*
				 * dato_fabricado debe ser nulo en este momemto, caso contrario ya se encontr�
				 * otra instancia diferente a la que se esta evaluando ahora. esto implica
				 * ambig�edad en la localizacion de la clase
				 */
				if (dato_fabricado != null) {

					salvarAmbiguedad(dato_fabricado, nueva_clase);

					throw new CampoTextoAmbiguoExcepcion(
							discriminante + " [1er ocurrencia: " + dato_fabricado.getClass().getSimpleName() + " - "
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

	private void salvarAmbiguedad(TipoDatoFabricable pri_ocurrencia, Class<?> seg_ocurr)
			throws UsarPrimerOcurrenciaExcepcion, UsarSegundaOcurrenciaExcepcion {

		TipoDatoFabricable seg_ocurrencia = null;

		try {
			seg_ocurrencia = (TipoDatoFabricable) seg_ocurr.newInstance();

			// (Cisterna || CamaraAspiracion) -> InstrumentoCampo
			if ((pri_ocurrencia instanceof Cisterna || pri_ocurrencia instanceof CamaraAspiracion)
					&& seg_ocurrencia instanceof InstrumentoCampo)
				throw new UsarSegundaOcurrenciaExcepcion();

			// NivelAlto -> NivelRebalse
			if (pri_ocurrencia instanceof NivelAlto && seg_ocurrencia instanceof NivelRebalse)
				throw new UsarSegundaOcurrenciaExcepcion();

			// Bomba -> CentroControlMotores
			if (pri_ocurrencia instanceof Bomba && seg_ocurrencia instanceof CentroControlMotores)
				throw new UsarSegundaOcurrenciaExcepcion();

			// InstrumentoCampo <- Pozo
			if (pri_ocurrencia instanceof InstrumentoCampo && seg_ocurrencia instanceof Pozo)
				throw new UsarPrimerOcurrenciaExcepcion();

			// ActuadoParadaEmergencia <- ComandoParada
			if (pri_ocurrencia instanceof ActuadoParadaEmergencia && seg_ocurrencia instanceof ComandoParada)
				throw new UsarPrimerOcurrenciaExcepcion();

			// Cisterna -> Plc
			if (pri_ocurrencia instanceof Cisterna && seg_ocurrencia instanceof Plc)
				throw new UsarSegundaOcurrenciaExcepcion();

			// CentroControlMotores <- Valvula
			if (pri_ocurrencia instanceof CentroControlMotores && seg_ocurrencia instanceof Valvula)
				throw new UsarPrimerOcurrenciaExcepcion();

			// Plc <- Valvula
			if (pri_ocurrencia instanceof Plc && seg_ocurrencia instanceof Valvula)
				throw new UsarPrimerOcurrenciaExcepcion();

		} catch (InstantiationException | IllegalAccessException e) {
			log.error("ERROR: no se pudo pedir nueva instancia de clase " + seg_ocurr.getSimpleName());
		}
	}
}
