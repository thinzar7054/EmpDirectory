package View;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import Config.Checking;
import Controller.AddAdminController;
import Controller.PositionController;
import Model.PositionModel;
import Config.AutoID;
import Config.MySqlQueries;
import Controller.DepartmentController;
import Model.AddAdminModel;
import Model.DepartmentModel;



public class AddAdminView extends JPanel {

    private static final long serialVersionUID = 1L;
	
    private static JTextField txtName;
    private static JTextField txtEmail;
    private static JTextField txtPhone;
    private static JTextField txtHiringDate;
    private JLabel lbAdminID;
	private JComboBox cboDepartment;
	private JComboBox cboJobTitle;

	String adminID=null;
	private JTextField txtPassword;


    public AddAdminView() {
        setLayout(null);

        JLabel lblHeader = new JLabel("Add Admin Details");
        lblHeader.setBounds(58, 11, 300, 30);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblHeader);

        setPreferredSize(new Dimension(755, 679));
        setBackground(new Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setBounds(58, 53, 657, 611);
        add(panel);
        panel.setLayout(null);

        JLabel lbName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
        lbName.setFont(new Font("Arial", Font.PLAIN, 14));
        lbName.setBounds(31, 10, 73, 20);
        panel.add(lbName);
        
        txtName = new JTextField();
        txtName.setBounds(31, 35, 590, 25);
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
//                    lblMessage.setText("Username cannot contain digits.");
                }
            }
        });

        JLabel lbPhone = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
        lbPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lbPhone.setBounds(31, 70, 135, 20);
        panel.add(lbPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(31, 100, 590, 25);
        panel.add(txtPhone);
        
        txtPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String phoneInput = txtPhone.getText();

                // Check if the phone number has exactly 11 digits and starts with "09"
                if (!Checking.isPhoneNo(phoneInput) || phoneInput.length() != 11) {
                    // Set the border color to red if the validation fails
                    txtPhone.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    // Reset the border to the default color if the validation passes
                    txtPhone.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        });

        
        JLabel lbEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
        lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lbEmail.setBounds(31, 135, 73, 20);
        panel.add(lbEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(31, 170, 590, 25);
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

        JLabel lbHiringDate = new JLabel("<html>Hiring Date <span style='color:red;'>*</span></html>");
        lbHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
        lbHiringDate.setBounds(31, 205, 100, 20);
        panel.add(lbHiringDate);

        txtHiringDate = new JTextField();
        txtHiringDate.setBounds(31, 235, 590, 25);
        panel.add(txtHiringDate);

        JCheckBox chkIsActive = new JCheckBox();
        chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
        chkIsActive.setBounds(31, 351, 25, 25);
        panel.add(chkIsActive);
        chkIsActive.setBackground(Color.WHITE);

        JCheckBox chkIsAgreement = new JCheckBox();
        chkIsAgreement.setBounds(31, 408, 25, 25);
        panel.add(chkIsAgreement);
        chkIsAgreement.setBackground(Color.WHITE);

        JCheckBox chkIsManager = new JCheckBox();
        chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
        chkIsManager.setBounds(31, 301, 25, 25);
        panel.add(chkIsManager);
        chkIsManager.setBackground(Color.WHITE);

        JLabel lbDepartment = new JLabel("<html>Department <span style='color:red;'>*</span></html>");
        lbDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
        lbDepartment.setBounds(31, 439, 100, 20);
        panel.add(lbDepartment);

        JLabel lbJobTitle = new JLabel("<html>Job Title <span style='color:red;'>*</span></html>");
        lbJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lbJobTitle.setBounds(344, 439, 100, 20);
        panel.add(lbJobTitle);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(lbAdminID.getText().trim().toString().equals("") || txtName.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "There is a blank field!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
					txtName.selectAll();
				}else if(txtPhone.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type phone number!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if(txtEmail.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type email!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if(txtHiringDate.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type Hiring Date!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if(cboDepartment.getSelectedIndex()<=0) {
					JOptionPane.showMessageDialog(null, "Please choose Department Name!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if(cboJobTitle.getSelectedIndex()<=0) {
					JOptionPane.showMessageDialog(null, "Please choose JobTital Name!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if(txtPassword.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type password!","Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
				}else if (!chkIsManager.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please select if this person is a manager!", "Fail", JOptionPane.ERROR_MESSAGE);
					chkIsManager.requestFocus(true);
				}else if (!chkIsActive.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please select if this person is Active!", "Fail", JOptionPane.ERROR_MESSAGE);
					chkIsActive.requestFocus(true);
				}else if (!chkIsAgreement.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please select if this person is Agreement!", "Fail", JOptionPane.ERROR_MESSAGE);
					chkIsAgreement.requestFocus(true);
				}
				else {
					PositionController pc = new PositionController();
					PositionModel pm = new PositionModel();
					pm.setPositionName(cboJobTitle.getSelectedItem().toString());
					String postId = pc.searchPostId(pm);
					
					DepartmentController dc = new DepartmentController();
					DepartmentModel dm = new DepartmentModel();
					dm.setDepName(cboDepartment.getSelectedItem().toString());
					String depId = dc.searchDepId(dm);
					
					AddAdminController ac = new AddAdminController();
					AddAdminModel am = new AddAdminModel();
					am.setAdm_id(lbAdminID.getText().toString());
					am.setAdmName(txtName.getText().toString());
					am.setPhone(txtPhone.getText().toString());
					am.setEmail(txtEmail.getText().toString());
					am.setHiringDate(txtHiringDate.getText().toString());
					am.setManager(chkIsManager.isSelected());
					am.setActive(chkIsActive.isSelected());
					am.setAgreement(chkIsAgreement.isSelected());
					am.setDepId(depId);
					am.setPostId(postId);
					am.setPassword(txtPassword.getText().toString());
	
					if(!Checking.IsValidName(am.getAdmName())) {
						JOptionPane.showMessageDialog(null, "Invalid Name!","Fail", JOptionPane.ERROR_MESSAGE);
						txtName.requestFocus(true);
						txtName.selectAll();
					}else {
						int rs = ac.insert(am);
						if(rs==1) {
							AutoID();
							JOptionPane.showMessageDialog(null, "Save Successfully","Success", JOptionPane.INFORMATION_MESSAGE);

						}
					}
				}
			}
		});
        btnSave.setBackground(Color.RED);
        btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSave.setBounds(31, 563, 85, 30);
        panel.add(btnSave);

        JLabel lbIsManager = new JLabel("<html>Is Manager <span style='color:red;'>*</span></html>");
        lbIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
        lbIsManager.setBounds(31, 275, 100, 20);
        panel.add(lbIsManager);

        JLabel lbIsActive = new JLabel("<html>Is Active <span style='color:red;'>*</span></html>");
        lbIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
        lbIsActive.setBounds(31, 332, 73, 13);
        panel.add(lbIsActive);

        JLabel lbIsAgreement = new JLabel("<html>Is Agreement <span style='color:red;'>*</span></html>");
        lbIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
        lbIsAgreement.setBounds(31, 382, 100, 20);
        panel.add(lbIsAgreement);
        
        cboDepartment = new JComboBox();
        cboDepartment.setBounds(31, 469, 303, 21);
        panel.add(cboDepartment);
        
        cboJobTitle = new JComboBox();
        cboJobTitle.setBounds(344, 470, 303, 21);
        panel.add(cboJobTitle);
        
        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lbPassword.setBounds(31, 514, 100, 25);
        panel.add(lbPassword);
        
        txtPassword = new JTextField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setBounds(141, 517, 227, 20);
        panel.add(txtPassword);
        txtPassword.setColumns(10);
        
        lbAdminID = new JLabel("");
        lbAdminID.setFont(new Font("Arial", Font.PLAIN, 14));
        lbAdminID.setBounds(637, 11, 78, 25);
        add(lbAdminID);
        
        AutoID();
        fillCombo();

    }
    
    
    public void AutoID() {
        try {
            AutoID idGenerator = new AutoID();
            lbAdminID.setText(idGenerator.getAdminID());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating admin ID", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

	
	public void fillCombo() {
		MySqlQueries.addCoboBox("department","depName",cboDepartment);
		MySqlQueries.addCoboBox("position","postName",cboJobTitle);
	}

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AddAdmin");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new JScrollPane(new AddAdminView()));
            frame.setSize(740, 750);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
