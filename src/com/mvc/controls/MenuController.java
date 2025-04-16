package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mvc.view.MainView;

public class MenuController {
    
    private MainView mainView;
    
    public MenuController(MainView mainView) {
        this.mainView = mainView;
        setupMenuListeners();
    }
    
    private void setupMenuListeners() {
        mainView.setupMenuListeners(
            //Listener para el boton Universidad
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("UNIVERSIDAD");
                }
            },
            //Listener para el boton Escuelas
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("ESCUELAS");
                }
            },
            //Listener para el boton Cursos
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CURSOS");
                }
            },
          //Listener para el boton Buscar por escuela
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("BUSQUEDA");
                }
            } 
        );
    }
}