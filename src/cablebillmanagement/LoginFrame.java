/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cablebillmanagement;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author Jaideep
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    String Username;
    String Password;
    boolean isAdmin = false;
    public LoginFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
//        scaleImage();
    }

//    validating user login here
    Connection  connection = null;
    public void validateUser(){
        
        String email = loginIdJTextField.getText();
        String password = new String(jPasswordField1.getPassword());
        
        connection = ConnectionManager.getConnection();
        try{
            String url;
//            IF user login user USER table else use ADMIN table
            if(isAdmin){
                url = "select Ad_email, Ad_pass from ADMIN where Ad_email = '"+email+"' and Ad_pass = '"+ password+"';";
            }
            else{
                url = "select U_email, U_pass from USER where U_email = '"+ email +"' and U_pass = '"+ password+"';";
                
            }
            
//            Creating statement 
            Statement st = connection.createStatement();
//            it will store the result of querry in set form.
            ResultSet set = st.executeQuery(url);
            
            if( set.next()){
                
                if(isAdmin){
//                    saving name that's in 1 column of querried table
                   String name = set.getString(1);
                   
//                   Opening admin UI page
                   AdminMainUI newObj = new AdminMainUI();
                   
//                   setting user name from login user to User UI page
                   newObj.jLabelAdminName.setText(name);
                   newObj.setVisible(true);
                   this.dispose();
                }
                else{
//                    saving name that's in 1 column of querried table                    
                   String name = set.getString(1);
                   
//                 opening User UI page
                   UserMainUI newObj = new UserMainUI();
                   
//                   setting user name from login user to User UI page
                   newObj.jLabelUserName.setText(name);
                   newObj.setVisible(true);
                   this.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, " Invalid Login ID or password. ");
                loginIdJTextField.setText("");
                jPasswordField1.setText("");
            }
            
            
        }catch( Exception ex){
            
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioAdminUser = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        loginIdJTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jRadioButtonAdmin = new javax.swing.JRadioButton();
        jRadioButtonUser = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cable Bill Management - Login");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(53, 68, 84));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(53, 68, 84));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/frontImage440.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 450, 420));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 470, 520));

        loginIdJTextField.setBackground(new java.awt.Color(53, 68, 84));
        loginIdJTextField.setForeground(new java.awt.Color(255, 255, 204));
        loginIdJTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        loginIdJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginIdJTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(loginIdJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 230, 240, 30));

        jLabel3.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 250, 204));
        jLabel3.setText("Login ID");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 120, -1));

        jLabel4.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 250, 204));
        jLabel4.setText("Password");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 120, -1));

        jButton1.setBackground(new java.awt.Color(51, 255, 153));
        jButton1.setText("Sign Up");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        jPasswordField1.setBackground(new java.awt.Color(53, 68, 84));
        jPasswordField1.setForeground(new java.awt.Color(255, 255, 204));
        jPasswordField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jPanel1.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 240, -1));

        jLabel2.setFont(new java.awt.Font("Palatino Linotype", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Login");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 180, 70));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, 180, 20));

        jRadioButtonAdmin.setBackground(new java.awt.Color(53, 68, 84));
        RadioAdminUser.add(jRadioButtonAdmin);
        jRadioButtonAdmin.setForeground(new java.awt.Color(255, 255, 204));
        jRadioButtonAdmin.setText("Admin");
        jRadioButtonAdmin.setFocusable(false);
        jRadioButtonAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAdminActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButtonAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 70, -1));

        jRadioButtonUser.setBackground(new java.awt.Color(53, 68, 84));
        RadioAdminUser.add(jRadioButtonUser);
        jRadioButtonUser.setForeground(new java.awt.Color(255, 255, 204));
        jRadioButtonUser.setSelected(true);
        jRadioButtonUser.setText("User");
        jRadioButtonUser.setFocusable(false);
        jRadioButtonUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonUserActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButtonUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 142, 110, 10));

        jButton2.setBackground(new java.awt.Color(51, 204, 255));
        jButton2.setText("Login");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginIdJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginIdJTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginIdJTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        Opening Sign Up form
        new SignupFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButtonAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAdminActionPerformed
        // TODO add your handling code here:
        isAdmin = jRadioButtonAdmin.isSelected();
    }//GEN-LAST:event_jRadioButtonAdminActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
//        checking user name and password
        validateUser();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButtonUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonUserActionPerformed
        // TODO add your handling code here:
        isAdmin = jRadioButtonAdmin.isSelected();
    }//GEN-LAST:event_jRadioButtonUserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup RadioAdminUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JRadioButton jRadioButtonAdmin;
    private javax.swing.JRadioButton jRadioButtonUser;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField loginIdJTextField;
    // End of variables declaration//GEN-END:variables
}
