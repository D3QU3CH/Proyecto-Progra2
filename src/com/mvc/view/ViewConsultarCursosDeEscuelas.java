package com.mvc.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.Arrays;

public class ViewConsultarCursosDeEscuelas extends JFrame {

	
	private static final long serialVersionUID = 1L;

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    public JTable tablaEscuelas; 
    private JScrollPane scrollPane;

    public ViewConsultarCursosDeEscuelas() {
        setTitle("Cursos: ");
        setSize(600, 300);
        setLocation(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout(10, 10));


        lblTitulo = new JLabel("Lista de las Escuelas y Cursos:", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        
        String[] columnas = {"Nombre de la Escuela", "Siglas del Curso", "Descripción del Curso"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0); 

        
        tablaEscuelas = new JTable(modeloTabla);
        tablaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14)); 
        tablaEscuelas.setRowHeight(25); 
        tablaEscuelas.setEnabled(false); 

     
        scrollPane = new JScrollPane(tablaEscuelas);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

      
        add(panelPrincipal);

        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

   
        setVisible(true);
    }

    
    public void agregarDatosTabla(Object[][] datos) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaEscuelas.getModel();

        // Iterar sobre los datos que queremos agregar
        for (Object[] nuevaFila : datos) {
            boolean existe = false;

            // Verificar si la fila ya existe en la tabla
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                Object[] filaExistente = new Object[modeloTabla.getColumnCount()];
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    filaExistente[j] = modeloTabla.getValueAt(i, j);
                }

                // Comparar la nueva fila con la fila existente
                if (Arrays.equals(filaExistente, nuevaFila)) {
                    existe = true;
                    break; // Si ya existe, no es necesario seguir buscando
                }
            }

            // Si la fila no existe, agregarla a la tabla
            if (!existe) {
                modeloTabla.addRow(nuevaFila);
            }
        }
    }
	
	
	
	
}
