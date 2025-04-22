package com.mvc.controls;

import com.mvc.view.MainView;

public class Control {

    private MainView mainView;
    private MenuController menuController;
    private UniversidadController universidadController;
    private EscuelaController escuelaController;
    private CursosController cursosController;

    public Control(MainView mainView) {
        this.mainView = mainView;
        
        //Inicializar los controladores específicos
        menuController = new MenuController(mainView);
        universidadController = new UniversidadController(mainView);
        escuelaController = new EscuelaController(mainView, universidadController);
        cursosController = new CursosController(mainView, universidadController);
        
        //Mostrar la vista
        mainView.setVisible(true);
    }
}