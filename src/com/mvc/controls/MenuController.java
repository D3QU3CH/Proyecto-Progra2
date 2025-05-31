package com.mvc.controls;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mvc.view.MainView;
import com.mvc.view.StudentView;

public class MenuController {
    
    private MainView mainView;
    private StudentView studentView;
    
    private StudentController studentController;

    public MenuController(MainView mainView, StudentView studentView, StudentController studentController) {
        this.mainView = mainView;
        this.studentView = studentView;
        this.studentController = studentController; 
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
            },
            //Listener para el boton Profesores - NUEVO
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("PROFESORES");
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainView.showPanel("ASIGNACION PROFESORES");
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
        
        mainView.btnRegresarConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("PROFESORES");
            }
        });
        
        mainView.btnRegresarAsignacionProf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("PROFESORES");
            }
        });
        
        mainView.btnConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("CONSULTAS");
            }
        });
        
        studentView.btnMatricular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentController.mostrarEstudiadesEnPanelMatriculaCursosDisponibles();
            	studentController.mostrarEstudiadesEnPanelMatricula();
            	studentView.showPanel("MATRICULA");
            }
        });
        
        studentView.btnRegresarMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("ESTUDIANTES");
            }
        });
        
        studentView.btnBuscarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("BUSQUEDA_ESTUDIANTE");
            }
        });
        
        studentView.btnRegresarBusquedaEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("ESTUDIANTES");
            }
        });
        
        studentView.btnEstudiantesMatriculados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("ESTUDIANTES_MATRICULADOS");
            }
        });
        
        studentView.btnRegresarEstudiantesMatriculados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("MATRICULA");
            }
        });
        
        studentView.btnConsultaCursosPorEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("BUSQUEDA_CURSOS");
            }
        });
        
        studentView.btnRegresarBusquedaCursos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("MATRICULA");
            }
        });
        
        studentView.btnPagoCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("PAGO_CREDITOS");
            	studentController.pagoDeCreditos();
            }
        });
        
        studentView.btnRegresarPagoCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	studentView.showPanel("MATRICULA");
            }
        });
        
        
        
    }
}