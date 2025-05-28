package com.mvc.view;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.mvc.controls.ControllerPanelConsultas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;

	// Panel principal que contiene todo
	private JPanel mainPanel;

	// Panel para el menú lateral
	private JPanel menuPanel;

	// Panel para mostrar el contenido segun la seleccion
	private JPanel contentPanel;

	public JButton btnVerEscuelas;
	// instancia de los objetos del tod la logica de profesores consultas
	public ControllerPanelConsultas panelPorProfesor;
	public ControllerPanelConsultas panelPorCurso;
	public ControllerPanelConsultas panelPorCedula;
	public ControllerPanelConsultas panelPorEscuela;

	public MainView() {
		setTitle("Sistema de Gestión Universitaria");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		FlatDraculaIJTheme.setup();
		// Configuracion del panel principal
		mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Configuracion del panel de menu
		MenuPanel();

		// Configuracion del panel de contenido
		contentPanel = new JPanel(new CardLayout());
		contentPanel.setBorder(BorderFactory.createEtchedBorder());

		// Configurar los paneles de contenido
		UniversidadPanel();
		EscuelasPanel();
		CursosPanel();
		BusquedaPanel();
		ProfesoresPanel();
		AsignacionPanel();
		ConsultasPanel();
		DirectoresPanel();
		ConsultaEscuelasPanel();

		// Añadir paneles al contenido
		contentPanel.add(universidadPanel, "UNIVERSIDAD");
		contentPanel.add(escuelasPanel, "ESCUELAS");
		contentPanel.add(cursosPanel, "CURSOS");
		contentPanel.add(panelBusqueda, "BUSQUEDA");
		contentPanel.add(profesoresPanel, "PROFESORES");
		contentPanel.add(asignacionPanel, "ASIGNACION PROFESORES");
		contentPanel.add(consultasPanel, "CONSULTAS");
		contentPanel.add(panelConsultaDirectores, "CONSULTA DIRECTORES");
		contentPanel.add(panelConsultaEscuelas, "CONSULTA ESCUELAS");

		/// anadir los objetos
		this.panelPorProfesor = BusquedaPanelConsultas("Buscar Cursos por Profesor",
				"Debes de ingresar el nombre de un profesor: ");
		this.panelPorCurso = BusquedaPanelConsultas("Buscar Profesor por Sigla de Curso",
				"Debes de ingresar las siglas de un curso: ");
		this.panelPorCedula = BusquedaPanelConsultas("Buscar Profesor por Cedula",
				"Debes de ingresar un numero de cedula: ");
		this.panelPorEscuela = BusquedaPanelConsultas("Buscar Profesores por Escuela",
				"Debes de ingresar el nombre de una escuela: ");
		contentPanel.add(panelPorProfesor.panel, "CONSULTASPANEL");
		contentPanel.add(panelPorCurso.panel, "CONSULTASPANELPORCURSO");
		contentPanel.add(panelPorCedula.panel, "CONSULAPORCEDULA");
		contentPanel.add(panelPorEscuela.panel, "CONSULTAPORESCUELA");
		// Añadir componentes al panel principal
		mainPanel.add(menuPanel, BorderLayout.WEST);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Añadir panel principal al frame
		add(mainPanel);

	}

	// Botones del menu
	public JButton btnUniversidad;
	public JButton btnEscuelas;
	public JButton btnCursos;
	// En la declaración de botones del menú, agregar:
	public JButton btnProfesores;

	private void MenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setBorder(BorderFactory.createTitledBorder("Menú"));
		menuPanel.setPreferredSize(new Dimension(150, 600));

		JLabel lblMenu = new JLabel("OPCIONES");
		lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
		lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnUniversidad = new JButton("Universidad");
		btnUniversidad.setBorderPainted(false);
		btnEscuelas = new JButton("Escuelas");
		btnEscuelas.setBorderPainted(false);
		btnCursos = new JButton("Cursos");
		btnCursos.setBorderPainted(false);
		btnProfesores = new JButton("Profesores");
		btnProfesores.setBorderPainted(false);

		// Configuracion de los botones
		JButton[] buttons = { btnUniversidad, btnEscuelas, btnCursos, btnProfesores };
		for (JButton btn : buttons) {
			btn.setMaximumSize(new Dimension(140, 40));
			btn.setFont(new Font("Arial", Font.BOLD, 12));
			btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		}

		// Añadir componentes al panel de menu
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(lblMenu);
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(btnUniversidad);
		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(btnEscuelas);
		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(btnCursos);

		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(btnProfesores); // AAA
	}

	// Componentes de Universidad (Registrar y Actualizar)
	// Registrar Universidad
	private JLabel lblName;
	private JLabel lblAdress;
	private JLabel lblPhoneNumber;
	public JTextField txtName;
	public JTextField txtAdress;
	public JTextField txtPhoneNumber;
	public JButton btnRegisterUniversity;

	private JLabel lblTituloActualizarUniversidad;
	private JLabel lblNewAdress;
	private JLabel lblNewPhone;
	public JTextField txtNewAdress;
	public JTextField txtNewPhone;
	public JButton btnUpdateUniversity;

	private JPanel universidadPanel;
	public JPanel registerPanel;

	private void UniversidadPanel() {
		universidadPanel = new JPanel(new BorderLayout(10, 10));
		universidadPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleUniversidad = new JLabel("Gestión de Universidad", JLabel.CENTER);
		lblTitleUniversidad.setFont(new Font("Arial", Font.BOLD, 18));
		universidadPanel.add(lblTitleUniversidad, BorderLayout.NORTH);

		JPanel contentUniversidad = new JPanel(new GridLayout(2, 1, 10, 10));

		// Panel para registrar universidad
		registerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		registerPanel.setBorder(BorderFactory.createTitledBorder("Registrar Universidad"));

		lblName = new JLabel("  Nombre de la Universidad:");
		lblName.setFont(new Font("Arial", Font.BOLD, 16));
		txtName = new JTextField();

		lblAdress = new JLabel("  Dirección:");
		lblAdress.setFont(new Font("Arial", Font.BOLD, 16));
		txtAdress = new JTextField();

		lblPhoneNumber = new JLabel("  Número de Teléfono:");
		lblPhoneNumber.setFont(new Font("Arial", Font.BOLD, 16));
		txtPhoneNumber = new JTextField();

		btnRegisterUniversity = new JButton("Registrar Universidad");
		btnRegisterUniversity.setFont(new Font("Arial", Font.BOLD, 16));

		registerPanel.add(lblName);
		registerPanel.add(txtName);
		registerPanel.add(lblAdress);
		registerPanel.add(txtAdress);
		registerPanel.add(lblPhoneNumber);
		registerPanel.add(txtPhoneNumber);
		registerPanel.add(new JLabel());
		registerPanel.add(btnRegisterUniversity);

		// Panel para actualizar universidad
		JPanel updatePanel = new JPanel(new GridLayout(4, 2, 5, 5));
		updatePanel.setBorder(BorderFactory.createTitledBorder("Actualizar Universidad"));

		lblTituloActualizarUniversidad = new JLabel("  Datos para editar la universidad...");
		lblTituloActualizarUniversidad.setFont(new Font("Arial", Font.ITALIC, 16));

		lblNewAdress = new JLabel("  Nueva Dirección:");
		lblNewAdress.setFont(new Font("Arial", Font.BOLD, 16));
		txtNewAdress = new JTextField();

		lblNewPhone = new JLabel("  Nuevo Número de Teléfono:");
		lblNewPhone.setFont(new Font("Arial", Font.BOLD, 16));
		txtNewPhone = new JTextField();

		btnUpdateUniversity = new JButton("Actualizar Universidad");
		btnUpdateUniversity.setFont(new Font("Arial", Font.BOLD, 16));
		btnUpdateUniversity.setEnabled(false);

		updatePanel.add(lblTituloActualizarUniversidad);
		updatePanel.add(new JLabel());
		updatePanel.add(lblNewAdress);
		updatePanel.add(txtNewAdress);
		updatePanel.add(lblNewPhone);
		updatePanel.add(txtNewPhone);
		updatePanel.add(new JLabel());
		updatePanel.add(btnUpdateUniversity);

		contentUniversidad.add(registerPanel);
		contentUniversidad.add(updatePanel);

		universidadPanel.add(contentUniversidad, BorderLayout.CENTER);
	}

	// Componentes de Escuelas
	public JLabel lblEscuelasTitle;
	public JLabel lblNameSchool;
	public JTextField txtNameSchool;
	public JButton btnRegisterSchool;
	private JLabel lblTituloEscuelas;
	public JTextArea txtAreaEscuelas;
	private JScrollPane scrollPaneEscuelas;

	private JPanel escuelasPanel;

	private void EscuelasPanel() {
		escuelasPanel = new JPanel(new BorderLayout(10, 10));
		escuelasPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleEscuelas = new JLabel("Gestión de Escuelas", JLabel.CENTER);
		lblTitleEscuelas.setFont(new Font("Arial", Font.BOLD, 18));
		escuelasPanel.add(lblTitleEscuelas, BorderLayout.NORTH);

		JPanel panelRegistroEscuela = new JPanel(new GridLayout(3, 2, 5, 5));
		panelRegistroEscuela.setBorder(BorderFactory.createTitledBorder("Registrar Escuela"));

		lblEscuelasTitle = new JLabel("Universidad: ");
		lblEscuelasTitle.setFont(new Font("Arial", Font.ITALIC, 16));

		lblNameSchool = new JLabel("Nombre De la Escuela:");
		lblNameSchool.setFont(new Font("Arial", Font.BOLD, 12));

		txtNameSchool = new JTextField();

		btnRegisterSchool = new JButton("Registrar Escuela");
		btnRegisterSchool.setFont(new Font("Arial", Font.BOLD, 14));
		btnRegisterSchool.setEnabled(false);

		panelRegistroEscuela.add(lblEscuelasTitle);
		panelRegistroEscuela.add(new JLabel());
		panelRegistroEscuela.add(lblNameSchool);
		panelRegistroEscuela.add(txtNameSchool);
		panelRegistroEscuela.add(btnRegisterSchool);

		JPanel panelConsultaEscuelas = new JPanel(new BorderLayout(10, 10));
		panelConsultaEscuelas.setBorder(BorderFactory.createTitledBorder("Escuelas Registradas"));

		lblTituloEscuelas = new JLabel("Lista de las Escuelas:", JLabel.CENTER);
		lblTituloEscuelas.setFont(new Font("Arial", Font.BOLD, 16));
		panelConsultaEscuelas.add(lblTituloEscuelas, BorderLayout.NORTH);

		txtAreaEscuelas = new JTextArea();
		txtAreaEscuelas.setEditable(false);
		txtAreaEscuelas.setFont(new Font("Arial", Font.BOLD, 20));
		txtAreaEscuelas.setBorder(BorderFactory.createEtchedBorder());

		scrollPaneEscuelas = new JScrollPane(txtAreaEscuelas);
		panelConsultaEscuelas.add(scrollPaneEscuelas, BorderLayout.CENTER);

		JPanel contentEscuelas = new JPanel(new BorderLayout(10, 10));
		contentEscuelas.add(panelRegistroEscuela, BorderLayout.NORTH);
		contentEscuelas.add(panelConsultaEscuelas, BorderLayout.CENTER);

		escuelasPanel.add(contentEscuelas, BorderLayout.CENTER);
	}

	// Componentes de Cursos
	private JPanel panelRegistroCursos;
	private JPanel panelConsultaCursos;
	private JPanel panelOpcionesCursos;

	private JLabel varLblSiglasNom;
	private JLabel varLblNombreCurso;
	private JLabel varLblNombreEscuela;
	public JTextField varTxtSigla;
	public JTextField varTxtDescripcion;
	public JTextField varTxtEscuelaNombres;

	public JButton varBtnRegistrar;
	public JButton varBtnEliminar;
	public JButton varBtnModificar;
	public JButton varBtnBuscarPorEscuela;
	public JButton varBtnDeseleccionarTabla;

	private JLabel lblTituloCursos;
	public JTable tablaCursos;
	private JScrollPane scrollPaneCursos;

	private JPanel cursosPanel;

	public void CursosPanel() {
		cursosPanel = new JPanel(new BorderLayout(10, 10));
		cursosPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleCursos = new JLabel("Gestión de Cursos", JLabel.CENTER);
		lblTitleCursos.setFont(new Font("Arial", Font.BOLD, 18));
		cursosPanel.add(lblTitleCursos, BorderLayout.NORTH);

		panelRegistroCursos = new JPanel(new GridLayout(3, 2, 10, 10));
		panelRegistroCursos.setBorder(BorderFactory.createTitledBorder("Registro de Cursos"));

		varLblNombreEscuela = new JLabel("Nombre de la Escuela:");
		varLblNombreEscuela.setFont(new Font("Arial", Font.BOLD, 14));

		// Panel para el campo de texto y el botón de ver escuelas
		JPanel escuelaPanel = new JPanel(new BorderLayout(5, 0));
		varTxtEscuelaNombres = new JTextField();
		btnVerEscuelas = new JButton("Ver Escuelas "); // Asignamos al campo de clase
		btnVerEscuelas.setFont(new Font("Arial", Font.PLAIN, 12));
		escuelaPanel.add(varTxtEscuelaNombres, BorderLayout.CENTER);
		escuelaPanel.add(btnVerEscuelas, BorderLayout.EAST);

		varLblSiglasNom = new JLabel("Siglas del Curso:");
		varLblSiglasNom.setFont(new Font("Arial", Font.BOLD, 14));
		varTxtSigla = new JTextField();
		varLblNombreCurso = new JLabel("Nombre del Curso:");
		varLblNombreCurso.setFont(new Font("Arial", Font.BOLD, 14));
		varTxtDescripcion = new JTextField();

		panelRegistroCursos.add(varLblNombreEscuela);
		panelRegistroCursos.add(escuelaPanel);
		panelRegistroCursos.add(varLblSiglasNom);
		panelRegistroCursos.add(varTxtSigla);
		panelRegistroCursos.add(varLblNombreCurso);
		panelRegistroCursos.add(varTxtDescripcion);

		panelConsultaCursos = new JPanel(new BorderLayout(10, 10));
		panelConsultaCursos.setBorder(BorderFactory.createTitledBorder("Cursos Registrados"));

		lblTituloCursos = new JLabel("Lista de las Escuelas y Cursos...", JLabel.CENTER);
		lblTituloCursos.setFont(new Font("Arial", Font.BOLD, 16));
		panelConsultaCursos.add(lblTituloCursos, BorderLayout.NORTH);

		String[] columnas = { "Nombre de la Escuela", "Siglas del Curso", "Nombre del Curso" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		tablaCursos = new JTable(modelo);
		tablaCursos.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaCursos.setEnabled(true); // Permitir seleccion
		tablaCursos.setDefaultEditor(Object.class, null); // NO PERMITIR EDITAR DESDE LA TABLE

		scrollPaneCursos = new JScrollPane(tablaCursos);
		panelConsultaCursos.add(scrollPaneCursos, BorderLayout.CENTER);

		JPanel panelBotonARegistar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		varBtnRegistrar = new JButton("Agregar Curso");
		varBtnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
		varBtnRegistrar.setEnabled(false);
		varBtnDeseleccionarTabla = new JButton("Deseleccionar Tabla");
		varBtnDeseleccionarTabla.setFont(new Font("Arial", Font.BOLD, 13));
		varBtnDeseleccionarTabla.setEnabled(false);

		panelBotonARegistar.add(varBtnRegistrar);
		panelBotonARegistar.add(varBtnDeseleccionarTabla);

		panelOpcionesCursos = new JPanel(new BorderLayout(5, 5));
		panelOpcionesCursos.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));

		JLabel varOpciones = new JLabel("Más Opciones...");
		varOpciones.setFont(new Font("Arial", Font.BOLD, 14));
		varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
		panelOpcionesCursos.add(varOpciones, BorderLayout.NORTH);

		JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		varBtnEliminar = new JButton("Eliminar Curso");
		varBtnModificar = new JButton("Modificar Curso");
		varBtnBuscarPorEscuela = new JButton("Buscar Cursos por Escuela");

		JButton[] botones = { varBtnEliminar, varBtnModificar, varBtnBuscarPorEscuela };
		for (JButton btn : botones) {
			btn.setFont(new Font("Arial", Font.BOLD, 12));
			btn.setEnabled(false);
			buttonsPanel.add(btn);
		}

		panelOpcionesCursos.add(buttonsPanel, BorderLayout.CENTER);
		JPanel contentCursos = new JPanel(new BorderLayout(10, 10));
		contentCursos.add(panelRegistroCursos, BorderLayout.NORTH);
		contentCursos.add(panelConsultaCursos, BorderLayout.CENTER);
		contentCursos.add(panelBotonARegistar, BorderLayout.SOUTH);
		contentCursos.add(panelOpcionesCursos, BorderLayout.EAST);

		cursosPanel.add(contentCursos, BorderLayout.CENTER);
	}

	// panel de busqueda por curso (Escuela)
	public JPanel panelBusqueda;
	public JTextField txtBuscar;
	public JTextArea showTextArea;
	public JButton btnBuscar;
	public JButton btnLimpiar;
	public JButton varBtnRegresar;

	private void BusquedaPanel() {
		panelBusqueda = new JPanel(new BorderLayout(10, 10));
		panelBusqueda.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblBusquedaTitle = new JLabel("Buscar Cursos por Escuela", JLabel.CENTER);
		lblBusquedaTitle.setFont(new Font("Arial", Font.BOLD, 16));
		panelBusqueda.add(lblBusquedaTitle, BorderLayout.NORTH);

		JPanel panelBusquedaForm = new JPanel(new BorderLayout(5, 15));
		panelBusquedaForm.setBorder(BorderFactory.createTitledBorder("Consulta"));

		JPanel panelTxtYBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtBuscar = new JTextField(30);
		panelTxtYBoton.add(txtBuscar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		panelTxtYBoton.add(btnBuscar);

		panelBusquedaForm.add(panelTxtYBoton, BorderLayout.NORTH);

		showTextArea = new JTextArea(10, 20);
		showTextArea.setEditable(false);
		showTextArea.setFont(new Font("Arial", Font.BOLD, 20));

		JScrollPane scrollBusqueda = new JScrollPane(showTextArea);
		panelBusquedaForm.add(scrollBusqueda, BorderLayout.CENTER);

		// Agregar el botón "Regresar"
		JPanel panelBotonRegresar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		varBtnRegresar = new JButton("Regresar");
		varBtnRegresar.setFont(new Font("Arial", Font.BOLD, 14));
		panelBotonRegresar.add(varBtnRegresar);
		panelBusquedaForm.add(panelBotonRegresar, BorderLayout.SOUTH);

		panelBusqueda.add(panelBusquedaForm, BorderLayout.CENTER);
	}

	// Componentes de Profesores - DECLARACIONES MODIFICADAS
	private JPanel profesoresPanel;
	private JPanel panelRegistroProfesores;
	private JPanel panelConsultaProfesores;
	private JPanel panelOpcionesProfesores;

	// Campos del formulario de profesores (sin siglas y grupo)
	private JLabel lblNombreProfesor;
	private JLabel lblPrimerApe;
	private JLabel lblSegundoApe;
	private JLabel lblCedula;

	public JTextField txtNombreProfesor;
	public JTextField txtPrimerApe;
	public JTextField txtSegundoApe;
	public JTextField txtCedula;

	// Botones de profesores (modificados)
	public JButton btnAgregarProfesor;
	public JButton btnEliminarProfesor;
	public JButton btnDeseleccionarTablaProf;
	public JButton btnAsignacionProfesores;
	public JButton btnConsultas;
	public JButton btnModificarProfesor;

	// Tabla de profesores
	public JTable tablaProfesores;
	private JScrollPane scrollPaneProfesores;
	private JLabel lblTituloProfesores;

	// MÉTODO COMPLETO DEL PANEL DE PROFESORES MODIFICADO
	private void ProfesoresPanel() {
		profesoresPanel = new JPanel(new BorderLayout(10, 10));
		profesoresPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleProfesores = new JLabel("Gestión de Profesores", JLabel.CENTER);
		lblTitleProfesores.setFont(new Font("Arial", Font.BOLD, 18));
		profesoresPanel.add(lblTitleProfesores, BorderLayout.NORTH);

		// Panel de registro de profesores (solo datos básicos)
		panelRegistroProfesores = new JPanel(new GridLayout(4, 2, 10, 10));
		panelRegistroProfesores.setBorder(BorderFactory.createTitledBorder("Registro de Profesores"));

		lblNombreProfesor = new JLabel("Nombre Profesor:");
		lblNombreProfesor.setFont(new Font("Arial", Font.BOLD, 14));
		txtNombreProfesor = new JTextField();

		lblPrimerApe = new JLabel("Primer Apellido:");
		lblPrimerApe.setFont(new Font("Arial", Font.BOLD, 14));
		txtPrimerApe = new JTextField();

		lblSegundoApe = new JLabel("Segundo Apellido:");
		lblSegundoApe.setFont(new Font("Arial", Font.BOLD, 14));
		txtSegundoApe = new JTextField();

		lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(new Font("Arial", Font.BOLD, 14));

		// Panel para el campo de texto y el botón de ver cursos
		JPanel cedulaPanel = new JPanel(new BorderLayout(5, 0));
		txtCedula = new JTextField();
		cedulaPanel.add(txtCedula, BorderLayout.CENTER);

		// Agregar elementos al panel en el orden correcto
		panelRegistroProfesores.add(lblNombreProfesor);
		panelRegistroProfesores.add(txtNombreProfesor);
		panelRegistroProfesores.add(lblPrimerApe);
		panelRegistroProfesores.add(txtPrimerApe);
		panelRegistroProfesores.add(lblSegundoApe);
		panelRegistroProfesores.add(txtSegundoApe);
		panelRegistroProfesores.add(lblCedula);
		panelRegistroProfesores.add(cedulaPanel);

		// Panel de consulta de profesores
		panelConsultaProfesores = new JPanel(new BorderLayout(10, 10));
		panelConsultaProfesores.setBorder(BorderFactory.createTitledBorder("Profesores Registrados"));

		lblTituloProfesores = new JLabel("Lista de Profesores...", JLabel.CENTER);
		lblTituloProfesores.setFont(new Font("Arial", Font.BOLD, 16));
		panelConsultaProfesores.add(lblTituloProfesores, BorderLayout.NORTH);

		// Tabla de profesores con columnas básicas (sin siglas y grupo)
		String[] columnasProfesores = { "Nombre Profesor", "Apellido 1", "Apellido 2", "Cédula" };
		DefaultTableModel modeloProfesores = new DefaultTableModel(columnasProfesores, 0);
		tablaProfesores = new JTable(modeloProfesores);
		tablaProfesores.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaProfesores.setEnabled(true);
		tablaProfesores.setDefaultEditor(Object.class, null);

		scrollPaneProfesores = new JScrollPane(tablaProfesores);
		panelConsultaProfesores.add(scrollPaneProfesores, BorderLayout.CENTER);

		// Panel de botones principales (modificado)
		JPanel panelBotonesPrincipales = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		btnAgregarProfesor = new JButton("Agregar Profesor");
		btnAgregarProfesor.setFont(new Font("Arial", Font.BOLD, 13));
		btnAgregarProfesor.setEnabled(true);

		btnEliminarProfesor = new JButton("Eliminar Profesor");
		btnEliminarProfesor.setFont(new Font("Arial", Font.BOLD, 13));
		btnEliminarProfesor.setEnabled(false);

		btnDeseleccionarTablaProf = new JButton("Deseleccionar Tabla");
		btnDeseleccionarTablaProf.setFont(new Font("Arial", Font.BOLD, 13));
		btnDeseleccionarTablaProf.setEnabled(false);

		panelBotonesPrincipales.add(btnAgregarProfesor);
		panelBotonesPrincipales.add(btnEliminarProfesor);
		panelBotonesPrincipales.add(btnDeseleccionarTablaProf);

		// Panel de opciones adicionales (modificado)
		panelOpcionesProfesores = new JPanel(new BorderLayout(5, 5));
		panelOpcionesProfesores.setBorder(BorderFactory.createTitledBorder("Opciones"));

		JPanel buttonsPanelProf = new JPanel(new GridLayout(3, 1, 10, 10));

		btnModificarProfesor = new JButton("Modificar Profesor");
		btnAsignacionProfesores = new JButton("Asignación de Profesores");
		btnConsultas = new JButton("Consultas");

		JButton[] botonesProfesores = { btnModificarProfesor, btnAsignacionProfesores, btnConsultas };
		for (JButton btn : botonesProfesores) {
			btn.setFont(new Font("Arial", Font.BOLD, 12));
			btn.setEnabled(false);
			buttonsPanelProf.add(btn);
		}
		btnConsultas.setEnabled(true);

		panelOpcionesProfesores.add(buttonsPanelProf, BorderLayout.CENTER);

		// Ensamblaje del panel principal de profesores
		JPanel contentProfesores = new JPanel(new BorderLayout(10, 10));
		contentProfesores.add(panelRegistroProfesores, BorderLayout.NORTH);
		contentProfesores.add(panelConsultaProfesores, BorderLayout.CENTER);
		contentProfesores.add(panelBotonesPrincipales, BorderLayout.SOUTH);
		contentProfesores.add(panelOpcionesProfesores, BorderLayout.EAST);

		profesoresPanel.add(contentProfesores, BorderLayout.CENTER);
	}

	// Componentes del Panel de Asignación de Profesores
	private JPanel asignacionPanel;
	public JTextArea txtAreaProfesores;
	public JTextArea txtAreaCursos;
	private JScrollPane scrollPaneProfesoresArea;
	private JScrollPane scrollPaneCursosArea;
	public JTextField txtCedulaAsignar;
	public JTextField txtSiglasAsignar;
	public JTextField txtGrupoAsignar;
	public JTable tablaAsignaciones;
	private JScrollPane scrollPaneAsignaciones;

	// Botones del panel de asignación
	public JButton btnAsignarProfesorAsig;
	public JButton btnDesasignarProfesorAsig;
	public JButton btnAsignarDirectorAsig;
	public JButton btnDeseleccionarTablaAsig;
	public JButton btnRegresarAsignacionProf;

	// MÉTODO COMPLETO DEL PANEL DE ASIGNACIÓN
	private void AsignacionPanel() {
		asignacionPanel = new JPanel(new BorderLayout(10, 10));
		asignacionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleAsignacion = new JLabel("Asignación de Profesores a Cursos", JLabel.CENTER);
		lblTitleAsignacion.setFont(new Font("Arial", Font.BOLD, 18));
		asignacionPanel.add(lblTitleAsignacion, BorderLayout.NORTH);

		// Panel superior con los TextAreas
		JPanel panelTextAreas = new JPanel(new GridLayout(1, 2, 10, 10));

		// Panel de profesores
		JPanel panelProfesores = new JPanel(new BorderLayout(5, 5));
		panelProfesores.setBorder(BorderFactory.createTitledBorder("Profesores Disponibles"));

		txtAreaProfesores = new JTextArea(10, 30);
		txtAreaProfesores.setFont(new Font("Arial", Font.PLAIN, 12));
		txtAreaProfesores.setEditable(false);
		scrollPaneProfesoresArea = new JScrollPane(txtAreaProfesores);
		panelProfesores.add(scrollPaneProfesoresArea, BorderLayout.CENTER);

		// Panel de cursos
		JPanel panelCursos = new JPanel(new BorderLayout(5, 5));
		panelCursos.setBorder(BorderFactory.createTitledBorder("Cursos Disponibles"));

		txtAreaCursos = new JTextArea(10, 30);
		txtAreaCursos.setFont(new Font("Arial", Font.PLAIN, 12));
		txtAreaCursos.setEditable(false);
		scrollPaneCursosArea = new JScrollPane(txtAreaCursos);
		panelCursos.add(scrollPaneCursosArea, BorderLayout.CENTER);

		panelTextAreas.add(panelProfesores);
		panelTextAreas.add(panelCursos);

		// Panel de campos de entrada en fila con etiquetas
		JPanel panelCampos = new JPanel(new GridLayout(2, 3, 10, 5));

		JLabel lblCedulaAsignar = new JLabel(" Cédula del profesor que quiere asignar:");
		lblCedulaAsignar.setFont(new Font("Arial", Font.BOLD, 12));
		txtCedulaAsignar = new JTextField();

		JLabel lblGrupoAsignar = new JLabel(" Nombre del grupo (G1, G2, etc):");
		lblGrupoAsignar.setFont(new Font("Arial", Font.BOLD, 12));
		txtGrupoAsignar = new JTextField();

		JLabel lblSiglasAsignar = new JLabel(" Siglas del curso que le quiere asignar:");
		lblSiglasAsignar.setFont(new Font("Arial", Font.BOLD, 12));
		txtSiglasAsignar = new JTextField();

		panelCampos.add(lblCedulaAsignar);
		panelCampos.add(lblGrupoAsignar);
		panelCampos.add(lblSiglasAsignar);

		panelCampos.add(txtCedulaAsignar);
		panelCampos.add(txtGrupoAsignar);
		panelCampos.add(txtSiglasAsignar);

		JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
		panelSuperior.add(panelTextAreas, BorderLayout.NORTH);
		panelSuperior.add(panelCampos, BorderLayout.SOUTH);

		JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
		panelCentral.setBorder(BorderFactory.createTitledBorder("Asignaciones Realizadas"));

		String[] columnasAsignaciones = { "Escuela", "Cédula del Profesor", "Siglas del Curso", "Grupo" };
		DefaultTableModel modeloAsignaciones = new DefaultTableModel(columnasAsignaciones, 0);
		tablaAsignaciones = new JTable(modeloAsignaciones);
		tablaAsignaciones.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaAsignaciones.setEnabled(true);
		tablaAsignaciones.setDefaultEditor(Object.class, null);

		scrollPaneAsignaciones = new JScrollPane(tablaAsignaciones);
		panelCentral.add(scrollPaneAsignaciones, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		btnAsignarProfesorAsig = new JButton("Asignar Profesor");
		btnAsignarProfesorAsig.setFont(new Font("Arial", Font.BOLD, 13));
		btnAsignarProfesorAsig.setEnabled(true);

		btnDesasignarProfesorAsig = new JButton("Desasignar Profesor");
		btnDesasignarProfesorAsig.setFont(new Font("Arial", Font.BOLD, 13));
		btnDesasignarProfesorAsig.setEnabled(false);

		btnAsignarDirectorAsig = new JButton("Asignar Director");
		btnAsignarDirectorAsig.setFont(new Font("Arial", Font.BOLD, 13));
		btnAsignarDirectorAsig.setEnabled(false);

		btnDeseleccionarTablaAsig = new JButton("Deseleccionar Tabla");
		btnDeseleccionarTablaAsig.setFont(new Font("Arial", Font.BOLD, 13));
		btnDeseleccionarTablaAsig.setEnabled(false);

		btnRegresarAsignacionProf = new JButton("Regresar");
		btnRegresarAsignacionProf.setFont(new Font("Arial", Font.BOLD, 13));
		btnRegresarAsignacionProf.setEnabled(true);

		panelBotones.add(btnAsignarProfesorAsig);
		panelBotones.add(btnDesasignarProfesorAsig);
		panelBotones.add(btnAsignarDirectorAsig);
		panelBotones.add(btnDeseleccionarTablaAsig);
		panelBotones.add(btnRegresarAsignacionProf);

		// Ensamblaje del panel principal
		asignacionPanel.add(panelSuperior, BorderLayout.NORTH);
		asignacionPanel.add(panelCentral, BorderLayout.CENTER);
		asignacionPanel.add(panelBotones, BorderLayout.SOUTH);
	}

	// Panel de consultas
	private JPanel consultasPanel;
	public JButton btnConsultarProfesorCedula;
	public JButton btnConsultarProfesorEscuela;
	public JButton btnConsultarCursosPorProfesor;
	public JButton btnConsultarProfesoresPorCurso;
	public JButton btnConsultaEscuelas;
	public JButton btnConsultaDirectores;
	public JButton btnRegresarConsultas;

	private void ConsultasPanel() {
		consultasPanel = new JPanel(new BorderLayout(10, 10));
		consultasPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleConsultas = new JLabel("Consultas", JLabel.CENTER);
		lblTitleConsultas.setFont(new Font("Arial", Font.BOLD, 30));
		consultasPanel.add(lblTitleConsultas, BorderLayout.NORTH);

		// Panel central con los botones de consulta
		JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20));
		panelBotones.setBorder(new EmptyBorder(50, 50, 50, 50));

		btnConsultarProfesorCedula = new JButton("Consultar Profesor por Cédula");
		btnConsultarProfesorCedula.setFont(new Font("Arial", Font.BOLD, 18));

		btnConsultarProfesorEscuela = new JButton("Consultar Profesor por Escuela");
		btnConsultarProfesorEscuela.setFont(new Font("Arial", Font.BOLD, 18));

		btnConsultarCursosPorProfesor = new JButton("Consultar Cursos por Profesor");
		btnConsultarCursosPorProfesor.setFont(new Font("Arial", Font.BOLD, 18));

		btnConsultarProfesoresPorCurso = new JButton("Consultar Profesores por Curso");
		btnConsultarProfesoresPorCurso.setFont(new Font("Arial", Font.BOLD, 18));

		btnConsultaEscuelas = new JButton("Consulta de Escuelas");
		btnConsultaEscuelas.setFont(new Font("Arial", Font.BOLD, 18));

		btnConsultaDirectores = new JButton("Consulta de Directores");
		btnConsultaDirectores.setFont(new Font("Arial", Font.BOLD, 18));

		panelBotones.add(btnConsultarProfesorCedula);
		panelBotones.add(btnConsultarProfesorEscuela);

		panelBotones.add(btnConsultarCursosPorProfesor);
		panelBotones.add(btnConsultarProfesoresPorCurso);

		panelBotones.add(btnConsultaEscuelas);
		panelBotones.add(btnConsultaDirectores);

		consultasPanel.add(panelBotones, BorderLayout.CENTER);

		// Botón regresar
		JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnRegresarConsultas = new JButton("Regresar");
		btnRegresarConsultas.setFont(new Font("Arial", Font.BOLD, 14));
		panelRegresar.add(btnRegresarConsultas);
		consultasPanel.add(panelRegresar, BorderLayout.SOUTH);
	}

	public ControllerPanelConsultas BusquedaPanelConsultas(String titulo, String labelDescripcion) {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel etiquetaTitulo = new JLabel(titulo, JLabel.CENTER);
		etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(etiquetaTitulo, BorderLayout.NORTH);

		JPanel varPanelFormulario = new JPanel(new BorderLayout(5, 15));
		varPanelFormulario.setBorder(BorderFactory.createTitledBorder("Consulta"));

		JLabel etiquetaCampo = new JLabel(labelDescripcion);
		etiquetaCampo.setFont(new Font("Arial", Font.PLAIN, 14));
		etiquetaCampo.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0)); // Espaciado

		JPanel varPanelCampoBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JTextField campoBuscar = new JTextField(30);
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.setFont(new Font("Arial", Font.BOLD, 14));

		varPanelCampoBoton.add(campoBuscar);
		varPanelCampoBoton.add(botonBuscar);

		// Agrupamos label + campo
		JPanel contenedorCampo = new JPanel(new BorderLayout());
		contenedorCampo.add(etiquetaCampo, BorderLayout.NORTH);
		contenedorCampo.add(varPanelCampoBoton, BorderLayout.CENTER);

		varPanelFormulario.add(contenedorCampo, BorderLayout.NORTH);

		JTextArea areaMostrar = new JTextArea(10, 20);
		areaMostrar.setEditable(false);
		areaMostrar.setFont(new Font("Arial", Font.PLAIN, 14));

		JScrollPane scrollResultado = new JScrollPane(areaMostrar);
		varPanelFormulario.add(scrollResultado, BorderLayout.CENTER);

		JPanel varPanelBotonVolver = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton botonVolver = new JButton("Regresar");
		botonVolver.setFont(new Font("Arial", Font.BOLD, 14));
		varPanelBotonVolver.add(botonVolver);

		varPanelFormulario.add(varPanelBotonVolver, BorderLayout.SOUTH);

		panel.add(varPanelFormulario, BorderLayout.CENTER);

		return new ControllerPanelConsultas(panel, campoBuscar, areaMostrar, botonBuscar, botonVolver);
	}

	// Panel de consulta de directores
	private JPanel panelConsultaDirectores;
	public JTextArea txtAreaDirectores;
	private JScrollPane scrollPaneDirectores;
	public JButton btnRegresarDirectores;

	private void DirectoresPanel() {
		panelConsultaDirectores = new JPanel(new BorderLayout(10, 10));
		panelConsultaDirectores.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitleDirectores = new JLabel("Consulta de Directores", JLabel.CENTER);
		lblTitleDirectores.setFont(new Font("Arial", Font.BOLD, 18));
		panelConsultaDirectores.add(lblTitleDirectores, BorderLayout.NORTH);

		JPanel panelContenidoDirectores = new JPanel(new BorderLayout(10, 10));
		panelContenidoDirectores.setBorder(BorderFactory.createTitledBorder("Directores Registrados"));

		JLabel lblTituloDirectores = new JLabel("Lista de Directores por Escuela:", JLabel.CENTER);
		lblTituloDirectores.setFont(new Font("Arial", Font.BOLD, 16));
		panelContenidoDirectores.add(lblTituloDirectores, BorderLayout.NORTH);

		txtAreaDirectores = new JTextArea();
		txtAreaDirectores.setEditable(false);
		txtAreaDirectores.setFont(new Font("Arial", Font.PLAIN, 14));
		txtAreaDirectores.setBorder(BorderFactory.createEtchedBorder());

		scrollPaneDirectores = new JScrollPane(txtAreaDirectores);
		panelContenidoDirectores.add(scrollPaneDirectores, BorderLayout.CENTER);

		JPanel panelBotonRegresar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnRegresarDirectores = new JButton("Regresar");
		btnRegresarDirectores.setFont(new Font("Arial", Font.BOLD, 14));
		panelBotonRegresar.add(btnRegresarDirectores);
		panelContenidoDirectores.add(panelBotonRegresar, BorderLayout.SOUTH);

		panelConsultaDirectores.add(panelContenidoDirectores, BorderLayout.CENTER);
	}

	// Panel de consulta de escuelas
	private JPanel panelConsultaEscuelas;
	public JTextArea txtAreaConsultaEscuelas;
	private JScrollPane scrollPanelConsultaEscuelas;
	public JButton btnRegresarEscuelas;

	private void ConsultaEscuelasPanel() {
		panelConsultaEscuelas = new JPanel(new BorderLayout(10, 10));
		panelConsultaEscuelas.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTitulo = new JLabel("Consulta de Escuelas", JLabel.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		panelConsultaEscuelas.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
		panelContenido.setBorder(BorderFactory.createTitledBorder("Escuelas y Cursos"));

		JLabel lblDescripcion = new JLabel("Listado de cursos por escuela y profesor asignado:", JLabel.CENTER);
		lblDescripcion.setFont(new Font("Arial", Font.BOLD, 16));
		panelContenido.add(lblDescripcion, BorderLayout.NORTH);

		txtAreaConsultaEscuelas = new JTextArea();
		txtAreaConsultaEscuelas.setEditable(false);
		txtAreaConsultaEscuelas.setFont(new Font("Arial", Font.PLAIN, 14));
		txtAreaConsultaEscuelas.setBorder(BorderFactory.createEtchedBorder());

		scrollPanelConsultaEscuelas = new JScrollPane(txtAreaConsultaEscuelas);
		panelContenido.add(scrollPanelConsultaEscuelas, BorderLayout.CENTER);

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnRegresarEscuelas = new JButton("Regresar");
		btnRegresarEscuelas.setFont(new Font("Arial", Font.BOLD, 14));
		panelBoton.add(btnRegresarEscuelas);
		panelContenido.add(panelBoton, BorderLayout.SOUTH);

		panelConsultaEscuelas.add(panelContenido, BorderLayout.CENTER);
	}

	public void showPanel(String panelName) {
		CardLayout cl = (CardLayout) contentPanel.getLayout();
		cl.show(contentPanel, panelName);
	}

	public void setUniversityName(String name) {
		lblEscuelasTitle.setText("Universidad: " + name);
	}

	public void enableCursosControls(boolean enable) {
		varBtnEliminar.setEnabled(enable);
		varBtnBuscarPorEscuela.setEnabled(enable);
	}

	// Metodo para configurar listeners de los botones del menu
	public void setupMenuListeners(ActionListener universidadListener, ActionListener escuelasListener,
			ActionListener cursosListener, ActionListener busquedaPorEscuela, ActionListener profesoresListener,
			ActionListener asignacionProfesores, ActionListener consultasListener,
			ActionListener busquedaDeProfesorPorCurso, ActionListener busquedaPorCedula,
			ActionListener busquedaPorEscuelaConsultas) {
		btnUniversidad.addActionListener(universidadListener);
		btnEscuelas.addActionListener(escuelasListener);
		btnCursos.addActionListener(cursosListener);
		varBtnBuscarPorEscuela.addActionListener(busquedaPorEscuela);
		btnProfesores.addActionListener(profesoresListener);
		btnAsignacionProfesores.addActionListener(asignacionProfesores);
		btnConsultarCursosPorProfesor.addActionListener(consultasListener);
		btnConsultarProfesoresPorCurso.addActionListener(busquedaDeProfesorPorCurso);
		btnConsultarProfesorCedula.addActionListener(busquedaPorCedula);
		btnConsultarProfesorEscuela.addActionListener(busquedaPorEscuelaConsultas);

	}
}