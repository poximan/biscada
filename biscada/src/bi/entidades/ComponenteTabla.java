/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.entidades;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import bi.controles.servicios.dimensiones.ServDimAbstract;
import bi.vistas.consultas.TableModelEntradaFila;
import bi.vistas.consultas.TableModelMedicionTemporal;
import comunes.vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponenteTabla extends JPanel implements PanelIniciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ComponenteTabla.class);

	private static final long serialVersionUID = 1L;

	private GroupLayout groupLayout;

	private JTextField txtPrimera;
	private JTextField txtUltima;

	private JTable tbl_medicion;
	private JTable tbl_titulo_filas;

	private JScrollPane scrollPane;

	private JPanel pl_priUlt_alarma;

	private JLabel lblPrimera;
	private JLabel lblUltima;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponenteTabla() {

		iniciarComponentes();
		log.info("se iniciaron componentes");

		ordenarTabla();
		log.info("se ordernaron tablas");
	}

	public void contruirModeloEntradaColumnas(float[][] datos_tabla, String[] encabezado_tabla) {

		tbl_medicion.setModel(new TableModelMedicionTemporal(datos_tabla, encabezado_tabla));
		ordenarTabla();
	}

	public void contruirModeloEntradaFila(ServDimAbstract serv_dim_vista_seleccionada) {
		tbl_titulo_filas.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));
	}

	public JTable getTbl_medicion() {
		return tbl_medicion;
	}

	public JTable getTbl_titulo_filas() {
		return tbl_titulo_filas;
	}

	@Override
	public void iniciarComponentes() {

		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		pl_priUlt_alarma = new JPanel();
		pl_priUlt_alarma.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblPrimera = new JLabel("primera:");
		lblPrimera.setHorizontalAlignment(SwingConstants.RIGHT);
		pl_priUlt_alarma.add(lblPrimera);

		txtPrimera = new JTextField();
		txtPrimera.setEditable(false);
		txtPrimera.setColumns(10);
		pl_priUlt_alarma.add(txtPrimera);

		lblUltima = new JLabel("ultima:");
		lblUltima.setHorizontalAlignment(SwingConstants.RIGHT);
		pl_priUlt_alarma.add(lblUltima);

		txtUltima = new JTextField();
		txtUltima.setEditable(false);
		txtUltima.setColumns(10);
		pl_priUlt_alarma.add(txtUltima);

		groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(pl_priUlt_alarma,
												GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 93,
										Short.MAX_VALUE)
								.addComponent(pl_priUlt_alarma, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96,
										Short.MAX_VALUE))
						.addContainerGap()));

		// -------------------------------------
		//
		// seccion tablas
		// -------------------------------------

		tbl_titulo_filas = new JTable(new TableModelEntradaFila(new Object[0]));
		tbl_medicion = new JTable(new TableModelMedicionTemporal(new float[0][0], new String[] { "" }));

		tbl_medicion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tbl_medicion);

		tbl_titulo_filas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setRowHeaderView(tbl_titulo_filas);

		// recuperar el tama�o preferido en caso que la tabla este contenida
		// en
		// un scroll
		Dimension nueva_dimension = tbl_titulo_filas.getPreferredScrollableViewportSize();
		// define el tama�o preferido de la tabla
		nueva_dimension.width = tbl_titulo_filas.getPreferredSize().width + 90;
		tbl_titulo_filas.setPreferredScrollableViewportSize(nueva_dimension);
		tbl_titulo_filas.setIntercellSpacing(new Dimension(0, 0));

		// recuperar el tama�o preferido en caso que la tabla este contenida
		// en
		// un scroll
		nueva_dimension = tbl_medicion.getPreferredScrollableViewportSize();
		tbl_medicion.setPreferredScrollableViewportSize(nueva_dimension);
		tbl_medicion.setIntercellSpacing(new Dimension(0, 0));

		setLayout(groupLayout);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	private void ordenarTabla() {

		RowSorter<TableModel> ordenador_filas1 = new TableRowSorter<TableModel>(tbl_medicion.getModel());
		tbl_medicion.setRowSorter(ordenador_filas1);
		tbl_titulo_filas.setRowSorter(ordenador_filas1);

		RowSorterListener l = new RowSorterListener() {
			@Override
			public void sorterChanged(RowSorterEvent e) {

				if (RowSorterEvent.Type.SORT_ORDER_CHANGED == e.getType()) {
					@SuppressWarnings("unchecked")
					RowSorter<Float> sorter = e.getSource();
					tbl_medicion.getRowSorter().setSortKeys(sorter.getSortKeys());
					tbl_titulo_filas.getRowSorter().setSortKeys(sorter.getSortKeys());
				}
			}
		};
		tbl_medicion.getRowSorter().addRowSorterListener(l);
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setIntervalo(IntervaloFechas intervalo) {

		txtPrimera.setText(intervalo.getFechaCorta(intervalo.getPrimer_alarma()));
		txtUltima.setText(intervalo.getFechaCorta(intervalo.getUltima_alarma()));
	}
}