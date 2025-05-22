package com.mvc.controls;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControllerPanelConsultas {
	 
		public JPanel panel;
	    public JTextField campoBuscar;
	    public JTextArea areaMostrar;
	    public JButton botonBuscar;
	    public JButton botonVolver;

	    public ControllerPanelConsultas(JPanel panel, JTextField campoBuscar, JTextArea areaMostrar,
	                         JButton botonBuscar, JButton botonVolver) {
	        this.panel = panel;
	        this.campoBuscar = campoBuscar;
	        this.areaMostrar = areaMostrar;
	        this.botonBuscar = botonBuscar;
	        this.botonVolver = botonVolver;
	    }
}
