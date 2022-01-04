/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package triplaeplaybillmanagement;

import static triplaeplaybillmanagement.SignupFrame.checkFieldValidity;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaideep
 */
public class UserMainUI extends javax.swing.JFrame {

    /**
     * Creates new form UserMainUI
     */
     Connection connection = null;
     public int userID=800 ;
     public String userName =" No User Name";

     public UserMainUI() {
        initComponents();
        
//        Setting User name in Menu Option
        userNamejLabel.setText(userName);
        
        
//        To initialise Subscrption plan menue drop down with values adn Request table
            addPlanToComboBox();
            displayRequestPlanToUser();
        
        
//        Initial panel to show on right side of the screen
        showThisPane(SubscriptionPlansUIjPanel);
        
//        Changing table header color and font
        tableModify.changeTableHeader(this.pendingPaymenttableHeader, this.pendingPaymentjTable);
        tableModify.changeTableHeader(this.billHistoryjTableHeader, this.billHistoryjTable);
        tableModify.changeTableHeader(this.raisedComplaintjTableHeader, this.raisedComplaintjTable);
        tableModify.changeTableHeader(this.requestedPlanjTableHeader, this.requestedPlanjTable);

//        Setting Scoll view bg color
         pendingPaymentjScrollPane.getViewport().setBackground(new Color(125,159,154));        
         billingHistoryjScrollPane.getViewport().setBackground(new Color(125,159,154));        
         raisedComplaintjScrollPane.getViewport().setBackground(new Color(125,159,154));        
         requestedPlanjScrollPane.getViewport().setBackground(new Color(125,159,154));        
        
        
        changeTOBtnDefaultColor();
        highlightClicked(subscriptionPlansJPanel, subscriptionPlansTextjLabel);
    }
    
    
    
    //    changing menu button to default color
    private void changeTOBtnDefaultColor(){
//        changing bg color
        subscriptionPlansJPanel.setBackground(new Color(61,81,85));
        pendingPaymentJPanel.setBackground(new Color(61,81,85));
        billingHistoryJPanel.setBackground(new Color(61,81,85));
        userComplaintsJPanel.setBackground(new Color(61,81,85));
        userChangeCredentialJPanel.setBackground(new Color(61,81,85));
//        changing font colr
        subscriptionPlansTextjLabel.setForeground(new Color(255,255,255));
        pendingPaymentTextjLabel.setForeground(new Color(255,255,255));
        billingHistoryTextjLabel.setForeground(new Color(255,255,255));
        userComplaintsTextjLabel.setForeground(new Color(255,255,255));
        userChangeCredentialTextjLabel.setForeground(new Color(255,255,255));
    }
    
//    changing menu color to make highlight clicked button
//    and will change Heading text to clicked item
    private void highlightClicked(JPanel clickPanel,JLabel clickLable){
        clickPanel.setBackground(new Color(125,159,154));
        clickLable.setForeground(new Color(21,49,63));
        headingOfPagejLabel.setText(clickLable.getText());
    }

    private void showThisPane(JPanel myPane){
        PendingPaymentUIjPanel.setVisible(false);
        SubscriptionPlansUIjPanel.setVisible(false);
        BillingHistoryUIjPanel.setVisible(false);
        UserComplaintsUIjPanel.setVisible(false);
        UserChangeCredentialUIjPanel.setVisible(false);
        
        myPane.setVisible(true);
    }
    
//    ***************************Pending Paymets***************
 private void displayDataToPendingBillTable(int uID, JTable myTable){
      
     String url ="";
     String actionMsg = "";
     
//     Checking if the table if Pendingpayment or Billing History table
     if( myTable == pendingPaymentjTable){

         url = "select B_id, Sub_plan,B_issue_date, B_due_date from BILL NATURAL JOIN SUBSCRIPTION_PLAN NATURAL JOIN USER WHERE U_id = "+ uID +" AND B_status LIKE 'Pending';";
         actionMsg = "Click To Action";
         
     }
     else{
         url = "select B_id, Sub_plan,B_issue_date, B_due_date, B_status from BILL NATURAL JOIN SUBSCRIPTION_PLAN NATURAL JOIN USER WHERE U_id = "+ uID+" ORDER BY B_status DESC;";
         actionMsg = "Click To More Info";
     }
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) myTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
//            Checking if the table if Pendingpayment or Billing History table
            if( myTable == pendingPaymentjTable){
                
                while(rs.next()){
                    model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), actionMsg});
                }                
            }
            else{
                while(rs.next()){
                    model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), actionMsg});
                }
            }
            
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayDataToPendingBillTable method : " + ex.getMessage());
        }
    }
 
 private void updatePendingBillTable( int billID){
     
     String url = "UPDATE BILL SET B_status = 'Paid' WHERE B_id = "+billID+" ;";
     
     try{
         connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);
         
         int i = ps.executeUpdate();
         
         if( i> 0){
                JOptionPane.showMessageDialog(this, "Payment Successful.");
            }
         
     }
     catch( Exception ex){
         System.out.println(" Error in updatePendingBillTable : " + ex.getMessage());
     }
     
//     Refresh table
     displayDataToPendingBillTable(userID, pendingPaymentjTable);
 }

// *******************************Billing History*************************
 private void displayDataToBillHistoryTable(){
//     The function alredy there to display same output Only last table change
     displayDataToPendingBillTable(userID, billHistoryjTable);
 }
 
 private String getBillPlanDesciption(int billID){
     
     String url = "select B_id,Sub_plan, Sub_description,B_issue_date, B_due_date, B_status from BILL NATURAL JOIN SUBSCRIPTION_PLAN WHERE  B_id= "+billID+";";
     String planName ="";
     String planDiscription ="";
     String dueDate ="";
     String issueDate ="";
     String status ="";
     String moreInfo = "";
     
     try{
         
         connection = ConnectionManager.getConnection();
         Statement st = connection.createStatement();
         
//         executing and storing result
        ResultSet set = st.executeQuery(url);
         
        if( set.next()){
            planName = set.getString("Sub_plan");
            planDiscription = set.getString("Sub_description");
            dueDate = set.getString("B_due_date");
            issueDate = set.getString("B_issue_date");
            status = set.getString("B_status");
            
            moreInfo = "You have Subscribed to ' "+planName+" ' Plan. \n This subscription includes : "+ planDiscription+". \n Issue Date   : "+issueDate+". \n Due date      : "+dueDate+". \n And Bill "+ ( (status.equals("Paid"))? " Cleared":" not yet Cleared")+". ";
            
            return moreInfo;
        }
         
         
     }
     catch( Exception ex){
         System.out.println("Error in cablebillmanagement.UserMainUI.getBillPlanDesciption() : " + ex.getMessage());
     }
     return " No More Info Error Occured.";
 }

// ********************************** Raised Complaints **************************
 private void displayRaisedComplaints(){
     String url =" SELECT C_id,Complaint, Ad_name, C_status from COMPLAINTS NATURAL JOIN ADMIN WHERE U_id = " +userID+" ORDER BY C_status;";
     
     try{
         connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);
         ResultSet rs = ps.executeQuery();
         
         DefaultTableModel model = (DefaultTableModel) raisedComplaintjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
         while(rs.next()){
            model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
         }
                 
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.displayRaisedComplaints() : " + ex.getMessage());
     }
     
 }
 
 private void billIDToComboBox(){
     
     String url = "select B_id from BILL WHERE U_id = "+userID+" ;";
     System.out.println("uid :" + userID);
     try{
         connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);

         ResultSet r=ps.executeQuery();
         
           
//         Removing all default dummy values accept at 0 th index coz it gives null exception
            for ( int i= billIDListjComboBox.getItemCount()-1;i>0 ; i--){
                billIDListjComboBox.removeItemAt(i);
            }
            
//            Adding all neccerry values
        while (r.next()) {  

            this.billIDListjComboBox.addItem(r.getString("B_id"));  
        }
         
//        Now we can remove dummy value at index 0
         billIDListjComboBox.removeItemAt(0);
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.billIDToComboBox() : " + ex.getMessage());
     }
     
 }
 
// Plan name for complaint from BIll id
 private void complaintRaise(int billID){
     String url = "select Ad_name, Ad_email, Sub_plan, Sub_description from BILL  NATURAL JOIN SUBSCRIPTION_PLAN NATURAL JOIN ADMIN WHERE B_id = "+billID+";";
     try{
         connection = ConnectionManager.getConnection();
         Statement st = connection.createStatement();

         ResultSet r=st.executeQuery(url);
         
         if(r.next()){
                adminNameComplaintjLabel.setText(r.getString("Ad_name"));
                adminEmailComplaintjLabel.setText(r.getString("Ad_email"));
                planNameComplaintjLabel.setText(r.getString("Sub_plan"));
                planDescComplaintjLabel.setText(r.getString("Sub_description"));
         }
            
            
                 
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.billIDToComboBox() : " + ex.getMessage());
     }
     
 }
//    Adding Complaint to table from user.
 private void addComplaintToTable(String email, String complaint){
     String url = "INSERT INTO COMPLAINTS (U_id, Ad_id, Complaint) VALUES ("+userID+", (select Ad_id from admin where Ad_email ='"+email+"'), '"+complaint+"');";
     
     try{
         connection =  ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);
         
         int i = ps.executeUpdate();
         
         if( i> 0){
                JOptionPane.showMessageDialog(this, "Complaints Raised.");
            }
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.addComplaintToTable() : " + ex);
     }
 }
 
 
// ********************** Subscription plans ******************
 private void addPlanToComboBox(){
     
     String url = "SELECT Sub_plan FROM SUBSCRIPTION_PLAN;";
     try{
         connection = ConnectionManager.getConnection();
         Statement st = connection.createStatement();

         ResultSet r=st.executeQuery(url);
         
//         Removing all default dummy values
         planListjComboBox.removeAllItems();
        while (r.next()) {  

            planListjComboBox.addItem(r.getString("Sub_plan"));  
        }
                 
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.adminNamesInItemList() : " + ex.getMessage());
     }
 }
 private String planDescription(String planName){
     String url = "SELECT Sub_description FROM SUBSCRIPTION_PLAN WHERE Sub_plan  = '"+planName+"';";
     String description ="";
     try{
         connection = ConnectionManager.getConnection();
         Statement st = connection.createStatement();

         ResultSet set =st.executeQuery(url);

         if(set.next()){
            description = set.getString("Sub_description");         
         }
                 
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.planDescription() : " + ex.getMessage());
     }
     return description;
 }
 
 private void insertIntoRequestTable( String planName){
     
     String url = "INSERT INTO REQUEST_PLANS (U_id, Sub_id) VALUES ( "+userID+",(SELECT Sub_id from SUBSCRIPTION_PLAN WHERE Sub_plan = '"+planName+"') );";
     
     try{
         connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);
         
         int i = ps.executeUpdate();
         
         if( i> 0){
                JOptionPane.showMessageDialog(this, "Request Raised.");
            }
     }
     catch( Exception ex){
         System.out.println("Error  in  cablebillmanagement.UserMainUI.insertIntoRequestTable() : " + ex.getMessage());
     }
 }
 
 private void displayRequestPlanToUser(){
     String url = "select Sub_plan, Sub_description,Sub_amt,R_status from SUBSCRIPTION_PLAN natural join REQUEST_PLANS WHERE U_id = "+userID+" ORDER BY R_status;";
     
     try{
         connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(url);
         ResultSet rs = ps.executeQuery();
         
         DefaultTableModel model = (DefaultTableModel) requestedPlanjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
         while(rs.next()){
            model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
         }
                 
         
     }
     catch( Exception ex){
         System.out.println(" Error in cablebillmanagement.UserMainUI.displayRequestPlanToUser() : " + ex.getMessage());
     }
 }
 
 //  *******************************Profile ***************************
private void updateUserPassword(String password ){
    String url = "UPDATE USER SET U_pass = '"+password+"' WHERE U_id = "+this.userID+";";
    try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            
            int choice = JOptionPane.showConfirmDialog(this, "Do You Want To Change Password ?"," Confirmation", JOptionPane.YES_NO_OPTION );
//            ) means YES
            if ( choice == 0){
            System.out.println("Choice: " + choice);
            
                int i = ps.executeUpdate();
                System.out.println(" i = " + i);
                if( i> 0){
                    int loginAgain = JOptionPane.showConfirmDialog(this, "Do You Want To Login Again ?"," Confirmation", JOptionPane.YES_NO_OPTION );
                    if( loginAgain ==0 ){
//                        Opening Login lage
                        new LoginFrame().setVisible(true);
//                        closing Current page
                        this.dispose();
                    }
                 }
            }
        }
//    
        catch( SQLException ex){
            System.out.println("Error in cablebillmanagement.AdminMainUI.updateUserPassword() : " + ex.getMessage());
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

        userMenujPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        userNamejLabel = new javax.swing.JLabel();
        subscriptionPlansJPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        subscriptionPlansTextjLabel = new javax.swing.JLabel();
        pendingPaymentJPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pendingPaymentTextjLabel = new javax.swing.JLabel();
        billingHistoryJPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        billingHistoryTextjLabel = new javax.swing.JLabel();
        userComplaintsJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        userComplaintsTextjLabel = new javax.swing.JLabel();
        userChangeCredentialJPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        userChangeCredentialTextjLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        headerOfUserUIjPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        headingOfPagejLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        userLogoutjButton = new javax.swing.JButton();
        RightBottomContentUserjPanel = new javax.swing.JPanel();
        UserComplaintsUIjPanel = new javax.swing.JPanel();
        AddPlanComplaintjPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        billIDListjComboBox = new javax.swing.JComboBox<>();
        adminNameComplaintjLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        adminEmailComplaintjLabel = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        planNameComplaintjLabel = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        planDescComplaintjLabel = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        addPlanjButton = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        complaintDescriptionjTextField = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        addPlanjButton1 = new javax.swing.JButton();
        raisedComplaintjScrollPane = new javax.swing.JScrollPane();
        raisedComplaintjTable = new javax.swing.JTable();
        SubscriptionPlansUIjPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        planNamejLabel = new javax.swing.JLabel();
        planListjComboBox = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        planDetailsjLabel = new javax.swing.JLabel();
        planDesciptionjLabel = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        requestedPlanjScrollPane = new javax.swing.JScrollPane();
        requestedPlanjTable = new javax.swing.JTable();
        UserChangeCredentialUIjPanel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        userNameInProfilejLabel = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        adminProfilejLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        newUserjPasswordField = new javax.swing.JPasswordField();
        confirmConfirmjPasswordField = new javax.swing.JPasswordField();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        updatePasswordjButton = new javax.swing.JButton();
        PendingPaymentUIjPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        pendingPaymentjScrollPane = new javax.swing.JScrollPane();
        pendingPaymentjTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        BillingHistoryUIjPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        billingHistoryjScrollPane = new javax.swing.JScrollPane();
        billHistoryjTable = new javax.swing.JTable();
        billHistoryRefreshjButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triple Play Bill Management - User");

        userMenujPanel.setBackground(new java.awt.Color(20, 33, 48));
        userMenujPanel.setPreferredSize(new java.awt.Dimension(200, 583));
        userMenujPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_user_100px_1.png"))); // NOI18N
        userMenujPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 100, 90));
        userMenujPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 176, 22));

        userNamejLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 18)); // NOI18N
        userNamejLabel.setForeground(new java.awt.Color(222, 210, 181));
        userNamejLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNamejLabel.setText("Jaideep");
        userMenujPanel.add(userNamejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 140, 29));

        subscriptionPlansJPanel.setBackground(new java.awt.Color(125, 159, 154));
        subscriptionPlansJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        subscriptionPlansJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subscriptionPlansJPanelMouseClicked(evt);
            }
        });
        subscriptionPlansJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_new_letter_24px.png"))); // NOI18N
        subscriptionPlansJPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        subscriptionPlansTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        subscriptionPlansTextjLabel.setForeground(new java.awt.Color(21, 49, 63));
        subscriptionPlansTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        subscriptionPlansTextjLabel.setText("Subscription Plans");
        subscriptionPlansJPanel.add(subscriptionPlansTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, 20));

        userMenujPanel.add(subscriptionPlansJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 200, 40));

        pendingPaymentJPanel.setBackground(new java.awt.Color(61, 81, 85));
        pendingPaymentJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        pendingPaymentJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingPaymentJPanelMouseClicked(evt);
            }
        });
        pendingPaymentJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_payment_history_24px.png"))); // NOI18N
        pendingPaymentJPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        pendingPaymentTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        pendingPaymentTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        pendingPaymentTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pendingPaymentTextjLabel.setText("Pending payment");
        pendingPaymentJPanel.add(pendingPaymentTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, 20));

        userMenujPanel.add(pendingPaymentJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 200, 40));

        billingHistoryJPanel.setBackground(new java.awt.Color(61, 81, 85));
        billingHistoryJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        billingHistoryJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billingHistoryJPanelMouseClicked(evt);
            }
        });
        billingHistoryJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_transaction_24px.png"))); // NOI18N
        billingHistoryJPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        billingHistoryTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        billingHistoryTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        billingHistoryTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        billingHistoryTextjLabel.setText("Billing History");
        billingHistoryJPanel.add(billingHistoryTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 90, 20));

        userMenujPanel.add(billingHistoryJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 200, 40));

        userComplaintsJPanel.setBackground(new java.awt.Color(61, 81, 85));
        userComplaintsJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        userComplaintsJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userComplaintsJPanelMouseClicked(evt);
            }
        });
        userComplaintsJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_complaint_24px_1.png"))); // NOI18N
        userComplaintsJPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        userComplaintsTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        userComplaintsTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        userComplaintsTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userComplaintsTextjLabel.setText("Complaints");
        userComplaintsJPanel.add(userComplaintsTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 70, 20));

        userMenujPanel.add(userComplaintsJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 200, 40));

        userChangeCredentialJPanel.setBackground(new java.awt.Color(61, 81, 85));
        userChangeCredentialJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        userChangeCredentialJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userChangeCredentialJPanelMouseClicked(evt);
            }
        });
        userChangeCredentialJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/triplaeplaybillmanagement/assets/icons8_registration_24px_3.png"))); // NOI18N
        userChangeCredentialJPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 30, 40));

        userChangeCredentialTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        userChangeCredentialTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        userChangeCredentialTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userChangeCredentialTextjLabel.setText("Chance Credential");
        userChangeCredentialJPanel.add(userChangeCredentialTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 120, 20));

        userMenujPanel.add(userChangeCredentialJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 200, 40));

        getContentPane().add(userMenujPanel, java.awt.BorderLayout.LINE_START);

        jPanel5.setLayout(new java.awt.BorderLayout());

        headerOfUserUIjPanel.setBackground(new java.awt.Color(29, 50, 73));
        headerOfUserUIjPanel.setPreferredSize(new java.awt.Dimension(860, 80));
        headerOfUserUIjPanel.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(29, 50, 73));
        jPanel10.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headingOfPagejLabel.setFont(new java.awt.Font("Goudy Old Style", 0, 48)); // NOI18N
        headingOfPagejLabel.setForeground(new java.awt.Color(222, 210, 181));
        headingOfPagejLabel.setText("Subscription Plans");
        headingOfPagejLabel.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel10.add(headingOfPagejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 370, 60));

        headerOfUserUIjPanel.add(jPanel10, java.awt.BorderLayout.LINE_START);

        jPanel11.setBackground(new java.awt.Color(29, 50, 73));
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 90));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userLogoutjButton.setBackground(new java.awt.Color(94, 185, 228));
        userLogoutjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        userLogoutjButton.setText("Log Out");
        userLogoutjButton.setBorder(null);
        userLogoutjButton.setFocusable(false);
        userLogoutjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userLogoutjButtonActionPerformed(evt);
            }
        });
        jPanel11.add(userLogoutjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 90, 40));

        headerOfUserUIjPanel.add(jPanel11, java.awt.BorderLayout.LINE_END);

        jPanel5.add(headerOfUserUIjPanel, java.awt.BorderLayout.PAGE_START);

        RightBottomContentUserjPanel.setBackground(new java.awt.Color(125, 159, 154));
        RightBottomContentUserjPanel.setAlignmentX(0.0F);
        RightBottomContentUserjPanel.setLayout(new javax.swing.OverlayLayout(RightBottomContentUserjPanel));

        UserComplaintsUIjPanel.setBackground(new java.awt.Color(255, 153, 153));
        UserComplaintsUIjPanel.setLayout(new java.awt.GridLayout(2, 1));

        AddPlanComplaintjPanel.setBackground(new java.awt.Color(125, 159, 154));
        AddPlanComplaintjPanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel6.setBackground(new java.awt.Color(125, 159, 154));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 0, 51));
        jLabel12.setText("Select Bill ");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 40));

        billIDListjComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        billIDListjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        billIDListjComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                billIDListjComboBoxItemStateChanged(evt);
            }
        });
        billIDListjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billIDListjComboBoxActionPerformed(evt);
            }
        });
        jPanel6.add(billIDListjComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 130, -1));

        adminNameComplaintjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        adminNameComplaintjLabel.setForeground(new java.awt.Color(255, 255, 255));
        adminNameComplaintjLabel.setText("Amin1");
        jPanel6.add(adminNameComplaintjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 110, 30));

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 0, 51));
        jLabel17.setText("Admin Name");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 100, 40));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 120, 20));

        AddPlanComplaintjPanel.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(125, 159, 154));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 51));
        jLabel13.setText("Plan Name");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 100, 40));

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 0, 51));
        jLabel18.setText("Admin Email");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 100, 40));

        adminEmailComplaintjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        adminEmailComplaintjLabel.setForeground(new java.awt.Color(255, 255, 255));
        adminEmailComplaintjLabel.setText("Amin1");
        jPanel7.add(adminEmailComplaintjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 120, 30));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 120, 20));

        planNameComplaintjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        planNameComplaintjLabel.setForeground(new java.awt.Color(255, 255, 255));
        planNameComplaintjLabel.setText("Amin1");
        jPanel7.add(planNameComplaintjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 140, 30));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 150, 20));

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 0, 51));
        jLabel19.setText("Plan Description");
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 100, 40));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 280, 20));

        planDescComplaintjLabel.setBackground(new java.awt.Color(125, 159, 154));
        planDescComplaintjLabel.setColumns(20);
        planDescComplaintjLabel.setForeground(new java.awt.Color(255, 255, 255));
        planDescComplaintjLabel.setRows(5);
        jScrollPane1.setViewportView(planDescComplaintjLabel);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 310, 50));

        AddPlanComplaintjPanel.add(jPanel7);

        jPanel3.setBackground(new java.awt.Color(125, 159, 154));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(125, 159, 154));
        jPanel12.setPreferredSize(new java.awt.Dimension(303, 70));

        addPlanjButton.setBackground(new java.awt.Color(51, 255, 153));
        addPlanjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        addPlanjButton.setText("Add Plan");
        addPlanjButton.setBorder(null);
        addPlanjButton.setFocusable(false);
        addPlanjButton.setPreferredSize(new java.awt.Dimension(50, 20));
        addPlanjButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPlanjButtonMouseClicked(evt);
            }
        });
        addPlanjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPlanjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addComponent(addPlanjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(addPlanjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel13.setBackground(new java.awt.Color(125, 159, 154));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 51));
        jLabel7.setText("Complain Description");
        jPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 140, 40));

        complaintDescriptionjTextField.setBackground(new java.awt.Color(125, 159, 154));
        complaintDescriptionjTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        complaintDescriptionjTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                complaintDescriptionjTextFieldActionPerformed(evt);
            }
        });
        jPanel13.add(complaintDescriptionjTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 160, 30));

        jPanel3.add(jPanel13, java.awt.BorderLayout.CENTER);

        AddPlanComplaintjPanel.add(jPanel3);

        UserComplaintsUIjPanel.add(AddPlanComplaintjPanel);

        jPanel15.setBackground(new java.awt.Color(125, 159, 154));

        jLabel9.setFont(new java.awt.Font("Palatino Linotype", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(222, 210, 181));
        jLabel9.setText("Raised Requets");

        addPlanjButton1.setBackground(new java.awt.Color(51, 204, 255));
        addPlanjButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        addPlanjButton1.setText("Refresh");
        addPlanjButton1.setBorder(null);
        addPlanjButton1.setFocusable(false);
        addPlanjButton1.setPreferredSize(new java.awt.Dimension(50, 20));
        addPlanjButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPlanjButton1MouseClicked(evt);
            }
        });
        addPlanjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPlanjButton1ActionPerformed(evt);
            }
        });

        raisedComplaintjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        raisedComplaintjScrollPane.setBorder(null);

        raisedComplaintjTable.setBackground(new java.awt.Color(226, 220, 220));
        raisedComplaintjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        raisedComplaintjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Complaint ID", "Complaint Description", "To Admin", "Status"
            }
        ));
        raisedComplaintjTable.setRowHeight(32);
        raisedComplaintjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        raisedComplaintjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        raisedComplaintjScrollPane.setViewportView(raisedComplaintjTable);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addPlanjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(raisedComplaintjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(addPlanjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(raisedComplaintjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
        );

        UserComplaintsUIjPanel.add(jPanel15);

        RightBottomContentUserjPanel.add(UserComplaintsUIjPanel);

        SubscriptionPlansUIjPanel.setBackground(new java.awt.Color(255, 153, 153));
        SubscriptionPlansUIjPanel.setLayout(new java.awt.GridLayout(1, 1));

        jPanel4.setBackground(new java.awt.Color(125, 159, 154));

        jLabel10.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(222, 210, 181));
        jLabel10.setText("Choose Your Plan");

        jPanel8.setBackground(new java.awt.Color(125, 159, 154));
        jPanel8.setLayout(new java.awt.GridLayout(1, 3));

        jPanel9.setLayout(new java.awt.GridLayout(1, 2));

        jPanel17.setBackground(new java.awt.Color(125, 159, 154));
        jPanel17.setPreferredSize(new java.awt.Dimension(300, 137));

        planNamejLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        planNamejLabel.setForeground(new java.awt.Color(51, 0, 51));
        planNamejLabel.setText("Plan Name");

        planListjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        planListjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planListjComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(planNamejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(planListjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(300, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(planNamejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(planListjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel17);

        jPanel14.setBackground(new java.awt.Color(125, 159, 154));
        jPanel14.setPreferredSize(new java.awt.Dimension(700, 137));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        planDetailsjLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        planDetailsjLabel.setForeground(new java.awt.Color(51, 0, 51));
        planDetailsjLabel.setText("Plan Details");
        jPanel14.add(planDetailsjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 7, 120, 36));

        planDesciptionjLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        planDesciptionjLabel.setForeground(new java.awt.Color(51, 0, 51));
        planDesciptionjLabel.setText("This plan includes ");
        jPanel14.add(planDesciptionjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 62, 370, 27));
        jPanel14.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 380, 17));

        jButton1.setBackground(new java.awt.Color(51, 204, 255));
        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jButton1.setText("Request Plan");
        jButton1.setBorder(null);
        jButton1.setFocusable(false);
        jButton1.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 130, 37));

        jPanel9.add(jPanel14);

        jPanel8.add(jPanel9);

        jPanel16.setBackground(new java.awt.Color(125, 159, 154));

        jLabel11.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(222, 210, 181));
        jLabel11.setText("Requested Plans");

        requestedPlanjTable.setBackground(new java.awt.Color(226, 220, 220));
        requestedPlanjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        requestedPlanjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Requested Plan", "Plan Description", "Amount", "Request Status"
            }
        ));
        requestedPlanjTable.setRowHeight(32);
        requestedPlanjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        requestedPlanjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        requestedPlanjScrollPane.setViewportView(requestedPlanjTable);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(requestedPlanjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requestedPlanjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SubscriptionPlansUIjPanel.add(jPanel4);

        RightBottomContentUserjPanel.add(SubscriptionPlansUIjPanel);

        UserChangeCredentialUIjPanel.setBackground(new java.awt.Color(125, 159, 154));
        UserChangeCredentialUIjPanel.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(new java.awt.Color(125, 159, 154));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 0, 51));
        jLabel14.setText("Confirm Password");

        userNameInProfilejLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        userNameInProfilejLabel.setForeground(new java.awt.Color(51, 0, 51));
        userNameInProfilejLabel.setText("User Name 1111");

        adminProfilejLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        adminProfilejLabel.setForeground(new java.awt.Color(51, 0, 51));
        adminProfilejLabel.setText("User Name");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 0, 51));
        jLabel16.setText("New Password");

        newUserjPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newUserjPasswordFieldFocusLost(evt);
            }
        });

        confirmConfirmjPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirmConfirmjPasswordFieldFocusLost(evt);
            }
        });
        confirmConfirmjPasswordField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmConfirmjPasswordFieldMouseExited(evt);
            }
        });

        updatePasswordjButton.setBackground(new java.awt.Color(94, 185, 228));
        updatePasswordjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        updatePasswordjButton.setText("Update Password");
        updatePasswordjButton.setBorder(null);
        updatePasswordjButton.setFocusable(false);
        updatePasswordjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePasswordjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(newUserjPasswordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(confirmConfirmjPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jSeparator9))
                        .addGap(231, 231, 231))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adminProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userNameInProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updatePasswordjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(adminProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userNameInProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(confirmConfirmjPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(newUserjPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(updatePasswordjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        UserChangeCredentialUIjPanel.add(jPanel18, java.awt.BorderLayout.CENTER);

        RightBottomContentUserjPanel.add(UserChangeCredentialUIjPanel);

        PendingPaymentUIjPanel.setBackground(new java.awt.Color(255, 153, 153));
        PendingPaymentUIjPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(125, 159, 154));

        jLabel3.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(222, 210, 181));
        jLabel3.setText("Pending Bills To be Paid");

        pendingPaymentjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        pendingPaymentjScrollPane.setBorder(null);

        pendingPaymentjTable.setAutoCreateRowSorter(true);
        pendingPaymentjTable.setBackground(new java.awt.Color(226, 220, 220));
        pendingPaymentjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pendingPaymentjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null}
            },
            new String [] {
                " Bill ID", " Plan Name","Isuue Date","Due Date", "Pay Now/Later"
            }
        ));
        pendingPaymentjTable.setRowHeight(32);
        pendingPaymentjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        pendingPaymentjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        pendingPaymentjTable.setShowHorizontalLines(false);
        pendingPaymentjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingPaymentjTableMouseClicked(evt);
            }
        });
        pendingPaymentjScrollPane.setViewportView(pendingPaymentjTable);

        jButton2.setBackground(new java.awt.Color(51, 204, 255));
        jButton2.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.setBorder(null);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pendingPaymentjScrollPane)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 445, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(pendingPaymentjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        PendingPaymentUIjPanel.add(jPanel1);

        RightBottomContentUserjPanel.add(PendingPaymentUIjPanel);

        BillingHistoryUIjPanel.setBackground(new java.awt.Color(255, 153, 153));
        BillingHistoryUIjPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(125, 159, 154));

        jLabel5.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(222, 210, 181));
        jLabel5.setText(" Bills History");

        billingHistoryjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        billingHistoryjScrollPane.setBorder(null);

        billHistoryjTable.setAutoCreateRowSorter(true);
        billHistoryjTable.setBackground(new java.awt.Color(226, 220, 220));
        billHistoryjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        billHistoryjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null, null},
                {null, null, null,null, null, null},
                {null, null, null,null, null, null},
                {null, null, null,null, null, null}
            },
            new String [] {
                " Bill ID", " Plan Name","Isuue Date","Due Date"," Bill Status", "More Info"
            }
        ));
        billHistoryjTable.setRowHeight(32);
        billHistoryjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        billHistoryjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        billHistoryjTable.setShowHorizontalLines(false);
        billHistoryjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billHistoryjTableMouseClicked(evt);
            }
        });
        billingHistoryjScrollPane.setViewportView(billHistoryjTable);

        billHistoryRefreshjButton.setBackground(new java.awt.Color(51, 204, 255));
        billHistoryRefreshjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        billHistoryRefreshjButton.setText("Refresh");
        billHistoryRefreshjButton.setBorder(null);
        billHistoryRefreshjButton.setFocusable(false);
        billHistoryRefreshjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billHistoryRefreshjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(billingHistoryjScrollPane)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 445, Short.MAX_VALUE)
                        .addComponent(billHistoryRefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(billHistoryRefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(billingHistoryjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        BillingHistoryUIjPanel.add(jPanel2);

        RightBottomContentUserjPanel.add(BillingHistoryUIjPanel);

        jPanel5.add(RightBottomContentUserjPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void subscriptionPlansJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subscriptionPlansJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(subscriptionPlansJPanel, subscriptionPlansTextjLabel);
        
//        Initialise Plan drop box ( JComboBox) with values and request plans
        addPlanToComboBox();
        displayRequestPlanToUser();
        showThisPane(SubscriptionPlansUIjPanel);

    }//GEN-LAST:event_subscriptionPlansJPanelMouseClicked

    private void pendingPaymentJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingPaymentJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(pendingPaymentJPanel , pendingPaymentTextjLabel);
        
        showThisPane(PendingPaymentUIjPanel);
        displayDataToPendingBillTable(userID, pendingPaymentjTable);
    }//GEN-LAST:event_pendingPaymentJPanelMouseClicked

    private void billingHistoryJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingHistoryJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(billingHistoryJPanel, billingHistoryTextjLabel);
        
        showThisPane(BillingHistoryUIjPanel);
        
        displayDataToBillHistoryTable();
    }//GEN-LAST:event_billingHistoryJPanelMouseClicked

    private void userComplaintsJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userComplaintsJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(userComplaintsJPanel, userComplaintsTextjLabel);
        
//        Initialising Admin names to Drop down -- JCombo box
            billIDToComboBox();
            
            displayRaisedComplaints();
        showThisPane(UserComplaintsUIjPanel);
    }//GEN-LAST:event_userComplaintsJPanelMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        displayDataToPendingBillTable(userID, pendingPaymentjTable);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pendingPaymentjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingPaymentjTableMouseClicked
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Pay This Bill ?"," Confirmation", JOptionPane.YES_NO_OPTION );
        System.out.println(" Selected " + choice);
        
//        choice 0 means YES.
        if( choice == 0){
//            To get selected row So we can get Request ID
              int rowIndex = pendingPaymentjTable.getSelectedRow();
              int billID =Integer.parseInt( pendingPaymentjTable.getModel().getValueAt(rowIndex, 0).toString() );
              
             updatePendingBillTable(billID);
             
        }
        
    }//GEN-LAST:event_pendingPaymentjTableMouseClicked

    private void billHistoryjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billHistoryjTableMouseClicked
        // TODO add your handling code here:
        
        int rowIndex = billHistoryjTable.getSelectedRow();
        int billID =Integer.parseInt( billHistoryjTable.getModel().getValueAt(rowIndex, 0).toString() );
        
//        To get more info in JOption pane
        String desciption = getBillPlanDesciption(billID);
        
        JOptionPane.showMessageDialog(this, desciption);
    }//GEN-LAST:event_billHistoryjTableMouseClicked

    private void billHistoryRefreshjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billHistoryRefreshjButtonActionPerformed
        // TODO add your handling code here:
        displayDataToBillHistoryTable();
    }//GEN-LAST:event_billHistoryRefreshjButtonActionPerformed

    private void userLogoutjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userLogoutjButtonActionPerformed
        // TODO add your handling code here:
        new LoginFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userLogoutjButtonActionPerformed

    private void addPlanjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPlanjButtonActionPerformed
        // TODO add your handling code here:
        if(complaintDescriptionjTextField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Complain Description is Empty !!!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else{
            String email =adminEmailComplaintjLabel.getText();
            String complaint = complaintDescriptionjTextField.getText();
            addComplaintToTable(email, complaint );
            displayRaisedComplaints();
        }
        
    }//GEN-LAST:event_addPlanjButtonActionPerformed

    private void addPlanjButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlanjButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addPlanjButtonMouseClicked

    private void complaintDescriptionjTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_complaintDescriptionjTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_complaintDescriptionjTextFieldActionPerformed

    private void addPlanjButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlanjButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addPlanjButton1MouseClicked

    private void addPlanjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPlanjButton1ActionPerformed
        // TODO add your handling code here:
        displayRaisedComplaints();
    }//GEN-LAST:event_addPlanjButton1ActionPerformed

    private void billIDListjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billIDListjComboBoxActionPerformed
        // TODO add your handling code here:\
//        String ID = String.valueOf(billIDListjComboBox.getSelectedItem());
        int billID = Integer.parseInt(String.valueOf(billIDListjComboBox.getSelectedItem()));
        System.out.println("Selected Bill ID: " + billID);
        complaintRaise(billID);
    }//GEN-LAST:event_billIDListjComboBoxActionPerformed

    private void planListjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planListjComboBoxActionPerformed
        // TODO add your handling code here:
        String selectedPlanName = String.valueOf(planListjComboBox.getSelectedItem());
        
        String planDescription = planDescription(selectedPlanName);
        planDesciptionjLabel.setText(planDescription);
    }//GEN-LAST:event_planListjComboBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        int choice = JOptionPane.showConfirmDialog(this, "Do you what to request This Plan ?"," Confirmation", JOptionPane.YES_NO_OPTION );
        System.out.println(" Selected " + choice);
        
//        choice 0 means YES.
        if( choice == 0){
            String planName = String.valueOf(planListjComboBox.getSelectedItem());
            insertIntoRequestTable(planName);

    //        Showing updated view of Request table
            displayRequestPlanToUser();
             
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void confirmConfirmjPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmConfirmjPasswordFieldFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_confirmConfirmjPasswordFieldFocusLost

    private void confirmConfirmjPasswordFieldMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmConfirmjPasswordFieldMouseExited
        // TODO add your handling code here:

    }//GEN-LAST:event_confirmConfirmjPasswordFieldMouseExited

    private void updatePasswordjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePasswordjButtonActionPerformed
        // TODO add your handling code here:
        int valid = SignupFrame.validatePassword(newUserjPasswordField, confirmConfirmjPasswordField);
        if( valid ==1){
            String password = String.valueOf(newUserjPasswordField.getPassword());
            updateUserPassword(password);
        }
    }//GEN-LAST:event_updatePasswordjButtonActionPerformed

    private void userChangeCredentialJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userChangeCredentialJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(userChangeCredentialJPanel , userChangeCredentialTextjLabel);
        
        userNameInProfilejLabel.setText(userName);
        showThisPane(UserChangeCredentialUIjPanel);
    }//GEN-LAST:event_userChangeCredentialJPanelMouseClicked

    private void newUserjPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newUserjPasswordFieldFocusLost
        // TODO add your handling code here:
        int valid = checkFieldValidity(String.valueOf(newUserjPasswordField.getPassword()), SignupFrame.CheckFlied.PASSWORD);
        
        if( ! String.valueOf(newUserjPasswordField.getPassword()).equals(""))
        if(valid == 0 ){
            JOptionPane.showMessageDialog(this, "Password is Not Valid.\n Password must be atleast 8 digit Alpha Numaric with atleast one special charecter");
        }
    }//GEN-LAST:event_newUserjPasswordFieldFocusLost

    private void billIDListjComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_billIDListjComboBoxItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_billIDListjComboBoxItemStateChanged

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
            java.util.logging.Logger.getLogger(UserMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserMainUI().setVisible(true);
            }
        });
    }
    

//    new jTable header for planShowTable in Addf plan Tab
    private javax.swing.table.JTableHeader pendingPaymenttableHeader;
    private javax.swing.table.JTableHeader billHistoryjTableHeader;
    private javax.swing.table.JTableHeader raisedComplaintjTableHeader;
    private javax.swing.table.JTableHeader requestedPlanjTableHeader;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddPlanComplaintjPanel;
    private javax.swing.JPanel BillingHistoryUIjPanel;
    private javax.swing.JPanel PendingPaymentUIjPanel;
    private javax.swing.JPanel RightBottomContentUserjPanel;
    private javax.swing.JPanel SubscriptionPlansUIjPanel;
    private javax.swing.JPanel UserChangeCredentialUIjPanel;
    private javax.swing.JPanel UserComplaintsUIjPanel;
    private javax.swing.JButton addPlanjButton;
    private javax.swing.JButton addPlanjButton1;
    private javax.swing.JLabel adminEmailComplaintjLabel;
    private javax.swing.JLabel adminNameComplaintjLabel;
    private javax.swing.JLabel adminProfilejLabel;
    private javax.swing.JButton billHistoryRefreshjButton;
    private javax.swing.JTable billHistoryjTable;
    private javax.swing.JComboBox<String> billIDListjComboBox;
    private javax.swing.JPanel billingHistoryJPanel;
    private javax.swing.JLabel billingHistoryTextjLabel;
    private javax.swing.JScrollPane billingHistoryjScrollPane;
    private javax.swing.JTextField complaintDescriptionjTextField;
    private javax.swing.JPasswordField confirmConfirmjPasswordField;
    private javax.swing.JPanel headerOfUserUIjPanel;
    private javax.swing.JLabel headingOfPagejLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPasswordField newUserjPasswordField;
    private javax.swing.JPanel pendingPaymentJPanel;
    private javax.swing.JLabel pendingPaymentTextjLabel;
    private javax.swing.JScrollPane pendingPaymentjScrollPane;
    private javax.swing.JTable pendingPaymentjTable;
    private javax.swing.JTextArea planDescComplaintjLabel;
    private javax.swing.JLabel planDesciptionjLabel;
    private javax.swing.JLabel planDetailsjLabel;
    private javax.swing.JComboBox<String> planListjComboBox;
    private javax.swing.JLabel planNameComplaintjLabel;
    private javax.swing.JLabel planNamejLabel;
    private javax.swing.JScrollPane raisedComplaintjScrollPane;
    private javax.swing.JTable raisedComplaintjTable;
    private javax.swing.JScrollPane requestedPlanjScrollPane;
    private javax.swing.JTable requestedPlanjTable;
    private javax.swing.JPanel subscriptionPlansJPanel;
    private javax.swing.JLabel subscriptionPlansTextjLabel;
    private javax.swing.JButton updatePasswordjButton;
    private javax.swing.JPanel userChangeCredentialJPanel;
    private javax.swing.JLabel userChangeCredentialTextjLabel;
    private javax.swing.JPanel userComplaintsJPanel;
    private javax.swing.JLabel userComplaintsTextjLabel;
    private javax.swing.JButton userLogoutjButton;
    private javax.swing.JPanel userMenujPanel;
    private javax.swing.JLabel userNameInProfilejLabel;
    public javax.swing.JLabel userNamejLabel;
    // End of variables declaration//GEN-END:variables
}
