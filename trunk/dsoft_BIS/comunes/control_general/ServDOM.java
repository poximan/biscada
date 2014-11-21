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

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDOM() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			File f = new File(ServPropiedades.getInstancia().getProperty("Persistencia.DIRECCION_PU"));
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
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

	public void ModificarXML() {

		try {

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ServPropiedades.getInstancia().getProperty(
					"Persistencia.DIRECCION_PU")));
			transformer.transform(source, result);

			System.out.println("Done");

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