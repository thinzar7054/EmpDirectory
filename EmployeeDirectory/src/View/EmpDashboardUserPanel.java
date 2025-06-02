package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.EmployeeDAO;
import Model.EmployeeDetailsModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EmpDashboardUserPanel extends JPanel {
    private EmployeeNavBar parentFrame;
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPanel gridPanel;
    private EmployeeDAO employeeDAO;

    private final String PLACEHOLDER_TEXT = "Enter Employee name or Department";

    public EmpDashboardUserPanel(EmployeeNavBar parentFrame) {
        this.parentFrame = parentFrame;
        setBackground(new Color(245, 245, 245));
        setLayout(null);
        this.employeeDAO = new EmployeeDAO();

        // Initialize UI components
        initializeUI();

        // Load all employees by default
        loadEmployees();
    }

    private void initializeUI() {
        // Header Label
        JLabel lblHeader = new JLabel("Employee Dashboard");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setBounds(23, 20, 282, 32);
        add(lblHeader);

        // Search Field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBounds(22, 68, 525, 32);
        add(textField);
        setPlaceholder(textField, PLACEHOLDER_TEXT);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String keyword = textField.getText().trim();
            if (keyword.isEmpty() || keyword.equals(PLACEHOLDER_TEXT)) {
                loadEmployees();
                return;
            }
            searchEmployees(keyword);
        });
        btnSearch.setBackground(Color.RED);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBounds(576, 67, 91, 32);
        add(btnSearch);

        // Grid Panel for employee cards
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(5, 1, 10, 10));
        gridPanel.setBackground(new Color(245, 245, 245));
        gridPanel.setBounds(23, 125, 615, 500);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(gridPanel);
    }

    public void loadEmployees() {
        gridPanel.removeAll();
        try {
            List<EmployeeDetailsModel> employees = employeeDAO.getAllEmployeesWithDetails();

            if (employees.isEmpty()) {
                JLabel noDataLabel = new JLabel("No employee data found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                gridPanel.add(noDataLabel);
            } else {
                int count = Math.min(employees.size(), 5);
                for (int i = 0; i < count; i++) {
                    addEmployeeCard(employees.get(i));
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

    private void addEmployeeCard(EmployeeDetailsModel emp) {
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
        JLabel nameLabel = new JLabel("<html><a href='#' style='color:black;text-decoration:none'>" + emp.getEmpName() + "</a></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDetails");
                parentFrame.setActiveNav("Employee Details");
                EmployeeDetailUserView detailsView = (EmployeeDetailUserView)
                        parentFrame.getPanels().get("EmployeeDetails");
                detailsView.loadEmployeeData(emp);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#0066cc;text-decoration:none'>" + emp.getEmpName() + "</a></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:black;text-decoration:none'>" + emp.getEmpName() + "</a></html>");
            }
        });

        // Department Label
        JLabel detailsLabel = new JLabel(emp.getDepartment());
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsLabel.setForeground(new Color(100, 100, 100));
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(detailsLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        gridPanel.add(card);
    }

    private void searchEmployees(String searchText) {
        try {
            List<EmployeeDetailsModel> allEmployees = employeeDAO.getAllEmployeesWithDetails();
            List<EmployeeDetailsModel> filteredEmployees = new ArrayList<>();

            for (EmployeeDetailsModel emp : allEmployees) {
                if (emp.getEmpName().toLowerCase().contains(searchText.toLowerCase()) ||
                        emp.getDepartment().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredEmployees.add(emp);
                }
            }

            gridPanel.removeAll();
            if (filteredEmployees.isEmpty()) {
                JLabel noDataLabel = new JLabel("No matching employees found");
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
                    "Error searching employee data: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
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
                if (keyword.isEmpty() || keyword.equals(placeholder)) {
                    loadEmployees();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            public void changedUpdate(DocumentEvent e) {
                handleChange();
            }
        });
    }

    private void showAddEmployeeDialog() {
        // Implement add employee dialog similar to AdminDashboard
        JOptionPane.showMessageDialog(this,
                "Add Employee functionality to be implemented",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
