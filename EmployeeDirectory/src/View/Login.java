package View;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Config.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUserName;
    private JPasswordField txtPassword; // Changed to JPasswordField
    private JLabel lblMessage;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 651, 476);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Load and scale the image
        ImageIcon icon = new ImageIcon("C:\\EmployeeDirectory\\icon.jfif");
        Image img = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        // Add image label
        JLabel lblImage = new JLabel(scaledIcon);
        lblImage.setBounds(250, 20, 120, 100);
        contentPane.add(lblImage);

        JLabel lblNewLabel = new JLabel("EmployeeDirectory");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
        lblNewLabel.setBounds(228, 120, 276, 21);
        contentPane.add(lblNewLabel);

        JLabel lbUserName = new JLabel("Username");
        lbUserName.setFont(new Font("Arial", Font.PLAIN, 13));
        lbUserName.setBounds(102, 184, 90, 21);
        contentPane.add(lbUserName);

        txtUserName = new JTextField();
        txtUserName.setBounds(226, 185, 178, 28);
        contentPane.add(txtUserName);
        txtUserName.setColumns(10);
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char typedChar = e.getKeyChar();
                if (Character.isDigit(typedChar)) {
                    e.consume();
                    lblMessage.setText("Username cannot contain digits.");
                }
            }
        });

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(102, 255, 90, 13);
        contentPane.add(lblNewLabel_2);

        // Changed to JPasswordField
        txtPassword = new JPasswordField();
        txtPassword.setBounds(228, 249, 176, 28);
        contentPane.add(txtPassword);
        txtPassword.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUserName.getText().trim();
                // Changed to getPassword()
                String password = new String(txtPassword.getPassword()).trim();

                lblMessage.setText("");

                if (username.isEmpty() && password.isEmpty()) {
                    lblMessage.setText("Invalid username and password!");
                    return;
                }

                if (!username.isEmpty() && password.isEmpty()) {
                    lblMessage.setText("Invalid Password");
                    return;
                }

                try {
                    DBConfig db = new DBConfig();
                    Connection conn = db.getConnection();

                    // Admin check
                    String adminSQL = "SELECT * FROM empdirectory.admin WHERE admName = ? AND password = ?";
                    PreparedStatement adminStmt = conn.prepareStatement(adminSQL);
                    adminStmt.setString(1, username);
                    adminStmt.setString(2, password);
                    ResultSet adminRs = adminStmt.executeQuery();

                    if (adminRs.next()) {
                        lblMessage.setText("Admin Login Successful!");
                        new AdminNavBar().setVisible(true);
                        dispose();
                        return;
                    }

                    // Employee check
                    String employeeSQL = "SELECT * FROM empdirectory.employee WHERE empName = ? AND password = ?";
                    PreparedStatement employeeStmt = conn.prepareStatement(employeeSQL);
                    employeeStmt.setString(1, username);
                    employeeStmt.setString(2, password);
                    ResultSet employeeRs = employeeStmt.executeQuery();

                    if (employeeRs.next()) {
                        lblMessage.setText("Employee Login Successful!");
                        new EmployeeNavBar().setVisible(true);
                        dispose();
                        return;
                    }

                    // Username existence check
                    String checkAdminSQL = "SELECT * FROM empdirectory.admin WHERE admName = ?";
                    PreparedStatement checkAdminStmt = conn.prepareStatement(checkAdminSQL);
                    checkAdminStmt.setString(1, username);
                    ResultSet checkAdminRs = checkAdminStmt.executeQuery();

                    String checkEmployeeSQL = "SELECT * FROM empdirectory.employee WHERE empName = ?";
                    PreparedStatement checkEmployeeStmt = conn.prepareStatement(checkEmployeeSQL);
                    checkEmployeeStmt.setString(1, username);
                    ResultSet checkEmployeeRs = checkEmployeeStmt.executeQuery();

                    if (!checkAdminRs.next() && !checkEmployeeRs.next()) {
                        lblMessage.setText("Incorrect username and password!");
                    } else {
                        lblMessage.setText("Incorrect password!");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    lblMessage.setText("Database connection error!");
                }
            }
        });

        btnLogin.setBounds(268, 361, 91, 21);
        contentPane.add(btnLogin);

        lblMessage = new JLabel("");
        lblMessage.setBounds(226, 305, 204, 28);
        contentPane.add(lblMessage);

        txtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                lblMessage.setText("");
            }
        });

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                lblMessage.setText("");
            }
        });
    }
}