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
        
        //Añadir componentes al panel principal
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        //Añadir panel principal al frame
        add(mainPanel);
    
    }
    
     //Botones del menu
    public JButton M_btnUniversidad;
    public JButton M_btnEscuelas;
    public JButton M_btnCursos;
  
    private void MenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menú"));
        menuPanel.setPreferredSize(new Dimension(150, 600));
        
        JLabel lblMenu = new JLabel("OPCIONES");
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        M_btnUniversidad = new JButton("Universidad");
        M_btnEscuelas = new JButton("Escuelas");
        M_btnCursos = new JButton("Cursos");
        
        //Configuracion de los botones
        JButton[] buttons = {M_btnUniversidad, M_btnEscuelas, M_btnCursos};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(140, 40));
            btn.setPreferredSize(new Dimension(140, 40));
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMargin(new Insets(10, 10, 10, 10));
        }
        
        //Añadir componentes al panel de menu
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(lblMenu);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(M_btnUniversidad);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(M_btnEscuelas);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(M_btnCursos);
    }
    
    //Componentes de Universidad (Registrar y Actualizar)
    //Registrar Universidad
    private JLabel U_lblName;
    private JLabel U_lblAdress;
    private JLabel U_lblPhoneNumber;
    public JTextField U_txtName;
    public JTextField U_txtAdress;
    public JTextField U_txtPhoneNumber;
    public JButton U_btnRegisterUniversity;
    
    private JLabel U_lblUniversityName;
    private JLabel U_lblNewAdress;
    private JLabel U_lblNewPhone;
    public JTextField U_txtNewAdress;
    public JTextField U_txtNewPhone;
    public JButton U_btnUpdateUniversity;
    
    private JPanel universidadPanel;
    
    private void UniversidadPanel() {
        universidadPanel = new JPanel(new BorderLayout(10, 10));
        universidadPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitleUniversidad = new JLabel("Gestión de Universidad", JLabel.CENTER);
        lblTitleUniversidad.setFont(new Font("Arial", Font.BOLD, 18));
        universidadPanel.add(lblTitleUniversidad, BorderLayout.NORTH);
        
        JPanel contentUniversidad = new JPanel(new GridLayout(2, 1, 10, 10));
        
        //Panel para registrar universidad
        JPanel registerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrar Universidad"));
        
        U_lblName = new JLabel("Nombre de la Universidad:");
        U_lblName.setFont(new Font("Arial", Font.BOLD, 12));
        U_txtName = new JTextField();
        
        U_lblAdress = new JLabel("Dirección:");
        U_lblAdress.setFont(new Font("Arial", Font.BOLD, 12));
        U_txtAdress = new JTextField();
        
        U_lblPhoneNumber = new JLabel("Número de Teléfono:");
        U_lblPhoneNumber.setFont(new Font("Arial", Font.BOLD, 12));
        U_txtPhoneNumber = new JTextField();
        
        U_btnRegisterUniversity = new JButton("Registrar Universidad");
        U_btnRegisterUniversity.setFont(new Font("Arial", Font.PLAIN, 14));
        
        registerPanel.add(U_lblName);
        registerPanel.add(U_txtName);
        registerPanel.add(U_lblAdress);
        registerPanel.add(U_txtAdress);
        registerPanel.add(U_lblPhoneNumber);
        registerPanel.add(U_txtPhoneNumber);
        registerPanel.add(new JLabel());
        registerPanel.add(U_btnRegisterUniversity);
        
        //Panel para actualizar universidad
        JPanel updatePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        updatePanel.setBorder(BorderFactory.createTitledBorder("Actualizar Universidad"));
        
        U_lblUniversityName = new JLabel("Universidad: ");
        U_lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));
        
        U_lblNewAdress = new JLabel("Nueva Dirección:");
        U_lblNewAdress.setFont(new Font("Arial", Font.BOLD, 12));
        U_txtNewAdress = new JTextField();
        
        U_lblNewPhone = new JLabel("Nuevo Número de Teléfono:");
        U_lblNewPhone.setFont(new Font("Arial", Font.BOLD, 12));
        U_txtNewPhone = new JTextField();
        
        U_btnUpdateUniversity = new JButton("Actualizar Universidad");
        U_btnUpdateUniversity.setFont(new Font("Arial", Font.PLAIN, 14));
        U_btnUpdateUniversity.setEnabled(false);
        
        updatePanel.add(U_lblUniversityName);
        updatePanel.add(new JLabel());
        updatePanel.add(U_lblNewAdress);
        updatePanel.add(U_txtNewAdress);
        updatePanel.add(U_lblNewPhone);
        updatePanel.add(U_txtNewPhone);
        updatePanel.add(new JLabel());
        updatePanel.add(U_btnUpdateUniversity);
        
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
        lblEscuelasTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblNameSchool = new JLabel("Nombre De la Escuela:");
        lblNameSchool.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtNameSchool = new JTextField();
        
        btnRegisterSchool = new JButton("Registrar Escuela");
        btnRegisterSchool.setFont(new Font("Arial", Font.PLAIN, 14));
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
        txtAreaEscuelas.setFont(new Font("Arial", Font.PLAIN, 30));
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
        tablaCursos = new JTable(modelo);
        tablaCursos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaCursos.setEnabled(true); // Permitir seleccion
        tablaCursos.setDefaultEditor(Object.class, null); // NO PERMITIR EDITAR DESDE LA TABLE
        
        scrollPaneCursos = new JScrollPane(tablaCursos);
        panelConsultaCursos.add(scrollPaneCursos, BorderLayout.CENTER);
        
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        varBtnRegistrar = new JButton("Registrar Curso");
        varBtnRegistrar.setFont(new Font("Arial", Font.PLAIN, 13));
        varBtnRegistrar.setEnabled(false);
        panelBotonesAccion.add(varBtnRegistrar);
        
        panelOpcionesCursos = new JPanel(new BorderLayout(5, 5));
        panelOpcionesCursos.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));
        
        JLabel varOpciones = new JLabel("Más Opciones, Funcionan ingresando las Siglas");
        varOpciones.setFont(new Font("Arial", Font.BOLD, 14));
        varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        panelOpcionesCursos.add(varOpciones, BorderLayout.NORTH);
        
        JPanel additionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varBtnEliminar = new JButton("Eliminar Curso");
        varBtnModificar = new JButton("Modificar Curso");
        varBtnBuscarPorEscuela = new JButton("Buscar Por Escuela");
        
        JButton[] botones = { varBtnEliminar, varBtnModificar, varBtnBuscarPorEscuela };
        for (JButton btn : botones) {
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
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
    //jeferson
    public JPanel panelBusqueda;
    public JTextField imputBuscar;
    public JTextArea showTexteArea;
    public JButton btnBuscar;
    private void BusquedaPanel() {
        // Configurar el panel principal de búsqueda
        panelBusqueda = new JPanel(new BorderLayout(10, 10));
        panelBusqueda.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelBusqueda.setPreferredSize(new Dimension(300, 500));
        panelBusqueda.setBackground(Color.LIGHT_GRAY); // Color de fondo para depuración

        // Título del panel de búsqueda
        JLabel lblBusquedaTitle = new JLabel("Buscar Escuela", JLabel.CENTER);
        lblBusquedaTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panelBusqueda.add(lblBusquedaTitle, BorderLayout.NORTH);

        // Panel para el formulario de consulta
        JPanel panelBusquedaForm = new JPanel(new BorderLayout(5, 5));
        panelBusquedaForm.setBorder(BorderFactory.createTitledBorder("Consulta"));
        panelBusquedaForm.setBackground(Color.CYAN); // Color de fondo para depuración

        // Panel para el campo de texto y el botón
        JPanel panelInputYBoton = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Usar FlowLayout
        panelInputYBoton.setBackground(Color.YELLOW); // Color de fondo para depuración

        // Campo de texto
        imputBuscar = new JTextField();
        imputBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        imputBuscar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Eliminar título
        panelInputYBoton.add(imputBuscar);

        // Botón de búsqueda
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        panelInputYBoton.add(btnBuscar);

        // Agregar el panel de entrada al formulario de consulta
        panelBusquedaForm.add(panelInputYBoton, BorderLayout.NORTH);

        // Área de resultados
        showTexteArea = new JTextArea(10, 20);
        showTexteArea.setEditable(false);
        showTexteArea.setFont(new Font("Arial", Font.PLAIN, 12));
        showTexteArea.setBorder(BorderFactory.createEtchedBorder());

        JScrollPane scrollBusqueda = new JScrollPane(showTexteArea);
        panelBusquedaForm.add(scrollBusqueda, BorderLayout.CENTER);

        // Agregar el formulario de consulta al panel de búsqueda
        panelBusqueda.add(panelBusquedaForm, BorderLayout.CENTER);

        // Forzar redimensionamiento y actualización
        panelBusqueda.revalidate();
        panelBusqueda.repaint();
    }
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
    
    public void setUniversityName(String name) {
        U_lblUniversityName.setText("Universidad: " + name);
        lblEscuelasTitle.setText("Universidad: " + name);
    }
    
    public void enableCursosControls(boolean enable) {
        varBtnRegistrar.setEnabled(enable);
        varBtnEliminar.setEnabled(enable);
        varBtnModificar.setEnabled(enable);
        varBtnBuscarPorEscuela.setEnabled(enable);
    }
    
    //Metodo para configurar listeners de los botones del menu
    public void setupMenuListeners(ActionListener universidadListener, ActionListener escuelasListener, ActionListener cursosListener, ActionListener busquedaPorEscuela ) {
        M_btnUniversidad.addActionListener(universidadListener);
        M_btnEscuelas.addActionListener(escuelasListener);
        M_btnCursos.addActionListener(cursosListener); 
        varBtnBuscarPorEscuela.addActionListener(busquedaPorEscuela);

    }
}