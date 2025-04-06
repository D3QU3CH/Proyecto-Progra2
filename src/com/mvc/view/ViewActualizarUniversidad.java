package com.mvc.view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ViewActualizarUniversidad extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblUniversityName;
	private JLabel lblNewAdress;
	private JLabel lblNewPhone;

	private JTextField txtNewAdress;
	private JTextField txtNewPhone;

	private JButton btnUpdateUniversity;

	public ViewActualizarUniversidad(String universityName, String currentAdress, String currentPhone) {
		
		setTitle("Actualizar Universidad (Opcional)");
        setSize(400, 160);  
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new GridLayout(4, 2, 10, 10));  

        lblUniversityName = new JLabel("Universidad: " + universityName);
        lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));

        add(lblUniversityName);  
        add(new JLabel()); 

        lblNewAdress = new JLabel("Dirección:");
        lblNewAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblNewAdress);  
        
        txtNewAdress = new JTextField(currentAdress);  
        add(txtNewAdress); 

        lblNewPhone = new JLabel("Número de Teléfono:");
        lblNewPhone.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblNewPhone);  
        
        txtNewPhone = new JTextField(currentPhone); 
        add(txtNewPhone); 

        btnUpdateUniversity = new JButton("Actualizar Universidad");
        btnUpdateUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        add(btnUpdateUniversity);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        
        setVisible(true);
        
	}

}
