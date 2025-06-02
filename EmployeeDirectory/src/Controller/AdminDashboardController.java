package Controller;

import java.sql.*;
import java.util.*;
import Config.DBConfig;
import Model.AdminModel;
import Model.DepartmentModel;
import Model.PositionModel;

public class AdminDashboardController {
    private Connection con;

    public AdminDashboardController() {
        try {
            this.con = DBConfig.getInstance().getConnection();
            System.out.println("Database connection successful: " + 
                              (con != null && !con.isClosed()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    // Get all employees with details
    public List<AdminModel> getAllEmployeesWithDetails() {
        List<AdminModel> employees = new ArrayList<>();
        String sql = "SELECT a.*, d.depName, p.postName " +
                     "FROM admin a " +
                     "LEFT JOIN department d ON a.dep_id = d.dep_id " +
                     "LEFT JOIN position p ON a.post_id = p.post_id";  // Note: 'postition' is intentional (per your schema)

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                AdminModel emp = new AdminModel();
                emp.setAdmId(rs.getString("adm_id"));       // Match DB column
                emp.setName(rs.getString("admName"));       // Match DB column
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                emp.setHiringDate(rs.getString("hiringDate"));  // Match DB column
                emp.setActive(rs.getBoolean("isActive"));  // Match DB column
                emp.setAgreement(rs.getBoolean("isAgreement")); // Match DB column
                emp.setManager(rs.getBoolean("isManager")); // Match DB column
                emp.setDepartment(rs.getString("depName"));
                emp.setJobTitle(rs.getString("postName"));
                
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());  // Debug
        }
        return employees;
    }
    
    // Get all departments
    public List<DepartmentModel> getAllDepartments() {
        List<DepartmentModel> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DepartmentModel dept = new DepartmentModel();
                dept.setDep_id(rs.getString("dep_id"));
                dept.setDepName(rs.getString("depName"));
                departments.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    // Get all positions
    public List<PositionModel> getAllPositions() {
        List<PositionModel> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PositionModel pos = new PositionModel();
                pos.setPost_id(rs.getString("post_id"));
                pos.setPositionName(rs.getString("postName"));
                positions.add(pos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    // Add a new employee
    public boolean addEmployee(AdminModel admin) {
        String sql = "INSERT INTO admin (adm_id, admName, email, phone, hiringDate, " +
                    "isActive, isAgreement, isManager, dep_id, post_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, admin.getAdmId());
            ps.setString(2, admin.getName());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getPhone());
            ps.setString(5, admin.getHiringDate());
            ps.setBoolean(6, admin.isActive());
            ps.setBoolean(7, admin.isAgreement());
            ps.setBoolean(8, admin.isManager());
            ps.setString(9, getDepartmentIdByName(admin.getDepartment()));
            ps.setString(10, getPositionIdByName(admin.getJobTitle()));

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException a) {
            a.printStackTrace();
            return false;
        }
    }

    // Helper method to get department ID by name
    private String getDepartmentIdByName(String depName) throws SQLException {
        String sql = "SELECT dep_id FROM department WHERE depName = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, depName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("dep_id");
            }
        }
        return null;
    }

    // Helper method to get position ID by name
    private String getPositionIdByName(String positionName) throws SQLException {
        String sql = "SELECT post_id FROM position WHERE postName = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, positionName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("post_id");
            }
        }
        return null;
    }
    
 // In EmployeeDAO.java
    public boolean deleteAdmin(String admId) throws SQLException {
        String sql = "DELETE FROM admin WHERE adm_id = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, admId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Close resources
    public void close() {
        DBConfig.getInstance().closeConnection();
    }
}