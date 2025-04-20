package com.mvc.view;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

    // Panel principal que contiene todo
    private JPanel varMainPanel;

    // Panel para el menú lateral
    private JPanel varMenuPanel;

    // Panel para mostrar el contenido según la selección
    private JPanel varContentPanel;

    public MainView() {
        setTitle("University Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tema de toda la interfaz
        FlatDraculaIJTheme.setup();

        // Configuración del panel principal
        varMainPanel = new JPanel(new BorderLayout(10, 10));
        varMainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Configuración del panel de contenido
        varContentPanel = new JPanel(new CardLayout());
        varContentPanel.setBorder(BorderFactory.createEtchedBorder());

        // Llamar los paneles de contenido
        MenuPanel();
        UniversityPanel();
        SchoolsPanel();
        CoursesPanel();
        SearchPanel();

        // Añadir paneles al contenido
        varContentPanel.add(varUniversityPanel, "UNIVERSITY");
        varContentPanel.add(varSchoolsPanel, "SCHOOLS");
        varContentPanel.add(varCoursesPanel, "COURSES");
        varContentPanel.add(varSearchPanel, "SEARCH");

        // Añadir componentes al panel principal
        varMainPanel.add(varMenuPanel, BorderLayout.WEST);
        varMainPanel.add(varContentPanel, BorderLayout.CENTER);

        // Añadir panel principal al frame
        add(varMainPanel);
    }

    // Botones del menú
    public JButton varUniversityButton;
    public JButton varSchoolsButton;
    public JButton varCoursesButton;

    private void MenuPanel() {
        varMenuPanel = new JPanel();
        varMenuPanel.setLayout(new BoxLayout(varMenuPanel, BoxLayout.Y_AXIS));
        varMenuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        varMenuPanel.setPreferredSize(new Dimension(150, 600));

        JLabel varMenuLabel = new JLabel("OPCIONES");
        varMenuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        varUniversityButton = new JButton("Universidad");
        varSchoolsButton = new JButton("Escuelas");
        varCoursesButton = new JButton("Cursos");

        // Configuración de los botones
        JButton[] buttons = {varUniversityButton, varSchoolsButton, varCoursesButton};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(140, 40));
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // Añadir componentes al panel de menú
        varMenuPanel.add(Box.createVerticalStrut(20));
        varMenuPanel.add(varMenuLabel);
        varMenuPanel.add(Box.createVerticalStrut(20));
        varMenuPanel.add(varUniversityButton);
        varMenuPanel.add(Box.createVerticalStrut(10));
        varMenuPanel.add(varSchoolsButton);
        varMenuPanel.add(Box.createVerticalStrut(10));
        varMenuPanel.add(varCoursesButton);
    }

    // Componentes de Universidad (Registrar y Actualizar)
    private JLabel varNameLabel;
    private JLabel varAddressLabel;
    private JLabel varPhoneLabel;
    public JTextField varNameField;
    public JTextField varAddressField;
    public JTextField varPhoneField;
    public JButton varRegisterUniversityButton;
    private JLabel varUniversityNameLabel;
    private JLabel varNewAddressLabel;
    private JLabel varNewPhoneLabel;
    public JTextField varNewAddressField;
    public JTextField varNewPhoneField;
    public JButton varUpdateUniversityButton;
    private JPanel varUniversityPanel;

    private void UniversityPanel() {
        varUniversityPanel = new JPanel(new BorderLayout(10, 10));
        varUniversityPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel varUniversityTitle = new JLabel("Gestion de Universidad", JLabel.CENTER);
        varUniversityTitle.setFont(new Font("Arial", Font.BOLD, 18));
        varUniversityPanel.add(varUniversityTitle, BorderLayout.NORTH);

        JPanel varUniversityContent = new JPanel(new GridLayout(2, 1, 10, 10));

        // Panel para registrar universidad
        JPanel varRegisterPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        varRegisterPanel.setBorder(BorderFactory.createTitledBorder("Registrar Universidad"));

        varNameLabel = new JLabel("Nombre de Universidad:");
        varNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varNameField = new JTextField();

        varAddressLabel = new JLabel("Direccion:");
        varAddressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varAddressField = new JTextField();

        varPhoneLabel = new JLabel("Numero Telefonico:");
        varPhoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varPhoneField = new JTextField();

        varRegisterUniversityButton = new JButton("Registrar Universidad");
        varRegisterUniversityButton.setFont(new Font("Arial", Font.BOLD, 16));

        varRegisterPanel.add(varNameLabel);
        varRegisterPanel.add(varNameField);
        varRegisterPanel.add(varAddressLabel);
        varRegisterPanel.add(varAddressField);
        varRegisterPanel.add(varPhoneLabel);
        varRegisterPanel.add(varPhoneField);
        varRegisterPanel.add(new JLabel());
        varRegisterPanel.add(varRegisterUniversityButton);

        // Panel para actualizar universidad
        JPanel varUpdatePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        varUpdatePanel.setBorder(BorderFactory.createTitledBorder("Actualizar Universidad"));

        varUniversityNameLabel = new JLabel("Universidad: ");
        varUniversityNameLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        varNewAddressLabel = new JLabel("Nueva Direccion:");
        varNewAddressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varNewAddressField = new JTextField();

        varNewPhoneLabel = new JLabel("Nuevo Numero Telefonico:");
        varNewPhoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        varNewPhoneField = new JTextField();

        varUpdateUniversityButton = new JButton("Actualizar Universidad");
        varUpdateUniversityButton.setFont(new Font("Arial", Font.BOLD, 16));
        varUpdateUniversityButton.setEnabled(false);

        varUpdatePanel.add(varUniversityNameLabel);
        varUpdatePanel.add(new JLabel());
        varUpdatePanel.add(varNewAddressLabel);
        varUpdatePanel.add(varNewAddressField);
        varUpdatePanel.add(varNewPhoneLabel);
        varUpdatePanel.add(varNewPhoneField);
        varUpdatePanel.add(new JLabel());
        varUpdatePanel.add(varUpdateUniversityButton);

        varUniversityContent.add(varRegisterPanel);
        varUniversityContent.add(varUpdatePanel);

        varUniversityPanel.add(varUniversityContent, BorderLayout.CENTER);
    }

    // Componentes de Escuelas
    public JLabel varSchoolsTitle;
    public JLabel varSchoolNameLabel1;
    public JTextField varSchoolNameField2;
    public JButton varRegisterSchoolButton;
    private JLabel varSchoolsListTitle;
    public JTextArea varSchoolsTextArea;
    private JScrollPane varSchoolsScrollPane;
    private JPanel varSchoolsPanel;

    private void SchoolsPanel() {
        varSchoolsPanel = new JPanel(new BorderLayout(10, 10));
        varSchoolsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        
        varSchoolsTitle = new JLabel("Gestion de Escuelas", JLabel.CENTER);
        varSchoolsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        varSchoolsPanel.add(varSchoolsTitle, BorderLayout.NORTH);

        JPanel varSchoolRegistrationPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        varSchoolRegistrationPanel.setBorder(BorderFactory.createTitledBorder("Register School"));

     
        varSchoolNameLabel1 = new JLabel("Universidad: ");
        varSchoolNameLabel1.setFont(new Font("Arial", Font.ITALIC, 16));

        varSchoolNameLabel1 = new JLabel("Nombre de Escuela:");
        varSchoolNameLabel1.setFont(new Font("Arial", Font.BOLD, 12));
        varSchoolNameField2 = new JTextField();
        varRegisterSchoolButton = new JButton("Registrar Escuela");
        varRegisterSchoolButton.setFont(new Font("Arial", Font.BOLD, 14));
        varRegisterSchoolButton.setEnabled(false);

        varSchoolRegistrationPanel.add(varSchoolNameLabel1);
        varSchoolRegistrationPanel.add(new JLabel());
        varSchoolRegistrationPanel.add(varSchoolNameLabel1);
        varSchoolRegistrationPanel.add(varSchoolNameField2);
        varSchoolRegistrationPanel.add(varRegisterSchoolButton);

        JPanel varSchoolQueryPanel = new JPanel(new BorderLayout(10, 10));
        varSchoolQueryPanel.setBorder(BorderFactory.createTitledBorder("Escuelas registradas"));

        varSchoolsListTitle = new JLabel("Listas de Escuelas:", JLabel.CENTER);
        varSchoolsListTitle.setFont(new Font("Arial", Font.BOLD, 16));
        varSchoolQueryPanel.add(varSchoolsListTitle, BorderLayout.NORTH);

        varSchoolsTextArea = new JTextArea();
        varSchoolsTextArea.setEditable(false);
        varSchoolsTextArea.setFont(new Font("Arial", Font.BOLD, 20));
        varSchoolsTextArea.setBorder(BorderFactory.createEtchedBorder());

        varSchoolsScrollPane = new JScrollPane(varSchoolsTextArea);
        varSchoolQueryPanel.add(varSchoolsScrollPane, BorderLayout.CENTER);

        JPanel varSchoolContent = new JPanel(new BorderLayout(10, 10));
        varSchoolContent.add(varSchoolRegistrationPanel, BorderLayout.NORTH);
        varSchoolContent.add(varSchoolQueryPanel, BorderLayout.CENTER);

        varSchoolsPanel.add(varSchoolContent, BorderLayout.CENTER);
    }
    // Componentes de Cursos
    private JPanel varCourseRegistrationPanel;
    private JPanel varCourseQueryPanel;
    private JPanel varCourseOptionsPanel;
    private JLabel varCourseCodeLabel;
    private JLabel varCourseDescriptionLabel;
    private JLabel varSchoolNameLabel;
    public JTextField varCourseCodeField;
    public JTextField varCourseDescriptionField;
    public JTextField varSchoolNameFieldCursos;
    public JButton varRegisterCourseButton;
    public JButton varDeleteCourseButton;
    public JButton varModifyCourseButton;
    public JButton varSearchBySchoolButton;
    private JLabel varCoursesTitle;
    public JTable varCoursesTable;
    private JScrollPane varCoursesScrollPane;
    private JPanel varCoursesPanel;

    private void CoursesPanel() {
        varCoursesPanel = new JPanel(new BorderLayout(10, 10));
        varCoursesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel varCoursesTitle = new JLabel("Gestión de cursos", JLabel.CENTER);
        varCoursesTitle.setFont(new Font("Arial", Font.BOLD, 18));
        varCoursesPanel.add(varCoursesTitle, BorderLayout.NORTH);

        varCourseRegistrationPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        varCourseRegistrationPanel.setBorder(BorderFactory.createTitledBorder("Registro del curso"));

        varSchoolNameLabel = new JLabel("Nombre de la escuela");
        varSchoolNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        varSchoolNameFieldCursos = new JTextField();

        varCourseCodeLabel = new JLabel("Codigo del curso:");
        varCourseCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        varCourseCodeField = new JTextField();

        varCourseDescriptionLabel = new JLabel("Descripcion del curso:");
        varCourseDescriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        varCourseDescriptionField = new JTextField();

        varCourseRegistrationPanel.add(varSchoolNameLabel);
        varCourseRegistrationPanel.add(varSchoolNameFieldCursos);
        varCourseRegistrationPanel.add(varCourseCodeLabel);
        varCourseRegistrationPanel.add(varCourseCodeField);
        varCourseRegistrationPanel.add(varCourseDescriptionLabel);
        varCourseRegistrationPanel.add(varCourseDescriptionField);

        varCourseQueryPanel = new JPanel(new BorderLayout(10, 10));
        varCourseQueryPanel.setBorder(BorderFactory.createTitledBorder("Registered Courses"));

        varCoursesTitle = new JLabel("List of Schools and Courses:", JLabel.CENTER);
        varCoursesTitle.setFont(new Font("Arial", Font.BOLD, 16));
        varCourseQueryPanel.add(varCoursesTitle, BorderLayout.NORTH);

        String[] columns = {"School Name", "Course Code", "Course Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        varCoursesTable = new JTable(model);
        varCoursesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        varCoursesTable.setEnabled(true); // Permitir selección
        varCoursesTable.setDefaultEditor(Object.class, null); // No permitir edición desde la tabla

        varCoursesScrollPane = new JScrollPane(varCoursesTable);
        varCourseQueryPanel.add(varCoursesScrollPane, BorderLayout.CENTER);

        JPanel varActionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        varRegisterCourseButton = new JButton("Registrar Curso");
        varRegisterCourseButton.setFont(new Font("Arial", Font.BOLD, 13));
        varRegisterCourseButton.setEnabled(false);
        varActionButtonsPanel.add(varRegisterCourseButton);

        varCourseOptionsPanel = new JPanel(new BorderLayout(5, 5));
        varCourseOptionsPanel.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));

        JLabel varOptions = new JLabel("Mas Opciones...");
        varOptions.setFont(new Font("Arial", Font.BOLD, 14));
        varOptions.setHorizontalAlignment(SwingConstants.CENTER);
        varCourseOptionsPanel.add(varOptions, BorderLayout.NORTH);

        JPanel varAdditionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varDeleteCourseButton = new JButton("Eliminar Course");
        varModifyCourseButton = new JButton("modificar Course");
        varSearchBySchoolButton = new JButton("Buscar por escuela");

        JButton[] buttons = {varDeleteCourseButton, varModifyCourseButton, varSearchBySchoolButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setEnabled(false);
            varAdditionalButtonsPanel.add(btn);
        }

        varCourseOptionsPanel.add(varAdditionalButtonsPanel, BorderLayout.CENTER);

        JPanel varCoursesContent = new JPanel(new BorderLayout(10, 10));
        varCoursesContent.add(varCourseRegistrationPanel, BorderLayout.NORTH);
        varCoursesContent.add(varCourseQueryPanel, BorderLayout.CENTER);
        varCoursesContent.add(varActionButtonsPanel, BorderLayout.SOUTH);
        varCoursesContent.add(varCourseOptionsPanel, BorderLayout.EAST);

        varCoursesPanel.add(varCoursesContent, BorderLayout.CENTER);
    }

    // Panel de búsqueda (Escuela)
    public JPanel varSearchPanel;
    public JTextField varSearchField;
    public JTextArea varSearchTextArea;
    public JButton varSearchButton;
    public JButton varClearButton;

    private void SearchPanel() {
        varSearchPanel = new JPanel(new BorderLayout(10, 10));
        varSearchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel varSearchTitle = new JLabel("Buscar Escuela", JLabel.CENTER);
        varSearchTitle.setFont(new Font("Arial", Font.BOLD, 16));
        varSearchPanel.add(varSearchTitle, BorderLayout.NORTH);

        JPanel varSearchFormPanel = new JPanel(new BorderLayout(5, 15));
        varSearchFormPanel.setBorder(BorderFactory.createTitledBorder("Query"));

        JPanel varTextFieldAndButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        varSearchField = new JTextField(30);
        varTextFieldAndButtonPanel.add(varSearchField);

        varSearchButton = new JButton("Buscar");
        varSearchButton.setFont(new Font("Arial", Font.BOLD, 14));
        varTextFieldAndButtonPanel.add(varSearchButton);

        varSearchFormPanel.add(varTextFieldAndButtonPanel, BorderLayout.NORTH);

        varSearchTextArea = new JTextArea(10, 20);
        varSearchTextArea.setEditable(false);
        varSearchTextArea.setFont(new Font("Arial", Font.BOLD, 20));
        JScrollPane varSearchScrollPane = new JScrollPane(varSearchTextArea);
        varSearchFormPanel.add(varSearchScrollPane);

        varSearchPanel.add(varSearchFormPanel, BorderLayout.CENTER);
    }

    public void showPanel(String pPanelName) {
        CardLayout cl = (CardLayout) varContentPanel.getLayout();
        cl.show(varContentPanel, pPanelName);
    }

    public void setUniversityName(String pName) {
        if (varUniversityNameLabel != null) {
            varUniversityNameLabel.setText("Universidad: " + pName);
        } else {
            System.err.println("Error: varUniversityNameLabel is null");
        }
        if (varSchoolsTitle != null) {
            varSchoolsTitle.setText("Universidad: " + pName);
        } else {
            System.err.println("Error: varSchoolsTitle is null");
        }
    }

    public void enableCourseControls(boolean pEnable) {
        varDeleteCourseButton.setEnabled(pEnable);
        varSearchBySchoolButton.setEnabled(pEnable);
        varModifyCourseButton.setEnabled(pEnable);
        varRegisterCourseButton.setEnabled(pEnable);
        
    }

    // Método para configurar listeners para los botones del menú
    public void setupMenuListeners(ActionListener pUniversityListener, ActionListener pSchoolsListener,
                                   ActionListener pCoursesListener, ActionListener pSearchBySchoolListener) {
        varUniversityButton.addActionListener(pUniversityListener);
        varSchoolsButton.addActionListener(pSchoolsListener);
        varCoursesButton.addActionListener(pCoursesListener);
        varSearchBySchoolButton.addActionListener(pSearchBySchoolListener);
    }
}