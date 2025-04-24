package com.mvc.controls;

import com.mvc.view.MainView;

public class Controller {

    private MainView mainView;
    private MenuController menuController;
    private UniversityController universidadController;
    private SchoolController escuelaController;
    private CoursesController cursosController;

    public Controller(MainView mainView) {
        this.mainView = mainView;
        
        //Inicializar los controladores específicos
        menuController = new MenuController(mainView);
        universidadController = new UniversityController(mainView);
        escuelaController = new SchoolController(mainView, universidadController);
        cursosController = new CoursesController(mainView, universidadController);
        
        //Mostrar la vista
        mainView.setVisible(true);
    }
}