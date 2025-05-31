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
        setSize(1100, 600);
        setLocationRelativeTo(null);
        //setResizable(false);
        
        // Panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());
        
        EstudiantesPanel();
        MatriculaEstudiantesPanel();
        BusquedaEstudiantePanel(); 
        EstudiantesMatriculadosPanel();
        BusquedaCursosPanel();
        PagoCreditosPanel();
        
        // Agregar paneles al CardLayout
        mainPanel.add(estudiantesPanel, "ESTUDIANTES");
        mainPanel.add(matriculaPanel, "MATRICULA");
        mainPanel.add(panelBusquedaEstudiante, "BUSQUEDA_ESTUDIANTE");
        mainPanel.add(panelConsultaEstudiantesMatriculados, "ESTUDIANTES_MATRICULADOS");
        mainPanel.add(panelBusquedaCursos, "BUSQUEDA_CURSOS");
        mainPanel.add(panelConsultaPagoCreditos, "PAGO_CREDITOS");
        
        add(mainPanel);
        setVisible(false);
    }

 // Panel principal con CardLayout
    private JPanel mainPanel;

    // ========================= ATRIBUTOS PARA EstudiantesPanel() =========================
    // Panel de estudiantes original
    public JPanel estudiantesPanel;
    private JPanel panelRegistroEstudiantes;
    private JPanel panelConsultaEstudiantes;
    private JPanel panelOpcionesEstudiantes;

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

    // Botones de estudiantes
    public JButton btnModificarEstudiante;
    public JButton btnAgregarEstudiante;
    public JButton btnEliminarEstudiante;
    public JButton btnDeseleccionarTabla;
    public JButton btnMatricular;
    public JButton btnBuscarEstudiante;
    public JButton btnRegresarGestionUniversidad;

    // Tabla de estudiantes
    public JTable tablaEstudiantes;
    private JScrollPane scrollPaneEstudiantes;

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

        String[] columnasEstudiantes = { "Nombre", "Apellidos", "Cédula", "Carnet", "Nacionalidad", "Porcentaje Beca" };
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

        btnRegresarGestionUniversidad = new JButton("Regresar a GESTIÓN DE UNIVERSIDAD");
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
        btnBuscarEstudiante  = new JButton("Buscar Estudiante");
        btnMatricular = new JButton("Matricular");

        JButton[] botonesEstudiantes = { btnModificarEstudiante, btnBuscarEstudiante , btnMatricular };
        for (JButton btn : botonesEstudiantes) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setEnabled(true);
            buttonsPanelEst.add(btn);
            
        }
        btnModificarEstudiante.setEnabled(false);
        
        panelOpcionesEstudiantes.add(buttonsPanelEst, BorderLayout.CENTER);

        // Ensamblaje del panel principal de estudiantes
        JPanel contentEstudiantes = new JPanel(new BorderLayout(10, 10));
        contentEstudiantes.add(panelRegistroEstudiantes, BorderLayout.NORTH);
        contentEstudiantes.add(panelConsultaEstudiantes, BorderLayout.CENTER);
        contentEstudiantes.add(panelBotonesPrincipales, BorderLayout.SOUTH);
        contentEstudiantes.add(panelOpcionesEstudiantes, BorderLayout.EAST);

        estudiantesPanel.add(contentEstudiantes, BorderLayout.CENTER);
    }
	//========================= ATRIBUTOS PARA MatriculaEstudiantesPanel() =========================
	// Panel de matr�cula de estudiantes
	public JPanel matriculaPanel;
	public JTextArea txtAreaEstudiantes;
	public JTextArea txtAreaCursosDisponibles;
	private JScrollPane scrollPaneEstudiantesArea;
	private JScrollPane scrollPaneCursosArea;

	// Campos del formulario de matr�cula
	public JTextField txtCedulaEstudianteMatricula;
	public JTextField txtCedulaProfesorMatricula;
	public JTextField txtSiglaCursoMatricula;
	public JTextField txtGrupoMatricula; // Campo adicional para grupo

	// Botones de matr�cula
	public JButton btnMatricularEstudiante;
	public JButton btnDesmatricularEstudiante;
	public JButton btnDeseleccionarTablaMatricula;
	public JButton btnRegresarMatricula;

	// Botones de opciones de matr�cula
	public JButton btnEstudiantesMatriculados;
	public JButton btnConsultaCursosPorEstudiante;
	public JButton btnPagoCreditos;

	// Tabla de matr�culas
	public JTable tablaMatriculas;
	private JScrollPane scrollPaneMatriculas;
	
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
        txtAreaEstudiantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPaneEstudiantesArea = new JScrollPane(txtAreaEstudiantes);
        panelEstudiantes.add(scrollPaneEstudiantesArea, BorderLayout.CENTER);

        // Panel de cursos disponibles
        JPanel panelCursosDisponibles = new JPanel(new BorderLayout(5, 5));
        panelCursosDisponibles.setBorder(BorderFactory.createTitledBorder("Información de Cursos Disponibles"));

        txtAreaCursosDisponibles = new JTextArea(10, 30);
        txtAreaCursosDisponibles.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAreaCursosDisponibles.setEditable(false);
        txtAreaCursosDisponibles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPaneCursosArea = new JScrollPane(txtAreaCursosDisponibles);
        panelCursosDisponibles.add(scrollPaneCursosArea, BorderLayout.CENTER);

        panelTextAreas.add(panelEstudiantes);
        panelTextAreas.add(panelCursosDisponibles);

        // Panel de campos de entrada en fila con etiquetas (cambiado a 2x4 para incluir Grupo)
        JPanel panelCampos = new JPanel(new GridLayout(2, 4, 10, 5));

        JLabel lblCedulaEstudianteMatricula = new JLabel("Cédula de Estudiante:");
        lblCedulaEstudianteMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtCedulaEstudianteMatricula = new JTextField();

        JLabel lblCedulaProfesorMatricula = new JLabel("Cédula Profesor:");
        lblCedulaProfesorMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtCedulaProfesorMatricula = new JTextField();

        JLabel lblGrupoMatricula = new JLabel("Grupo:");
        lblGrupoMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtGrupoMatricula = new JTextField();

        JLabel lblSiglaCursoMatricula = new JLabel("Sigla del Curso:");
        lblSiglaCursoMatricula.setFont(new Font("Arial", Font.BOLD, 12));
        txtSiglaCursoMatricula = new JTextField();

        panelCampos.add(lblCedulaEstudianteMatricula);
        panelCampos.add(lblCedulaProfesorMatricula);
        panelCampos.add(lblGrupoMatricula);
        panelCampos.add(lblSiglaCursoMatricula);

        panelCampos.add(txtCedulaEstudianteMatricula);
        panelCampos.add(txtCedulaProfesorMatricula);
        panelCampos.add(txtGrupoMatricula);
        panelCampos.add(txtSiglaCursoMatricula);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(panelTextAreas, BorderLayout.NORTH);
        panelSuperior.add(panelCampos, BorderLayout.SOUTH);

        // Panel central con la tabla y botones adicionales
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        // Panel de la tabla
        JPanel panelTabla = new JPanel(new BorderLayout(5, 5));
        panelTabla.setBorder(BorderFactory.createTitledBorder("Matriculas Realizadas"));

        String[] columnasMatriculas = { "Escuela", "Cédula Estudiante", "Cédula Profesor", "Grupo", "Siglas Curso", "Créditos" };
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

        btnEstudiantesMatriculados = new JButton("Estudiantes Matriculados");
        btnConsultaCursosPorEstudiante = new JButton("Consulta de Cursos por Estudiante");
        btnPagoCreditos = new JButton("Pago de Créditos");

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

        // Ensamblaje del panel principal de matr�cula
        matriculaPanel.add(panelSuperior, BorderLayout.NORTH);
        matriculaPanel.add(panelCentral, BorderLayout.CENTER);
        matriculaPanel.add(panelBotones, BorderLayout.SOUTH);
    }
    
    // Panel de b�squeda de estudiantes
    public JPanel panelBusquedaEstudiante;
    public JTextField txtBuscarEstudiante;
    public JTextArea showTextAreaEstudiante;
    public JButton btnBuscarEstudiantePorCedula;
    public JButton btnRegresarBusquedaEstudiante;
    
    private void BusquedaEstudiantePanel() {
        panelBusquedaEstudiante = new JPanel(new BorderLayout(10, 10));
        panelBusquedaEstudiante.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblBusquedaEstudianteTitle = new JLabel("Buscar Estudiante Por Cédula", JLabel.CENTER);
        lblBusquedaEstudianteTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panelBusquedaEstudiante.add(lblBusquedaEstudianteTitle, BorderLayout.NORTH);
        
        JPanel panelBusquedaEstudianteForm = new JPanel(new BorderLayout(5, 15));
        panelBusquedaEstudianteForm.setBorder(BorderFactory.createTitledBorder("Consulta"));
        
        JPanel panelTxtYBotonEstudiante = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Label instructivo antes del campo de texto
        JLabel lblInstruccion = new JLabel("Debes ingresar la cédula o el carnet del estudiante:");
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTxtYBotonEstudiante.add(lblInstruccion);
        
        txtBuscarEstudiante = new JTextField(55);
        panelTxtYBotonEstudiante.add(txtBuscarEstudiante);
        
        btnBuscarEstudiantePorCedula = new JButton("Buscar");
        btnBuscarEstudiantePorCedula.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscarEstudiantePorCedula.setEnabled(true);
        panelTxtYBotonEstudiante.add(btnBuscarEstudiantePorCedula);
        
        panelBusquedaEstudianteForm.add(panelTxtYBotonEstudiante, BorderLayout.NORTH);
        
        showTextAreaEstudiante = new JTextArea(4, 40);
        showTextAreaEstudiante.setEditable(false);
        showTextAreaEstudiante.setFont(new Font("Arial", Font.BOLD, 20));
        showTextAreaEstudiante.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollBusquedaEstudiante = new JScrollPane(showTextAreaEstudiante);
        scrollBusquedaEstudiante.setPreferredSize(new Dimension(500, 100));
        panelBusquedaEstudianteForm.add(scrollBusquedaEstudiante, BorderLayout.CENTER);
        
        // Agregar el bot�n "Regresar"
        JPanel panelBotonRegresarEstudiante = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegresarBusquedaEstudiante = new JButton("Regresar");
        btnRegresarBusquedaEstudiante.setFont(new Font("Arial", Font.BOLD, 14));
        panelBotonRegresarEstudiante.add(btnRegresarBusquedaEstudiante);
        
        panelBusquedaEstudianteForm.add(panelBotonRegresarEstudiante, BorderLayout.SOUTH);

        panelBusquedaEstudiante.add(panelBusquedaEstudianteForm, BorderLayout.CENTER);
    }
    
    // Panel de estudiantes matriculados
    public JPanel panelConsultaEstudiantesMatriculados;
    public JTextArea txtAreaEstudiantesMatriculados;
    private JScrollPane scrollPaneEstudiantesMatriculados;
    public JButton btnRegresarEstudiantesMatriculados;

    private void EstudiantesMatriculadosPanel() {
        panelConsultaEstudiantesMatriculados = new JPanel(new BorderLayout(10, 10));
        panelConsultaEstudiantesMatriculados.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitleEstudiantesMatriculados = new JLabel("Informaci�n de los Estudiantes Matriculados", JLabel.CENTER);
        lblTitleEstudiantesMatriculados.setFont(new Font("Arial", Font.BOLD, 18));
        panelConsultaEstudiantesMatriculados.add(lblTitleEstudiantesMatriculados, BorderLayout.NORTH);
        
        JPanel panelContenidoEstudiantesMatriculados = new JPanel(new BorderLayout(10, 10));
        panelContenidoEstudiantesMatriculados.setBorder(BorderFactory.createTitledBorder(""));
        
        txtAreaEstudiantesMatriculados = new JTextArea();
        txtAreaEstudiantesMatriculados.setEditable(false);
        txtAreaEstudiantesMatriculados.setFont(new Font("Arial", Font.PLAIN, 20));
        txtAreaEstudiantesMatriculados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPaneEstudiantesMatriculados = new JScrollPane(txtAreaEstudiantesMatriculados);
        panelContenidoEstudiantesMatriculados.add(scrollPaneEstudiantesMatriculados, BorderLayout.CENTER);
        
        JPanel panelBotonRegresar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegresarEstudiantesMatriculados = new JButton("Regresar");
        btnRegresarEstudiantesMatriculados.setFont(new Font("Arial", Font.BOLD, 14));
        panelBotonRegresar.add(btnRegresarEstudiantesMatriculados);
        
        panelContenidoEstudiantesMatriculados.add(panelBotonRegresar, BorderLayout.SOUTH);
        panelConsultaEstudiantesMatriculados.add(panelContenidoEstudiantesMatriculados, BorderLayout.CENTER);
    }
    
    // Panel de b�squeda de cursos por estudiante
    public JPanel panelBusquedaCursos;
    public JTextField txtBuscarCursos;
    public JTextArea showTextAreaCursos;
    public JButton btnBuscarCursosPorCedula;
    public JButton btnRegresarBusquedaCursos;

    private void BusquedaCursosPanel() {
        panelBusquedaCursos = new JPanel(new BorderLayout(10, 10));
        panelBusquedaCursos.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblBusquedaCursosTitle = new JLabel("Buscar Cursos Por Cédula de Estudiante", JLabel.CENTER);
        lblBusquedaCursosTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panelBusquedaCursos.add(lblBusquedaCursosTitle, BorderLayout.NORTH);
        
        JPanel panelBusquedaCursosForm = new JPanel(new BorderLayout(5, 15));
        panelBusquedaCursosForm.setBorder(BorderFactory.createTitledBorder("Consulta"));
        
        JPanel panelTxtYBotonCursos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Label instructivo antes del campo de texto
        JLabel lblInstruccion = new JLabel("Debes ingresar la cédula del estudiante:");
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTxtYBotonCursos.add(lblInstruccion);
        
        txtBuscarCursos = new JTextField(60);
        panelTxtYBotonCursos.add(txtBuscarCursos);
        
        btnBuscarCursosPorCedula = new JButton("Buscar");
        btnBuscarCursosPorCedula.setFont(new Font("Arial", Font.BOLD, 14));
        panelTxtYBotonCursos.add(btnBuscarCursosPorCedula);
        
        panelBusquedaCursosForm.add(panelTxtYBotonCursos, BorderLayout.NORTH);
        
        showTextAreaCursos = new JTextArea(10, 20);
        showTextAreaCursos.setEditable(false);
        showTextAreaCursos.setFont(new Font("Arial", Font.BOLD, 20));
        showTextAreaCursos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollBusquedaCursos = new JScrollPane(showTextAreaCursos);
        panelBusquedaCursosForm.add(scrollBusquedaCursos, BorderLayout.CENTER);
        
        // Agregar el bot�n "Regresar"
        JPanel panelBotonRegresarCursos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegresarBusquedaCursos = new JButton("Regresar");
        btnRegresarBusquedaCursos.setFont(new Font("Arial", Font.BOLD, 14));
        panelBotonRegresarCursos.add(btnRegresarBusquedaCursos);
        
        panelBusquedaCursosForm.add(panelBotonRegresarCursos, BorderLayout.SOUTH);
        panelBusquedaCursos.add(panelBusquedaCursosForm, BorderLayout.CENTER);
    }
    
    // Panel de pago de cr�ditos
    public JPanel panelConsultaPagoCreditos;
    public JTextArea txtAreaPagoCreditos;
    private JScrollPane scrollPanePagoCreditos;
    public JButton btnRegresarPagoCreditos;

    private void PagoCreditosPanel() {
        panelConsultaPagoCreditos = new JPanel(new BorderLayout(10, 10));
        panelConsultaPagoCreditos.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitlePagoCreditos = new JLabel("Pago de Créditos Para Cada Estudiante", JLabel.CENTER);
        lblTitlePagoCreditos.setFont(new Font("Arial", Font.BOLD, 18));
        panelConsultaPagoCreditos.add(lblTitlePagoCreditos, BorderLayout.NORTH);
        
        JPanel panelContenidoPagoCreditos = new JPanel(new BorderLayout(10, 10));
        panelContenidoPagoCreditos.setBorder(BorderFactory.createTitledBorder(""));
        
        txtAreaPagoCreditos = new JTextArea();
        txtAreaPagoCreditos.setEditable(false);
        txtAreaPagoCreditos.setFont(new Font("Arial", Font.PLAIN, 20));
        txtAreaPagoCreditos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPanePagoCreditos = new JScrollPane(txtAreaPagoCreditos);
        panelContenidoPagoCreditos.add(scrollPanePagoCreditos, BorderLayout.CENTER);
        
        JPanel panelBotonRegresar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegresarPagoCreditos = new JButton("Regresar");
        btnRegresarPagoCreditos.setFont(new Font("Arial", Font.BOLD, 14));
        panelBotonRegresar.add(btnRegresarPagoCreditos);
        
        panelContenidoPagoCreditos.add(panelBotonRegresar, BorderLayout.SOUTH);
        panelConsultaPagoCreditos.add(panelContenidoPagoCreditos, BorderLayout.CENTER);
    }
    
    // M�todo para cambiar entre paneles usando CardLayout
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
    }
}




