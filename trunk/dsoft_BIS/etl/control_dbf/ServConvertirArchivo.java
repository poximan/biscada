/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dbf;

import java.util.LinkedList;
import java.util.List;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServConvertirArchivo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private List<ArchAlarma> lista_alarmas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConvertirArchivo() {

		lista_alarmas = new LinkedList<>();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void agregarAlarma(String texto_extraido) {

		ArchAlarma nueva_alarma = completarPropiedades(texto_extraido);
		lista_alarmas.add(nueva_alarma);
	}

	public ArchAlarma completarPropiedades(String texto_extraido) {

		ArchAlarma nueva_alarma = new ArchAlarma();

		nueva_alarma.setInicio_segundo(texto_extraido.substring(1, 11)); // 10, filtra 1er blanco adicional
		nueva_alarma.setInicio_milisegundo(texto_extraido.substring(11, 14)); // 3
		nueva_alarma.setFin_segundo(texto_extraido.substring(14, 24)); // 10
		nueva_alarma.setFin_milisegundo(texto_extraido.substring(24, 27)); // 3
		nueva_alarma.setAck_segundo(texto_extraido.substring(27, 37)); // 10
		nueva_alarma.setAck_nombre(texto_extraido.substring(37, 46)); // 9
		nueva_alarma.setSeveridad(texto_extraido.substring(46, 51)); // 5
		nueva_alarma.setClase(texto_extraido.substring(51, 61)); // 10
		nueva_alarma.setZona(texto_extraido.substring(61, 66)); // 5
		nueva_alarma.setAtributo(texto_extraido.substring(66, 76)); // 10
		nueva_alarma.setIdentificacion(texto_extraido.substring(76, 84)); // 8
		nueva_alarma.setNombre(texto_extraido.substring(84, 100)); // 16
		nueva_alarma.setTexto(texto_extraido.substring(100, 161)); // 61
		nueva_alarma.setId_estacion(texto_extraido.substring(161, 164)); // 3

		return nueva_alarma;
	}

	public List<ArchAlarma> getAlarmas() {

		return lista_alarmas;
	}

	public void liberarLista() {
		lista_alarmas.clear();
	}
}
