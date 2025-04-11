package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ViewCursos extends JFrame {
    
    private static final long serialVersionUID = 1L;
   
    private JPanel panelPrincipal;
    private JPanel panelRegistro;
    private JPanel panelConsulta;
    private JPanel panelOpciones;
    
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
    
    // Table components
    private JLabel lblTitulo;
    public JTable tablaEscuelas;
    private JScrollPane scrollPane;
    
    public ViewCursos() {
        setTitle("Gestión de Cursos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panelRegistro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Cursos"));
        
        varLblNombreEscuela = new JLabel("Nombre de la Escuela:");
        varTxtEscuelaNombres = new JTextField();
        varLblSiglasNom = new JLabel("Siglas del Curso:");
        varTxtSigla = new JTextField();
        varLblDescripcionNom = new JLabel("Descripción del Curso:");
        varTxtDescripcion = new JTextField();
        
        panelRegistro.add(varLblNombreEscuela);
        panelRegistro.add(varTxtEscuelaNombres);
        panelRegistro.add(varLblSiglasNom);
        panelRegistro.add(varTxtSigla);
        panelRegistro.add(varLblDescripcionNom);
        panelRegistro.add(varTxtDescripcion);
        
        panelConsulta = new JPanel(new BorderLayout(10, 10));
        panelConsulta.setBorder(BorderFactory.createTitledBorder("Cursos Registrados"));
        
        lblTitulo = new JLabel("Lista de las Escuelas y Cursos:", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelConsulta.add(lblTitulo, BorderLayout.NORTH);
    
        String[] columnas = {"Nombre de la Escuela", "Siglas del Curso", "Descripción del Curso"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        
        tablaEscuelas = new JTable(modeloTabla);
        tablaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14));
        tablaEscuelas.setRowHeight(25);
        tablaEscuelas.setEnabled(false);
        
        scrollPane = new JScrollPane(tablaEscuelas);
        panelConsulta.add(scrollPane, BorderLayout.CENTER);
   
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        varBtnRegistrar = new JButton("Registrar Curso");
        varBtnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelBotonesAccion.add(varBtnRegistrar);
        
        panelOpciones = new JPanel(new BorderLayout(5, 5));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Operaciones Adicionales"));
        
        JLabel varOpciones = new JLabel("Más Opciones, Funcionan ingresando las Siglas");
        varOpciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
        varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        panelOpciones.add(varOpciones, BorderLayout.NORTH);
        
        JPanel additionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varBtnEliminar = new JButton("Eliminar Curso");
        varBtnModificar = new JButton("Modificar Curso");
        varBtnBuscarPorEscuela = new JButton("Buscar Por Escuela");
        
        JButton[] botones = { varBtnEliminar, varBtnModificar, varBtnBuscarPorEscuela };
        for (JButton btn : botones) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btn.setFocusPainted(false);
            additionalButtonsPanel.add(btn);
        }
        
        panelOpciones.add(additionalButtonsPanel, BorderLayout.CENTER);
        
        panelPrincipal.add(panelRegistro, BorderLayout.NORTH);
        panelPrincipal.add(panelConsulta, BorderLayout.CENTER);
        panelPrincipal.add(panelBotonesAccion, BorderLayout.SOUTH);
        panelPrincipal.add(panelOpciones, BorderLayout.EAST);
        
        add(panelPrincipal);
        setVisible(false);
    }
    
    public void agregarDatosTabla(Object[][] datos) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaEscuelas.getModel();
        
        for (Object[] nuevaFila : datos) {
            boolean existe = false;
            
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                Object[] filaExistente = new Object[modeloTabla.getColumnCount()];
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    filaExistente[j] = modeloTabla.getValueAt(i, j);
                }
                
                if (Arrays.equals(filaExistente, nuevaFila)) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                modeloTabla.addRow(nuevaFila);
            }
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
}