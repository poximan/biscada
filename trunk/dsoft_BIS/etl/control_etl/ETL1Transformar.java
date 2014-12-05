/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import modelo.Alarma;
import modelo.ArchivoDBF;
import modelo.EquipoEnSitio;
import modelo.Familia;
import modelo.Sitio;
import modelo.Suceso;

import org.apache.log4j.Logger;

import sitios.FabricaSitio;
import sucesos.FabricaSuceso;
import control_dbf.ArchAlarma;
import control_general.ObjetosBorrables;
import equipos.FabricaEquipo;
import familias.FabricaFamilia;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * La segunda fase es de transformación, y aplica una serie de reglas de negocio o funciones sobre los datos extraídos
 * para convertirlos en datos que serán cargados. Puede ser necesario aplicar algunas de las siguientes
 * transformaciones: 1) Seleccionar sólo ciertas columnas para su carga. 2) Dividir una columna en varias.
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
	private List<ArchAlarma> alarmas_rechazadas;

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
		alarmas_rechazadas = new LinkedList<ArchAlarma>();

		for (ArchAlarma alarma_no_transformada : alarmas_extraidas) {

			try {
				Alarma alarma_transformada = new Alarma();

				recortarBlancos(alarma_no_transformada);
				pasarPrimitivosDirecto(alarma_transformada, alarma_no_transformada);
				convertirTextoANumero(alarma_transformada, alarma_no_transformada);
				unificarColumnasFecha(alarma_transformada, alarma_no_transformada);

				/*
				 * caso especial descubrir familia
				 */
				TextoDiferenciable nueva_familia = new FabricaFamilia(alarma_rechazada);
				convertirTextoAObjeto(alarma_transformada, alarma_no_transformada.getNombre(), nueva_familia);

				/*
				 * caso especial descubrir sitio, equipo y suceso a partir de campo unico
				 */
				TextoDiferenciable elementos_separados[] = { new FabricaSitio(alarma_rechazada),
						new FabricaEquipo(alarma_rechazada), new FabricaSuceso(alarma_rechazada) };

				dividirCampoTexto(alarma_transformada, alarma_no_transformada.getTexto(), elementos_separados, fila);

				agregarPropietario(alarma_transformada, archivo_propietario);

				if (esAlarmaValida(alarma_transformada))
					alarmas_transformadas.add(alarma_transformada);
				else
					alarmas_rechazadas.add(alarma_no_transformada);

			}
			catch (NullPointerException excepcion) {
				log.error("error leyendo fila " + (fila + 1) + ": " + alarma_no_transformada.getTexto());
			}
			fila++;
		}
	}

	private void agregarPropietario(Alarma alarma_transformada, ArchivoDBF archivo_propietario) {
		alarma_transformada.setArchivo_propietario(archivo_propietario);
	}

	private void convertirTextoANumero(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setSeveridad(insertarInt(alarma_no_transformada.getSeveridad()));
		alarma_transformada.setClase(insertarLong(alarma_no_transformada.getClase()));
		alarma_transformada.setZona(insertarInt(alarma_no_transformada.getZona()));
		alarma_transformada.setAtributo(insertarInt(alarma_no_transformada.getAtributo()));
	}

	private void convertirTextoAObjeto(Alarma alarma_transformada, String campo_nombre, TextoDiferenciable nueva_familia) {

		try {
			nueva_familia.prepararExpresionRegular(campo_nombre);
		}
		catch (NullPointerException excepcion) {
			throw excepcion;
		}
		alarma_transformada.setFamilia((Familia) nueva_familia.getPropietario());
	}

	private void dividirCampoTexto(Alarma alarma_transformada, String campo_texto,
			TextoDiferenciable[] elementos_separados, int fila) {

		try {
			for (int indice = 0; indice < elementos_separados.length; indice++)
				elementos_separados[indice].prepararExpresionRegular(campo_texto);
		}
		catch (NullPointerException excepcion) {
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

		return new Integer(texto);
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
		alarmas_rechazadas.clear();

		System.gc();
	}

	private void pasarPrimitivosDirecto(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setAck_usuario(alarma_no_transformada.getAck_nombre());
		alarma_transformada.setIdentificacion(alarma_no_transformada.getIdentificacion());
	}

	private void recortarBlancos(ArchAlarma alarma_no_transformada) {

		alarma_no_transformada.setInicio_segundo(alarma_no_transformada.getInicio_segundo().trim());
		alarma_no_transformada.setInicio_milisegundo(alarma_no_transformada.getInicio_milisegundo().trim());
		alarma_no_transformada.setFin_segundo(alarma_no_transformada.getFin_segundo().trim());
		alarma_no_transformada.setFin_milisegundo(alarma_no_transformada.getFin_milisegundo().trim());
		alarma_no_transformada.setAck_segundo(alarma_no_transformada.getAck_segundo().trim());
		alarma_no_transformada.setAck_nombre(alarma_no_transformada.getAck_nombre().trim());
		alarma_no_transformada.setSeveridad(alarma_no_transformada.getSeveridad().trim());
		alarma_no_transformada.setClase(alarma_no_transformada.getClase().trim());
		alarma_no_transformada.setZona(alarma_no_transformada.getZona().trim());
		alarma_no_transformada.setAtributo(alarma_no_transformada.getAtributo().trim());
		alarma_no_transformada.setIdentificacion(alarma_no_transformada.getIdentificacion().trim());
		alarma_no_transformada.setNombre(alarma_no_transformada.getNombre().trim());
		alarma_no_transformada.setTexto(alarma_no_transformada.getTexto().trim());
		alarma_no_transformada.setId_estacion(alarma_no_transformada.getId_estacion().trim());
	}

	@Override
	public String toString() {
		return alarmas_transformadas.toString();
	}

	private Calendar transformarTiempos(String segundo, String milisegundo) {

		if (segundo.equals(new String("0")))
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(segundo) * 1000 + Long.parseLong(milisegundo));
		return calendar;
	}

	private void unificarColumnasFecha(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		Calendar fecha_unificada;

		fecha_unificada = transformarTiempos(alarma_no_transformada.getInicio_segundo(),
				alarma_no_transformada.getInicio_milisegundo());
		alarma_transformada.setFecha_inicio(fecha_unificada);

		fecha_unificada = transformarTiempos(alarma_no_transformada.getFin_segundo(),
				alarma_no_transformada.getFin_milisegundo());
		alarma_transformada.setFecha_finalizacion(fecha_unificada);

		// fecha ack no tiene campo milisegundo
		fecha_unificada = transformarTiempos(alarma_no_transformada.getAck_segundo(), "0");
		alarma_transformada.setFecha_ack(fecha_unificada);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public List<Alarma> getAlarmas_transformadas() {
		return alarmas_transformadas;
	}

	public List<ArchAlarma> getAlarmas_rechazadas() {
		return alarmas_rechazadas;
	}
}
