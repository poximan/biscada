/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class CompSeleccionarDireccion extends JPanel implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JButton btnCambiar;
	private JFileChooser chooser;
	private String choosertitle;

	private JTextField txtDireccionFuente;
	private File texto_directorio;
	private File texto_archivo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CompSeleccionarDireccion(JTextField txtDireccionFuente) {

		this.txtDireccionFuente = txtDireccionFuente;

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);

		btnCambiar = new JButton("Cambiar...");
		btnCambiar.addActionListener(this);
		add(btnCambiar);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * cuando el usuario presiona el boton cambiar se despliega una ventana de navegacion para seleccionar la nueva
	 * carpeta origen de los datos para procesar por el ETL
	 */
	public void actionPerformed(ActionEvent e) {

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(txtDireccionFuente.getText()));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			texto_archivo = chooser.getSelectedFile();
			txtDireccionFuente.setText(texto_archivo.getPath());
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public File getTexto_directorio() {
		return texto_directorio;
	}

	public File getTexto_archivo() {
		return texto_archivo;
	}
}