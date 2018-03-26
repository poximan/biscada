/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.servicios.dimensiones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.java.com.servicoop.app.bi.controles.servicios.mediciones.ServMedAbstract;
import main.java.com.servicoop.app.bi.controles.servicios.periodos.ServPeriodoAbstract;
import main.java.com.servicoop.app.comunes.entidades.Alarma;
import main.java.com.servicoop.app.comunes.entidades.Suceso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO los servicios concretos que necesitan las vistas
 * bi.vistas.dimensiones.VistaDimSucesoSimple y
 * bi.vistas.dimensiones.VistaDimSucesoCompuesta
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO soy responsable de los servicios de dimension en las superclases
 * bi.vistas.dimensiones.VistaDimAbstractSimple y
 * bi.vistas.dimensiones.VistaDimAbstractCompuesta
 *
 * LO QUE CONOZCO el mapa hash {clave, valor} para agrupar la consulta (simil
 * "group by" de sql) segun la dimension elegida
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, las herramientas de hash y ordenamiento de
 * colecciones
 *
 * COMO INTERACTUO CON MI COLABORADOR, las herramientas hash me permiten crear
 * el mapa, dada una clave pedir los valores y visceversa. con el ordenamiento
 * de colecciones aseguro que mis mapa tenga sus valores (las alarmas) ordenadas
 * siempre de menor a mayor.
 *
 */
public class ServDimSuceso extends ServDimAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/*
	 * An instance of HashMap has two parameters that affect its performance:
	 * initial capacity and load factor. The capacity is the number of buckets in
	 * the hash table, and the initial capacity is simply the capacity at the time
	 * the hash table is created. The load factor is a measure of how full the hash
	 * table is allowed to get before its capacity is automatically increased. When
	 * the number of entries in the hash table exceeds the product of the load
	 * factor and the current capacity, the hash table is rehashed (that is,
	 * internal data structures are rebuilt) so that the hash table has
	 * approximately twice the number of buckets.
	 */
	private Map<Suceso, List<Alarma>> map;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public float[][] completarTabla(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			boolean contar_periodos_nulos) {

		int indice = 0;

		float[][] valor_retorno = new float[map.size()][0];
		List<Alarma> lista_alarmas_una_clave = null;

		for (Map.Entry<Suceso, List<Alarma>> hash_alarmas_una_clave : map.entrySet()) {

			lista_alarmas_una_clave = hash_alarmas_una_clave.getValue();

			valor_retorno[indice] = serv_medicion.completarFila(lista_alarmas_una_clave, serv_periodo);
			indice++;
		}
		return postProcesarTabla(valor_retorno, contar_periodos_nulos);
	}

	/**
	 * pide los nombres de los grupos que se obtienen de observar una lista desde
	 * una dimension espec�fica.
	 *
	 * @return con estos nombres se llenara una tabla de simple columna que simula
	 *         la seguda entrada de la que posee los datos.
	 */
	@Override
	public Object[] getGrupos() {

		List<Suceso> lista_de_suceso = new ArrayList<Suceso>();

		Set<Suceso> keys = map.keySet();

		for (Suceso suceso_encontrado : keys)
			lista_de_suceso.add(suceso_encontrado);

		Suceso arreglo_suceso[] = new Suceso[lista_de_suceso.size()];
		arreglo_suceso = lista_de_suceso.toArray(arreglo_suceso);
		lista_de_suceso.clear();

		return arreglo_suceso;
	}

	@Override
	public void realizarHash(List<Alarma> consultas) {

		map = new HashMap<Suceso, List<Alarma>>();

		for (Alarma alarma_actual : consultas) {

			Suceso key = alarma_actual.getSuceso();

			if (map.get(key) == null)
				map.put(key, new ArrayList<Alarma>());

			map.get(key).add(alarma_actual);
		}

		Iterator<Entry<Suceso, List<Alarma>>> it = map.entrySet().iterator();

		while (it.hasNext()) {

			Entry<Suceso, List<Alarma>> pair = it.next();

			List<Alarma> lista = pair.getValue();
			Collections.sort(lista);

			map.put(pair.getKey(), lista);
		}
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
