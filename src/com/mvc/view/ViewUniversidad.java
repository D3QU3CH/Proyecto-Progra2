package com.mvc.view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

public class ViewUniversidad extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JPanel panelRegistro;
    private JLabel lblName;
    private JLabel lblAdress;
    private JLabel lblPhoneNumber;
    
    public JTextField txtName;
    public JTextField txtAdress;
    public JTextField txtPhoneNumber;
    public JButton btnRegisterUniversity;
    
    private JPanel panelActualizacion;
    private JLabel lblUniversityName;
    private JLabel lblNewAdress;
    private JLabel lblNewPhone;
    public JTextField txtNewAdress;
    public JTextField txtNewPhone;
    public JButton btnUpdateUniversity;
    
    public ViewUniversidad() {
        setTitle("Gestión de Universidad");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
 
        panelRegistro = new JPanel(new GridLayout(4, 2, 5, 5));
        panelRegistro.setBorder(new TitledBorder("Registrar Universidad"));
        
        lblName = new JLabel("Nombre de la Universidad:");
        lblName.setFont(new Font("Courier New", Font.BOLD, 12));
        panelRegistro.add(lblName);
        
        txtName = new JTextField();
        panelRegistro.add(txtName);
        
        lblAdress = new JLabel("Dirección:");
        lblAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        panelRegistro.add(lblAdress);
        
        txtAdress = new JTextField();
        panelRegistro.add(txtAdress);
        
        lblPhoneNumber = new JLabel("Número de Teléfono:");
        lblPhoneNumber.setFont(new Font("Courier New", Font.BOLD, 12));
        panelRegistro.add(lblPhoneNumber);
        
        txtPhoneNumber = new JTextField();
        panelRegistro.add(txtPhoneNumber);
        
        btnRegisterUniversity = new JButton("Registrar Universidad");
        btnRegisterUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelRegistro.add(btnRegisterUniversity);
        
        panelActualizacion = new JPanel(new GridLayout(4, 2, 5, 5));
        panelActualizacion.setBorder(new TitledBorder("Actualizar Universidad"));
        
        lblUniversityName = new JLabel("Universidad: ");
        lblUniversityName.setFont(new Font("Arial", Font.BOLD, 14));
        panelActualizacion.add(lblUniversityName);
        panelActualizacion.add(new JLabel());
        
        lblNewAdress = new JLabel("Nueva Dirección:");
        lblNewAdress.setFont(new Font("Courier New", Font.BOLD, 12));
        panelActualizacion.add(lblNewAdress);
        
        txtNewAdress = new JTextField();
        panelActualizacion.add(txtNewAdress);
        
        lblNewPhone = new JLabel("Nuevo Número de Teléfono:");
        lblNewPhone.setFont(new Font("Courier New", Font.BOLD, 12));
        panelActualizacion.add(lblNewPhone);
        
        txtNewPhone = new JTextField();
        panelActualizacion.add(txtNewPhone);
        
        btnUpdateUniversity = new JButton("Actualizar Universidad");
        btnUpdateUniversity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelActualizacion.add(btnUpdateUniversity);
        
        add(panelRegistro, BorderLayout.NORTH);
        add(panelActualizacion, BorderLayout.CENTER);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        setVisible(true);
    }
    
    public void actualizarNombreUniversidad(String nombre) {
        lblUniversityName.setText("Universidad: " + nombre);
    }
}