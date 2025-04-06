package com.mvc.view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ViewRegistrarEscuelas extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	public JLabel lblUniversityName;
	public JLabel lblNameSchool;
	public JTextField txtNameSchool;
	public JButton btnRegisterSchool;

	public ViewRegistrarEscuelas(String universityName) {
		setTitle("Registrar Escuelas");
		setSize(400, 150);
		setLocation(650,100); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2, 5, 5));
		
		lblUniversityName = new JLabel("Universidad: " + universityName);
        lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));

        add(lblUniversityName);  
        add(new JLabel());
       
        lblNameSchool = new JLabel("Nombre De la Escuela:");
        lblNameSchool.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblNameSchool);

        txtNameSchool = new JTextField();
        add(txtNameSchool);
        
        btnRegisterSchool = new JButton("Registrar Escuelas");
        btnRegisterSchool.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        add(btnRegisterSchool);
        
		getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		
		setVisible(true);
	}
}