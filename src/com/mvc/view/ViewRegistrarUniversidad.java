package com.mvc.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ViewRegistrarUniversidad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblName;
	private JLabel lblAdress;
	private JLabel lblPhoneNumber;
	
	private JTextField txtName;
	private JTextField txtAdress;
	private JTextField txtPhoneNumber;
	//private JTextField txtNameSchool;

	private JButton btnRegisterUniversity;
	//private JButton btnRegisterSchool;

	public ViewRegistrarUniversidad() {
		setTitle("Registrar Universidad");
		setSize(400, 160);
		setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 1, 1));

        lblName = new JLabel("Nombre de la Universidad:");
        lblName.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblName);
        txtName = new JTextField();
        add(txtName);

        lblAdress = new JLabel("Dirección:");
        lblAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblAdress);
        txtAdress = new JTextField();
        add(txtAdress);

        lblPhoneNumber = new JLabel("Número de Telefono:");
        lblPhoneNumber.setFont(new Font("Courier New", Font.BOLD, 12));
        add(lblPhoneNumber);
        txtPhoneNumber = new JTextField();
        add(txtPhoneNumber);

        btnRegisterUniversity = new JButton("Registrar Universidad Siervos");
        btnRegisterUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        add(btnRegisterUniversity);
        
	}
}
