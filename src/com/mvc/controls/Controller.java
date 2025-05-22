package com.mvc.controls;

import com.mvc.view.MainView;

public class Controller {

	private MainView mainView;
	private MenuController menuController;
	private UniversityController universidadController;
	private SchoolController escuelaController;
	private CoursesController cursosController;
	private TeacherController teacherController;
	private ConsultasController consultasController;

	public Controller(MainView mainView) {
		this.mainView = mainView;

		// Inicializar los controladores específicos
		menuController = new MenuController(mainView);
		universidadController = new UniversityController(mainView);
		escuelaController = new SchoolController(mainView, universidadController);
		cursosController = new CoursesController(mainView, universidadController);
		teacherController = new TeacherController(mainView);
		
		consultasController = new ConsultasController(mainView, universidadController);

		// Mostrar la vista
		mainView.setVisible(true);
	}
}