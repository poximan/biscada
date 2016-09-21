/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import comunes.controles.ObjetosBorrables;
import comunes.fabrica.Constantes;
import comunes.fabrica.FabricaAbstracta;
import comunes.fabrica.ProductorFabricas;
import comunes.fabrica.TipoDatoFabricable;
import comunes.modelo.Alarma;
import comunes.modelo.ArchivoDBF;
import comunes.modelo.EquipoEnSitio;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import etl.controles.dbf.ArchAlarma;
import etl.controles.servicios.CampoTextoDefectuoso;

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

	private void convertirTextoANumero(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setSeveridad(insertarInt(alarma_no_transformada.getSEVERITY()));
		alarma_transformada.setClase(insertarLong(alarma_no_transformada.getCLASS()));
		alarma_transformada.setZona(insertarInt(alarma_no_transformada.getZONE()));
		alarma_transformada.setAtributo(insertarInt(alarma_no_transformada.getATTR()));
	}

	private boolean esAlarmaValida(Alarma alarma_transformada) {

		if (//
		alarma_transformada.getFecha_inicio() != null && //
				alarma_transformada.getFamilia() != null && //
				alarma_transformada.getSitio() != null && //
				alarma_transformada.getSuceso() != null//
		)
			return true;

		return false;
	}

	public List<Alarma> getAlarmas() {
		return alarmas_transformadas;
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
	}

	private void pasarPrimitivosDirecto(Alarma alarma_transformada, ArchAlarma alarma_no_transformada) {

		alarma_transformada.setAck_usuario(alarma_no_transformada.getACK_NAME().trim());
		alarma_transformada.setIdentificacion(alarma_no_transformada.getIDENT().trim());
	}

	@Override
	public String toString() {
		return alarmas_transformadas.toString();
	}

	public void transformarAlarmas(ArchivoDBF archivo_propietario, CampoTextoDefectuoso alarma_rechazada) {

		int fila = 0;
		alarmas_transformadas = new LinkedList<Alarma>();

		for (ArchAlarma alarma_no_transformada : alarmas_extraidas) {

			try {
				Alarma alarma_transformada = new Alarma();

				pasarPrimitivosDirecto(alarma_transformada, alarma_no_transformada);
				convertirTextoANumero(alarma_transformada, alarma_no_transformada);
				unificarColumnasFecha(alarma_transformada, alarma_no_transformada);

				usarAbstractFactory(alarma_transformada, alarma_no_transformada, alarma_rechazada);

				alarma_transformada.setArchivo_propietario(archivo_propietario);

				if (esAlarmaValida(alarma_transformada))
					alarmas_transformadas.add(alarma_transformada);

			} catch (NullPointerException excepcion) {
				log.error("error leyendo fila " + (fila + 1) + ": " + alarma_no_transformada.getTEXT());
			}
			fila++;
		}
	}

	private Calendar transformarTiempos(String _segundo, String _milisegundo) {

		String segundo = _segundo.trim();
		String milisegundo = _milisegundo.trim();

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

	private void usarAbstractFactory(Alarma alarma_transformada, ArchAlarma alarma_no_transformada,
			CampoTextoDefectuoso alarma_rechazada) {

		String texto_compuesto = alarma_no_transformada.getTEXT().trim();

		/*
		 * caso especial descubrir sitio desde campo de texto compartido
		 */
		FabricaAbstracta fabrica_sitio = ProductorFabricas.getFactory(Constantes.FABRICA_SITIO, alarma_rechazada);
		TipoDatoFabricable sitio = fabrica_sitio.getInstancia(texto_compuesto);
		alarma_transformada.setSitio((Sitio) sitio);

		/*
		 * caso especial descubrir familia
		 */
		FabricaAbstracta fabrica_familia = ProductorFabricas.getFactory(Constantes.FABRICA_FAMILIA, alarma_rechazada);
		TipoDatoFabricable familia = fabrica_familia.getInstancia(alarma_no_transformada.getNAME().trim());

		if (familia == null)
			familia = ((Sitio) sitio).getFamiliaPorDefecto();

		alarma_transformada.setFamilia((Familia) familia);

		/*
		 * caso especial descubrir sitio desde campo de texto compartido
		 */
		FabricaAbstracta fabrica_suceso = ProductorFabricas.getFactory(Constantes.FABRICA_SUCESO, alarma_rechazada);
		TipoDatoFabricable suceso = fabrica_suceso.getInstancia(texto_compuesto);
		alarma_transformada.setSuceso((Suceso) suceso);

		/*
		 * caso especial descubrir sitio desde campo de texto compartido
		 */
		FabricaAbstracta fabrica_equipo_en_sitio = ProductorFabricas.getFactory(Constantes.FABRICA_EQUIPO_EN_SITIO,
				alarma_rechazada);
		TipoDatoFabricable equipo_en_sitio = fabrica_equipo_en_sitio.getInstancia(texto_compuesto);

		((EquipoEnSitio) equipo_en_sitio).setSitio(alarma_transformada.getSitio());

		alarma_transformada.setEquipo_en_sitio((EquipoEnSitio) equipo_en_sitio);
	}
}
