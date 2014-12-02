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
import modelo.ComponenteMenuDimension;
import vista_evento.EventoConsultaCompuesta;
import vista_evento.EventoManejable;
import vistas.EventoConfigurable;
import vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * reutiliza los servicios de consulta a base de datos, pero una vez devuelta la consulta la ventana se cierra,
 * entregando la lista a la ventana que solicito una consulta adicional para realizar comparacion contra una consulta
 * origen
 * 
 * @author hdonato
 *
 */
public class VistaConsultaCompuesta extends JPanel implements PanelIniciable, EventoConfigurable, EventoManejable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private GroupLayout groupLayout;

	private JPanel panelComparacion;

	private ComponenteMenuConsulta frame_menu_bi;
	private ComponenteConsulta componenteConsulta;
	private ComponenteMenuDimension componenteMenuDimension;

	private JButton btnUsarConsulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaConsultaCompuesta(ComponenteMenuDimension componenteMenuDimension, ComponenteMenuConsulta frame_menu_bi) {

		this.frame_menu_bi = frame_menu_bi;
		this.componenteMenuDimension = componenteMenuDimension;

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

		btnUsarConsulta = new JButton("usar esta consulta");
		btnUsarConsulta.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnUsarConsulta.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelComparacion.add(btnUsarConsulta);

		setLayout(groupLayout);
	}

	@Override
	public void configEventos() {

		EventoConsultaCompuesta eventos = new EventoConsultaCompuesta(this);
		btnUsarConsulta.addActionListener(eventos);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtnUsarConsulta() {
		return btnUsarConsulta;
	}

	public ComponenteConsulta getComponenteConsulta() {
		return componenteConsulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	@Override
	public void resolver() {
		componenteMenuDimension.setConsulta_comparador(componenteConsulta.getConsultas());
		frame_menu_bi.dispose();
	}
}