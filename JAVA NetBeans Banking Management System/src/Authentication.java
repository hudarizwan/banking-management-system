import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;



public class Authentication extends javax.swing.JFrame {
Connection conn;
ResultSet rs;
PreparedStatement pst;
    /**
     * Creates new form Authentication
     */
    // constructor for authentication
    public Authentication() {
        super("Login");
        initComponents();
        conn = dbconnect.connectDb();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginPage = new javax.swing.JPanel();
        AccnumLP = new javax.swing.JTextField();
        PinLP = new javax.swing.JTextField();
        PintxtLP = new javax.swing.JLabel();
        AccnumtxtLP = new javax.swing.JLabel();
        Logon = new javax.swing.JButton();
        LogoLP = new javax.swing.JLabel();
        NewAccount = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        LoginPage.setBackground(new java.awt.Color(0, 0, 0));
        LoginPage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(11, 56, 95), 3, true));
        LoginPage.setAutoscrolls(true);

        AccnumLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccnumLPActionPerformed(evt);
            }
        });

        PintxtLP.setBackground(new java.awt.Color(204, 255, 204));
        PintxtLP.setFont(new java.awt.Font("Lucida Handwriting", 1, 18)); // NOI18N
        PintxtLP.setForeground(new java.awt.Color(255, 255, 255));
        PintxtLP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PintxtLP.setText("PIN:");

        AccnumtxtLP.setBackground(new java.awt.Color(255, 255, 255));
        AccnumtxtLP.setFont(new java.awt.Font("Lucida Handwriting", 1, 18)); // NOI18N
        AccnumtxtLP.setForeground(new java.awt.Color(255, 255, 255));
        AccnumtxtLP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AccnumtxtLP.setText("Account Number:");

        Logon.setBackground(new java.awt.Color(51, 51, 51));
        Logon.setFont(new java.awt.Font("Lucida Handwriting", 0, 14)); // NOI18N
        Logon.setForeground(new java.awt.Color(153, 255, 51));
        Logon.setText("login...");
        Logon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogonActionPerformed(evt);
            }
        });

        LogoLP.setBackground(new java.awt.Color(200, 200, 200));
        LogoLP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BankingImages/bl_60.png"))); // NOI18N

        NewAccount.setBackground(new java.awt.Color(51, 51, 51));
        NewAccount.setFont(new java.awt.Font("Lucida Handwriting", 0, 14)); // NOI18N
        NewAccount.setForeground(new java.awt.Color(153, 255, 51));
        NewAccount.setText("New Account");
        NewAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewAccountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoginPageLayout = new javax.swing.GroupLayout(LoginPage);
        LoginPage.setLayout(LoginPageLayout);
        LoginPageLayout.setHorizontalGroup(
            LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginPageLayout.createSequentialGroup()
                        .addComponent(AccnumtxtLP)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPageLayout.createSequentialGroup()
                        .addComponent(PintxtLP)
                        .addGap(41, 41, 41)))
                .addGroup(LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AccnumLP, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PinLP, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPageLayout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(LogoLP)
                .addGap(71, 71, 71))
            .addGroup(LoginPageLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(NewAccount)
                .addContainerGap())
        );
        LoginPageLayout.setVerticalGroup(
            LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LogoLP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(NewAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AccnumtxtLP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccnumLP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(LoginPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PintxtLP)
                    .addComponent(PinLP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(Logon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AccnumLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccnumLPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AccnumLPActionPerformed
// create new account 
    private void NewAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewAccountActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Account ob = new Account();
        ob.setVisible(true);
    }//GEN-LAST:event_NewAccountActionPerformed
// authenticate user details with database
    private void LogonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogonActionPerformed
        // TODO add your handling code here:
        String sql = "select * from Account where Acc=? and Pin=?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, AccnumLP.getText());
            pst.setString(2, PinLP.getText());
            rs=pst.executeQuery();
            if(rs.next()){
                setVisible(false);
                Loading ob = new Loading();
                ob.setUpLoading();
                ob.setVisible(true);
                rs.close();
                pst.close();
            }else{
                JOptionPane.showMessageDialog(null,"Incorrect login");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }finally{
            try{
                rs.close();
                pst.close();
               
            }catch(Exception e){
                
            }
        }
        
    }//GEN-LAST:event_LogonActionPerformed

    /**
     * @param args 
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                /*if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }*/
                UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
            
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Authentication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AccnumLP;
    private javax.swing.JLabel AccnumtxtLP;
    private javax.swing.JPanel LoginPage;
    private javax.swing.JLabel LogoLP;
    private javax.swing.JButton Logon;
    private javax.swing.JButton NewAccount;
    private javax.swing.JTextField PinLP;
    private javax.swing.JLabel PintxtLP;
    // End of variables declaration//GEN-END:variables
}
