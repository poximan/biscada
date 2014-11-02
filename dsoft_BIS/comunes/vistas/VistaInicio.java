/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import control_general.MainBI;
import control_general.MainETL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaInicio extends Application {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaInicio.class);

	private BorderPane root;
	private VBox topContainer;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void start(Stage primaryStage) {

		root = new BorderPane();
		topContainer = new VBox();

		MenuBar menu_principal = new MenuBar();
		ToolBar barra_herramientas = new ToolBar();

		agregarOpcionesMenu(menu_principal);
		agregarOpcionesBarraHerramientas(barra_herramientas);

		topContainer.getChildren().add(menu_principal);
		topContainer.getChildren().add(barra_herramientas);

		root.setTop(topContainer);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Solucion BI para usar datos de SCADA");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void agregarOpcionesBarraHerramientas(ToolBar toolBar) {

		Button btn_etl = new Button("gestion archivos");
		btn_etl.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				new MainETL();
			};
		});

		Button btn_bi = new Button("analisis de datos");
		btn_bi.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				new MainBI();
			};
		});

		toolBar.getItems().addAll(btn_etl, btn_bi);
	}

	private void agregarOpcionesMenu(MenuBar mainMenu) {

		Menu menu_archivo = new Menu("Archivo");
		MenuItem exitApp = new MenuItem("Salir");
		menu_archivo.getItems().addAll(exitApp);

		Menu menu_edicion = new Menu("Edicion");
		MenuItem properties = new MenuItem("Propiedades");
		menu_edicion.getItems().add(properties);

		Menu menu_ayuda = new Menu("Ayuda");

		mainMenu.getMenus().addAll(menu_archivo, menu_edicion, menu_ayuda);
	}

	public static void main(String[] args) {

		PropertyConfigurator.configure("log4j.properties");

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			log.error(MainETL.class.getName());
		}

		launch();
	}
}