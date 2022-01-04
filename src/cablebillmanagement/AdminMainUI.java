/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cablebillmanagement;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaideep
 */
public class AdminMainUI extends javax.swing.JFrame {
    
    public int adminID = 101;
    public String adminName = " No Admin Name";
    Connection connection = null;

    /**
     * Creates new form AdminMainUI
     */
    public AdminMainUI() {
        initComponents();
        
//        Setting User name in Menu Option
        adminNamejLabel.setText(adminName);        
//        Initial panel to show on right side of the screen
        showThisPane(PendingRequestUIjPanel);
        displayRequestInTable();
        
//        Changing table header color and font
        tableModify.changeTableHeader(this.planShowtableHeader, this.planjShowjTable);
        tableModify.changeTableHeader(this.requestShowtableHeader, this.requestShowjTable);
        tableModify.changeTableHeader(this.billListtableHeader, this.billListjTable);
        tableModify.changeTableHeader(this.userListtableHeader, this.UserListjTable);
        tableModify.changeTableHeader(this.pendingComplaintstableHeader, this.pendingComplaitsjTable);
        tableModify.changeTableHeader(this.allComplaintstableHeader, this.allComplaintsjTable);
        
//        Setting Scoll view bg color
         planTablejScrollPane.getViewport().setBackground(new Color(125,159,154));
         requestShowjTableScrollPane.getViewport().setBackground(new Color(125,159,154));
         billListjScrollPane.getViewport().setBackground(new Color(125,159,154));
         userListjScrollPane.getViewport().setBackground(new Color(125,159,154));
         pendingComplaitsjScrollPane.getViewport().setBackground(new Color(125,159,154));
         allComplaintsjScrollPane.getViewport().setBackground(new Color(125,159,154));
         
         
//        changing table row color alternatively
//        tableModify.alternativeRowColor(this.requestShowjTable);
        //    changing menu button to default color
        
        
        changeTOBtnDefaultColor();
        highlightClicked(pendingRequestJPanel, pendingRequestTextjLabel);
        
    }
    

//    changing menu button to default color
    private void changeTOBtnDefaultColor(){
//        changing bg color
        pendingRequestJPanel.setBackground(new Color(61,81,85));
        manageUserJPanel.setBackground(new Color(61,81,85));
        addPlanJPanel.setBackground(new Color(61,81,85));
        adminComplaintsJPanel.setBackground(new Color(61,81,85));
        adminChangeCredentialJPanel.setBackground(new Color(61,81,85));
//        changing font colr
        pendingRequestTextjLabel.setForeground(new Color(255,255,255));
        manageUserTextjLabel.setForeground(new Color(255,255,255));
        addPlanTextjLabel.setForeground(new Color(255,255,255));
        adminComplaintsTextjLabel.setForeground(new Color(255,255,255));
        adminChangeCredentialTextjLabel.setForeground(new Color(255,255,255));
    }
    
//    changing menu color to make highlight clicked button
//    and will change Heading text to clicked item
    private void highlightClicked(JPanel clickPanel,JLabel clickLable){
        clickPanel.setBackground(new Color(125,159,154));
        clickLable.setForeground(new Color(21,49,63));
        headingOfPagejLabel.setText(clickLable.getText());
    }
    
    private void showThisPane(JPanel myPane){
        PendingRequestUIjPanel.setVisible(false);
        ManageUserUIjPanel.setVisible(false);
        AddPlanesUIjPanel.setVisible(false);
        ComplaintsUItempjPanel.setVisible(false);
        AdminChangeCredentialjPanel.setVisible(false);
        
        tempjPanel.setVisible(false);
        
        myPane.setVisible(true);
    }

//  **********************************************Subscription table ******************************************
    
    
//    Adding plan to database
    private void addDataToPlanTable(){
        
        try{
            connection = ConnectionManager.getConnection();
            String url;
            PreparedStatement ps;
            int amt = Integer.parseInt(planAmtjTextField.getText());
//            Initializing querry statement
            url = "insert into SUBSCRIPTION_PLAN (Sub_plan,Sub_description, Sub_amt) values(?,?, ?);";
            ps = connection.prepareStatement(url);
            ps.setString(1, planNamejTextField.getText());
            ps.setString(2, planDescriptionjTextField.getText());
            ps.setInt(3, amt);
            
//            Executing querry. It will return the Number of ROW wffwcted by querry. Here it is one.
            int i = ps.executeUpdate();
            
//          If sucessfull Acknowledging  
            if( i> 0){
                JOptionPane.showMessageDialog(this, "Saved successfully.");
            }
            ResetingField();
        }
        catch(HeadlessException | NumberFormatException | SQLException ex){
//            if any error accured
            System.out.println(" Error in line 82 : " + ex);
        }
    }
    
    private void ResetingField(){
        planNamejTextField.setText("");
        planDescriptionjTextField.setText("");
        planAmtjTextField.setText("");
    }
    
//    Display plan details in table view
    
    private void displayPlanInTable(){
        String url = "select * from SUBSCRIPTION_PLAN;";
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) planjShowjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
            while(rs.next()){
                model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayPlanTable method : " + ex.getMessage());
        }
    }
    
//  ******************************************* Request table **********************************************
    private void displayRequestInTable(){
        String url = "select R_id, U_name, U_email, Sub_plan from REQUEST_PLANS AS R, USER AS U, SUBSCRIPTION_PLAN AS S where U.U_id = R.U_id AND R.Sub_id = S.Sub_id AND R.R_status = 'Pending';";
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) requestShowjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
            while(rs.next()){
                model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), "Click To take Action"});
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayRequestInTable method : " + ex.getMessage());
        }
        
    }

//  **************************************************Bill table adiing *************************************
    
//     Request is granted and Bill generated
    private void generateBill( int requestID, String date){
        
        String requestQuery = "UPDATE REQUEST_PLANS SET R_status = 'Processed' WHERE R_id = " +requestID+" ;";
        String billGenerateQuery = "INSERT INTO BILL (U_id, Sub_id, B_issue_date, B_due_date)  SELECT U_id,Sub_id, curdate(),'" +date+"' FROM REQUEST_PLANS WHERE R_id = " +requestID+" ;";
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(requestQuery);
            PreparedStatement ps1 = connection.prepareStatement(billGenerateQuery);
            
            int i = ps.executeUpdate();
            int j = ps1.executeUpdate();
            
            if( i> 0){
                JOptionPane.showMessageDialog(this, "Request Granted.");
            }
            if( j> 0){
                JOptionPane.showMessageDialog(this, " Bill Generated.");
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in generateBill method : " + ex.getMessage());
        }
        
//        Showing Updated table 
        displayRequestInTable();
    }
    
    
//    ******************************** Show Bill table in Manage user Option
    
  private void displayBillListInTable(){
        String url = "select U_name, Sub_plan, B_status, B_issue_date, B_due_date from BILL AS B JOIN subscription_plan AS S ON S.SUb_id =B.Sub_id JOIN USER AS U ON U.U_id = B.U_id order by B_status desc;";
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) billListjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
            while(rs.next()){
                model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5)});
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayRequestInTable method : " + ex.getMessage());
        }
        
    }
  
//****************************************  Showing Customized User info in Manage User section ************
  
  private void displayUserListInTable(){
        String url = "select U.U_id, U.U_name, U.U_email, U.U_address,count(U.U_id) AS 'Subscribed Plan', count( if( B.B_status = 'Paid', B.B_status,NULL)) AS 'Cleared Bill', sum(Sub_amt) AS 'Net Amount' from bill as B natural join subscription_plan AS S JOIN USER AS U ON B.U_id = U.U_id group by U.U_id  UNION select U_id, U_name, U_email, U_address,0,0,0 from user where U_id not in (select U_id from REQUEST_PLANS);";
        
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) UserListjTable.getModel();

//Removing blank row OR clearing row before initializing with new values            
            model.setRowCount(0);
            
            while(rs.next()){
                model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayUserListInTable method : " + ex.getMessage());
        }
        
    }
  
//  ************************** Delete user from table **********
  private void deleteUser(int id){
      
      String url =" DELETE FROM USER WHERE   U_id= "+ id+";";
      try{
          
          connection = ConnectionManager.getConnection();
          PreparedStatement ps = connection.prepareStatement(url);
          int i = ps.executeUpdate();
          
          if ( i>0){
              JOptionPane.showMessageDialog(this, " User Deleted.");
          }
          
      }catch( Exception ex){
          System.out.println(" Error in deleteUser : " + ex);
      }
  }
  
// ***********************Complaints***********************
private void displayPendingComplaintsInTable(int displayPending){
        
    String url ="";
         if( displayPending == 1){
                url = "SELECT C_id, U_name,U_email, Ad_name, Complaint FROM COMPLAINTS NATURAL JOIN USER NATURAL JOIN ADMIN where C_status like 'Pending';";
             }
         else{
             url = "SELECT C_id, U_name,U_email, Ad_name, Complaint, C_status FROM COMPLAINTS NATURAL JOIN USER NATURAL JOIN ADMIN ORDER BY  C_status;";
         }
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            ResultSet rs = ps.executeQuery();
            
//To display only pending complaints
            if ( displayPending ==1 ){
                DefaultTableModel model = (DefaultTableModel) pendingComplaitsjTable.getModel();
                model.setRowCount(0);
                while(rs.next()){
                    model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5)});
                }      
            }
//            To display all Complaints
            else{
                
                DefaultTableModel model = (DefaultTableModel) allComplaintsjTable.getModel();
                model.setRowCount(0);
                while(rs.next()){
                    model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6)});
                
                          
                }
            }
            
        }
        catch( SQLException ex){
            System.out.println("Error in displayPendingComplaintsInTable method : " + ex.getMessage());
        }
}  

// updating Complaints 
private void updatePendingComplaintsInTable(int complaintID){
        
    String url =" UPDATE COMPLAINTS SET C_status = 'Solved' where C_id = "+ complaintID+";";
        try{
            connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(url);
            
            int choice = JOptionPane.showConfirmDialog(this, "Do You Want To Mark It As Solved ?"," Confirmation", JOptionPane.YES_NO_OPTION );
//            ) means YES
            if ( choice == 0){
            
                int i = ps.executeUpdate();
                if( i> 0){
                    JOptionPane.showMessageDialog(this, "Complaint Marked As Solved.");
                 }
            }
            displayPendingComplaintsInTable(1);
        }
//    
        catch( SQLException ex){
            System.out.println("Error in displayPendingComplaintsInTable method : " + ex.getMessage());
        }
}  
  
//  *******************************Profile ***************************
private void updateAdminPassword(String password ){
    String url = "UPDATE ADMIN SET Ad_pass = '"+password+"' WHERE Ad_id = "+this.adminID+";";
    System.out.println("Admin id " + url);
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
            System.out.println("Error in cablebillmanagement.AdminMainUI.updateAdminPassword() : " + ex.getMessage());
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

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        adminNamejLabel = new javax.swing.JLabel();
        pendingRequestJPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pendingRequestTextjLabel = new javax.swing.JLabel();
        manageUserJPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        manageUserTextjLabel = new javax.swing.JLabel();
        addPlanJPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        addPlanTextjLabel = new javax.swing.JLabel();
        adminComplaintsJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        adminComplaintsTextjLabel = new javax.swing.JLabel();
        adminChangeCredentialJPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        adminChangeCredentialTextjLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        adminLogoutjButton = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        headingOfPagejLabel = new javax.swing.JLabel();
        RightBottomContentjPanel = new javax.swing.JPanel();
        AdminChangeCredentialjPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        adminNameInProfilejLabel = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        adminProfilejLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        newAdminjPasswordField = new javax.swing.JPasswordField();
        confirmAdminjPasswordField = new javax.swing.JPasswordField();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        updatePasswordjButton = new javax.swing.JButton();
        ComplaintsUItempjPanel = new javax.swing.JPanel();
        billListContainerjPanel1 = new javax.swing.JPanel();
        billListjLabel1 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        pendingComplaitsjScrollPane = new javax.swing.JScrollPane();
        pendingComplaitsjTable = new javax.swing.JTable();
        pendingComplaintsRefreshjButton1 = new javax.swing.JButton();
        UsertListContainerjPanel1 = new javax.swing.JPanel();
        allComplaintsjLabel = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        allComplaintsRefreshjButton = new javax.swing.JButton();
        allComplaintsjScrollPane = new javax.swing.JScrollPane();
        allComplaintsjTable = new javax.swing.JTable();
        AddPlanesUIjPanel = new javax.swing.JPanel();
        AddPlanContainerjPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        planNamejTextField = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        planDescriptionjTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        addPlanjButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        planAmtjTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        plansShowjPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        refreshPlanOnTablejButton = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        planTablejScrollPane = new javax.swing.JScrollPane();
        planjShowjTable = new javax.swing.JTable();
        PendingRequestUIjPanel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        requestShowjTableScrollPane = new javax.swing.JScrollPane();
        requestShowjTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        ManageUserUIjPanel = new javax.swing.JPanel();
        billListContainerjPanel = new javax.swing.JPanel();
        billListjLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        billListjScrollPane = new javax.swing.JScrollPane();
        billListjTable = new javax.swing.JTable();
        billListrefreshjButton = new javax.swing.JButton();
        UsertListContainerjPanel = new javax.swing.JPanel();
        userListjLabel = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        userListRefreshjButton = new javax.swing.JButton();
        userListjScrollPane = new javax.swing.JScrollPane();
        UserListjTable = new javax.swing.JTable();
        tempjPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cable Bill Management - Admin");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(20, 33, 48));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 583));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_administrator_male_100px_2.png"))); // NOI18N
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 100, 90));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 176, 22));

        adminNamejLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 18)); // NOI18N
        adminNamejLabel.setForeground(new java.awt.Color(222, 210, 181));
        adminNamejLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminNamejLabel.setText("Jaideep");
        jPanel4.add(adminNamejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 140, 29));

        pendingRequestJPanel.setBackground(new java.awt.Color(125, 159, 154));
        pendingRequestJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        pendingRequestJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingRequestJPanelMouseClicked(evt);
            }
        });
        pendingRequestJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_data_pending_24px.png"))); // NOI18N
        pendingRequestJPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 30, 40));

        pendingRequestTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        pendingRequestTextjLabel.setForeground(new java.awt.Color(21, 49, 63));
        pendingRequestTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pendingRequestTextjLabel.setText("Pending Request");
        pendingRequestJPanel.add(pendingRequestTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 100, 20));

        jPanel4.add(pendingRequestJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 200, 40));

        manageUserJPanel.setBackground(new java.awt.Color(61, 81, 85));
        manageUserJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        manageUserJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageUserJPanelMouseClicked(evt);
            }
        });
        manageUserJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_admin_settings_male_24px.png"))); // NOI18N
        manageUserJPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        manageUserTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        manageUserTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        manageUserTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        manageUserTextjLabel.setText("Manage User");
        manageUserJPanel.add(manageUserTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 90, 20));

        jPanel4.add(manageUserJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 200, 40));

        addPlanJPanel.setBackground(new java.awt.Color(61, 81, 85));
        addPlanJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        addPlanJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPlanJPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addPlanJPanelMouseEntered(evt);
            }
        });
        addPlanJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_new_ticket_25px.png"))); // NOI18N
        addPlanJPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        addPlanTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        addPlanTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        addPlanTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addPlanTextjLabel.setText("Add Plans");
        addPlanJPanel.add(addPlanTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 70, 20));

        jPanel4.add(addPlanJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 200, 40));

        adminComplaintsJPanel.setBackground(new java.awt.Color(61, 81, 85));
        adminComplaintsJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        adminComplaintsJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminComplaintsJPanelMouseClicked(evt);
            }
        });
        adminComplaintsJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_complaint_24px_1.png"))); // NOI18N
        adminComplaintsJPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 40));

        adminComplaintsTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        adminComplaintsTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        adminComplaintsTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminComplaintsTextjLabel.setText("Complaints");
        adminComplaintsJPanel.add(adminComplaintsTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 70, 20));

        jPanel4.add(adminComplaintsJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 200, 40));

        adminChangeCredentialJPanel.setBackground(new java.awt.Color(61, 81, 85));
        adminChangeCredentialJPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(125, 159, 154)));
        adminChangeCredentialJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminChangeCredentialJPanelMouseClicked(evt);
            }
        });
        adminChangeCredentialJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cablebillmanagement/assets/icons8_registration_24px_3.png"))); // NOI18N
        adminChangeCredentialJPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 30, 40));

        adminChangeCredentialTextjLabel.setFont(new java.awt.Font("Goudy Old Style", 1, 15)); // NOI18N
        adminChangeCredentialTextjLabel.setForeground(new java.awt.Color(255, 255, 255));
        adminChangeCredentialTextjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminChangeCredentialTextjLabel.setText("Chance Credential");
        adminChangeCredentialJPanel.add(adminChangeCredentialTextjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 120, 20));

        jPanel4.add(adminChangeCredentialJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 200, 40));

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(29, 50, 73));
        jPanel1.setPreferredSize(new java.awt.Dimension(860, 90));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(29, 50, 73));
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 90));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adminLogoutjButton.setBackground(new java.awt.Color(94, 185, 228));
        adminLogoutjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        adminLogoutjButton.setText("Log Out");
        adminLogoutjButton.setBorder(null);
        adminLogoutjButton.setFocusable(false);
        adminLogoutjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminLogoutjButtonActionPerformed(evt);
            }
        });
        jPanel8.add(adminLogoutjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 90, 40));

        jPanel1.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel9.setBackground(new java.awt.Color(29, 50, 73));
        jPanel9.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headingOfPagejLabel.setFont(new java.awt.Font("Goudy Old Style", 0, 48)); // NOI18N
        headingOfPagejLabel.setForeground(new java.awt.Color(222, 210, 181));
        headingOfPagejLabel.setText("Pending Request");
        headingOfPagejLabel.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel9.add(headingOfPagejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 370, 60));

        jPanel1.add(jPanel9, java.awt.BorderLayout.LINE_START);

        jPanel5.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        RightBottomContentjPanel.setBackground(new java.awt.Color(125, 159, 154));
        RightBottomContentjPanel.setPreferredSize(new java.awt.Dimension(900, 496));
        RightBottomContentjPanel.setLayout(new javax.swing.OverlayLayout(RightBottomContentjPanel));

        AdminChangeCredentialjPanel.setBackground(new java.awt.Color(125, 159, 154));
        AdminChangeCredentialjPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(125, 159, 154));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 0, 51));
        jLabel14.setText("Confirm Password");

        adminNameInProfilejLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        adminNameInProfilejLabel.setForeground(new java.awt.Color(51, 0, 51));
        adminNameInProfilejLabel.setText("Admin Name 1111");

        adminProfilejLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        adminProfilejLabel.setForeground(new java.awt.Color(51, 0, 51));
        adminProfilejLabel.setText("Admin Name");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 0, 51));
        jLabel16.setText("New Password");

        confirmAdminjPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirmAdminjPasswordFieldFocusLost(evt);
            }
        });
        confirmAdminjPasswordField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmAdminjPasswordFieldMouseExited(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(newAdminjPasswordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(confirmAdminjPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jSeparator9))
                        .addGap(231, 231, 231))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adminProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminNameInProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updatePasswordjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(adminProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminNameInProfilejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(confirmAdminjPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(newAdminjPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(updatePasswordjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        AdminChangeCredentialjPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        RightBottomContentjPanel.add(AdminChangeCredentialjPanel);

        ComplaintsUItempjPanel.setBackground(new java.awt.Color(125, 159, 154));
        ComplaintsUItempjPanel.setLayout(new java.awt.GridLayout(2, 1));

        billListContainerjPanel1.setBackground(new java.awt.Color(125, 159, 154));

        billListjLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        billListjLabel1.setForeground(new java.awt.Color(222, 210, 181));
        billListjLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        billListjLabel1.setText("Pending Complaints");

        pendingComplaitsjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        pendingComplaitsjScrollPane.setBorder(null);

        pendingComplaitsjTable.setBackground(new java.awt.Color(226, 220, 220));
        pendingComplaitsjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pendingComplaitsjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null}
            },
            new String [] {
                "Complaint ID", " User "," User Email", "To Admin", "Complaint Description"
            }
        ));
        pendingComplaitsjTable.setRowHeight(32);
        pendingComplaitsjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        pendingComplaitsjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        pendingComplaitsjTable.setShowHorizontalLines(false);
        pendingComplaitsjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingComplaitsjTableMouseClicked(evt);
            }
        });
        pendingComplaitsjScrollPane.setViewportView(pendingComplaitsjTable);

        pendingComplaintsRefreshjButton1.setBackground(new java.awt.Color(51, 204, 255));
        pendingComplaintsRefreshjButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        pendingComplaintsRefreshjButton1.setText("Refresh");
        pendingComplaintsRefreshjButton1.setBorder(null);
        pendingComplaintsRefreshjButton1.setFocusable(false);
        pendingComplaintsRefreshjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendingComplaintsRefreshjButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout billListContainerjPanel1Layout = new javax.swing.GroupLayout(billListContainerjPanel1);
        billListContainerjPanel1.setLayout(billListContainerjPanel1Layout);
        billListContainerjPanel1Layout.setHorizontalGroup(
            billListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pendingComplaitsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
            .addGroup(billListContainerjPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(billListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator6)
                    .addComponent(billListjLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pendingComplaintsRefreshjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        billListContainerjPanel1Layout.setVerticalGroup(
            billListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billListContainerjPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(billListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(billListjLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pendingComplaintsRefreshjButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendingComplaitsjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ComplaintsUItempjPanel.add(billListContainerjPanel1);

        UsertListContainerjPanel1.setBackground(new java.awt.Color(125, 159, 154));

        allComplaintsjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        allComplaintsjLabel.setForeground(new java.awt.Color(222, 210, 181));
        allComplaintsjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        allComplaintsjLabel.setText("All Complaints");

        allComplaintsRefreshjButton.setBackground(new java.awt.Color(51, 204, 255));
        allComplaintsRefreshjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        allComplaintsRefreshjButton.setText("Refresh");
        allComplaintsRefreshjButton.setBorder(null);
        allComplaintsRefreshjButton.setFocusable(false);
        allComplaintsRefreshjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allComplaintsRefreshjButtonActionPerformed(evt);
            }
        });

        allComplaintsjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        allComplaintsjScrollPane.setBorder(null);

        allComplaintsjTable.setBackground(new java.awt.Color(226, 220, 220));
        allComplaintsjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        allComplaintsjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null, null},
                {null, null, null,null, null, null},
                {null, null, null,null, null, null},
                {null, null, null,null, null, null}
            },
            new String [] {
                "Complaint ID", " User "," User Email", "To Admin", "Complaint Description", "Status"
            }
        ));
        allComplaintsjTable.setRowHeight(32);
        allComplaintsjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        allComplaintsjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        allComplaintsjTable.setShowHorizontalLines(false);
        allComplaintsjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allComplaintsjTableMouseClicked(evt);
            }
        });
        allComplaintsjScrollPane.setViewportView(allComplaintsjTable);

        javax.swing.GroupLayout UsertListContainerjPanel1Layout = new javax.swing.GroupLayout(UsertListContainerjPanel1);
        UsertListContainerjPanel1.setLayout(UsertListContainerjPanel1Layout);
        UsertListContainerjPanel1Layout.setHorizontalGroup(
            UsertListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allComplaintsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
            .addGroup(UsertListContainerjPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(UsertListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allComplaintsjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(allComplaintsRefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        UsertListContainerjPanel1Layout.setVerticalGroup(
            UsertListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsertListContainerjPanel1Layout.createSequentialGroup()
                .addGroup(UsertListContainerjPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UsertListContainerjPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(allComplaintsRefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(UsertListContainerjPanel1Layout.createSequentialGroup()
                        .addComponent(allComplaintsjLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(allComplaintsjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ComplaintsUItempjPanel.add(UsertListContainerjPanel1);

        RightBottomContentjPanel.add(ComplaintsUItempjPanel);

        AddPlanesUIjPanel.setBackground(new java.awt.Color(153, 153, 255));
        AddPlanesUIjPanel.setLayout(new java.awt.GridLayout(2, 1));

        AddPlanContainerjPanel.setBackground(new java.awt.Color(125, 159, 154));
        AddPlanContainerjPanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel6.setBackground(new java.awt.Color(125, 159, 154));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 51));
        jLabel3.setText("Plan Name");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 140, 40));

        planNamejTextField.setBackground(new java.awt.Color(125, 159, 154));
        planNamejTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        planNamejTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planNamejTextFieldActionPerformed(evt);
            }
        });
        jPanel6.add(planNamejTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 160, 30));

        AddPlanContainerjPanel.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(125, 159, 154));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        planDescriptionjTextField.setBackground(new java.awt.Color(125, 159, 154));
        planDescriptionjTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel7.add(planDescriptionjTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 260, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 0, 51));
        jLabel12.setText("Plan Description");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 100, 40));

        AddPlanContainerjPanel.add(jPanel7);

        jPanel3.setBackground(new java.awt.Color(125, 159, 154));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(125, 159, 154));
        jPanel11.setPreferredSize(new java.awt.Dimension(303, 70));

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(addPlanjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(addPlanjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jPanel10.setBackground(new java.awt.Color(125, 159, 154));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        planAmtjTextField.setBackground(new java.awt.Color(125, 159, 154));
        planAmtjTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel10.add(planAmtjTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 210, 30));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 51));
        jLabel13.setText("Amount");
        jPanel10.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 100, 40));

        jPanel3.add(jPanel10, java.awt.BorderLayout.CENTER);

        AddPlanContainerjPanel.add(jPanel3);

        AddPlanesUIjPanel.add(AddPlanContainerjPanel);

        plansShowjPanel.setBackground(new java.awt.Color(125, 159, 154));
        plansShowjPanel.setLayout(new java.awt.BorderLayout());

        jPanel12.setPreferredSize(new java.awt.Dimension(910, 50));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(125, 159, 154));
        jPanel13.setPreferredSize(new java.awt.Dimension(300, 58));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        refreshPlanOnTablejButton.setBackground(new java.awt.Color(51, 204, 255));
        refreshPlanOnTablejButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        refreshPlanOnTablejButton.setText("Refresh");
        refreshPlanOnTablejButton.setBorder(null);
        refreshPlanOnTablejButton.setFocusable(false);
        refreshPlanOnTablejButton.setPreferredSize(new java.awt.Dimension(50, 20));
        refreshPlanOnTablejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPlanOnTablejButtonActionPerformed(evt);
            }
        });
        jPanel13.add(refreshPlanOnTablejButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 100, 30));

        jPanel12.add(jPanel13, java.awt.BorderLayout.LINE_END);

        jPanel14.setBackground(new java.awt.Color(125, 159, 154));
        jPanel14.setPreferredSize(new java.awt.Dimension(700, 50));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Palatino Linotype", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(222, 210, 181));
        jLabel5.setText("Subscription Plans Available");
        jPanel14.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 380, 50));
        jPanel14.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 32, 610, 10));

        jPanel12.add(jPanel14, java.awt.BorderLayout.CENTER);

        plansShowjPanel.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        planTablejScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        planTablejScrollPane.setBorder(null);

        planjShowjTable.setAutoCreateRowSorter(true);
        planjShowjTable.setBackground(new java.awt.Color(226, 220, 220));
        planjShowjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        planjShowjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Subscription ID", "Subscription Plan Name", "Subscription Description", "Subscription Amount"
            }
        ));
        planjShowjTable.setRowHeight(32);
        planjShowjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        planjShowjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        planjShowjTable.setShowGrid(false);
        planjShowjTable.setShowVerticalLines(true);
        planTablejScrollPane.setViewportView(planjShowjTable);

        plansShowjPanel.add(planTablejScrollPane, java.awt.BorderLayout.CENTER);

        AddPlanesUIjPanel.add(plansShowjPanel);

        RightBottomContentjPanel.add(AddPlanesUIjPanel);

        PendingRequestUIjPanel.setBackground(new java.awt.Color(153, 153, 255));
        PendingRequestUIjPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel15.setBackground(new java.awt.Color(125, 159, 154));

        jLabel7.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(222, 210, 181));
        jLabel7.setText("Requests from users");

        requestShowjTableScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        requestShowjTableScrollPane.setBorder(null);
        requestShowjTableScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestShowjTableScrollPaneMouseClicked(evt);
            }
        });

        requestShowjTable.setAutoCreateRowSorter(true);
        requestShowjTable.setBackground(new java.awt.Color(226, 220, 220));
        requestShowjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        requestShowjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null}
            },
            new String [] {
                " Request ID", " User","Email ID", " Plan Name", "Grant/Reject"
            }
        ));
        requestShowjTable.setRowHeight(32);
        requestShowjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        requestShowjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        requestShowjTable.setShowHorizontalLines(false);
        requestShowjTable.getTableHeader().setReorderingAllowed(false);
        requestShowjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestShowjTableMouseClicked(evt);
            }
        });
        requestShowjTableScrollPane.setViewportView(requestShowjTable);

        jButton1.setBackground(new java.awt.Color(51, 204, 255));
        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.setBorder(null);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(requestShowjTableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(408, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(requestShowjTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PendingRequestUIjPanel.add(jPanel15);

        RightBottomContentjPanel.add(PendingRequestUIjPanel);

        ManageUserUIjPanel.setBackground(new java.awt.Color(125, 159, 154));
        ManageUserUIjPanel.setLayout(new java.awt.GridLayout(2, 1));

        billListContainerjPanel.setBackground(new java.awt.Color(125, 159, 154));

        billListjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        billListjLabel.setForeground(new java.awt.Color(222, 210, 181));
        billListjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        billListjLabel.setText("Bill List");

        billListjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        billListjScrollPane.setBorder(null);

        billListjTable.setBackground(new java.awt.Color(226, 220, 220));
        billListjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        billListjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null},
                {null, null, null,null, null}
            },
            new String [] {
                "User Name", " Subscription"," Status", "Date Isuue", "Due Date"
            }
        ));
        billListjTable.setRowHeight(32);
        billListjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        billListjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        billListjTable.setShowHorizontalLines(false);
        billListjScrollPane.setViewportView(billListjTable);

        billListrefreshjButton.setBackground(new java.awt.Color(51, 204, 255));
        billListrefreshjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        billListrefreshjButton.setText("Refresh");
        billListrefreshjButton.setBorder(null);
        billListrefreshjButton.setFocusable(false);
        billListrefreshjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billListrefreshjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout billListContainerjPanelLayout = new javax.swing.GroupLayout(billListContainerjPanel);
        billListContainerjPanel.setLayout(billListContainerjPanelLayout);
        billListContainerjPanelLayout.setHorizontalGroup(
            billListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billListContainerjPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(billListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billListjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(billListrefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addComponent(billListjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        billListContainerjPanelLayout.setVerticalGroup(
            billListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billListContainerjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(billListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billListjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billListrefreshjButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billListjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ManageUserUIjPanel.add(billListContainerjPanel);

        UsertListContainerjPanel.setBackground(new java.awt.Color(125, 159, 154));

        userListjLabel.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        userListjLabel.setForeground(new java.awt.Color(222, 210, 181));
        userListjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userListjLabel.setText("User List");

        userListRefreshjButton.setBackground(new java.awt.Color(51, 204, 255));
        userListRefreshjButton.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        userListRefreshjButton.setText("Refresh");
        userListRefreshjButton.setBorder(null);
        userListRefreshjButton.setFocusable(false);
        userListRefreshjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userListRefreshjButtonActionPerformed(evt);
            }
        });

        userListjScrollPane.setBackground(new java.awt.Color(125, 159, 154));
        userListjScrollPane.setBorder(null);

        UserListjTable.setBackground(new java.awt.Color(226, 220, 220));
        UserListjTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        UserListjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,null, null, null, null},
                {null, null, null,null, null, null, null},
                {null, null, null,null, null, null, null},
                {null, null, null,null, null, null, null}
            },
            new String [] {
                "User ID", "User Name"," Email", "Address", "Subscribed Plan", "Cleared Bill", "Net Amount(Rs)"
            }
        ));
        UserListjTable.setRowHeight(32);
        UserListjTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        UserListjTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        UserListjTable.setShowHorizontalLines(false);
        UserListjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserListjTableMouseClicked(evt);
            }
        });
        userListjScrollPane.setViewportView(UserListjTable);

        javax.swing.GroupLayout UsertListContainerjPanelLayout = new javax.swing.GroupLayout(UsertListContainerjPanel);
        UsertListContainerjPanel.setLayout(UsertListContainerjPanelLayout);
        UsertListContainerjPanelLayout.setHorizontalGroup(
            UsertListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsertListContainerjPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(UsertListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userListjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userListRefreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addComponent(userListjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        UsertListContainerjPanelLayout.setVerticalGroup(
            UsertListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsertListContainerjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UsertListContainerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userListjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userListRefreshjButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userListjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ManageUserUIjPanel.add(UsertListContainerjPanel);

        RightBottomContentjPanel.add(ManageUserUIjPanel);

        tempjPanel.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout tempjPanelLayout = new javax.swing.GroupLayout(tempjPanel);
        tempjPanel.setLayout(tempjPanelLayout);
        tempjPanelLayout.setHorizontalGroup(
            tempjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        tempjPanelLayout.setVerticalGroup(
            tempjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        RightBottomContentjPanel.add(tempjPanel);

        jPanel5.add(RightBottomContentjPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void manageUserJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageUserJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(manageUserJPanel, manageUserTextjLabel);
        
//        Displaying user and bill list when user oprn this tab
        displayBillListInTable();
        displayUserListInTable();
        
        showThisPane(ManageUserUIjPanel);
    }//GEN-LAST:event_manageUserJPanelMouseClicked

    private void pendingRequestJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingRequestJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(pendingRequestJPanel, pendingRequestTextjLabel);
        
        showThisPane(PendingRequestUIjPanel);
        
    }//GEN-LAST:event_pendingRequestJPanelMouseClicked

    private void addPlanJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlanJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(addPlanJPanel, addPlanTextjLabel);
        
        displayPlanInTable();
//        Showing Add Plans panel on Righ side windows
        showThisPane(AddPlanesUIjPanel);
    }//GEN-LAST:event_addPlanJPanelMouseClicked

    private void adminComplaintsJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminComplaintsJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(adminComplaintsJPanel, adminComplaintsTextjLabel);
        
        showThisPane(ComplaintsUItempjPanel);
        displayPendingComplaintsInTable(1);
        displayPendingComplaintsInTable(0);
    }//GEN-LAST:event_adminComplaintsJPanelMouseClicked

    private void addPlanJPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlanJPanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_addPlanJPanelMouseEntered

    private void adminLogoutjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminLogoutjButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        new LoginFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_adminLogoutjButtonActionPerformed

    private void refreshPlanOnTablejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPlanOnTablejButtonActionPerformed
        //         When refresh button clicked updated table content showed
        displayPlanInTable();
    }//GEN-LAST:event_refreshPlanOnTablejButtonActionPerformed

    private void addPlanjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPlanjButtonActionPerformed
        // TODO add your handling code here:
        addDataToPlanTable();
        displayPlanInTable();
    }//GEN-LAST:event_addPlanjButtonActionPerformed

    private void addPlanjButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlanjButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addPlanjButtonMouseClicked

    private void planNamejTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planNamejTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_planNamejTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        displayRequestInTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void requestShowjTableScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestShowjTableScrollPaneMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_requestShowjTableScrollPaneMouseClicked

    private void requestShowjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestShowjTableMouseClicked
        // TODO add your handling code here:
        
        int choice = JOptionPane.showConfirmDialog(this, "Grant This Request ?"," Confirmation", JOptionPane.YES_NO_OPTION );
        System.out.println(" Selected " + choice);
        
//        choice 0 means we will Grant request and generate bill.
        if( choice == 0){
//            To get selected row So we can get Request ID
              int rowIndex = requestShowjTable.getSelectedRow();
              int requestID =Integer.parseInt( requestShowjTable.getModel().getValueAt(rowIndex, 0).toString() );
              
              String dueDate = JOptionPane.showInputDialog("Enter Due date in YYYY/MM/DD format");
              System.out.println(" Due date : " + dueDate);
              
              try{
                    if( ( dueDate.isEmpty() ) || ( dueDate == null ) ){
                        System.out.println(" Not valid date");
                    }
                    else {
                        
                        generateBill(requestID, dueDate);
                    }
              }
              
              catch(Exception ex){
                  System.out.println(" Error : in pending Mouse Listener : " + ex);
              }
              
        }
    }//GEN-LAST:event_requestShowjTableMouseClicked

    private void billListrefreshjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billListrefreshjButtonActionPerformed
        // TODO add your handling code here:
        displayBillListInTable();
    }//GEN-LAST:event_billListrefreshjButtonActionPerformed

    private void userListRefreshjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userListRefreshjButtonActionPerformed
        // TODO add your handling code here:
        displayUserListInTable();
    }//GEN-LAST:event_userListRefreshjButtonActionPerformed

    private void UserListjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserListjTableMouseClicked
        // TODO add your handling code here:
        
         int choice = JOptionPane.showConfirmDialog(this, "Do You Want T Delete This User  ?"," Confirmation", JOptionPane.YES_NO_OPTION );
        System.out.println(" Selected " + choice);
        
//        choice 0 means YES. we will Delete request and generate bill.
        if( choice == 0){
//            To get selected row So we can get Request ID
              int rowIndex = UserListjTable.getSelectedRow();
              int userID =Integer.parseInt( UserListjTable.getModel().getValueAt(rowIndex, 0).toString() );
              
              System.out.println(" row and column" + rowIndex + " userId " + userID);
              
              deleteUser(userID);
        }
    }//GEN-LAST:event_UserListjTableMouseClicked

    private void pendingComplaintsRefreshjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendingComplaintsRefreshjButton1ActionPerformed
        // TODO add your handling code here:
        displayPendingComplaintsInTable(1);
    }//GEN-LAST:event_pendingComplaintsRefreshjButton1ActionPerformed

    private void allComplaintsRefreshjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allComplaintsRefreshjButtonActionPerformed
        // TODO add your handling code here:
        displayPendingComplaintsInTable(0);
    }//GEN-LAST:event_allComplaintsRefreshjButtonActionPerformed

    private void allComplaintsjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allComplaintsjTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allComplaintsjTableMouseClicked

    private void pendingComplaitsjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingComplaitsjTableMouseClicked
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Is Issue Solved  ?"," Confirmation", JOptionPane.YES_NO_OPTION );
        System.out.println(" Selected " + choice);
        
//        choice 0 means YES. we will Delete request and generate bill.
        if( choice == 0){
//            To get selected row So we can get Request ID
              int rowIndex = pendingComplaitsjTable.getSelectedRow();
              int complaintID =Integer.parseInt( pendingComplaitsjTable.getModel().getValueAt(rowIndex, 0).toString() );
              
              System.out.println(" row and column" + rowIndex + " userId " + complaintID);
              
              updatePendingComplaintsInTable(complaintID);
        }
    }//GEN-LAST:event_pendingComplaitsjTableMouseClicked

    private void adminChangeCredentialJPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminChangeCredentialJPanelMouseClicked
        // TODO add your handling code here:
        changeTOBtnDefaultColor();
        highlightClicked(adminChangeCredentialJPanel, adminChangeCredentialTextjLabel);
        
        adminNameInProfilejLabel.setText(adminName);
        showThisPane(AdminChangeCredentialjPanel);
    }//GEN-LAST:event_adminChangeCredentialJPanelMouseClicked

    private void updatePasswordjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePasswordjButtonActionPerformed
        // TODO add your handling code here:
        int valid = SignupFrame.validatePassword(newAdminjPasswordField, confirmAdminjPasswordField);
        if( valid ==1){
            String password = String.valueOf(newAdminjPasswordField.getPassword());
            updateAdminPassword(password);
        }
    }//GEN-LAST:event_updatePasswordjButtonActionPerformed

    private void confirmAdminjPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmAdminjPasswordFieldFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_confirmAdminjPasswordFieldFocusLost

    private void confirmAdminjPasswordFieldMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmAdminjPasswordFieldMouseExited
        // TODO add your handling code here:
        
    }//GEN-LAST:event_confirmAdminjPasswordFieldMouseExited

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMainUI().setVisible(true);
            }
        });
    }
//    new jTable header for planShowTable in Addf plan Tab
    private javax.swing.table.JTableHeader planShowtableHeader;
    private javax.swing.table.JTableHeader requestShowtableHeader;
    private javax.swing.table.JTableHeader billListtableHeader;
    private javax.swing.table.JTableHeader userListtableHeader;
    private javax.swing.table.JTableHeader pendingComplaintstableHeader;
    private javax.swing.table.JTableHeader allComplaintstableHeader;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddPlanContainerjPanel;
    private javax.swing.JPanel AddPlanesUIjPanel;
    private javax.swing.JPanel AdminChangeCredentialjPanel;
    private javax.swing.JPanel ComplaintsUItempjPanel;
    private javax.swing.JPanel ManageUserUIjPanel;
    private javax.swing.JPanel PendingRequestUIjPanel;
    private javax.swing.JPanel RightBottomContentjPanel;
    private javax.swing.JTable UserListjTable;
    private javax.swing.JPanel UsertListContainerjPanel;
    private javax.swing.JPanel UsertListContainerjPanel1;
    private javax.swing.JPanel addPlanJPanel;
    private javax.swing.JLabel addPlanTextjLabel;
    private javax.swing.JButton addPlanjButton;
    private javax.swing.JPanel adminChangeCredentialJPanel;
    private javax.swing.JLabel adminChangeCredentialTextjLabel;
    private javax.swing.JPanel adminComplaintsJPanel;
    private javax.swing.JLabel adminComplaintsTextjLabel;
    private javax.swing.JButton adminLogoutjButton;
    private javax.swing.JLabel adminNameInProfilejLabel;
    public javax.swing.JLabel adminNamejLabel;
    private javax.swing.JLabel adminProfilejLabel;
    private javax.swing.JButton allComplaintsRefreshjButton;
    private javax.swing.JLabel allComplaintsjLabel;
    private javax.swing.JScrollPane allComplaintsjScrollPane;
    private javax.swing.JTable allComplaintsjTable;
    private javax.swing.JPanel billListContainerjPanel;
    private javax.swing.JPanel billListContainerjPanel1;
    private javax.swing.JLabel billListjLabel;
    private javax.swing.JLabel billListjLabel1;
    private javax.swing.JScrollPane billListjScrollPane;
    private javax.swing.JTable billListjTable;
    private javax.swing.JButton billListrefreshjButton;
    private javax.swing.JPasswordField confirmAdminjPasswordField;
    private javax.swing.JLabel headingOfPagejLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel manageUserJPanel;
    private javax.swing.JLabel manageUserTextjLabel;
    private javax.swing.JPasswordField newAdminjPasswordField;
    private javax.swing.JButton pendingComplaintsRefreshjButton1;
    private javax.swing.JScrollPane pendingComplaitsjScrollPane;
    private javax.swing.JTable pendingComplaitsjTable;
    private javax.swing.JPanel pendingRequestJPanel;
    private javax.swing.JLabel pendingRequestTextjLabel;
    private javax.swing.JTextField planAmtjTextField;
    private javax.swing.JTextField planDescriptionjTextField;
    private javax.swing.JTextField planNamejTextField;
    private javax.swing.JScrollPane planTablejScrollPane;
    private javax.swing.JTable planjShowjTable;
    private javax.swing.JPanel plansShowjPanel;
    private javax.swing.JButton refreshPlanOnTablejButton;
    private javax.swing.JTable requestShowjTable;
    private javax.swing.JScrollPane requestShowjTableScrollPane;
    private javax.swing.JPanel tempjPanel;
    private javax.swing.JButton updatePasswordjButton;
    private javax.swing.JButton userListRefreshjButton;
    private javax.swing.JLabel userListjLabel;
    private javax.swing.JScrollPane userListjScrollPane;
    // End of variables declaration//GEN-END:variables
}
