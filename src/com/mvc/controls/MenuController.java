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
            //Listener para el boton Profesores - NUEVO
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("PROFESORES");
                }
            },
            //Listener para el boton Buscar por escuela
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("BUSQUEDA");
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CONSULTASPANEL");
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CONSULTASPANELPORCURSO");
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CONSULAPORCEDULA");
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("CONSULTAPORESCUELA");
                }
            }
        );
        
        mainView.btnConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("CONSULTAS");
            }
        });

        // Listener para el botón Regresar del panel de consultas
        mainView.btnRegresarConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("PROFESORES");
            }
        });
    }
}