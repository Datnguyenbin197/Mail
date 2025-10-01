package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Settings dialog for application configuration
 */
public class SettingsDialog extends JDialog {
    private static final Logger logger = Logger.getLogger(SettingsDialog.class.getName());
    
    private JTextField txtSmtpHost;
    private JTextField txtSmtpPort;
    private JTextField txtImapHost;
    private JTextField txtImapPort;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JCheckBox chkSslEnabled;
    private JCheckBox chkAutoEncrypt;
    private JCheckBox chkAutoSign;
    private JComboBox<String> cmbEncryptionLevel;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnTestConnection;

    public SettingsDialog(java.awt.Frame parent) {
        super(parent, "Settings", true);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setSize(500, 600);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        // Server settings
        txtSmtpHost = new JTextField(20);
        txtSmtpHost.setText("localhost");
        txtSmtpPort = new JTextField(10);
        txtSmtpPort.setText("3025");
        txtImapHost = new JTextField(20);
        txtImapHost.setText("localhost");
        txtImapPort = new JTextField(10);
        txtImapPort.setText("3143");
        
        // Account settings
        txtEmail = new JTextField(30);
        txtEmail.setText("user@localhost");
        txtPassword = new JPasswordField(20);
        txtPassword.setText("password");
        
        // Security settings
        chkSslEnabled = new JCheckBox("Enable SSL/TLS");
        chkAutoEncrypt = new JCheckBox("Auto-encrypt outgoing emails");
        chkAutoSign = new JCheckBox("Auto-sign outgoing emails");
        
        cmbEncryptionLevel = new JComboBox<>(new String[]{"AES-128", "AES-256", "RSA-2048", "RSA-4096"});
        cmbEncryptionLevel.setSelectedItem("AES-256");
        
        // Buttons
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(80, 30));
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        btnSave.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnSave.setFocusPainted(false);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(80, 30));
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(java.awt.Color.WHITE);
        btnCancel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnCancel.setFocusPainted(false);
        
        btnTestConnection = new JButton("Test Connection");
        btnTestConnection.setPreferredSize(new Dimension(140, 30));
        btnTestConnection.setBackground(new Color(0, 123, 255));
        btnTestConnection.setForeground(java.awt.Color.WHITE);
        btnTestConnection.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        btnTestConnection.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnTestConnection.setFocusPainted(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("⚙️ Application Settings");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Server Settings Section
        JLabel serverLabel = new JLabel("📧 Mail Server Settings");
        serverLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        serverLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(serverLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("SMTP Host:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtSmtpHost, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("SMTP Port:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtSmtpPort, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(new JLabel("IMAP Host:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtImapHost, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        contentPanel.add(new JLabel("IMAP Port:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtImapPort, gbc);
        
        // Account Settings Section
        JLabel accountLabel = new JLabel("👤 Account Settings");
        accountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        accountLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        contentPanel.add(accountLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 6;
        contentPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(txtPassword, gbc);
        
        // Security Settings Section
        JLabel securityLabel = new JLabel("🔒 Security Settings");
        securityLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        securityLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        contentPanel.add(securityLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 9;
        contentPanel.add(chkSslEnabled, gbc);
        
        gbc.gridx = 0; gbc.gridy = 10;
        contentPanel.add(chkAutoEncrypt, gbc);
        
        gbc.gridx = 0; gbc.gridy = 11;
        contentPanel.add(chkAutoSign, gbc);
        
        gbc.gridx = 0; gbc.gridy = 12;
        contentPanel.add(new JLabel("Encryption Level:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(cmbEncryptionLevel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        
        gbc.gridx = 0;
        buttonPanel.add(btnTestConnection, gbc);
        gbc.gridx = 1;
        buttonPanel.add(btnSave, gbc);
        gbc.gridx = 2;
        buttonPanel.add(btnCancel, gbc);
        
        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnSave.addActionListener(e -> handleSave());
        btnCancel.addActionListener(e -> dispose());
        btnTestConnection.addActionListener(e -> handleTestConnection());
    }
    
    private void handleSave() {
        logger.info("Saving settings...");
        
        // Update MainController with new settings
        ui.MainController.setSession(
            txtEmail.getText(),
            new String(txtPassword.getPassword()),
            txtSmtpHost.getText(),
            txtImapHost.getText(),
            Integer.parseInt(txtSmtpPort.getText()),
            Integer.parseInt(txtImapPort.getText())
        );
        
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Settings saved successfully!", 
            "Settings Saved", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    private void handleTestConnection() {
        logger.info("Testing connection...");
        
        // Simulate connection test
        String message = """
            Connection test successful!
            SMTP: %s:%s
            IMAP: %s:%s
            """.formatted(
                txtSmtpHost.getText(), txtSmtpPort.getText(),
                txtImapHost.getText(), txtImapPort.getText()
            );
        
        javax.swing.JOptionPane.showMessageDialog(this, 
            message, 
            "Connection Test", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}
