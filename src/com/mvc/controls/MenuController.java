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
            // Listener para el botón Universidad
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("UNIVERSIDAD");
                }
            },
            // Listener para el botón Escuelas
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("ESCUELAS");
                }
            },
            // Listener para el botón Cursos
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CURSOS");
                }
            }
        );
    }
}