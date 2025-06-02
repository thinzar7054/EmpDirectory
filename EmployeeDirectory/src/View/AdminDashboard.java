package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.AdminDashboardController;
import Model.AdminModel;
import Model.DepartmentModel;
import Model.PositionModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JPanel {
    private AdminNavBar parentFrame;
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPanel gridPanel;
    private AdminDashboardController employeeDAO;

    public AdminDashboard(AdminNavBar parentFrame) {
        this.parentFrame = parentFrame;
        setBackground(new Color(245, 245, 245));
        setLayout(null);
        this.employeeDAO = new AdminDashboardController();

        // Initialize UI components
        initializeUI();
        
        // Load all employees by default
        loadAdmin();
    }

    private void initializeUI() {
        // Header Label
        JLabel lblHeader = new JLabel("Admin Dashboard");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setBounds(23, 20, 282, 32);
        add(lblHeader);

        // Search Field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBounds(61, 68, 448, 32);
        add(textField);
        setPlaceholder(textField, "Enter Admin name or Department");

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String keyword = textField.getText().trim();
            if (keyword.isEmpty() || keyword.equals("Enter Admin name or Department")) {
                loadAdmin();
                return;
            }
            searchAdmin(keyword);
        });
        btnSearch.setBackground(Color.RED);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBounds(551, 67, 91, 32);
        add(btnSearch);

       

        // Grid Panel for employee cards
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(5, 1, 10, 10));
        gridPanel.setBackground(new Color(245, 245, 245));
        gridPanel.setBounds(23, 125, 700, 500);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(gridPanel);
    }

     void loadAdmin() {
        gridPanel.removeAll();
        try {
            List<AdminModel> admin =employeeDAO.getAllEmployeesWithDetails();
            
            if (admin.isEmpty()) {
                JLabel noDataLabel = new JLabel("No admin data found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                gridPanel.add(noDataLabel);
            } else {
                int count = Math.min(admin.size(), 5);
                for (int i = 0; i < count; i++) {
                    addEmployeeCard(admin.get(i));
                }
                
                // Add empty cards if less than 5 employees
                for (int i = count; i < 5; i++) {
                    JPanel emptyCard = new JPanel();
                    emptyCard.setBackground(new Color(245, 245, 245));
                    emptyCard.setPreferredSize(new Dimension(680, 80));
                    gridPanel.add(emptyCard);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error loading employee data: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void addEmployeeCard(AdminModel emp) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(680, 80));

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        infoPanel.setBackground(Color.WHITE);
        
        // Name Label with clickable behavior
        JLabel nameLabel = new JLabel("<html><a href='#' style='color:black;text-decoration:none'>" + emp.getName() + "</a></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        nameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDetails");
                parentFrame.setActiveNav("Admin Details");
                AdminDetailsAdminView detailsView = (AdminDetailsAdminView) 
                    parentFrame.getPanels().get("AdminDetails");
                detailsView.loadAdminData(emp);
            }
            
            public void mouseEntered(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#0066cc;'>" + emp.getName() + "</a></html>");
            }
            
            public void mouseExited(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:black;text-decoration:none'>" + emp.getName() + "</a></html>");
            }
        });
        
        // Department and Job Title Label
        JLabel detailsLabel = new JLabel(emp.getDepartment());
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsLabel.setForeground(new Color(100, 100, 100)); 
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(detailsLabel);
        card.add(infoPanel, BorderLayout.CENTER);
        URL iconURL = getClass().getResource("/image/bin.png");
		ImageIcon icon = null;
 
		if (iconURL != null) {
		    icon = new ImageIcon(iconURL);
		} else {
		    System.err.println("Icon not found");
		}

        // Delete Button
        JButton btnDelete = new JButton(icon);
        btnDelete.setBackground(Color.RED);
		btnDelete.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnDelete.setIconTextGap(10);
        btnDelete.addActionListener(e -> deleteAdmin(emp));
        card.add(btnDelete, BorderLayout.EAST);

        gridPanel.add(card);
    }

    private void searchAdmin(String searchText) {
        try {
            List<AdminModel> allEmployees = employeeDAO.getAllEmployeesWithDetails();
            List<AdminModel> filteredEmployees = new ArrayList<>();
            
            for (AdminModel emp : allEmployees) {
                if (emp.getName().toLowerCase().contains(searchText.toLowerCase()) || 
                    emp.getDepartment().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredEmployees.add(emp);
                }
            }
            
            gridPanel.removeAll();
            if (filteredEmployees.isEmpty()) {
                JLabel noDataLabel = new JLabel("No matching admin found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                gridPanel.add(noDataLabel);
            } else {
                int count = Math.min(filteredEmployees.size(), 5);
                for (int i = 0; i < count; i++) {
                    addEmployeeCard(filteredEmployees.get(i));
                }
                
                for (int i = count; i < 5; i++) {
                    JPanel emptyCard = new JPanel();
                    emptyCard.setBackground(new Color(245, 245, 245));
                    emptyCard.setPreferredSize(new Dimension(680, 80));
                    gridPanel.add(emptyCard);
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error searching admin data: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAdmin(AdminModel emp) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Delete " + emp.getName() + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (employeeDAO.deleteAdmin(emp.getAdmId())) {
                    loadAdmin(); // Refresh the list
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to delete admin", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        final boolean[] showingPlaceholder = {true};

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    showingPlaceholder[0] = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    showingPlaceholder[0] = true;
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (showingPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    showingPlaceholder[0] = false;
                }
            }
        });
        
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void handleChange() {
                String keyword = textField.getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search by Name or Dept")) {

                	loadAdmin();
                }
            }

            public void insertUpdate(DocumentEvent e) { handleChange(); }
            public void removeUpdate(DocumentEvent e) { handleChange(); }
            public void changedUpdate(DocumentEvent e) { handleChange(); }
        });
    }

    private void showAddAdminDialog() {
        // Implement add employee dialog similar to AdminDashboard
        JOptionPane.showMessageDialog(this, 
            "Add Employee functionality to be implemented", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }
	  
}