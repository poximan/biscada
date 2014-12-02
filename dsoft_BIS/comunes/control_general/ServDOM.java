/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServDOM {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Document doc;

	private Node marca_persistence;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDOM() {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			doc = docBuilder.parse(ServPropiedades.getInstancia().getProperty("Conexion.DIRECCION_PU"));
			marca_persistence = doc.getFirstChild();
		}
		catch (ParserConfigurationException excepcion) {
			excepcion.printStackTrace();
		}
		catch (SAXException | IOException excepcion) {
			excepcion.printStackTrace();
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@SuppressWarnings("unused")
	public void modificarXML(String nuevo_usuario) {

		Node marca_clase = marca_persistence.getFirstChild();
		Node marca_properties = doc.getElementsByTagName("properties").item(0);
		NamedNodeMap mapa_propiedades = marca_properties.getAttributes();
		Node nodo_usuario = mapa_propiedades.getNamedItem("propiedad_usuario");
		// nodo_usuario.setTextContent(nuevo_usuario);

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ServPropiedades.getInstancia().getProperty(
					"Conexion.DIRECCION_PU")));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
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