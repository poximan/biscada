/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.consultas;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import main.java.com.servicoop.app.bi.entidades.ComponenteConsulta;
import main.java.com.servicoop.app.bi.entidades.ComponenteMenuConsulta;
import main.java.com.servicoop.app.bi.entidades.ComponenteMenuDimension;
import main.java.com.servicoop.app.bi.vistas.eventos.EventoConsultaCompuesta;
import main.java.com.servicoop.app.bi.vistas.eventos.EventoManejable;
import main.java.com.servicoop.app.comunes.vistas.EventoConfigurable;
import main.java.com.servicoop.app.comunes.vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, lo mismo que bi.vistas.consultas.VistaConsultaSimple pero
 * reacciono diferente una vez obtenido el resultado de la consulta
 *
 * soy desplegado por el gestor
 * bi.vistas.eventos.EventoComponenteMenuDimension.actionPerformed(...) que es
 * el gestor para los eventos generados desde los menues superiores en el panel
 * de dimensiones.
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, soy una interfaz visual que cubre las necesidades del usuario
 * para realizar una consulta a BD. puedo filtrar la busqueda segun cada campo o
 * una combinacion de ellos. ademas, a traves del menu que me provee mi JFrame
 * bi.entidades.ComponenteMenuConsulta puedo anexar otros filtros adicionales.
 *
 * cuando obtengo el resultado de la consulta, lo transfieron a la ventana que
 * solicito una consulta adicional para realizar comparacion contra un set
 * patron.
 *
 * LO QUE CONOZCO,
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL,
 *
 * COMO INTERACTUO CON MI COLABORADOR,
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

	public VistaConsultaCompuesta(ComponenteMenuDimension componenteMenuDimension,
			ComponenteMenuConsulta frame_menu_bi) {

		this.frame_menu_bi = frame_menu_bi;
		this.componenteMenuDimension = componenteMenuDimension;

		iniciarComponentes();
		configEventos();
	}

	@Override
	public void configEventos() {

		EventoConsultaCompuesta eventos = new EventoConsultaCompuesta(this);
		btnUsarConsulta.addActionListener(eventos);
	}

	public JButton getBtnUsarConsulta() {
		return btnUsarConsulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public ComponenteConsulta getComponenteConsulta() {
		return componenteConsulta;
	}

	@Override
	public void iniciarComponentes() {

		panelComparacion = new JPanel();
		panelComparacion.setAlignmentX(Component.RIGHT_ALIGNMENT);

		componenteConsulta = new ComponenteConsulta(frame_menu_bi);

		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(componenteConsulta, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430,
										Short.MAX_VALUE)
								.addComponent(panelComparacion, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(componenteConsulta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(panelComparacion, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
		panelComparacion.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnUsarConsulta = new JButton("usar esta consulta");
		btnUsarConsulta.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnUsarConsulta.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelComparacion.add(btnUsarConsulta);

		setLayout(groupLayout);
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
