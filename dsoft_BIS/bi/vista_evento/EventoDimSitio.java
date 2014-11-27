/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTable;

import modelo.Sitio;
import vista_IU.TableModelMedicionTemporal;
import vista_IU.VistaDimSitio;
import vista_IU.VistaKpiAbstract;
import vista_IU.VistaKpiSitioCalidadServicio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoDimSitio extends EventoDim implements MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimSitio(VistaDimSitio vista_dimension) {
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

	public void lanzarVentana(JFrame frame, VistaKpiAbstract vista_dimension) {

		frame.setContentPane(vista_dimension);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		JTable tabla = (JTable) evt.getSource();
		int fila = tabla.getSelectedRow();
		Sitio sitio_actual = (Sitio) tabla.getValueAt(fila, 0);

		float valores[] = ((TableModelMedicionTemporal) getVista_dimension().getTbl_medicion().getModel())
				.getDatosFila(fila);

		System.out.println("encontrando nombre sitio " + sitio_actual.getDescripcion());

		int maximo_arreglo = getVista_dimension().getServ_unidad_tiempo().getEncabezado().length;
		valores = Arrays.copyOf(valores, maximo_arreglo);

		JFrame frame = new JFrame(sitio_actual.getDescripcion());
		lanzarVentana(frame, new VistaKpiSitioCalidadServicio(getVista_dimension().getServ_dim_sitio(),
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