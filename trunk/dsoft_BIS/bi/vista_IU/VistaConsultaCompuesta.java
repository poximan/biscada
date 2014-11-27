/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import modelo.ComponenteConsulta;
import modelo.ComponenteMenuConsulta;
import vista_evento.EventoConsultaCompuesta;
import vistas.EventoConfigurable;
import vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaConsultaCompuesta extends JPanel implements PanelIniciable, EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private GroupLayout groupLayout;

	private ComponenteMenuConsulta frame_menu_bi;

	private JPanel panelComparacion;

	private ComponenteConsulta componenteConsulta;
	private JButton btnGenerarComparacion;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaConsultaCompuesta(ComponenteMenuConsulta frame_menu_bi) {

		this.frame_menu_bi = frame_menu_bi;
		iniciarComponentes();
		configEventos();
	}

	@Override
	public void iniciarComponentes() {

		panelComparacion = new JPanel();
		panelComparacion.setAlignmentX(Component.RIGHT_ALIGNMENT);

		componenteConsulta = new ComponenteConsulta(frame_menu_bi);

		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addComponent(componenteConsulta, Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
												.addComponent(panelComparacion, GroupLayout.DEFAULT_SIZE, 430,
														Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(componenteConsulta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelComparacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		panelComparacion.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnGenerarComparacion = new JButton("generar comparacion");
		btnGenerarComparacion.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnGenerarComparacion.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelComparacion.add(btnGenerarComparacion);

		setLayout(groupLayout);
	}

	@Override
	public void configEventos() {

		EventoConsultaCompuesta eventos = new EventoConsultaCompuesta(this);
		btnGenerarComparacion.addActionListener(eventos);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtnUtilizarConsulta() {
		return btnGenerarComparacion;
	}

	public ComponenteConsulta getComponenteConsulta() {
		return componenteConsulta;
	}
}