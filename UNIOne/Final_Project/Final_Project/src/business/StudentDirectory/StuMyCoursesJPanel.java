/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package business.StudentDirectory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ui.DatabaseConnection;
/**
 *
 * @author Atharv Joshi
 */
public class StuMyCoursesJPanel extends javax.swing.JPanel {

    private String username;
    private int studentID; 

    /**
     * Creates new form StuMyCoursesJPanel
     */
    
    public StuMyCoursesJPanel(String username) {
        initComponents();
        this.username = username;
        this.studentID = getStudentIDByUsername(username); // Initialize studentID
        updateEnrolledCoursesTable();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcourseid = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        stucoursestable = new javax.swing.JTable();
        btnaddcourse = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Choose your course");

        jLabel2.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel2.setText("ENTER COURSE ID");

        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        stucoursestable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CourseID", "CourseName", "Credits", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(stucoursestable);

        btnaddcourse.setBackground(new java.awt.Color(51, 51, 255));
        btnaddcourse.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        btnaddcourse.setForeground(new java.awt.Color(255, 255, 255));
        btnaddcourse.setText("ADD COURSE");
        btnaddcourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddcourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnaddcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtcourseid, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcourseid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaddcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddcourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddcourseActionPerformed
        String courseID = txtcourseid.getText();

        // Validate Course ID
        if (!isCourseIDValid(courseID)) {
            JOptionPane.showMessageDialog(this, "Invalid Course ID. Please enter a valid Course ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the student is already enrolled in the course
        if (isAlreadyEnrolled(courseID)) {
            JOptionPane.showMessageDialog(this, "You are already enrolled in this course.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the student has already enrolled in the maximum number of courses (8)
        if (countEnrolledCourses() >= 8) {
            JOptionPane.showMessageDialog(this, "You have already enrolled in the maximum number of courses (8).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert the course enrollment record into studentcourseenrollment table
        try {
            Connection connection = DatabaseConnection.getConnection();
            String insertQuery = "INSERT INTO studentcourseenrollment (StudentID, CourseID) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, studentID);
                insertStatement.setInt(2, Integer.parseInt(courseID));
                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Course added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateEnrolledCoursesTable(); // Update the enrolled courses table after adding the course
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add course. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors according to your application's requirements
        }
    }//GEN-LAST:event_btnaddcourseActionPerformed

    
      private boolean isAlreadyEnrolled(String courseID) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM studentcourseenrollment WHERE StudentID = ? AND CourseID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, studentID);
                statement.setInt(2, Integer.parseInt(courseID));
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // If a record is found, the student is already enrolled
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors according to your application's requirements
        }
        return false;
    }
      
    private boolean isCourseIDValid(String courseID) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM courses WHERE CourseID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(courseID));
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // If there is a row, the course ID is valid
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors according to your application's requirements
        }
        return false;
    }

 private void updateEnrolledCoursesTable() {
   try {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT courses.CourseID, courses.CourseName, courses.Credits, courses.Schedule "
                + "FROM courses "
                + "JOIN studentcourseenrollment ON courses.CourseID = studentcourseenrollment.CourseID "
                + "WHERE studentcourseenrollment.StudentID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentID);
            try (ResultSet resultSet = statement.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("CourseID");
                model.addColumn("CourseName");
                model.addColumn("Credits");
                model.addColumn("Schedule");

                while (resultSet.next()) {
                    Object[] row = {
                        resultSet.getInt("CourseID"),
                        resultSet.getString("CourseName"),
                        resultSet.getInt("Credits"),
                        resultSet.getString("Schedule")
                    };
                    model.addRow(row);
                }

                stucoursestable.setModel(model);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle errors according to your application's requirements
    }

    }

    private int countEnrolledCourses() {
        int count = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();
            String countQuery = "SELECT COUNT(*) AS Count FROM studentcourseenrollment WHERE StudentID = ?";
            try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
                countStatement.setInt(1, studentID);
                try (ResultSet resultSet = countStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt("Count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors according to your application's requirements
        }
        return count;
    }

    private int getStudentIDByUsername(String username) {
        int studentID = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT StudentID FROM students WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        studentID = resultSet.getInt("StudentID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors according to your application's requirements
        }
        return studentID;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaddcourse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable stucoursestable;
    private javax.swing.JTextField txtcourseid;
    // End of variables declaration//GEN-END:variables

    }



   