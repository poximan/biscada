/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.modelo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import bi.vista_evento.EventoComponenteMenuDimension;
import comunes.modelo.Alarma;
import comunes.vistas.EventoConfigurable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponenteMenuDimension extends JFrame implements EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JMenuBar barra_menu;

	private JMenu entrada_menu_archivo;
	private JMenu entrada_menu_comparar;

	private JMenuItem item_salir;
	private JMenuItem item_configurar;
	private JMenuItem item_ejecutar;

	private List<Alarma> consulta_comparador;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponenteMenuDimension(String titulo) {

		super(titulo);

		setBounds(100, 100, 723, 605);
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
		configEventos();

		setJMenuBar(barra_menu);
	}

	private void configMenuArchivo() {

		// submenu
		entrada_menu_archivo = new JMenu("Archivo");
		entrada_menu_archivo.setMinimumSize(getMinimumSize());

		item_salir = new JMenuItem("Salir");
		item_salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		// agregar opciones del menu
		entrada_menu_archivo.add(item_salir);
		entrada_menu_archivo.addSeparator();

		// agregar menu a la barra
		barra_menu.add(entrada_menu_archivo);
	}

	private void configMenuComparar() {

		// submenu
		entrada_menu_comparar = new JMenu("Comparar periodos");
		entrada_menu_comparar.setMinimumSize(getMinimumSize());

		item_configurar = new JMenuItem("Configurar");

		item_ejecutar = new JMenuItem("Ejecutar");

		// agregar opciones del menu
		entrada_menu_comparar.add(item_configurar);
		entrada_menu_comparar.add(item_ejecutar);
		entrada_menu_comparar.addSeparator();

		// agregar menu a la barra
		barra_menu.add(entrada_menu_comparar);
	}

	@Override
	public void configEventos() {

		EventoComponenteMenuDimension evento = new EventoComponenteMenuDimension(this);

		item_salir.addActionListener(evento);
		item_configurar.addActionListener(evento);
		item_ejecutar.addActionListener(evento);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public List<Alarma> getConsulta_comparador() {
		return consulta_comparador;
	}

	public JMenuItem getItem_salir() {
		return item_salir;
	}

	public JMenuItem getItem_configurar() {
		return item_configurar;
	}

	public JMenuItem getItem_ejecutar() {
		return item_ejecutar;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setConsulta_comparador(List<Alarma> consulta_comparador) {
		this.consulta_comparador = consulta_comparador;
	}
}
