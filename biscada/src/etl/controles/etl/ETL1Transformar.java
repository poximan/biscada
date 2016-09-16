/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.etl;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import comunes.controles.ObjetosBorrables;
import comunes.modelo.Alarma;
import comunes.modelo.ArchivoDBF;
import comunes.modelo.EquipoEnSitio;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import etl.controles.dbf.ArchAlarma;
import etl.equipos.FabricaEquipo;
import etl.familias.FabricaFamilia;
import etl.sitios.FabricaSitio;
import etl.sucesos.FabricaSuceso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * La segunda fase es de transformaci�n, y aplica una serie de reglas de
 * negocio o funciones sobre los datos extra�dos para convertirlos en datos
 * que ser�n cargados. Puede ser necesario aplicar algunas de las siguientes
 * transformaciones: 1) Seleccionar s�lo ciertas columnas para su carga. 2)
 * Dividir una columna en varias.
 * 
 * @author hugo
 * 
 */
public class ETL1Transformar implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ETL1Transformar.class);

	private List<ArchAlarma> alarmas_extraidas;
	private List<Alarma> alarmas_transformadas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ETL1Transformar(List<ArchAlarma> alarmas_extraidas) {

		this.alarmas_extraidas = alarmas_extraidas;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void transformarAlarmas(ArchivoDBF archivo_propietario, CampoTextoDefectuoso alarma_rechazada) {

		int fila = 0;
		alarmas_transformadas = new LinkedList<Alarma>();

		for (ArchAlarma alarma_no_transformada : alarmas_extraidas) {

			try {
				Alarma alarma_transformada = new Alarma();

				pasarPrimitivosDirecto(alarma_transformada, alarma_no_transformada);
				convertirTextoANumero(alarma_transformada, alarma_no_transformada);
				unificarColumnasFecha(alarma_transformada, alarma_no_transformada);

				/*
				 * caso especial descubrir familia
				 */
				TextoDiferenciable nueva_familia = new FabricaFamilia(alarma_rechazada);
				convertirTextoAObjeto(alarma_transformada, alarma_no_transformada.getNAME(), nueva_familia);

				/*
				 * caso especial descubrir sitio, equipo y suceso a partir de
				 * campo unico
				 */
				TextoDiferenciable elementos_separados[] = { new FabricaSitio(alarma_rechazada),
						new FabricaEquipo(alarma_rechazada), new FabricaSuceso(alarma_rechazada) };

				dividirCampoTexto(alarma_transformada, alarma_no_transformada.getTEXT(), elementos_separados, fila);

				agregarPropietario(alarma_transformada, archivo_propietario);

				if (esAlarmaValida(alarma_transformada))
					alarmas_transformadas.add(alarma_transformada);

			} catch (NullPointerException excepcion) {
				log.error("error leyendo fila " + (fila + 1) + ": " + alarma_no_transformada.getTEXT());
			}
			fila++;
		}
	}

	private void agregarPropietario(Alarma alarma_transformada, ArchivoDBF archivo_propietario) {
		alarma_transformada.setArchivo_propietario(archivo_propietario);
	}

	private void convertirTextoANumero(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setSeveridad(insertarInt(alarma_no_transformada.getSEVERITY()));
		alarma_transformada.setClase(insertarLong(alarma_no_transformada.getCLASS()));
		alarma_transformada.setZona(insertarInt(alarma_no_transformada.getZONE()));
		alarma_transformada.setAtributo(insertarInt(alarma_no_transformada.getATTR()));
	}

	private void convertirTextoAObjeto(Alarma alarma_transformada, String campo_nombre,
			TextoDiferenciable nueva_familia) {

		try {
			nueva_familia.prepararExpresionRegular(campo_nombre);
		} catch (NullPointerException excepcion) {
			throw excepcion;
		}
		alarma_transformada.setFamilia((Familia) nueva_familia.getPropietario());
	}

	private void dividirCampoTexto(Alarma alarma_transformada, String campo_texto,
			TextoDiferenciable[] elementos_separados, int fila) {

		try {
			for (int indice = 0; indice < elementos_separados.length; indice++)
				elementos_separados[indice].prepararExpresionRegular(campo_texto);
		} catch (NullPointerException excepcion) {
			throw excepcion;
		}

		alarma_transformada.setSitio((Sitio) elementos_separados[0].getPropietario());
		alarma_transformada.setEquipo_en_sitio((EquipoEnSitio) elementos_separados[1].getPropietario());
		alarma_transformada.setSuceso((Suceso) elementos_separados[2].getPropietario());
	}

	private boolean esAlarmaValida(Alarma alarma_transformada) {

		if (alarma_transformada.getSitio() != null && alarma_transformada.getSuceso() != null)
			return true;
		return false;
	}

	private Integer insertarInt(String texto) {

		if (texto.equals(""))
			return null;

		return new Integer(texto.trim());
	}

	private Long insertarLong(String texto) {

		if (texto.equals(""))
			return null;

		return new Long(texto);
	}

	@Override
	public void liberarObjetos() {

		alarmas_extraidas.clear();
		alarmas_transformadas.clear();

		System.gc();
	}

	private void pasarPrimitivosDirecto(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setAck_usuario(alarma_no_transformada.getACK_NAME());
		alarma_transformada.setIdentificacion(alarma_no_transformada.getIDENT());
	}

	@Override
	public String toString() {
		return alarmas_transformadas.toString();
	}

	private Calendar transformarTiempos(String segundo, String milisegundo) {

		if (segundo.equals(new String("0")))
			return null;

		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTimeInMillis(Long.parseLong(segundo) * 1000 + Long.parseLong(milisegundo));

			/*
			 * en caso que no haya caracteres para los milisegundos
			 */
		} catch (NumberFormatException excepcion) {
			calendar.setTimeInMillis(Long.parseLong(segundo.trim()) * 1000);
		}
		return calendar;
	}

	private void unificarColumnasFecha(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		Calendar fecha_unificada;

		fecha_unificada = transformarTiempos(alarma_no_transformada.getSTART_SEC(),
				alarma_no_transformada.getSTART_MILI());
		alarma_transformada.setFecha_inicio(fecha_unificada);

		fecha_unificada = transformarTiempos(alarma_no_transformada.getEND_TIME(),
				alarma_no_transformada.getEND_MILI());
		alarma_transformada.setFecha_finalizacion(fecha_unificada);

		// fecha ack no tiene campo milisegundo
		fecha_unificada = transformarTiempos(alarma_no_transformada.getACK_TIME(), "0");
		alarma_transformada.setFecha_ack(fecha_unificada);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public List<Alarma> getAlarmas_transformadas() {
		return alarmas_transformadas;
	}
}
