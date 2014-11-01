/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoETL implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(EventoETL.class);

	private VistaETL vista_etl;
	private JFrame frame_consultas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoETL(VistaETL vista_etl) {
		this.vista_etl = vista_etl;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_etl.getBtn_iniciar()) {

			frame_consultas = new JFrame("BIS - software BI para SCADA");
			log.trace("se crea marco para panel consultas");

			frame_consultas.setContentPane(new VistaConsultas());
			log.trace("se lanza pantalla de consultas");

			frame_consultas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame_consultas.pack();
			frame_consultas.setVisible(true);

		} else
			if (evt.getSource() == vista_etl.getBtn_guardar()) {
				vista_etl.modificarListas();
			} else
				if (evt.getSource() == vista_etl.getBtn_reiniciar()) {
					vista_etl.restablecerListas();
				} else
					if (evt.getSource() == vista_etl.getBtn_agregar_candidato_extraer()) {
						vista_etl.agregarCandidatoExtraer();
					} else
						if (evt.getSource() == vista_etl.getBtn_agregar_candidato_procesar()) {
							vista_etl.agregarCandidatoProcesar();
						} else
							if (evt.getSource() == vista_etl.getBtn_remover_candidato_extraer()) {
								vista_etl.removerCandidatoExtraer();
							} else
								if (evt.getSource() == vista_etl.getBtn_remover_candidato_procesar()) {
									vista_etl.removerCandidatoProcesar();
								}
	}
}