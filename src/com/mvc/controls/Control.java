package com.mvc.controls;

import com.mvc.view.ViewRegistrarUniversidad;

public class Control {
	private ViewRegistrarUniversidad vRegistrarUniversidad;
	
	 public Control(ViewRegistrarUniversidad pRegistrarUniversidad) {
		 this.vRegistrarUniversidad=pRegistrarUniversidad;
		 vRegistrarUniversidad.setVisible(true);
	 }
}
