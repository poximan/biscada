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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

	private JTextField txtDireccionFuente;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CompSeleccionarDireccion(JTextField txtDireccionFuente) {

		this.txtDireccionFuente = txtDireccionFuente;
		File origen_datos = new File(txtDireccionFuente.getText());

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(origen_datos);
		chooser.setDialogTitle("Seleccione origen/destino para los datos");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		if (!origen_datos.exists())
			JOptionPane.showMessageDialog(new JFrame("Error"), "Carpeta origen de datos no existe", "Backup problem",
					JOptionPane.ERROR_MESSAGE);

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);

		btnCambiar = new JButton("Cambiar...");
		btnCambiar.addActionListener(this);
		add(btnCambiar);

		actualizarDireccion();
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

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			actualizarDireccion();
	}

	/**
	 * carga en la barra de texto solo direcciones validades por el navegador de carpetas (en esta implementacion
	 * jfilechooser)
	 */
	private void actualizarDireccion() {

		String directorio_actual = null, archivo_seleccionado;

		try {
			directorio_actual = chooser.getCurrentDirectory().getPath();
			archivo_seleccionado = chooser.getSelectedFile().getPath();
			archivo_seleccionado = archivo_seleccionado.substring(archivo_seleccionado.lastIndexOf("\\"));
		}
		catch (NullPointerException excepcion) {
			archivo_seleccionado = "";
		}
		txtDireccionFuente.setText(directorio_actual + archivo_seleccionado);
	}
	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */
}