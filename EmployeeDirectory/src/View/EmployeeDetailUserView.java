package View;

import javax.swing.*;

import Config.Checking;
//import Controller.AdminDetailController;
import Controller.EmployeeDetailsController;
import Model.EmployeeDetailsModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class EmployeeDetailUserView extends JPanel {
	 
    private  final long serialVersionUID = 1L;
    private  JTextField txtName;
    private  JTextField txtEmail;
    private  JTextField txtPhone;
    private  JTextField txtHiringDate;
    private  JTextField txtDepartment;
    private  JTextField txtJobTitle;
    private EmployeeNavBar parentFrame;
   // private EmployeeDetailsModel currentModel;
    private JCheckBox chkIsActive, chkIsAgreement, chkIsManager;
	private EmployeeDetailsModel currentModel;

	public EmployeeDetailUserView(EmployeeNavBar parentFrame) {
		 this.parentFrame = parentFrame;
	        setLayout(null);
	//	setLayout(null);
 
		JLabel lblHeader = new JLabel("Employee Details");
		lblHeader.setBounds(38, 10, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblHeader);
 
		setPreferredSize(new Dimension(669, 686));
		setBackground(new Color(245, 245, 245));
 
		JPanel panel = new JPanel();
		panel.setBounds(58, 43, 567, 594);
		add(panel);
		panel.setLayout(null);
 
		JLabel lbName = new JLabel("Name");
		lbName.setFont(new Font("Arial", Font.PLAIN, 14));
		lbName.setBounds(31, 10, 73, 20);
		panel.add(lbName);
 
		txtName = new JTextField();
		txtName.setBounds(31, 35, 508, 25);
		panel.add(txtName);
		 txtName.addKeyListener(new java.awt.event.KeyAdapter() {
	            @Override
	            public void keyTyped(java.awt.event.KeyEvent e) {
	                // Get the character typed
	                char typedChar = e.getKeyChar();
	 
	                // Check if the typed character is a digit
	                if (Character.isDigit(typedChar)) {
	                    // Consume the event to prevent the digit from being entered
	                    e.consume();
//	                    lblMessage.setText("Username cannot contain digits.");
	                }
	            }
	        });
 
		JLabel lbPhone = new JLabel("Phone Number");
		lbPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		lbPhone.setBounds(31, 70, 100, 20);
		panel.add(lbPhone);
 
		txtPhone = new JTextField();
		txtPhone.setBounds(31, 100, 508, 25);
		panel.add(txtPhone);
 
		JLabel lbEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
	    lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
	    lbEmail.setBounds(31, 135, 73, 20);
	    panel.add(lbEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(31, 165, 508, 25);
		panel.add(txtEmail);
		 panel.add(txtEmail);
	        
	        txtEmail.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyReleased(KeyEvent e) {
	                String emailInput = txtEmail.getText();

	                // Check if the email format is valid using the Checking method
	                if (!Checking.IsEmailFormat(emailInput)) {
	                    // Set the border color to red if the validation fails
	                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
	                } else {
	                    // Reset the border to the default color if the validation passes
	                    txtEmail.setBorder(UIManager.getBorder("TextField.border"));
	                }
	            }
	        });
 
		JLabel lbHiringDate = new JLabel("Hiring Date");
		lbHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		lbHiringDate.setBounds(31, 200, 100, 20);
		panel.add(lbHiringDate);
 
		txtHiringDate = new JTextField();
		txtHiringDate.setBounds(31, 230, 508, 25);
		panel.add(txtHiringDate);
 
		chkIsActive = new JCheckBox();
	    chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
	    chkIsActive.setBounds(31, 341, 25, 25);
	    panel.add(chkIsActive);
	    chkIsActive.setBackground(Color.WHITE);

	    chkIsAgreement = new JCheckBox();
	    chkIsAgreement.setBounds(31, 398, 25, 25);
	    panel.add(chkIsAgreement);
	    chkIsAgreement.setBackground(Color.WHITE);

	    chkIsManager = new JCheckBox();
	    chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
	    chkIsManager.setBounds(31, 291, 25, 25);
	    panel.add(chkIsManager);
	    chkIsManager.setBackground(Color.WHITE);
	    
		JLabel lbDepartment = new JLabel("Department");
		lbDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		lbDepartment.setBounds(31, 429, 100, 20);
		panel.add(lbDepartment);
 
		txtDepartment = new JTextField();
		txtDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDepartment.setBounds(31, 459, 508, 25);
		panel.add(txtDepartment);
 
		JLabel lbJobTitle = new JLabel("Job Title");
		lbJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lbJobTitle.setBounds(31, 494, 100, 20);
		panel.add(lbJobTitle);
 
		txtJobTitle = new JTextField();
		txtJobTitle.setBounds(31, 518, 508, 25);
		panel.add(txtJobTitle);
 
//		JButton btnSave = new JButton("Save");
//		btnSave.setBackground(Color.RED);
//		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
//		btnSave.setBounds(31, 563, 73, 24);
//		btnSave.addActionListener(e -> saveEmployeeDetails());  // Link to save method
//		panel.add(btnSave);
 
		JLabel lbIsManager = new JLabel("Is Manager");
		lbIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsManager.setBounds(31, 265, 100, 20);
		panel.add(lbIsManager);
 
		JLabel lbIsActive = new JLabel("Is Active");
		lbIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsActive.setBounds(31, 322, 73, 13);
		panel.add(lbIsActive);
 
		JLabel lbIsAgreement = new JLabel("Is Agreement");
		lbIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsAgreement.setBounds(31, 372, 100, 20);
		panel.add(lbIsAgreement);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.RED);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBounds(454, 560, 85, 27);
		btnBack.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
//		        parentFrame.refreshEmployeeDashboard(); // Refresh before showing
		        parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
		        parentFrame.setActiveNav("Employee Dashboard");
		    }
		});
		panel.add(btnBack);
		//panel.add(btnBack);
		
	}
		
	public void loadEmployeeData(EmployeeDetailsModel employee) {
	    this.currentModel = employee;
	    txtName.setText(employee.getEmpName());
	    txtEmail.setText(employee.getEmail());
	    txtPhone.setText(employee.getPhone());
	    txtHiringDate.setText(employee.getHiringDate());
	    chkIsActive.setSelected(employee.isActive());          // Uses instance variable
	    chkIsAgreement.setSelected(employee.isAgreement());    // Uses instance variable
	    chkIsManager.setSelected(employee.isManager());        // Uses instance variable
	    txtDepartment.setText(employee.getDepartment());
	    txtJobTitle.setText(employee.getJobTitle());
	}
	


//	private void saveEmployeeDetails() {
//	    // Validate required fields
//	    if (txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()) {
//	        JOptionPane.showMessageDialog(this, "Name, Email, and Phone are required.", "Error", JOptionPane.ERROR_MESSAGE);
//	        return;
//	    }
//
//	    if (currentModel == null) {
//	        JOptionPane.showMessageDialog(this, "No employee data loaded.", "Error", JOptionPane.ERROR_MESSAGE);
//	        return;
//	    }
//
//	    // Update the model with form data
//	    currentModel.setEmpName(txtName.getText());
//	    currentModel.setEmail(txtEmail.getText());
//	    currentModel.setPhone(txtPhone.getText());
//	    currentModel.setHiringDate(txtHiringDate.getText());
//	    currentModel.setActive(chkIsActive.isSelected());
//	    currentModel.setAgreement(chkIsAgreement.isSelected());
//	    currentModel.setManager(chkIsManager.isSelected());
//	    currentModel.setDepartment(txtDepartment.getText());
//	    currentModel.setJobTitle(txtJobTitle.getText());
//
//	    // Save to database
//	    EmployeeDetailsController controller = new EmployeeDetailsController(currentModel);
//	    boolean isSaved = controller.saveAdminDetails();
//
//	    if (isSaved) {
//	        JOptionPane.showMessageDialog(this, "Employee data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
////	        parentFrame.refreshEmployeeDashboard();
//            // Optionally go back to dashboard
//          //  parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
//           // parentFrame.setActiveNav("Employee Dashboard");
//	        
//	    } else {
//	        JOptionPane.showMessageDialog(this, "Failed to save employee data.", "Error", JOptionPane.ERROR_MESSAGE);
//	    }
//	}
	    public void loadUserDetail(String name) {
	        EmployeeDetailsController controller = new EmployeeDetailsController (null);
	        EmployeeDetailsModel model = controller.fetchAdminByName(name);
	        if (model != null) {
	            currentModel = model;
	            txtName.setText(model.getEmpName());
	            txtEmail.setText(model.getEmail());
	            txtPhone.setText(model.getPhone());
	            txtHiringDate.setText(model.getHiringDate());
	            chkIsActive.setSelected(model.isActive());
	            chkIsAgreement.setSelected(model.isAgreement());
	            chkIsManager.setSelected(model.isManager());
	            txtDepartment.setText(model.getDepartment());
	            txtJobTitle.setText(model.getJobTitle());
	        } else {
	            JOptionPane.showMessageDialog(this, "Employee not found!");
	        }
	    }
	
	
	}
 
	//public static void main(String[] args) {
		//SwingUtilities.invokeLater(() -> {
		//	JFrame frame = new JFrame("EmployeeDetailsAdminView");
		//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//	frame.setContentPane(new JScrollPane(new EmployeeDetailsAdminView()));
		//	frame.setSize(1000, 750);
		//	frame.setLocationRelativeTo(null);
		//	frame.setVisible(true);
		//});
	//}

