/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.dbf;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/**
 * represento una fila de alarma extraida de un archivo dbf. mis campos todos
 * ellos String, corresponden con cada uno de los campos de la fila en el
 * archivo origen
 * 
 * @author hugo
 * 
 */
public class ArchAlarma {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String START_SEC; // 10
	private String START_MILI; // 3
	private String END_TIME; // 10
	private String END_MILI; // 3
	private String ACK_TIME; // 10
	private String ACK_NAME; // 9
	private String SEVERITY; // 5
	private String CLASS; // 10
	private String ZONE; // 5
	private String ATTR; // 10
	private String IDENT; // 8
	private String NAME; // 16
	private String TEXT; // 61
	private String STATION_ID; // 3

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ArchAlarma() {
		super();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public String toString() {

		return "\n" + //
				"Ini -> " + "seg: " + START_SEC + " / " + "mili: " + START_MILI + "\n" + //
				"Fin -> " + "seg: " + END_TIME + " / " + "mili: " + END_MILI + "\n" + //
				"Ack -> " + "seg: " + ACK_TIME + " / " + "nombre: " + ACK_NAME + "\n" + //
				"Severidad: " + SEVERITY + "\n" + //
				"Clase: " + CLASS + "\n" + //
				"Zona: " + ZONE + "\n" + //
				"Atributo: " + ATTR + "\n" + //
				"Identificacion: " + IDENT + "\n" + //
				"Nombre: " + NAME + "\n" + //
				"Texto: " + TEXT + "\n" + //
				"Id estacion: " + STATION_ID + "\n"//
		;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public String getSTART_SEC() {
		return START_SEC;
	}

	public String getSTART_MILI() {
		return START_MILI;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public String getEND_MILI() {
		return END_MILI;
	}

	public String getACK_TIME() {
		return ACK_TIME;
	}

	public String getACK_NAME() {
		return ACK_NAME;
	}

	public String getSEVERITY() {
		return SEVERITY;
	}

	public String getCLASS() {
		return CLASS;
	}

	public String getZONE() {
		return ZONE;
	}

	public String getATTR() {
		return ATTR;
	}

	public String getIDENT() {
		return IDENT;
	}

	public String getNAME() {
		return NAME;
	}

	public String getTEXT() {
		return TEXT;
	}

	public String getSTATION_ID() {
		return STATION_ID;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setSTART_SEC(String sTART_SEC) {
		START_SEC = sTART_SEC;
	}

	public void setSTART_MILI(String sTART_MILI) {
		START_MILI = sTART_MILI;
	}

	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}

	public void setEND_MILI(String eND_MILI) {
		END_MILI = eND_MILI;
	}

	public void setACK_TIME(String aCK_TIME) {
		ACK_TIME = aCK_TIME;
	}

	public void setACK_NAME(String aCK_NAME) {
		ACK_NAME = aCK_NAME;
	}

	public void setSEVERITY(String sEVERITY) {
		SEVERITY = sEVERITY;
	}

	public void setCLASS(String cLASS) {
		CLASS = cLASS;
	}

	public void setZONE(String zONE) {
		ZONE = zONE;
	}

	public void setATTR(String aTTR) {
		ATTR = aTTR;
	}

	public void setIDENT(String iDENT) {
		IDENT = iDENT;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public void setTEXT(String tEXT) {
		TEXT = tEXT;
	}

	public void setSTATION_ID(String sTATION_ID) {
		STATION_ID = sTATION_ID;
	}
}
