package com.mvc.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ViewRegistroDeCursos extends JFrame {

	private JLabel varSiglasNom;
	private JLabel varDescripcionNom;
	private JLabel varNombreEscuela;
	public JTextField varSigla;
	public JTextField varDescripcion;
	public JTextField varEscuelaNombres;
	public JButton varBotonRegistrar;
	
	
	public ViewRegistroDeCursos() {
		
		setTitle("Registrar Cursos");
		setSize(400, 160);
		setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new GridLayout(4, 4, 1, 1));


        varNombreEscuela = new JLabel("Nombre de la Escuela");
        varNombreEscuela.setFont(new Font("Courier New", Font.BOLD, 12));
        add(varNombreEscuela);
        varEscuelaNombres = new JTextField();
        add(varEscuelaNombres);
        
        varSiglasNom = new JLabel("Siglas del Curso:");
        varSiglasNom.setFont(new Font("Courier New", Font.BOLD, 12));
        add(varSiglasNom);
        varSigla = new JTextField();
        add(varSigla);
        
        varDescripcionNom = new JLabel("Descripcion del Curso");
        varDescripcionNom.setFont(new Font("Courier New", Font.BOLD, 12));
        add(varDescripcionNom);
        varDescripcion = new JTextField();
        add(varDescripcion);
        

        varBotonRegistrar = new JButton("Registrar Curso");
        varBotonRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        add(varBotonRegistrar);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		
		
		
		
	}
	
	

	
	
	
	
	
	
	
	
	
}
