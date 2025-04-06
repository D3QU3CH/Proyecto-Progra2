package com.mvc.init;
import com.mvc.controls.Control;
import com.mvc.view.ViewRegistrarUniversidad;

public class Main {

	public static void main(String[] args) {
		ViewRegistrarUniversidad varRegistrarU = new ViewRegistrarUniversidad();
		new Control(varRegistrarU);
		new ViewRegistrarUniversidad();
	}

}
