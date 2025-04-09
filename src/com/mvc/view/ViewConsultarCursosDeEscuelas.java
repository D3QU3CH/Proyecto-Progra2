package com.mvc.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    	DefaultTableModel modeloTabla =(DefaultTableModel) tablaEscuelas.getModel();
    	
    	for (int i=0; i<modeloTabla.getRowCount();i++) {
    		Object  valorCelda = modeloTabla.getValueAt(i, 1);
    		if(valorCelda!=null&&valorCelda.toString().equalsIgnoreCase(pDato)) {
    			modeloTabla.removeRow(i);
    			JOptionPane.showMessageDialog(null,"se encontro el curso y se elimino");
    		}
    	}
    	JOptionPane.showMessageDialog(null,"nose encontro el curso");
    	
    	
    	
    	
    	
    	
    	
    	
    }
	
	
	
}
