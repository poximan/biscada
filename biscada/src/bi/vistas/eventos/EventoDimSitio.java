/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTable;

import bi.vistas.consultas.TableModelMedicionTemporal;
import bi.vistas.dimensiones.VistaDimSitioSimple;
import bi.vistas.kpi.VistaKpiSitioCalidadServicio;
import comunes.modelo.Sitio;

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

	public EventoDimSitio(VistaDimSitioSimple vista_dimension) {
		super(vista_dimension);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void mouseClicked(MouseEvent evt) {

		JTable tabla = (JTable) evt.getSource();
		int fila = tabla.getSelectedRow();
		Sitio sitio_actual = (Sitio) tabla.getValueAt(fila, 0);

		float valores[] = ((TableModelMedicionTemporal) getVista_dimension().getComponenteTabla().getTbl_medicion()
				.getModel()).getDatosFila(fila);

		int maximo_arreglo = getVista_dimension().getServ_periodo().getEncabezado().length;
		valores = Arrays.copyOf(valores, maximo_arreglo);

		JFrame frame = new JFrame(sitio_actual.getDescripcion());
		lanzarVentana(frame, new VistaKpiSitioCalidadServicio(valores));
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