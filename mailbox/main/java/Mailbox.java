import javax.swing.*;
import java.awt.*;

public class Mailbox extends JFrame {

  private ContactManager contactManager;
  private DefaultListModel<EmailMessage> sentMessagesModel;
  private JTextField toField;
  private JTextField subjectField;
  private JTextArea messageArea;

  public Mailbox() {
    this.setTitle("PJATK Mail");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(800, 600);

    contactManager = new ContactManager();
    sentMessagesModel = new DefaultListModel<>();

    this.setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.insets = new Insets(5, 5, 5, 5);

    JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

    ImageIcon mailIcon = new ImageIcon(getClass().getResource("/mail.png"));
    Image mailImg = mailIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    mailIcon = new ImageIcon(mailImg);

    ImageIcon contactIcon = new ImageIcon(getClass().getResource("/contact.png"));
    Image contactImg = contactIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    contactIcon = new ImageIcon(contactImg);

    JButton newMail = new JButton("Utwórz mail", mailIcon);
    JButton newContact = new JButton("Utwórz kontakt", contactIcon);
    navBar.add(newMail);
    navBar.add(newContact);

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    constraints.weighty = 0;
    this.add(navBar, constraints);

    newContact.addActionListener(e -> {
      NewContact newContactDialog = new NewContact(this, contactManager);
      newContactDialog.setVisible(true);
    });

    newMail.addActionListener(e -> {
      NewMail newMailDialog = new NewMail(this, contactManager, sentMessagesModel);
      newMailDialog.setVisible(true);
    });

    JLabel sentMessagesLabel = new JLabel("Wysłane wiadomości:");
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    constraints.weighty = 0;
    this.add(sentMessagesLabel, constraints);

    JList<EmailMessage> messageList = new JList<>(sentMessagesModel);
    JScrollPane messageListScrollPane = new JScrollPane(messageList);

    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 1;
    constraints.gridheight = 2;
    constraints.weightx = 0.3;
    constraints.weighty = 1;
    this.add(messageListScrollPane, constraints);

    messageList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        EmailMessage selectedMessage = messageList.getSelectedValue();
        if (selectedMessage != null) {
          toField.setText(selectedMessage.getTo());
          subjectField.setText(selectedMessage.getSubject());
          messageArea.setText(selectedMessage.getMessage());
        }
      }
    });

    JPanel mailPanel = new JPanel(new GridBagLayout());
    GridBagConstraints mailConstraints = new GridBagConstraints();
    mailConstraints.insets = new Insets(5, 5, 5, 5);
    mailConstraints.fill = GridBagConstraints.HORIZONTAL;
    mailConstraints.weightx = 1;

    JLabel toLabel = new JLabel("DO:");
    mailConstraints.gridx = 0;
    mailConstraints.gridy = 0;
    mailConstraints.weightx = 0;
    mailPanel.add(toLabel, mailConstraints);

    toField = new JTextField();
    toField.setEditable(false);
    mailConstraints.gridx = 1;
    mailConstraints.gridy = 0;
    mailConstraints.weightx = 1;
    mailPanel.add(toField, mailConstraints);

    JLabel subjectLabel = new JLabel("TEMAT:");
    mailConstraints.gridx = 0;
    mailConstraints.gridy = 1;
    mailConstraints.weightx = 0;
    mailPanel.add(subjectLabel, mailConstraints);

    subjectField = new JTextField();
    subjectField.setEditable(false);
    mailConstraints.gridx = 1;
    mailConstraints.gridy = 1;
    mailConstraints.weightx = 1;
    mailPanel.add(subjectField, mailConstraints);

    messageArea = new JTextArea(10, 30);
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    mailConstraints.gridx = 0;
    mailConstraints.gridy = 2;
    mailConstraints.gridwidth = 2;
    mailConstraints.weighty = 1;
    mailConstraints.fill = GridBagConstraints.BOTH;
    JScrollPane mailScrollPane = new JScrollPane(messageArea);
    mailPanel.add(mailScrollPane, mailConstraints);

    constraints.gridx = 1;
    constraints.gridy = 2;
    constraints.gridwidth = 1;
    constraints.gridheight = 2;
    constraints.weightx = 0.7;
    this.add(mailPanel, constraints);

    this.setVisible(true);
    centerFrame();
  }

  private void centerFrame() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    int x = (screenSize.width - frameSize.width) / 2;
    int y = (screenSize.height - frameSize.height) / 2;
    setLocation(x, y);
  }
}
