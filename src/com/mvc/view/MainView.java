package com.mvc.view;

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
    
    // Panel para mostrar el contenido según la selección
    private JPanel contentPanel;
    
    // Botones del menú
    public JButton btnUniversidad;
    public JButton btnEscuelas;
    public JButton btnCursos;
    
    // Paneles de contenido
    private JPanel universidadPanel;
    private JPanel escuelasPanel;
    private JPanel cursosPanel;
    
    // Componentes de Universidad (Registrar y Actualizar)
    // Registrar Universidad
    private JLabel lblName;
    private JLabel lblAdress;
    private JLabel lblPhoneNumber;
    public JTextField txtName;
    public JTextField txtAdress;
    public JTextField txtPhoneNumber;
    public JButton btnRegisterUniversity;
    
    private JLabel lblUniversityName;
    private JLabel lblNewAdress;
    private JLabel lblNewPhone;
    public JTextField txtNewAdress;
    public JTextField txtNewPhone;
    public JButton btnUpdateUniversity;
    
    // Componentes de Escuelas
    public JLabel lblEscuelasTitle;
    public JLabel lblNameSchool;
    public JTextField txtNameSchool;
    public JButton btnRegisterSchool;
    private JLabel lblTituloEscuelas;
    public JTextArea txtAreaEscuelas;
    private JScrollPane scrollPaneEscuelas;
    
    // Componentes de Cursos
    private JPanel panelRegistroCursos;
    private JPanel panelConsultaCursos;
    private JPanel panelOpcionesCursos;
    
    private JLabel varLblSiglasNom;
    private JLabel varLblDescripcionNom;
    private JLabel varLblNombreEscuela;
    public JTextField varTxtSigla;
    public JTextField varTxtDescripcion;
    public JTextField varTxtEscuelaNombres;
    
    public JButton varBtnRegistrar;
    public JButton varBtnEliminar;
    public JButton varBtnModificar;
    public JButton varBtnBuscarPorEscuela;
    
    private JLabel lblTituloCursos;
    public JTable tablaEscuelas;
    private JScrollPane scrollPaneCursos;
    
    public MainView() {
        setTitle("Sistema de Gestión Universitaria");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Configuración del panel principal
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Configuración del panel de menú
        MenuPanel();
        
        // Configuración del panel de contenido
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBorder(BorderFactory.createEtchedBorder());
        
        // Configurar los paneles de contenido
        UniversidadPanel();
        EscuelasPanel();
        CursosPanel();
        
        // Añadir paneles al contenido
        contentPanel.add(universidadPanel, "UNIVERSIDAD");
        contentPanel.add(escuelasPanel, "ESCUELAS");
        contentPanel.add(cursosPanel, "CURSOS");
        
        // Añadir componentes al panel principal
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Añadir panel principal al frame
        add(mainPanel);
    
    }
 
    private void MenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menú"));
        menuPanel.setPreferredSize(new Dimension(150, 600));
        
        JLabel lblMenu = new JLabel("OPCIONES");
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnUniversidad = new JButton("Universidad");
        btnEscuelas = new JButton("Escuelas");
        btnCursos = new JButton("Cursos");
        
        // Configuración de los botones
        JButton[] buttons = {btnUniversidad, btnEscuelas, btnCursos};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(140, 40));
            btn.setPreferredSize(new Dimension(140, 40));
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setMargin(new Insets(10, 10, 10, 10));
        }
        
        // Añadir componentes al panel de menú
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(lblMenu);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(btnUniversidad);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnEscuelas);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnCursos);
    }
    
    private void UniversidadPanel() {
        universidadPanel = new JPanel(new BorderLayout(10, 10));
        universidadPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitleUniversidad = new JLabel("Gestión de Universidad", JLabel.CENTER);
        lblTitleUniversidad.setFont(new Font("Arial", Font.BOLD, 18));
        universidadPanel.add(lblTitleUniversidad, BorderLayout.NORTH);
        
        JPanel contentUniversidad = new JPanel(new GridLayout(2, 1, 10, 10));
        
        // Panel para registrar universidad
        JPanel registerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrar Universidad"));
        
        lblName = new JLabel("Nombre de la Universidad:");
        lblName.setFont(new Font("Courier New", Font.BOLD, 12));
        txtName = new JTextField();
        
        lblAdress = new JLabel("Dirección:");
        lblAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        txtAdress = new JTextField();
        
        lblPhoneNumber = new JLabel("Número de Teléfono:");
        lblPhoneNumber.setFont(new Font("Courier New", Font.BOLD, 12));
        txtPhoneNumber = new JTextField();
        
        btnRegisterUniversity = new JButton("Registrar Universidad");
        btnRegisterUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        
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
        
        lblUniversityName = new JLabel("Universidad: ");
        lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblNewAdress = new JLabel("Nueva Dirección:");
        lblNewAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        txtNewAdress = new JTextField();
        
        lblNewPhone = new JLabel("Nuevo Número de Teléfono:");
        lblNewPhone.setFont(new Font("Courier New", Font.BOLD, 12));
        txtNewPhone = new JTextField();
        
        btnUpdateUniversity = new JButton("Actualizar Universidad");
        btnUpdateUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnUpdateUniversity.setEnabled(false);
        
        updatePanel.add(lblUniversityName);
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
    
    private void EscuelasPanel() {
        escuelasPanel = new JPanel(new BorderLayout(10, 10));
        escuelasPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitleEscuelas = new JLabel("Gestión de Escuelas", JLabel.CENTER);
        lblTitleEscuelas.setFont(new Font("Arial", Font.BOLD, 18));
        escuelasPanel.add(lblTitleEscuelas, BorderLayout.NORTH);
        
        JPanel panelRegistroEscuela = new JPanel(new GridLayout(3, 2, 5, 5));
        panelRegistroEscuela.setBorder(BorderFactory.createTitledBorder("Registrar Escuela"));
        
        lblEscuelasTitle = new JLabel("Universidad: ");
        lblEscuelasTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblNameSchool = new JLabel("Nombre De la Escuela:");
        lblNameSchool.setFont(new Font("Courier New", Font.BOLD, 12));
        
        txtNameSchool = new JTextField();
        
        btnRegisterSchool = new JButton("Registrar Escuela");
        btnRegisterSchool.setFont(new Font("Times New Roman", Font.PLAIN, 14));
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
        txtAreaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14));
        txtAreaEscuelas.setBorder(BorderFactory.createEtchedBorder());
        
        scrollPaneEscuelas = new JScrollPane(txtAreaEscuelas);
        panelConsultaEscuelas.add(scrollPaneEscuelas, BorderLayout.CENTER);
        
        JPanel contentEscuelas = new JPanel(new BorderLayout(10, 10));
        contentEscuelas.add(panelRegistroEscuela, BorderLayout.NORTH);
        contentEscuelas.add(panelConsultaEscuelas, BorderLayout.CENTER);
        
        escuelasPanel.add(contentEscuelas, BorderLayout.CENTER);
    }
    
    private void CursosPanel() {
        cursosPanel = new JPanel(new BorderLayout(10, 10));
        cursosPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitleCursos = new JLabel("Gestión de Cursos", JLabel.CENTER);
        lblTitleCursos.setFont(new Font("Arial", Font.BOLD, 18));
        cursosPanel.add(lblTitleCursos, BorderLayout.NORTH);
        
        panelRegistroCursos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelRegistroCursos.setBorder(BorderFactory.createTitledBorder("Registro de Cursos"));
        
        varLblNombreEscuela = new JLabel("Nombre de la Escuela:");
        varTxtEscuelaNombres = new JTextField();
        varLblSiglasNom = new JLabel("Siglas del Curso:");
        varTxtSigla = new JTextField();
        varLblDescripcionNom = new JLabel("Descripción del Curso:");
        varTxtDescripcion = new JTextField();
        
        panelRegistroCursos.add(varLblNombreEscuela);
        panelRegistroCursos.add(varTxtEscuelaNombres);
        panelRegistroCursos.add(varLblSiglasNom);
        panelRegistroCursos.add(varTxtSigla);
        panelRegistroCursos.add(varLblDescripcionNom);
        panelRegistroCursos.add(varTxtDescripcion);
        
        panelConsultaCursos = new JPanel(new BorderLayout(10, 10));
        panelConsultaCursos.setBorder(BorderFactory.createTitledBorder("Cursos Registrados"));
        
        lblTituloCursos = new JLabel("Lista de las Escuelas y Cursos:", JLabel.CENTER);
        lblTituloCursos.setFont(new Font("Arial", Font.BOLD, 16));
        panelConsultaCursos.add(lblTituloCursos, BorderLayout.NORTH);
        
        String[] columnas = {"Nombre de la Escuela", "Siglas del Curso", "Descripción del Curso"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaEscuelas = new JTable(modelo);
        tablaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14));
        tablaEscuelas.setRowHeight(25);
        tablaEscuelas.setEnabled(false);
        
        scrollPaneCursos = new JScrollPane(tablaEscuelas);
        panelConsultaCursos.add(scrollPaneCursos, BorderLayout.CENTER);
        
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        varBtnRegistrar = new JButton("Registrar Curso");
        varBtnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        varBtnRegistrar.setEnabled(false);
        panelBotonesAccion.add(varBtnRegistrar);
        
        panelOpcionesCursos = new JPanel(new BorderLayout(5, 5));
        panelOpcionesCursos.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));
        
        JLabel varOpciones = new JLabel("Más Opciones, Funcionan ingresando las Siglas");
        varOpciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
        varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        panelOpcionesCursos.add(varOpciones, BorderLayout.NORTH);
        
        JPanel additionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varBtnEliminar = new JButton("Eliminar Curso");
        varBtnModificar = new JButton("Modificar Curso");
        varBtnBuscarPorEscuela = new JButton("Buscar Por Escuela");
        
        JButton[] botones = { varBtnEliminar, varBtnModificar, varBtnBuscarPorEscuela };
        for (JButton btn : botones) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btn.setFocusPainted(false);
            btn.setEnabled(false);
            additionalButtonsPanel.add(btn);
        }
        
        panelOpcionesCursos.add(additionalButtonsPanel, BorderLayout.CENTER);
        
        JPanel contentCursos = new JPanel(new BorderLayout(10, 10));
        contentCursos.add(panelRegistroCursos, BorderLayout.NORTH);
        contentCursos.add(panelConsultaCursos, BorderLayout.CENTER);
        contentCursos.add(panelBotonesAccion, BorderLayout.SOUTH);
        contentCursos.add(panelOpcionesCursos, BorderLayout.EAST);
        
        cursosPanel.add(contentCursos, BorderLayout.CENTER);
    }
    
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
    
    public void setUniversityName(String name) {
        lblUniversityName.setText("Universidad: " + name);
        lblEscuelasTitle.setText("Universidad: " + name);
    }
    
    public void enableRegisterUniversity(boolean enable) {
    	txtName.setEnabled(enable);
    	txtAdress.setEnabled(enable);
    	txtPhoneNumber.setEnabled(enable);
    	btnRegisterUniversity.setEnabled(enable);
    }
    
    public void enableUpdateControls(boolean enable) {
        btnUpdateUniversity.setEnabled(enable);
    }
    
    public void enableEscuelaControls(boolean enable) {
        btnRegisterSchool.setEnabled(enable);
    }
    
    public void enableCursosControls(boolean enable) {
        varBtnRegistrar.setEnabled(enable);
        varBtnEliminar.setEnabled(enable);
        varBtnModificar.setEnabled(enable);
        varBtnBuscarPorEscuela.setEnabled(enable);
    }
    
    public void agregarDatosTabla(Object[][] datos) {
        // Obtener el modelo de tabla existente
    	DefaultTableModel modeloTabla = (DefaultTableModel) tablaEscuelas.getModel();
        
        // Agregar los nuevos datos
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    
    public void eliminarCurso(String pDato) {
    	 DefaultTableModel modeloTabla = (DefaultTableModel) tablaEscuelas.getModel();
        boolean encontrado = false;
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorCelda = modeloTabla.getValueAt(i, 1);
            if (valorCelda != null && valorCelda.toString().equalsIgnoreCase(pDato)) {
                modeloTabla.removeRow(i);
                JOptionPane.showMessageDialog(null, "Se encontró el curso y se eliminó");
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            JOptionPane.showMessageDialog(null, "No se encontró el curso");
        }
    }
    
    // Método para configurar listeners de los botones del menú
    public void setupMenuListeners(ActionListener universidadListener, ActionListener escuelasListener, ActionListener cursosListener) {
        btnUniversidad.addActionListener(universidadListener);
        btnEscuelas.addActionListener(escuelasListener);
        btnCursos.addActionListener(cursosListener);
    }
}