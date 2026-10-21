import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Account extends javax.swing.JFrame {
Connection conn;
ResultSet rs;
PreparedStatement pst;
    /**
     * Creates new form Account
     */

    // constructor for Account
    public Account() {
        super("Create Account");
        initComponents();
        conn = dbconnect.connectDb();
        randomAcc();
        
        randomPin();
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        AccountPage = new javax.swing.JPanel();
        AccnumtxtAP = new javax.swing.JLabel();
        Female = new javax.swing.JRadioButton();
        AddresstxtAP = new javax.swing.JLabel();
        Male = new javax.swing.JRadioButton();
        Other = new javax.swing.JRadioButton();
        Create = new javax.swing.JButton();
        LogoAP = new javax.swing.JLabel();
        DateofbirthAP = new com.toedter.calendar.JDateChooser();
        AnswerAP = new javax.swing.JTextField();
        FullnameAP = new javax.swing.JTextField();
        ClearAP = new javax.swing.JButton();
        DateofbirthtxtAP = new javax.swing.JLabel();
        AcctypetxtAP = new javax.swing.JLabel();
        SecurityqtxtAP = new javax.swing.JLabel();
        EthnicitytxtAP = new javax.swing.JLabel();
        MobileAP = new javax.swing.JTextField();
        MobiletxtAP = new javax.swing.JLabel();
        AnswertxtAP = new javax.swing.JLabel();
        GendertxtAP = new javax.swing.JLabel();
        AddressAP = new javax.swing.JTextField();
        PintxtAP = new javax.swing.JLabel();
        FullnametxtAP = new javax.swing.JLabel();
        EthnicityAP = new javax.swing.JComboBox<>();
        Back = new javax.swing.JButton();
        AcctypeAP = new javax.swing.JComboBox<>();
        AccnumAP = new javax.swing.JTextField();
        PinAP = new javax.swing.JTextField();
        NewacctxtAP = new javax.swing.JLabel();
        AmounttxtAP = new javax.swing.JLabel();
        AmountxtAP = new javax.swing.JTextField();
        SecurityqAP = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        AccountPage.setBackground(new java.awt.Color(0, 0, 0));
        AccountPage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 33, 57), 3));

        AccnumtxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AccnumtxtAP.setForeground(new java.awt.Color(255, 255, 255));
        AccnumtxtAP.setText("Account Number:");

        Female.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(Female);
        Female.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Female.setForeground(new java.awt.Color(255, 255, 255));
        Female.setText("Female");

        AddresstxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AddresstxtAP.setForeground(new java.awt.Color(255, 255, 255));
        AddresstxtAP.setText("Address:");

        Male.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(Male);
        Male.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Male.setForeground(new java.awt.Color(255, 255, 255));
        Male.setText("Male ");
        Male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaleActionPerformed(evt);
            }
        });

        Other.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(Other);
        Other.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Other.setForeground(new java.awt.Color(255, 255, 255));
        Other.setText("Other");

        Create.setBackground(new java.awt.Color(51, 51, 51));
        Create.setFont(new java.awt.Font("Lucida Handwriting", 0, 12)); // NOI18N
        Create.setForeground(new java.awt.Color(153, 255, 0));
        Create.setText("Create");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        LogoAP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoAP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BankingImages/bl_1_35.png"))); // NOI18N

        FullnameAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullnameAPActionPerformed(evt);
            }
        });

        ClearAP.setBackground(new java.awt.Color(51, 51, 51));
        ClearAP.setFont(new java.awt.Font("Lucida Handwriting", 0, 12)); // NOI18N
        ClearAP.setForeground(new java.awt.Color(153, 255, 0));
        ClearAP.setText("Clear");
        ClearAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearAPActionPerformed(evt);
            }
        });

        DateofbirthtxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DateofbirthtxtAP.setForeground(new java.awt.Color(255, 255, 255));
        DateofbirthtxtAP.setText("Date of birth:");

        AcctypetxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AcctypetxtAP.setForeground(new java.awt.Color(255, 255, 255));
        AcctypetxtAP.setText("Account type:");

        SecurityqtxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SecurityqtxtAP.setForeground(new java.awt.Color(255, 255, 255));
        SecurityqtxtAP.setText("Security Question:");

        EthnicitytxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        EthnicitytxtAP.setForeground(new java.awt.Color(255, 255, 255));
        EthnicitytxtAP.setText("Ethnicity:");

        MobiletxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MobiletxtAP.setForeground(new java.awt.Color(255, 255, 255));
        MobiletxtAP.setText("Mobile:");

        AnswertxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AnswertxtAP.setForeground(new java.awt.Color(255, 255, 255));
        AnswertxtAP.setText("Answer:");

        GendertxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        GendertxtAP.setForeground(new java.awt.Color(255, 255, 255));
        GendertxtAP.setText("Gender:");

        PintxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PintxtAP.setForeground(new java.awt.Color(255, 255, 255));
        PintxtAP.setText("PIN:");

        FullnametxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FullnametxtAP.setForeground(new java.awt.Color(255, 255, 255));
        FullnametxtAP.setText("Name:");

        EthnicityAP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EthnicityAP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "European", "Asian", "Pacific", "Pakistani", "Other" }));

        Back.setBackground(new java.awt.Color(51, 51, 51));
        Back.setFont(new java.awt.Font("Lucida Handwriting", 0, 12)); // NOI18N
        Back.setForeground(new java.awt.Color(153, 255, 0));
        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        AcctypeAP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AcctypeAP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Checking", "Savings", " ", " " }));

        AccnumAP.setEditable(false);
        AccnumAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccnumAPActionPerformed(evt);
            }
        });

        PinAP.setEditable(false);
        PinAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PinAPActionPerformed(evt);
            }
        });

        NewacctxtAP.setBackground(new java.awt.Color(255, 255, 255));
        NewacctxtAP.setFont(new java.awt.Font("Lucida Handwriting", 1, 18)); // NOI18N
        NewacctxtAP.setForeground(new java.awt.Color(255, 255, 255));
        NewacctxtAP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NewacctxtAP.setText("New Account Registration");
        NewacctxtAP.setRequestFocusEnabled(false);

        AmounttxtAP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AmounttxtAP.setForeground(new java.awt.Color(255, 255, 255));
        AmounttxtAP.setText("Amount:");

        SecurityqAP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SecurityqAP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "What is the name of your first pet?", "What is the name of your mother?", "What is the name of your favouite teacher?", "What is your nickname?" }));

        javax.swing.GroupLayout AccountPageLayout = new javax.swing.GroupLayout(AccountPage);
        AccountPage.setLayout(AccountPageLayout);
        AccountPageLayout.setHorizontalGroup(
            AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountPageLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AccountPageLayout.createSequentialGroup()
                        .addComponent(AccnumtxtAP)
                        .addGap(18, 18, 18)
                        .addComponent(AccnumAP, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(AccountPageLayout.createSequentialGroup()
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AccountPageLayout.createSequentialGroup()
                                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(PintxtAP)
                                        .addComponent(AcctypetxtAP)
                                        .addComponent(GendertxtAP)
                                        .addComponent(AddresstxtAP))
                                    .addComponent(AmounttxtAP))
                                .addGap(18, 18, 18)
                                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AccountPageLayout.createSequentialGroup()
                                        .addComponent(Male)
                                        .addGap(18, 18, 18)
                                        .addComponent(Female)
                                        .addGap(18, 18, 18)
                                        .addComponent(Other))
                                    .addComponent(AddressAP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AcctypeAP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PinAP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AmountxtAP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AccountPageLayout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AccountPageLayout.createSequentialGroup()
                                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(DateofbirthtxtAP)
                                            .addComponent(FullnametxtAP, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(EthnicitytxtAP)
                                            .addComponent(MobiletxtAP)
                                            .addComponent(AnswertxtAP))
                                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(AccountPageLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(FullnameAP)
                                                    .addComponent(DateofbirthAP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(MobileAP)
                                                    .addComponent(EthnicityAP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(AccountPageLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(AnswerAP))))
                                    .addGroup(AccountPageLayout.createSequentialGroup()
                                        .addComponent(SecurityqtxtAP)
                                        .addGap(18, 18, 18)
                                        .addComponent(SecurityqAP, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(85, 85, 85))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountPageLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ClearAP, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewacctxtAP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LogoAP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        AccountPageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DateofbirthtxtAP, EthnicitytxtAP, MobiletxtAP, SecurityqtxtAP});

        AccountPageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AccnumtxtAP, AcctypetxtAP, AddresstxtAP, GendertxtAP, PintxtAP});

        AccountPageLayout.setVerticalGroup(
            AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LogoAP, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NewacctxtAP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AccountPageLayout.createSequentialGroup()
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AccnumtxtAP)
                            .addComponent(AccnumAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PintxtAP)
                            .addComponent(PinAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AcctypetxtAP)
                            .addComponent(AcctypeAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GendertxtAP)
                            .addComponent(Male)
                            .addComponent(Female)
                            .addComponent(Other))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddresstxtAP)
                            .addComponent(AddressAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AmounttxtAP)
                            .addComponent(AmountxtAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Create)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AccountPageLayout.createSequentialGroup()
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FullnametxtAP)
                            .addComponent(FullnameAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DateofbirthtxtAP)
                            .addComponent(DateofbirthAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EthnicitytxtAP)
                            .addComponent(EthnicityAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MobileAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MobiletxtAP))
                        .addGap(18, 18, 18)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SecurityqtxtAP)
                            .addComponent(SecurityqAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(AccountPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AnswertxtAP)
                            .addComponent(AnswerAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(ClearAP)
                        .addGap(39, 39, 39)
                        .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );

        AccountPageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AccnumAP, AccnumtxtAP, AcctypeAP, AcctypetxtAP, AddressAP, AddresstxtAP, DateofbirthtxtAP, EthnicitytxtAP, Female, FullnametxtAP, GendertxtAP, Male, MobiletxtAP, Other, PinAP, PintxtAP, SecurityqtxtAP});

        AccountPageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AnswerAP, DateofbirthAP, EthnicityAP, FullnameAP, MobileAP});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AccountPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AccountPage, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1070, 621));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    //generate a random account number between 1 and 10000
    public void randomAcc(){
        Random ra = new Random();
        AccnumAP.setText(""+ra.nextInt(10000+1));
    }
    //generate a random MICR number between 1 and 1000
    
    //generate a random pin number between 1 and 1000
    public void randomPin(){
        Random ra = new Random();
        PinAP.setText(""+ra.nextInt(1000+1));
    }
    
    
    public void Bal(){
        String sql = "insert into Balances(Name,Acc,MICR_No,Balance)values (?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1,FullnameAP.getText());
            pst.setString(2,AccnumAP.getText());
            
            pst.setString(4,AmountxtAP.getText());
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
    private void PinAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PinAPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PinAPActionPerformed

    private void MaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaleActionPerformed
//Create a bank account
    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        // TODO add your handling code here:
        String sql = "insert into Account(Acc,Name,DOB,Pin,Acc_Type,Ethnicity,MICR_No,Gender,Mob,Address,Sec_Q,Sec_A,Balance) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, AccnumAP.getText());
            pst.setString(2, FullnameAP.getText());
            pst.setString(3, ((JTextField)DateofbirthAP.getDateEditor().getUiComponent()).getText());
            pst.setString(4, PinAP.getText());
            pst.setString(5, (String) AcctypeAP.getSelectedItem());
            pst.setString(6, (String) EthnicityAP.getSelectedItem());
            
            
            Male.setActionCommand("Male");
            Female.setActionCommand("Female");
            Other.setActionCommand("Other");
            pst.setString(8, buttonGroup1.getSelection().getActionCommand());
            pst.setString(9, MobileAP.getText());
            pst.setString(10, AddressAP.getText());
            pst.setString(11, (String) SecurityqAP.getSelectedItem());
            pst.setString(12, AnswerAP.getText());
            pst.setString(13, AmountxtAP.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Success! Account has been created");
            Bal();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
    }//GEN-LAST:event_CreateActionPerformed

    private void FullnameAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullnameAPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FullnameAPActionPerformed
//go back to login screen
    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Authentication ob = new Authentication();
        ob.setVisible(true);
    }//GEN-LAST:event_BackActionPerformed
//clearing fields
    private void ClearAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearAPActionPerformed
        // TODO add your handling code here:
        AccnumAP.setText("");
        
        PinAP.setText("");
        AddressAP.setText("");
        FullnameAP.setText("");
        AmountxtAP.setText("");
        MobileAP.setText("");
        
        AnswerAP.setText("");
        
        
    }//GEN-LAST:event_ClearAPActionPerformed

    private void AccnumAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccnumAPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AccnumAPActionPerformed
    
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
            java.util.logging.Logger.getLogger(Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Account().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AccnumAP;
    private javax.swing.JLabel AccnumtxtAP;
    private javax.swing.JPanel AccountPage;
    private javax.swing.JComboBox<String> AcctypeAP;
    private javax.swing.JLabel AcctypetxtAP;
    private javax.swing.JTextField AddressAP;
    private javax.swing.JLabel AddresstxtAP;
    private javax.swing.JLabel AmounttxtAP;
    private javax.swing.JTextField AmountxtAP;
    private javax.swing.JTextField AnswerAP;
    private javax.swing.JLabel AnswertxtAP;
    private javax.swing.JButton Back;
    private javax.swing.JButton ClearAP;
    private javax.swing.JButton Create;
    private com.toedter.calendar.JDateChooser DateofbirthAP;
    private javax.swing.JLabel DateofbirthtxtAP;
    private javax.swing.JComboBox<String> EthnicityAP;
    private javax.swing.JLabel EthnicitytxtAP;
    private javax.swing.JRadioButton Female;
    private javax.swing.JTextField FullnameAP;
    private javax.swing.JLabel FullnametxtAP;
    private javax.swing.JLabel GendertxtAP;
    private javax.swing.JLabel LogoAP;
    private javax.swing.JRadioButton Male;
    private javax.swing.JTextField MobileAP;
    private javax.swing.JLabel MobiletxtAP;
    private javax.swing.JLabel NewacctxtAP;
    private javax.swing.JRadioButton Other;
    private javax.swing.JTextField PinAP;
    private javax.swing.JLabel PintxtAP;
    private javax.swing.JComboBox<String> SecurityqAP;
    private javax.swing.JLabel SecurityqtxtAP;
    private javax.swing.ButtonGroup buttonGroup1;
    // End of variables declaration//GEN-END:variables
}
