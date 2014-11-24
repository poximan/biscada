/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import control_general.ServPropiedades;
import control_general.ServReiniciarApp;
import excepciones.ReiniciarAplicacionExcepcion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoPropiedades implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaPropiedades vista_propiedades;

	private JSpinner spinnerAceptacion;
	private JSpinner spinnerTiempoMaximo;
	private JSpinner spinnerTiempoMinimo;

	private JTextField txt_direccion_fuente;
	private JTextField txtURL;
	private JTextField txtUsuario;
	private JTextField txtContrasenia;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoPropiedades(VistaPropiedades vista_propiedades, JSpinner spinnerAceptacion,
			JSpinner spinnerTiempoMaximo, JSpinner spinnerTiempoMinimo, JTextField txt_direccion_fuente,
			JTextField txtURL, JTextField txtUsuario, JTextField txtContrasenia) {

		this.vista_propiedades = vista_propiedades;

		this.spinnerAceptacion = spinnerAceptacion;
		this.spinnerTiempoMaximo = spinnerTiempoMaximo;
		this.spinnerTiempoMinimo = spinnerTiempoMinimo;

		this.txt_direccion_fuente = txt_direccion_fuente;
		this.txtURL = txtURL;
		this.txtUsuario = txtUsuario;
		this.txtContrasenia = txtContrasenia;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_propiedades.getBtnConfirmar()) {
			actualizarArchivoPropiedades();
		} else
			if (evt.getSource() == vista_propiedades.getBtnPorDefecto())
				actualizarVistaPropiedades();
	}

	private void actualizarArchivoPropiedades() {

		ReiniciarAplicacionExcepcion reiniciar = null;
		/*
		 * graficos
		 */
		ServPropiedades.getInstancia().setProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA",
				String.valueOf(spinnerAceptacion.getModel().getValue()));

		/*
		 * datos
		 */
		ServPropiedades.getInstancia().setProperty("Datos.DIRECCION_LECTURA_DATOS", txt_direccion_fuente.getText());

		/*
		 * ruido
		 */
		ServPropiedades.getInstancia().setProperty("Ruido.MINIMA_DURACION_ALARMA",
				String.valueOf(spinnerTiempoMaximo.getModel().getValue()));
		ServPropiedades.getInstancia().setProperty("Ruido.MAXIMA_DURACION_ALARMA",
				String.valueOf(spinnerTiempoMinimo.getModel().getValue()));

		try {
			analizarCambiosPU();
		}
		catch (ReiniciarAplicacionExcepcion excepcion) {
			reiniciar = excepcion;
		}

		finally {

			/*
			 * conexion
			 */
			ServPropiedades.getInstancia().setProperty("Conexion.URL", txtURL.getText());
			ServPropiedades.getInstancia().setProperty("Conexion.USUARIO", txtUsuario.getText());
			ServPropiedades.getInstancia().setProperty("Conexion.CONTRASENIA", txtContrasenia.getText());

			ServPropiedades.guardarCambios();

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(vista_propiedades);

			if (reiniciar == null)
				frame.dispose();
			else {
				ServReiniciarApp ejecutar_nuevamente = new ServReiniciarApp();
				ejecutar_nuevamente.reinciarAplicacion();
			}
		}
	}

	private void actualizarVistaPropiedades() {

		/*
		 * graficos
		 */
		spinnerAceptacion.getModel().setValue(
				Integer.valueOf(ServPropiedades.getInstancia().getProperty(
						"Defecto.Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA")));

		/*
		 * datos
		 */
		txt_direccion_fuente.setText(ServPropiedades.getInstancia()
				.getProperty("Defecto.Datos.DIRECCION_LECTURA_DATOS"));

		/*
		 * ruido
		 */
		spinnerTiempoMaximo.getModel().setValue(
				Integer.valueOf(ServPropiedades.getInstancia().getProperty("Defecto.Ruido.MINIMA_DURACION_ALARMA")));
		spinnerTiempoMinimo.getModel().setValue(
				Integer.valueOf(ServPropiedades.getInstancia().getProperty("Defecto.Ruido.MAXIMA_DURACION_ALARMA")));

		/*
		 * conexion
		 */
		txtURL.setText(ServPropiedades.getInstancia().getProperty("Defecto.Conexion.URL"));
		txtUsuario.setText(ServPropiedades.getInstancia().getProperty("Defecto.Conexion.USUARIO"));
		txtContrasenia.setText(ServPropiedades.getInstancia().getProperty("Defecto.Conexion.CONTRASENIA"));
	}

	private void analizarCambiosPU() throws ReiniciarAplicacionExcepcion {

		int respuesta_usuario = JOptionPane.NO_OPTION;

		if (!ServPropiedades.getInstancia().getProperty("Conexion.URL").equals(txtURL.getText())
				|| !ServPropiedades.getInstancia().getProperty("Conexion.USUARIO").equals(txtUsuario.getText())
				|| !ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA").equals(txtContrasenia.getText()))

			respuesta_usuario = JOptionPane.showConfirmDialog((Component) null,
					"para nuevos parametros de conexion, la aplicacion debe reiniciar", "¿desea reiniciar?",
					JOptionPane.YES_NO_OPTION);

		if (!deseaReiniciar(respuesta_usuario))
			cambiarPU();

		if (deseaReiniciar(respuesta_usuario))
			throw new ReiniciarAplicacionExcepcion();
	}

	private void cambiarPU() {

		txtURL.setText(ServPropiedades.getInstancia().getProperty("Conexion.URL"));
		txtUsuario.setText(ServPropiedades.getInstancia().getProperty("Conexion.USUARIO"));
		txtContrasenia.setText(ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA"));
	}

	private boolean deseaReiniciar(int usuario_acepta) {

		return (usuario_acepta == 0) ? true : false;
	}
}