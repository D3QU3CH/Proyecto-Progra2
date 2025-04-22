package com.mvc.view;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    
    //Panel principal que contiene todo
    private JPanel mainPanel;
    
    //Panel para el menú lateral
    private JPanel menuPanel;
    
    //Panel para mostrar el contenido segun la seleccion
    private JPanel contentPanel;
    
    public JButton btnVerEscuelas;

    public MainView() {
        setTitle("Sistema de Gestión Universitaria");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        FlatDraculaIJTheme.setup();
        //Configuracion del panel principal
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        //Configuracion del panel de menu
        MenuPanel();
        
        //Configuracion del panel de contenido
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBorder(BorderFactory.createEtchedBorder());
        
        //Configurar los paneles de contenido
        UniversidadPanel();
        EscuelasPanel();
        CursosPanel();
        BusquedaPanel();
        
        //Añadir paneles al contenido
        contentPanel.add(universidadPanel, "UNIVERSIDAD");
        contentPanel.add(escuelasPanel, "ESCUELAS");
        contentPanel.add(cursosPanel, "CURSOS");
        contentPanel.add(panelBusqueda, "BUSQUEDA");
        //contentPanel.add(panelBusqueda, "REGRESAR");
        //Añadir componentes al panel principal
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        //Añadir panel principal al frame
        add(mainPanel);
    
    }
    
     //Botones del menu
    public JButton btnUniversidad;
    public JButton btnEscuelas;
    public JButton btnCursos;
  
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
        
        //Configuracion de los botones
        JButton[] buttons = {btnUniversidad, btnEscuelas, btnCursos};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(140, 40));
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        
        //Añadir componentes al panel de menu
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(lblMenu);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(btnUniversidad);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnEscuelas);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnCursos);
    }
    
    //Componentes de Universidad (Registrar y Actualizar)
    //Registrar Universidad
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
        
        //Panel para registrar universidad
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
        
        //Panel para actualizar universidad
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
    
    //Componentes de Escuelas
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
 
    //Componentes de Cursos
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
        varLblDescripcionNom = new JLabel("Descripción del Curso:");
        varLblDescripcionNom.setFont(new Font("Arial", Font.BOLD, 14));
        varTxtDescripcion = new JTextField();
        
        panelRegistroCursos.add(varLblNombreEscuela);
        panelRegistroCursos.add(escuelaPanel); // Añadimos el panel combinado
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
        tablaCursos = new JTable(modelo);
        tablaCursos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaCursos.setEnabled(true); // Permitir seleccion
        tablaCursos.setDefaultEditor(Object.class, null); // NO PERMITIR EDITAR DESDE LA TABLE
        
        scrollPaneCursos = new JScrollPane(tablaCursos);
        panelConsultaCursos.add(scrollPaneCursos, BorderLayout.CENTER);
        
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        varBtnRegistrar = new JButton("Agregar Curso");
        varBtnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        varBtnRegistrar.setEnabled(false);
        panelBotonesAccion.add(varBtnRegistrar);
        
        panelOpcionesCursos = new JPanel(new BorderLayout(5, 5));
        panelOpcionesCursos.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));
        
        JLabel varOpciones = new JLabel("Más Opciones...");
        varOpciones.setFont(new Font("Arial", Font.BOLD, 14));
        varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        panelOpcionesCursos.add(varOpciones, BorderLayout.NORTH);
        
        JPanel additionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varBtnEliminar = new JButton("Eliminar Curso");
        varBtnModificar = new JButton("Modificar Curso");
        varBtnBuscarPorEscuela = new JButton("Buscar Cursos por Escuela");
        
        JButton[] botones = { varBtnEliminar, varBtnModificar, varBtnBuscarPorEscuela };
        for (JButton btn : botones) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
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
    
    //panel de busqueda por curso (Escuela)
    public JPanel panelBusqueda;
    public JTextField txtBuscar;
    public JTextArea showTextArea;
    public JButton btnBuscar;
    public JButton btnLimpiar;
    public  JButton varBtnRegresar;
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
    
    //Metodo para configurar listeners de los botones del menu
    public void setupMenuListeners(ActionListener universidadListener, ActionListener escuelasListener, ActionListener cursosListener, ActionListener busquedaPorEscuela,ActionListener regresar ) {
        btnUniversidad.addActionListener(universidadListener);
        btnEscuelas.addActionListener(escuelasListener);
        btnCursos.addActionListener(cursosListener); 
        varBtnBuscarPorEscuela.addActionListener(busquedaPorEscuela);
        varBtnRegresar.addActionListener(regresar);
    }
}