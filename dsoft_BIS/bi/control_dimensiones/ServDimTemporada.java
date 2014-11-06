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
import modelo.Temporada;
import temporadas.FabricaTemporada;
import control_general.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServDimTemporada extends ServDimAbstract {

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
	private Map<Temporada, List<Alarma>> map;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDimTemporada() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void realizarHash(List<Alarma> consultas, boolean incluir_alarmas_incompletas) {

		if (esNecesarioReconstruirHash(incluir_alarmas_incompletas)) {

			map = new HashMap<Temporada, List<Alarma>>();
			FabricaTemporada fabrica_key = new FabricaTemporada();

			for (Alarma alarma_actual : consultas) {

				Temporada key = fabrica_key.buscarRango(alarma_actual);

				if (map.get(key) == null)
					map.put(key, new ArrayList<Alarma>());

				map.get(key).add(alarma_actual);
			}
			invertirCheckCalculoAnterior(incluir_alarmas_incompletas);
		}
	}

	@Override
	public float[][] completarTabla(ServIntervaloFechas serv_intervalo, ServMedAbstract serv_medicion,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo, boolean incluir_columnas_nulas) {

		int indice = 0;
		float[][] valor_retorno = new float[map.size()][1];
		List<Alarma> lista_alarmas_una_clave = null;

		for (Map.Entry<Temporada, List<Alarma>> hash_alarmas_una_clave : map.entrySet()) {

			lista_alarmas_una_clave = hash_alarmas_una_clave.getValue();

			valor_retorno[indice++] = procesamientoComunFila(indice, lista_alarmas_una_clave, serv_intervalo,
					serv_medicion, serv_unidad_tiempo);
		}

		if (!incluir_columnas_nulas) {
			valor_retorno = filtrarColumnasNulas(valor_retorno, serv_medicion);
			serv_intervalo.encontrarMinimoMaximo(lista_alarmas_una_clave);
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

		List<Temporada> lista_segun_temporada = new ArrayList<Temporada>();

		Set<Temporada> keys = map.keySet();

		for (Temporada rango_encontrado : keys)
			lista_segun_temporada.add(rango_encontrado);

		Temporada arreglo_tiempos[] = new Temporada[lista_segun_temporada.size()];
		arreglo_tiempos = lista_segun_temporada.toArray(arreglo_tiempos);
		lista_segun_temporada.clear();

		return arreglo_tiempos;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}