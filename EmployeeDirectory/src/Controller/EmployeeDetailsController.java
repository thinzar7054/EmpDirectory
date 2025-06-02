package Controller;

import Config.DBConfig;
import Model.EmployeeDetailsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EmployeeDetailsController {

    private EmployeeDetailsModel model;
    private DBConfig db = new DBConfig();

    public EmployeeDetailsController( EmployeeDetailsModel model) {
        this.model = model;
    }

  
    public boolean saveAdminDetails() {
        try (Connection con = db.getConnection()) {
            // Get department and position IDs
            String depId = getDepartmentIdByName(con, model.getDepartment());
            String postId = getJobIdByName(con, model.getJobTitle());

            if (depId == null || postId == null) {
                JOptionPane.showMessageDialog(null, "Invalid Department or Job Title.");
                return false;
            }

            // Update query for `employee` table
            String query = "UPDATE employee SET " +
                    "empName = ?, email = ?, phone = ?, hiringDate = ?, " +
                    "isActive = ?, isAgreement = ?, isManager = ?, dep_id = ?, post_id = ? " +
                    "WHERE emp_id = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, model.getEmpName());
            pst.setString(2, model.getEmail());
            pst.setString(3, model.getPhone());
            pst.setString(4, model.getHiringDate());
            pst.setBoolean(5, model.isActive());
            pst.setBoolean(6, model.isAgreement());
            pst.setBoolean(7, model.isManager());
            pst.setString(8, depId);
            pst.setString(9, postId);
            pst.setString(10, model.getEmp_id());  // Use emp_id from model

            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
            return false;
        }
    }
    // Fetch full admin details by name (including adm_id), return model
    public EmployeeDetailsModel fetchAdminByName(String name) {
    	EmployeeDetailsModel loadedModel = null;
        try (Connection con = db.getConnection()) {
            String query = "SELECT a.*, d.depName, p.postName FROM admin a " +
                    "JOIN department d ON a.dep_id = d.dep_id " +
                    "JOIN position p ON a.post_id = p.post_id " +
                    "WHERE a.admName = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, name);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        loadedModel = new EmployeeDetailsModel();
                        loadedModel.setEmp_id(rs.getString("emp_id"));   
                        loadedModel.setEmpName(rs.getString("empName"));
                        loadedModel.setEmail(rs.getString("email"));
                        loadedModel.setPhone(rs.getString("phone"));
                        loadedModel.setHiringDate(rs.getString("hiringDate"));
                        loadedModel.setActive(rs.getBoolean("isActive"));
                        loadedModel.setAgreement(rs.getBoolean("isAgreement"));
                        loadedModel.setManager(rs.getBoolean("isManager"));
                        loadedModel.setDepartment(rs.getString("depName"));
                        loadedModel.setJobTitle(rs.getString("postName"));
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading admin: " + ex.getMessage());
        }
        return loadedModel;
    }

    // Helper to get Department ID by name
    private String getDepartmentIdByName(Connection con, String depName) {
        String depId = null;
        String sql = "SELECT dep_id FROM department WHERE depName = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, depName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    depId = rs.getString("dep_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depId;
    }

    // Helper to get Job/Position ID by name
    private String getJobIdByName(Connection con, String postName) {
        String jobId = null;
        String sql = "SELECT post_id FROM position WHERE  postName= ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, postName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    jobId = rs.getString("post_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobId;
    }
}
