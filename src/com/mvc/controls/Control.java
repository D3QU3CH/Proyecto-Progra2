package com.mvc.controls;

import com.mvc.view.MainView;

public class Control {

    private MainView varMainView;
    private MenuController varMenuController;
    private UniversidadController varUniversityController;
    private EscuelaController varSchoolController;
    private CursosController varCourseController;

    public Control(MainView pMainView) {
        this.varMainView = pMainView;

        // Initialize specific controllers
        varMenuController = new MenuController(pMainView);
        varUniversityController = new UniversidadController(pMainView);
        varSchoolController = new EscuelaController(pMainView, varUniversityController);
        varCourseController = new CursosController(pMainView);

        // Show the view
        varMainView.setVisible(true);
    }
}