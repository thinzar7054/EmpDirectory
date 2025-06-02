package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import Config.Checking;
import Controller.AddEmployeeDetailsController;
import Controller.AddEmployeeDetailsController;
import Model.AddEmployeeDetailsModel;

public class AddEmployeeDetailsView extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JTextField txtName;
    private static JTextField txtPhoneNumber;
    private static JTextField txtEmail;
    private JTextField txtPassword;
    private JComboBox<String> cboDepartment;
    private JComboBox<String> cboJobTitle;

    private JCheckBox chkIsActive;
    private JCheckBox chkIsAgreement;
    private JCheckBox chkIsManager;
    private JDateChooser dateChooser;
    
    private AdminNavBar parentFrame;

    private String employeeId; // Hidden employee id stored here

    public AddEmployeeDetailsView(AdminNavBar parentFrame) {
    	this.parentFrame = parentFrame;
    	setLayout(null);
    

        JLabel lblHeader = new JLabel("Employee Details");
        lblHeader.setBounds(58, 11, 300, 30);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblHeader);

        setPreferredSize(new Dimension(1000, 753));
        setBackground(new Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setBounds(58, 53, 657, 611);
        add(panel);
        panel.setLayout(null);

        // Generate and store the employee ID but DO NOT show it on the UI
        employeeId = generateNextId();
        System.out.println("Generated Employee ID: " + employeeId); // debug

        JLabel lblName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setBounds(10, 10, 54, 13);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(10, 29, 516, 20);
        panel.add(txtName);
        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = txtName.getText();
                if (!input.matches("[a-zA-Z\\s]*")) {
                    txtName.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    txtName.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        });

        JLabel lblEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBounds(10, 59, 100, 20);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(10, 81, 516, 20);
        panel.add(txtEmail);

        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String emailInput = txtEmail.getText();
                if (!Checking.IsEmailFormat(emailInput)) {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    txtEmail.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        });

        JLabel lblPhone = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPhone.setBounds(10, 111, 127, 20);
        panel.add(lblPhone);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(10, 140, 516, 20);
        panel.add(txtPhoneNumber);

        txtPhoneNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = txtPhoneNumber.getText();
                String phoneRegex = "^09\\d{9}$";
                if (!input.matches(phoneRegex)) {
                    txtPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    txtPhoneNumber.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        });

        JLabel lblHiringDate = new JLabel("<html>Hiring Date <span style='color:red;'>*</span></html>");
        lblHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHiringDate.setBounds(10, 159, 100, 20);
        panel.add(lblHiringDate);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(10, 189, 516, 20);
        panel.add(dateChooser);

        Calendar calendar = Calendar.getInstance();
        dateChooser.setMaxSelectableDate(calendar.getTime());

        JLabel lblIsActive = new JLabel("Is Active");
        lblIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIsActive.setBounds(10, 263, 100, 20);
        panel.add(lblIsActive);

        chkIsActive = new JCheckBox();
        chkIsActive.setBounds(10, 289, 25, 25);
        panel.add(chkIsActive);
        chkIsActive.setBackground(Color.WHITE);

        JLabel lblIsAgreement = new JLabel("Is Agreement");
        lblIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIsAgreement.setBounds(10, 320, 100, 20);
        panel.add(lblIsAgreement);

        chkIsAgreement = new JCheckBox();
        chkIsAgreement.setBounds(10, 340, 25, 25);
        panel.add(chkIsAgreement);
        chkIsAgreement.setBackground(Color.WHITE);

        JLabel lblIsManager = new JLabel("Is Manager");
        lblIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIsManager.setBounds(10, 218, 100, 20);
        panel.add(lblIsManager);

        chkIsManager = new JCheckBox();
        chkIsManager.setBounds(10, 232, 25, 25);
        panel.add(chkIsManager);
        chkIsManager.setBackground(Color.WHITE);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDepartment.setBounds(10, 371, 100, 20);
        panel.add(lblDepartment);

        cboDepartment = new JComboBox<>();
        cboDepartment.setBounds(10, 397, 526, 20);
        panel.add(cboDepartment);

        JLabel lblJobTitle = new JLabel("Job Title");
        lblJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblJobTitle.setBounds(10, 431, 100, 20);
        panel.add(lblJobTitle);

        cboJobTitle = new JComboBox<>();
        cboJobTitle.setBounds(10, 459, 526, 20);
        panel.add(cboJobTitle);

        JLabel lblPassword = new JLabel("<html>Password <span style='color:red;'>*</span></html>");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setBounds(10, 479, 100, 20);
        panel.add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(10, 506, 526, 20);
        panel.add(txtPassword);

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = txtPassword.getText();
                // Updated regex: at least 6 characters, at least one letter (upper or lower), and at least one digit
                String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{6,}$";
                if (!input.matches(passwordRegex)) {
                    txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    txtPassword.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        });


        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSave.setBounds(10, 536, 100, 25);
        panel.add(btnSave);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddEmployeeDetailsModel pm = new AddEmployeeDetailsModel();
                AddEmployeeDetailsController pc = new AddEmployeeDetailsController();
            	if (!validateFields()) return; 
                if (txtName.getText().trim().isEmpty() ||
                        txtEmail.getText().trim().isEmpty() ||
                        txtPhoneNumber.getText().trim().isEmpty() ||
                        dateChooser.getDate() == null ||
                        cboDepartment.getSelectedItem() == null ||
                        cboJobTitle.getSelectedItem() == null ||
                        txtPassword.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "There is a blank field!", "Fail", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pm.setEmp_id(employeeId);
                pm.setEmpName(txtName.getText().trim());
                pm.setEmail(txtEmail.getText().trim());
                pm.setPhone(txtPhoneNumber.getText().trim());
                pm.setHiringDate(new java.sql.Date(dateChooser.getDate().getTime()));
                pm.setDep_id((String) cboDepartment.getSelectedItem());
                pm.setPos_id((String) cboJobTitle.getSelectedItem());
                pm.setManager(chkIsManager.isSelected());
                pm.setActive(chkIsActive.isSelected());
                pm.setAgreement(chkIsAgreement.isSelected());
                pm.setPassword(txtPassword.getText().trim());

                if (!Checking.IsValidName(pm.getEmpName()) ||
                        !Checking.IsEmailFormat(pm.getEmail()) ||
                        !Checking.isPhoneNo(pm.getPhone())) {

                    JOptionPane.showMessageDialog(null, "Invalid related field", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int rs = pc.insert(pm);
                    if (rs == 1) {
                        JOptionPane.showMessageDialog(null, "Save Successfully", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                        // regenerate employee ID for next entry after successful save
                        employeeId = generateNextId();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        populateComboBoxes();
    }

    private void populateComboBoxes() {
        try {
        	AddEmployeeDetailsController pc = new AddEmployeeDetailsController();

            cboDepartment.removeAllItems();
            cboJobTitle.removeAllItems();

            cboDepartment.addItem("Select Department");
            cboJobTitle.addItem("Select Job Title");

            List<String> departments = pc.getDepartments();
            for (String dept : departments) {
                cboDepartment.addItem(dept);
            }

            List<String> jobTitles = pc.getJobTitles();
            for (String job : jobTitles) {
                cboJobTitle.addItem(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching combobox data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateNextId() {
        try {
            AddEmployeeDetailsController pc = new AddEmployeeDetailsController();
            List<String> existingIds = pc.getAllEmployeeIds(); // method to get all emp ids from DB, must be implemented in controller

            int maxNum = 0;
            for (String id : existingIds) {
                if (id != null && id.startsWith("EP-")) {
                    String numericPart = id.substring(3); // after "EP-"
                    if (numericPart.matches("\\d+")) {
                        int num = Integer.parseInt(numericPart);
                        if (num > maxNum) {
                            maxNum = num;
                        }
                    }
                }
            }
            int nextNum = maxNum + 1;
            return String.format("EP-%04d", nextNum);
        } catch (Exception e) {
            e.printStackTrace();
            // fallback id if error occurs
            return "EP-0001";
        }
    }
    
    private boolean validateFields() {
        if (txtName.getText().trim().isEmpty() || !Checking.IsValidName(txtName.getText())) {
            showMessage("Please enter a valid name.");
            txtName.requestFocus();
            return false;
        }
        if (txtPhoneNumber.getText().trim().isEmpty() || !Checking.isPhoneNo(txtPhoneNumber.getText())) {
            showMessage("Invalid phone number. Must be digits and 11 characters.");
            txtPhoneNumber.requestFocus();
            return false;
        }
        if (txtEmail.getText().trim().isEmpty() || !Checking.IsEmailFormat(txtEmail.getText())) {
            showMessage("Invalid email address.");
            txtEmail.requestFocus();
            return false;
        }
//        
        if (txtPassword.getText().trim().isEmpty()) {
            showMessage("Please enter a password.");
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }


    private void showMessage(String string) {
		// TODO Auto-generated method stub
		
	}

//	public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("AddEmployeeDetails");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setContentPane(new JScrollPane(new AddEmployeeDetailsView(parentFrame)));
//            frame.setSize(1000, 750);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}

