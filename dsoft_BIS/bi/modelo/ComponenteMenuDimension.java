/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import vista_IU.VistaConsultaCompuesta;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponenteMenuDimension extends JFrame {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JMenuBar barra_menu;

	private JMenu entrada_menu_archivo;
	private JMenu entrada_menu_comparar;

	private List<Alarma> consulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponenteMenuDimension(String titulo) {

		super(titulo);

		setBounds(100, 100, 1110, 701);
		configMenu();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private void configMenu() {

		// barra menu
		barra_menu = new JMenuBar();

		configMenuArchivo();
		configMenuComparar();

		setJMenuBar(barra_menu);
	}

	private void configMenuArchivo() {

		// submeun
		entrada_menu_archivo = new JMenu("Archivo");
		entrada_menu_archivo.setMinimumSize(getMinimumSize());

		JMenuItem item_salir = new JMenuItem("Salir");
		item_salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		item_salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		// agregar opciones del menu
		entrada_menu_archivo.add(item_salir);
		entrada_menu_archivo.addSeparator();

		// agregar menu a la barra
		barra_menu.add(entrada_menu_archivo);
	}

	private void configMenuComparar() {

		// submeun
		entrada_menu_comparar = new JMenu("Comparar periodos");
		entrada_menu_comparar.setMinimumSize(getMinimumSize());

		JMenuItem item_configurar = new JMenuItem("Configurar");
		item_configurar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				ComponenteMenuConsulta frame_menu_bi = new ComponenteMenuConsulta(
						"BIS - consulta para usar como comparador");
				frame_menu_bi.setContentPane(new VistaConsultaCompuesta(ComponenteMenuDimension.this, frame_menu_bi));

				frame_menu_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame_menu_bi.setVisible(true);
			}
		});

		JMenu submenu_usar_ultima = new JMenu("Ultima consulta");
		JMenuItem item_ejecutar = new JMenuItem("Ejecutar");
		submenu_usar_ultima.add(item_ejecutar);

		entrada_menu_comparar.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		// agregar opciones del menu
		entrada_menu_comparar.add(item_configurar);
		entrada_menu_comparar.addSeparator();

		entrada_menu_comparar.add(submenu_usar_ultima);
		entrada_menu_comparar.addSeparator();

		// agregar menu a la barra
		barra_menu.add(entrada_menu_comparar);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public List<Alarma> getConsulta() {
		return consulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setConsulta(List<Alarma> consulta) {
		this.consulta = consulta;
	}
}
