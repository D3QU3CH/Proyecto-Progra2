package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ViewEscuelas extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JPanel panelPrincipal;
    private JPanel panelRegistro;
    private JPanel panelConsulta;
    
    public JLabel lblUniversityName;
    public JLabel lblNameSchool;
    public JTextField txtNameSchool;
    public JButton btnRegisterSchool;
    
    private JLabel lblTitulo;
    public JTextArea txtAreaEscuelas;
    private JScrollPane scrollPane;

    public ViewEscuelas(String universityName) {
        setTitle("Universidad: " + universityName);
        setSize(500, 400);
        setLocation(500, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        
        panelRegistro = new JPanel(new GridLayout(3, 2, 5, 5));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar Escuela"));
        
        lblUniversityName = new JLabel("Universidad: " + universityName);
        lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblNameSchool = new JLabel("Nombre De la Escuela:");
        lblNameSchool.setFont(new Font("Courier New", Font.BOLD, 12));
        
        txtNameSchool = new JTextField();
        
        btnRegisterSchool = new JButton("Registrar Escuela");
        btnRegisterSchool.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        
        panelRegistro.add(lblUniversityName);
        panelRegistro.add(new JLabel());
        panelRegistro.add(lblNameSchool);
        panelRegistro.add(txtNameSchool);
        panelRegistro.add(btnRegisterSchool);
        
        panelPrincipal.add(panelRegistro, BorderLayout.NORTH);
        
        panelConsulta = new JPanel(new BorderLayout(10, 10));
        panelConsulta.setBorder(BorderFactory.createTitledBorder("Escuelas Registradas"));
        
        lblTitulo = new JLabel("Lista de las Escuelas:", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelConsulta.add(lblTitulo, BorderLayout.NORTH);
        
        txtAreaEscuelas = new JTextArea();
        txtAreaEscuelas.setEditable(false);
        txtAreaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14));
        txtAreaEscuelas.setBorder(BorderFactory.createEtchedBorder());
        
        scrollPane = new JScrollPane(txtAreaEscuelas);
        panelConsulta.add(scrollPane, BorderLayout.CENTER);
        
        panelPrincipal.add(panelConsulta, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        
        setVisible(true);
    }
} 