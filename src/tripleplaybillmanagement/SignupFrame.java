/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tripleplaybillmanagement;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Jaideep
 */
public class SignupFrame extends javax.swing.JFrame {

    /**
     * Creates new form SignupFrame
     */
    boolean isAdmin = false;
    Connection connection = null;
    
    public SignupFrame() {
        initComponents();
        setIconImage();
        this.setLocationRelativeTo(null);
    }
private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons8_laptop_24px.png"))); 
    }

    enum CheckFlied{
        PASSWORD,
        PHONE,
        EMAIL,
        NAME,
        DATE,
        ADDRESS;
    }
    
    
public static int checkFieldValidity(String text , CheckFlied field){
    
//    Checking for valid password
    if( field == CheckFlied.PASSWORD){
        // String to be scanned to find the pattern.
      String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,19}$";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
        if(r.matcher(text).matches())
            return 1;
    }
//    checking for valid phone numbers
    if(field == CheckFlied.PHONE){
       String pattern = "^[6-9]\\d{9}$";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
      if(r.matcher(text).matches()){
          return 1;
      }
    }
//    checking for name 
    if( field == CheckFlied.NAME){
        
      String pattern = "^[a-zA-Z]{4,}$";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
      if(r.matcher(text).matches()){
          return 1;
      }
        
    }
    
//    checking for valid Email
    if( field == CheckFlied.EMAIL){
      
      String email = text;
      String pattern = "^[A-Za-z0-9]+[A-Za-z0-9\\.]{2,}@[A-Za-z0-9\\.-]{2,}\\.[A-Za-z]{2,3}";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
      if(r.matcher(email).matches()){
          return 1;
      }
    }
    
//    Checking for valid Address
    if(field == CheckFlied.ADDRESS){
      String pattern = "(?=.*[A-Za-z]{3,})[A-Za-z0-9\\.-:\\s]{4,}";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
      if(r.matcher(text).matches()){
          return 1;
      }
      }
//    Date validate
    if(field == CheckFlied.DATE){
      String pattern = "^(20[0-9]{2})\\-(0[1-9]|1[0-2])\\-(0[1-9]|[1-2][0-9]|3[0-1])";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);
      
      if(r.matcher(text).matches()){
          return 1;
      }
      }
    return 0;
    
}


    private void submitDataToDatabase(){
        
        try{
            connection = ConnectionManager.getConnection();
            String url;
            PreparedStatement ps;
            
            
//            If admin login updating ADMIN table or USER table
            if( !isAdmin){
                url = "insert into user (U_name, U_email, U_phone, U_pass, U_address) values( ?, ?, ?, ?, ?);";
                ps = connection.prepareStatement(url);
                ps.setString(1,jTextFieldName.getText());
                ps.setString(2,jTextFieldEmail.getText());
                ps.setString(4, new String(jPasswordField.getPassword()));            
                ps.setString(3,jTextFieldPhone.getText());
                ps.setString(5, jTextFieldAddress.getText());
                
            }
            else{
                url = "insert into ADMIN (Ad_name, Ad_email, Ad_pass) values (?, ?, ?);";
                ps = connection.prepareStatement(url);
                ps.setString(1,jTextFieldName.getText());
                ps.setString(2,jTextFieldEmail.getText());
                ps.setString(3, new String(jPasswordField.getPassword()));
                
            }
            
//            System.out.println(ps.toString());
            
//            it will store value 1 if querry executed
            int i = ps.executeUpdate();
            
            if( i> 0){
                JOptionPane.showMessageDialog(this, "Saved successfully.");
            }

            
        }catch( Exception ex){
            
        }
        
//        IF error resetting 
        ResetField();
        
    }
    
//    validating passward
    public static int validatePassword( JPasswordField passoword, JPasswordField confirmPassword){
        String testPassword1 = new String(passoword.getPassword());
        String testPassword2 = new String ( confirmPassword.getPassword());
        
        if( !testPassword1.equals(testPassword2)) {
            JOptionPane.showMessageDialog(null, " The password does not match, Please check it again. ");
            passoword.setText("");
            confirmPassword.setText("");
            
            return 0;
        }
        return 1;
    }
    
    
    private void ResetField(){
        jTextFieldName.setText("");
        jTextFieldEmail.setText("");
        jPasswordField.setText("");
        jPasswordFieldConfirm.setText("");
        jTextFieldPhone.setText("");
        jTextFieldAddress.setText("");

    }
    
//To diable Phone number field based on Admin or user selection
    public void checkAndDisablePhone(){
//        isAdmin is true if radio button is on Admin
        isAdmin = jRadioButtonAdmin.isSelected();
        if( isAdmin){
            jTextFieldAddress.setVisible(false);
            jLabelAddress.setVisible(false);
            jLabelPhone.setVisible(false);
            jTextFieldPhone.setVisible(false);
        }
        else{
             jTextFieldAddress.setVisible(true);
             jLabelAddress.setVisible(true);
             jLabelPhone.setVisible(true);
             jTextFieldPhone.setVisible(true);
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

        RadioButtonGpAdminUser = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jPasswordField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jRadioButtonAdmin = new javax.swing.JRadioButton();
        jRadioButtonUser = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButtonSignup = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPasswordFieldConfirm = new javax.swing.JPasswordField();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabelPhone = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triple Play Bill Management - SignUp");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(53, 68, 84));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(53, 68, 84));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tripleplaybillmanagement/assets/frontPageTriplePlayImage430.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 450, 420));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 470, 520));

        jTextFieldAddress.setBackground(new java.awt.Color(53, 68, 84));
        jTextFieldAddress.setForeground(new java.awt.Color(255, 255, 204));
        jTextFieldAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jTextFieldAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldAddressFocusLost(evt);
            }
        });
        jTextFieldAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddressActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 240, 20));

        jLabel3.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 250, 204));
        jLabel3.setText("Email");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 120, -1));

        jLabelAddress.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 250, 204));
        jLabelAddress.setText("Address");
        jPanel1.add(jLabelAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 440, 120, -1));

        jButtonLogin.setBackground(new java.awt.Color(51, 204, 255));
        jButtonLogin.setText("Login");
        jButtonLogin.setBorderPainted(false);
        jButtonLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 500, 70, 30));

        jPasswordField.setBackground(new java.awt.Color(53, 68, 84));
        jPasswordField.setForeground(new java.awt.Color(255, 255, 204));
        jPasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFieldFocusLost(evt);
            }
        });
        jPanel1.add(jPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 240, 20));

        jLabel2.setFont(new java.awt.Font("Palatino Linotype", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Sign Up");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 180, 70));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 490, 180, 20));

        jRadioButtonAdmin.setBackground(new java.awt.Color(53, 68, 84));
        RadioButtonGpAdminUser.add(jRadioButtonAdmin);
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
        RadioButtonGpAdminUser.add(jRadioButtonUser);
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

        jLabel6.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 250, 204));
        jLabel6.setText("Confirm Password");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, 150, 20));

        jTextFieldName.setBackground(new java.awt.Color(53, 68, 84));
        jTextFieldName.setForeground(new java.awt.Color(255, 255, 204));
        jTextFieldName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jTextFieldName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldNameFocusLost(evt);
            }
        });
        jTextFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 240, 20));

        jLabel7.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 250, 204));
        jLabel7.setText("Name");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, 120, -1));

        jButtonSignup.setBackground(new java.awt.Color(51, 255, 153));
        jButtonSignup.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSignup.setText("Sign Up");
        jButtonSignup.setBorderPainted(false);
        jButtonSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignupActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, 80, 30));

        jLabel8.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 250, 204));
        jLabel8.setText("Password");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 120, -1));

        jPasswordFieldConfirm.setBackground(new java.awt.Color(53, 68, 84));
        jPasswordFieldConfirm.setForeground(new java.awt.Color(255, 255, 204));
        jPasswordFieldConfirm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jPasswordFieldConfirm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFieldConfirmFocusLost(evt);
            }
        });
        jPanel1.add(jPasswordFieldConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 240, 20));

        jTextFieldEmail.setBackground(new java.awt.Color(53, 68, 84));
        jTextFieldEmail.setForeground(new java.awt.Color(255, 255, 204));
        jTextFieldEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jTextFieldEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldEmailFocusLost(evt);
            }
        });
        jTextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmailActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 240, 20));

        jLabelPhone.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabelPhone.setForeground(new java.awt.Color(255, 250, 204));
        jLabelPhone.setText("Phone Number");
        jPanel1.add(jLabelPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 120, -1));

        jTextFieldPhone.setBackground(new java.awt.Color(53, 68, 84));
        jTextFieldPhone.setForeground(new java.awt.Color(255, 255, 204));
        jTextFieldPhone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 204)));
        jTextFieldPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldPhoneFocusLost(evt);
            }
        });
        jTextFieldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhoneActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 240, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 560));
        jPanel1.getAccessibleContext().setAccessibleName("Sign up");
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddressActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
//        opening Login form
        new LoginFrame().setVisible(true);
        this.dispose();
       
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jRadioButtonAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAdminActionPerformed
        // TODO add your handling code here:
//        if radioButton selected to Admin then diable phone number
        checkAndDisablePhone();

    }//GEN-LAST:event_jRadioButtonAdminActionPerformed

    private void jTextFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNameActionPerformed

    private void jButtonSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignupActionPerformed
        // TODO add your handling code here:
//        checking password vmaching before submiting 
        int valid = validatePassword( jPasswordField, jPasswordFieldConfirm);
        
//        Adding data to database
        if( valid == 1){
            submitDataToDatabase();            
        }
    }//GEN-LAST:event_jButtonSignupActionPerformed

    private void jRadioButtonUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonUserActionPerformed
        // TODO add your handling code here:
//        To diable phone number field for Admin as its not in database
        checkAndDisablePhone();
        
        
        
    }//GEN-LAST:event_jRadioButtonUserActionPerformed

    private void jTextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmailActionPerformed

    private void jTextFieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneActionPerformed

    private void jPasswordFieldConfirmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldConfirmFocusLost
        // TODO add your handling code here:
        if( ! String.valueOf(jPasswordFieldConfirm.getPassword()).equals(""))
            validatePassword( jPasswordField, jPasswordFieldConfirm);
    }//GEN-LAST:event_jPasswordFieldConfirmFocusLost

    private void jTextFieldNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldNameFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(jTextFieldName.getText(), CheckFlied.NAME);
        
        if(! jTextFieldName.getText().equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Name is Not Valid.\n Name must contains atleast 4 charecters and Only Alphabets are are allowed");
            jTextFieldName.setText("");
        }
    }//GEN-LAST:event_jTextFieldNameFocusLost

    private void jTextFieldEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldEmailFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(jTextFieldEmail.getText(), CheckFlied.EMAIL);
        
        if(! jTextFieldEmail.getText().equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Email is Not Valid.");
        }
    }//GEN-LAST:event_jTextFieldEmailFocusLost

    private void jPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(String.valueOf(jPasswordField.getPassword()), CheckFlied.PASSWORD);
        
        if( ! String.valueOf(jPasswordField.getPassword()).equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Password is Not Valid.\n Password must be atleast 8 digit Alpha Numaric with atleast one special charecter");
        }
    }//GEN-LAST:event_jPasswordFieldFocusLost

    private void jTextFieldPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPhoneFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(jTextFieldPhone.getText(), CheckFlied.PHONE);
        
        if(! jTextFieldPhone.getText().equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Phone Number is Not Valid");
        }
    }//GEN-LAST:event_jTextFieldPhoneFocusLost

    private void jTextFieldAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldAddressFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(jTextFieldAddress.getText(), CheckFlied.ADDRESS);
        
        if(! jTextFieldAddress.getText().equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Address is Not Valid");
        }
    }//GEN-LAST:event_jTextFieldAddressFocusLost

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
            java.util.logging.Logger.getLogger(SignupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SignupFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup RadioButtonGpAdminUser;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JPasswordField jPasswordFieldConfirm;
    private javax.swing.JRadioButton jRadioButtonAdmin;
    private javax.swing.JRadioButton jRadioButtonUser;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhone;
    // End of variables declaration//GEN-END:variables
}
