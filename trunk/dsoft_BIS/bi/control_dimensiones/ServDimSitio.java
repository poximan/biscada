/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelo.Alarma;
import modelo.IntervaloFechas;
import modelo.Sitio;
import control_mediciones.ServMedAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServDimSitio extends ServDimAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/*
	 * An instance of HashMap has two parameters that affect its performance: initial capacity and load factor. The
	 * capacity is the number of buckets in the hash table, and the initial capacity is simply the capacity at the time
	 * the hash table is created. The load factor is a measure of how full the hash table is allowed to get before its
	 * capacity is automatically increased. When the number of entries in the hash table exceeds the product of the load
	 * factor and the current capacity, the hash table is rehashed (that is, internal data structures are rebuilt) so
	 * that the hash table has approximately twice the number of buckets.
	 */
	private Map<Sitio, List<Alarma>> map;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void realizarHash(List<Alarma> consultas) {

		map = new HashMap<Sitio, List<Alarma>>();

		for (Alarma alarma_actual : consultas) {

			Sitio key = alarma_actual.getSitio();

			// si no existe la clave, se crea
			if (map.get(key) == null)
				map.put(key, new ArrayList<Alarma>());

			map.get(key).add(alarma_actual);
		}
	}

	@Override
	public float[][] completarTabla(ServIntervaloFechas serv_intervalo, IntervaloFechas intervalo,
			ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			boolean incluir_columnas_nulas) {

		int indice = 0;
		float[][] valor_retorno = new float[map.size()][1];
		List<Alarma> lista_alarmas_una_clave = null;

		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_una_clave : map.entrySet()) {

			lista_alarmas_una_clave = hash_alarmas_una_clave.getValue();

			valor_retorno[indice++] = procesamientoComunFila(intervalo, lista_alarmas_una_clave, serv_intervalo,
					serv_medicion, serv_unidad_tiempo);
		}
		if (!incluir_columnas_nulas) {
			valor_retorno = filtrarColumnasNulas(valor_retorno, serv_medicion);
			serv_intervalo.encontrarMinimoMaximo(intervalo, lista_alarmas_una_clave);
		}
		return valor_retorno;
	}

	/**
	 * pide los nombres de los grupos que se obtienen de observar una lista desde una dimension específica.
	 * 
	 * @return con estos nombres se llenara una tabla de simple columna que simula la seguda entrada de la que posee los
	 *         datos.
	 */
	@Override
	public Object[] getGrupos() {

		List<Sitio> lista_de_sitios = new ArrayList<Sitio>();

		Set<Sitio> keys = map.keySet();

		for (Sitio sitio_encontrado : keys)
			lista_de_sitios.add(sitio_encontrado);

		Sitio arreglo_sitio[] = new Sitio[lista_de_sitios.size()];
		arreglo_sitio = lista_de_sitios.toArray(arreglo_sitio);
		lista_de_sitios.clear();

		return arreglo_sitio;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Map<Sitio, List<Alarma>> getMap() {
		return map;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}