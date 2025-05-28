package com.mvc.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JFrame {

    private static final long serialVersionUID = 1L;

    public StudentView() {
        setTitle("Gestión de Estudiantes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        EstudiantesPanel();
        add(estudiantesPanel);
        
        setVisible(false);
    }

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
    public JComboBox<String> boxNacionalidad; // ComboBox en lugar de JTextField
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

        JLabel lblTitleEstudiantes = new JLabel("AGREGACION DE ESTUDIANTES", JLabel.CENTER);
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
            btn.setEnabled(false);
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
}
