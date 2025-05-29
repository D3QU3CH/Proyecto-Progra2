package com.mvc.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JFrame {

    private static final long serialVersionUID = 1L;

    public StudentView() {
        setTitle("Gestion de Estudiantes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());
        
        EstudiantesPanel();
        MatriculaEstudiantesPanel();
        
        // Agregar paneles al CardLayout
        mainPanel.add(estudiantesPanel, "ESTUDIANTES");
        mainPanel.add(matriculaPanel, "MATRICULA");
        
        add(mainPanel);
        setVisible(false);
    }

    // Panel principal con CardLayout
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Panel de estudiantes original
    public JPanel estudiantesPanel;
    private JPanel panelRegistroEstudiantes;
    private JPanel panelConsultaEstudiantes;
    private JPanel panelOpcionesEstudiantes;

    // Panel de matrícula de estudiantes
    public JPanel matriculaPanel;
    private JTextArea txtAreaEstudiantes;
    private JTextArea txtAreaCursosDisponibles;
    private JScrollPane scrollPaneEstudiantesArea;
    private JScrollPane scrollPaneCursosArea;

    // Campos del formulario de estudiantes
    private JLabel lblNombre;
    private JLabel lblApellidos;
    private JLabel lblCedula;
    private JLabel lblCarnet;
    private JLabel lblNacionalidad;
    private JLabel lblPorcentajeBeca;

    public JTextField txtNombre;
    public JTextField txtApellidos;
    public JTextField txtCedula;
    public JTextField txtCarnet;
    public JComboBox<String> boxNacionalidad;
    public JTextField txtPorcentajeBeca;

    // Campos del formulario de matrícula
    public JTextField txtCedulaEstudianteMatricula;
    public JTextField txtCedulaProfesorMatricula;
    public JTextField txtSiglaCursoMatricula;

    // Botones de estudiantes
    public JButton btnModificarEstudiante;
    public JButton btnAgregarEstudiante;
    public JButton btnEliminarEstudiante;
    public JButton btnDeseleccionarTabla;
    public JButton btnMatricular;
    public JButton btnBuscarEstudiante;
    public JButton btnRegresarGestionUniversidad;

    // Botones de matrícula
    public JButton btnMatricularEstudiante;
    public JButton btnDesmatricularEstudiante;
    public JButton btnDeseleccionarTablaMatricula;
    public JButton btnRegresarMatricula;

    // Botones de opciones de matrícula
    public JButton btnEstudiantesMatriculados;
    public JButton btnConsultaCursosPorEstudiante;
    public JButton btnPagoCreditos;

    // Tabla de estudiantes
    public JTable tablaEstudiantes;
    private JScrollPane scrollPaneEstudiantes;

    // Tabla de matrículas
    public JTable tablaMatriculas;
    private JScrollPane scrollPaneMatriculas;

    private void EstudiantesPanel() {
        estudiantesPanel = new JPanel(new BorderLayout(10, 10));
        estudiantesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitleEstudiantes = new JLabel("Agregacion de Estudiantes", JLabel.CENTER);
        lblTitleEstudiantes.setFont(new Font("Arial", Font.BOLD, 18));
        estudiantesPanel.add(lblTitleEstudiantes, BorderLayout.NORTH);

        // Panel de registro de estudiantes
        panelRegistroEstudiantes = new JPanel(new GridLayout(6, 2, 10, 10));
        panelRegistroEstudiantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        txtNombre = new JTextField();

        lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Arial", Font.BOLD, 14));
        txtApellidos = new JTextField();

        lblCedula = new JLabel("Cedula:");
        lblCedula.setFont(new Font("Arial", Font.BOLD, 14));
        txtCedula = new JTextField();

        lblCarnet = new JLabel("Carnet:");
        lblCarnet.setFont(new Font("Arial", Font.BOLD, 14));
        txtCarnet = new JTextField();

        lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setFont(new Font("Arial", Font.BOLD, 14));
        boxNacionalidad = new JComboBox<>(new String[] { "Nacional", "Extranjero" });
        boxNacionalidad.setFont(new Font("Arial", Font.PLAIN, 14));

        lblPorcentajeBeca = new JLabel("Porcentaje de Beca:");
        lblPorcentajeBeca.setFont(new Font("Arial", Font.BOLD, 14));
        txtPorcentajeBeca = new JTextField();

        // Agregar elementos al panel en el orden correcto
        panelRegistroEstudiantes.add(lblNombre);
        panelRegistroEstudiantes.add(txtNombre);
        panelRegistroEstudiantes.add(lblApellidos);
        panelRegistroEstudiantes.add(txtApellidos);
        panelRegistroEstudiantes.add(lblCedula);
        panelRegistroEstudiantes.add(txtCedula);
        panelRegistroEstudiantes.add(lblCarnet);
        panelRegistroEstudiantes.add(txtCarnet);
        panelRegistroEstudiantes.add(lblNacionalidad);
        panelRegistroEstudiantes.add(boxNacionalidad);
        panelRegistroEstudiantes.add(lblPorcentajeBeca);
        panelRegistroEstudiantes.add(txtPorcentajeBeca);

        // Panel de consulta de estudiantes
        panelConsultaEstudiantes = new JPanel(new BorderLayout(10, 10));
        panelConsultaEstudiantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnasEstudiantes = { "Nombre", "Apellidos", "Cedula", "Carnet", "Nacionalidad", "Porcentaje Beca" };
        DefaultTableModel modeloEstudiantes = new DefaultTableModel(columnasEstudiantes, 0);
        tablaEstudiantes = new JTable(modeloEstudiantes);
        tablaEstudiantes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaEstudiantes.setEnabled(true);
        tablaEstudiantes.setDefaultEditor(Object.class, null);

        scrollPaneEstudiantes = new JScrollPane(tablaEstudiantes);
        panelConsultaEstudiantes.add(scrollPaneEstudiantes, BorderLayout.CENTER);

        // Panel de botones principales
        JPanel panelBotonesPrincipales = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregarEstudiante = new JButton("Agregar Estudiante");
        btnAgregarEstudiante.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarEstudiante.setEnabled(true);

        btnEliminarEstudiante = new JButton("Eliminar Estudiante");
        btnEliminarEstudiante.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminarEstudiante.setEnabled(false);

        btnDeseleccionarTabla = new JButton("Deseleccionar Tabla");
        btnDeseleccionarTabla.setFont(new Font("Arial", Font.BOLD, 12));
        btnDeseleccionarTabla.setEnabled(false);

        btnRegresarGestionUniversidad = new JButton("Regresar a GESTION DE UNIVERSIDAD");
        btnRegresarGestionUniversidad.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegresarGestionUniversidad.setEnabled(true);

        panelBotonesPrincipales.add(btnAgregarEstudiante);
        panelBotonesPrincipales.add(btnEliminarEstudiante);
        panelBotonesPrincipales.add(btnDeseleccionarTabla);
        panelBotonesPrincipales.add(btnRegresarGestionUniversidad);

        // Panel de opciones adicionales (lado derecho)
        panelOpcionesEstudiantes = new JPanel(new BorderLayout(5, 5));
        panelOpcionesEstudiantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonsPanelEst = new JPanel(new GridLayout(3, 1, 10, 10));

        btnModificarEstudiante = new JButton("Modificar Estudiante");
        btnBuscarEstudiante = new JButton("Buscar Estudiante");
        btnMatricular = new JButton("Matricular");

        JButton[] botonesEstudiantes = { btnModificarEstudiante, btnBuscarEstudiante, btnMatricular };
        for (JButton btn : botonesEstudiantes) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setEnabled(true);
            buttonsPanelEst.add(btn);
            
        }
        
        panelOpcionesEstudiantes.add(buttonsPanelEst, BorderLayout.CENTER);

        // Ensamblaje del panel principal de estudiantes
        JPanel contentEstudiantes = new JPanel(new BorderLayout(10, 10));
        contentEstudiantes.add(panelRegistroEstudiantes, BorderLayout.NORTH);
        contentEstudiantes.add(panelConsultaEstudiantes, BorderLayout.CENTER);
        contentEstudiantes.add(panelBotonesPrincipales, BorderLayout.SOUTH);
        contentEstudiantes.add(panelOpcionesEstudiantes, BorderLayout.EAST);

        estudiantesPanel.add(contentEstudiantes, BorderLayout.CENTER);
    }

    private void MatriculaEstudiantesPanel() {
        matriculaPanel = new JPanel(new BorderLayout(10, 10));
        matriculaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitleMatricula = new JLabel("Matricula de Estudiantes", JLabel.CENTER);
        lblTitleMatricula.setFont(new Font("Arial", Font.BOLD, 18));
        matriculaPanel.add(lblTitleMatricula, BorderLayout.NORTH);

        // Panel superior con los TextAreas
        JPanel panelTextAreas = new JPanel(new GridLayout(1, 2, 10, 10));

        // Panel de estudiantes
        JPanel panelEstudiantes = new JPanel(new BorderLayout(5, 5));
        panelEstudiantes.setBorder(BorderFactory.createTitledBorder("Estudiantes"));

        txtAreaEstudiantes = new JTextArea(10, 30);
        txtAreaEstudiantes.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAreaEstudiantes.setEditable(false);
        scrollPaneEstudiantesArea = new JScrollPane(txtAreaEstudiantes);
        panelEstudiantes.add(scrollPaneEstudiantesArea, BorderLayout.CENTER);

        // Panel de cursos disponibles
        JPanel panelCursosDisponibles = new JPanel(new BorderLayout(5, 5));
        panelCursosDisponibles.setBorder(BorderFactory.createTitledBorder("Cursos Disponibles"));

        txtAreaCursosDisponibles = new JTextArea(10, 30);
        txtAreaCursosDisponibles.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAreaCursosDisponibles.setEditable(false);
        scrollPaneCursosArea = new JScrollPane(txtAreaCursosDisponibles);
        panelCursosDisponibles.add(scrollPaneCursosArea, BorderLayout.CENTER);

        panelTextAreas.add(panelEstudiantes);
        panelTextAreas.add(panelCursosDisponibles);

        // Panel de campos de entrada en fila con etiquetas
        JPanel panelCampos = new JPanel(new GridLayout(2, 3, 10, 5));

        JLabel lblCedulaEstudianteMatricula = new JLabel("Cedula de estudiante:");
        lblCedulaEstudianteMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtCedulaEstudianteMatricula = new JTextField();

        JLabel lblCedulaProfesorMatricula = new JLabel("Cedula Profesor:");
        lblCedulaProfesorMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtCedulaProfesorMatricula = new JTextField();

        JLabel lblSiglaCursoMatricula = new JLabel("Sigla del curso:");
        lblSiglaCursoMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtSiglaCursoMatricula = new JTextField();

        panelCampos.add(lblCedulaEstudianteMatricula);
        panelCampos.add(lblCedulaProfesorMatricula);
        panelCampos.add(lblSiglaCursoMatricula);

        panelCampos.add(txtCedulaEstudianteMatricula);
        panelCampos.add(txtCedulaProfesorMatricula);
        panelCampos.add(txtSiglaCursoMatricula);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(panelTextAreas, BorderLayout.NORTH);
        panelSuperior.add(panelCampos, BorderLayout.SOUTH);

        // Panel central con la tabla y botones adicionales
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        // Panel de la tabla
        JPanel panelTabla = new JPanel(new BorderLayout(5, 5));
        panelTabla.setBorder(BorderFactory.createTitledBorder("Matriculas Realizadas"));

        String[] columnasMatriculas = { "Escuela", "Cedula Estudiante", "Cedula Profesor", "Siglas Curso", "Creditos" };
        DefaultTableModel modeloMatriculas = new DefaultTableModel(columnasMatriculas, 0);
        tablaMatriculas = new JTable(modeloMatriculas);
        tablaMatriculas.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaMatriculas.setEnabled(true);
        tablaMatriculas.setDefaultEditor(Object.class, null);

        scrollPaneMatriculas = new JScrollPane(tablaMatriculas);
        panelTabla.add(scrollPaneMatriculas, BorderLayout.CENTER);

        // Panel de botones adicionales (lado derecho)
        JPanel panelOpcionesMatricula = new JPanel(new BorderLayout(5, 5));
        panelOpcionesMatricula.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonsPanelMatricula = new JPanel(new GridLayout(3, 1, 10, 10));

        btnEstudiantesMatriculados = new JButton("Estudiantes matriculados");
        btnConsultaCursosPorEstudiante = new JButton("Consulta de cursos por estudiante");
        btnPagoCreditos = new JButton("Pago de creditos");

        JButton[] botonesMatricula = { btnEstudiantesMatriculados, btnConsultaCursosPorEstudiante, btnPagoCreditos };
        for (JButton btn : botonesMatricula) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setEnabled(true);
            buttonsPanelMatricula.add(btn);
        }

        panelOpcionesMatricula.add(buttonsPanelMatricula, BorderLayout.CENTER);

        // Combinar tabla y opciones
        JPanel panelTablaYOpciones = new JPanel(new BorderLayout(10, 10));
        panelTablaYOpciones.add(panelTabla, BorderLayout.CENTER);
        panelTablaYOpciones.add(panelOpcionesMatricula, BorderLayout.EAST);

        panelCentral.add(panelTablaYOpciones, BorderLayout.CENTER);

        // Panel de botones principales
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnMatricularEstudiante = new JButton("Matricular");
        btnMatricularEstudiante.setFont(new Font("Arial", Font.BOLD, 13));
        btnMatricularEstudiante.setEnabled(true);

        btnDesmatricularEstudiante = new JButton("Desmatricular");
        btnDesmatricularEstudiante.setFont(new Font("Arial", Font.BOLD, 13));
        btnDesmatricularEstudiante.setEnabled(false);

        btnDeseleccionarTablaMatricula = new JButton("Deseleccionar tabla");
        btnDeseleccionarTablaMatricula.setFont(new Font("Arial", Font.BOLD, 13));
        btnDeseleccionarTablaMatricula.setEnabled(false);

        btnRegresarMatricula = new JButton("Regresar");
        btnRegresarMatricula.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegresarMatricula.setEnabled(true);

        panelBotones.add(btnMatricularEstudiante);
        panelBotones.add(btnDesmatricularEstudiante);
        panelBotones.add(btnDeseleccionarTablaMatricula);
        panelBotones.add(btnRegresarMatricula);

        // Ensamblaje del panel principal de matrícula
        matriculaPanel.add(panelSuperior, BorderLayout.NORTH);
        matriculaPanel.add(panelCentral, BorderLayout.CENTER);
        matriculaPanel.add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para cambiar entre paneles usando CardLayout
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
    }
}




