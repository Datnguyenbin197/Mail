package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Main application window with modern mail client interface
 */
public class MainApplication extends JFrame {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());
    
    private SidebarPanel sidebar;
    private TopBarPanel topBar;
    private MailListPanel mailListPanel;
    private MailDetailPanel mailDetailPanel;
    private JSplitPane mainSplitPane;
    private JSplitPane leftSplitPane;
    
    public MainApplication() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Secure Mail Client");
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        sidebar = new SidebarPanel();
        topBar = new TopBarPanel();
        mailListPanel = new MailListPanel();
        mailDetailPanel = new MailDetailPanel();
        
        // Create split panes
        leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftSplitPane.setTopComponent(mailListPanel);
        leftSplitPane.setBottomComponent(new JPanel()); // Empty panel for now
        
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(leftSplitPane);
        mainSplitPane.setRightComponent(mailDetailPanel);
        
        // Set divider locations
        mainSplitPane.setDividerLocation(600);
        leftSplitPane.setDividerLocation(400);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add components
        add(topBar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainSplitPane, BorderLayout.CENTER);
        
        // Set minimum sizes for responsiveness
        sidebar.setMinimumSize(new Dimension(200, 0));
        mailListPanel.setMinimumSize(new Dimension(300, 0));
        mailDetailPanel.setMinimumSize(new Dimension(400, 0));
    }

    private void setupEventHandlers() {
        // Add event handlers for sidebar navigation
        sidebar.setNavigationListener(new SidebarPanel.NavigationListener() {
            @Override
            public void onInboxSelected() {
                logger.info("Inbox selected");
                mailListPanel.loadInbox();
            }
            
            @Override
            public void onComposeSelected() {
                logger.info("Compose selected");
                // Show compose dialog or switch to compose view
                showComposeDialog();
            }
            
            @Override
            public void onSentSelected() {
                logger.info("Sent selected");
                mailListPanel.loadSent();
            }
            
            @Override
            public void onDraftsSelected() {
                logger.info("Drafts selected");
                mailListPanel.loadDrafts();
            }
            
            @Override
            public void onTrashSelected() {
                logger.info("Trash selected");
                mailListPanel.loadTrash();
            }
            
            @Override
            public void onKeysSelected() {
                logger.info("Keys selected");
                showKeysDialog();
            }
            
            @Override
            public void onSettingsSelected() {
                logger.info("Settings selected");
                showSettingsDialog();
            }
        });
        
        // Add event handlers for mail list selection
        mailListPanel.setMailSelectionListener(mailId -> {
            logger.info(() -> "Mail selected: " + mailId);
            mailDetailPanel.loadMail(mailId);
        });
    }
    
    private void showComposeDialog() {
        ComposeDialog dialog = new ComposeDialog(this);
        dialog.setVisible(true);
    }
    
    private void showKeysDialog() {
        KeysDialog dialog = new KeysDialog(this);
        dialog.setVisible(true);
    }
    
    private void showSettingsDialog() {
        SettingsDialog dialog = new SettingsDialog(this);
        dialog.setVisible(true);
    }

    public void showApplication() {
        setVisible(true);
    }
}
