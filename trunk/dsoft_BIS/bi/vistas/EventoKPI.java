/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.ui.Spinner;

import sun.security.action.GetIntegerAction;
import sun.security.jca.GetInstance;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoKPI implements ChangeListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaKpiAbstract vista_kpi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoKPI(VistaKpiAbstract vista_kpi) {
		this.vista_kpi = vista_kpi;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void stateChanged(ChangeEvent e) {
		//- TODO jorge: aca pone lo que quieran hacer con este evento
		
		vista_kpi.getIndicador_kpi().Porcentaje((int)vista_kpi.getSpinner_porcentaje().getValue());
		vista_kpi.getIndicador_kpi().validate();
		
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

}