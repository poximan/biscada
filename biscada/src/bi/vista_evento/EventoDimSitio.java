/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import bi.vista_IU.TableModelMedicionTemporal;
import bi.vista_IU.VistaDimSitioSimple;
import bi.vista_IU.VistaKpiSitioCalidadServicio;
import comunes.modelo.Sitio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoDimSitio extends EventoDim implements MouseListener, VentanaLanzable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimSitio(VistaDimSitioSimple vista_dimension) {
		super(vista_dimension);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		super.actionPerformed(evt);

		if (evt.getSource() == getVista_dimension().getBtnCalidadServicio()) {

			JFrame frame = new JFrame();
			lanzarVentana(frame, new VistaKpiSitioCalidadServicio(getVista_dimension().getValoresTabla()));
		}
	}

	@Override
	public void lanzarVentana(JFrame frame, JPanel vista) {

		frame.setContentPane(vista);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		JTable tabla = (JTable) evt.getSource();
		int fila = tabla.getSelectedRow();
		Sitio sitio_actual = (Sitio) tabla.getValueAt(fila, 0);

		float valores[] = ((TableModelMedicionTemporal) getVista_dimension().getComponenteTabla().getTbl_medicion()
				.getModel()).getDatosFila(fila);

		System.out.println("encontrando nombre sitio " + sitio_actual.getDescripcion());

		int maximo_arreglo = getVista_dimension().getServ_unidad_tiempo().getEncabezado().length;
		valores = Arrays.copyOf(valores, maximo_arreglo);

		JFrame frame = new JFrame(sitio_actual.getDescripcion());
		lanzarVentana(frame,
				new VistaKpiSitioCalidadServicio(getVista_dimension().getServ_dim_sitio(),
						getVista_dimension().getServ_unidad_tiempo(), getVista_dimension().getServ_medicion(),
						getVista_dimension().getServ_intervalo(), sitio_actual, valores));
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
	}

	@Override
	public void mouseExited(MouseEvent evt) {
	}

	@Override
	public void mousePressed(MouseEvent evt) {
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
	}
}