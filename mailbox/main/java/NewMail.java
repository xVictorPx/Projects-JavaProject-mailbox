import javax.swing.*;
import java.awt.*;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class NewMail extends JDialog {

  private JTextField toField;
  private JTextField subjectField;
  private JTextArea messageArea;
  private ContactManager contactManager;
  private DefaultListModel<EmailMessage> sentMessagesModel;
  private String name = "Imię Nazwisko";
  private String from = "mail@example.com";
  private String password = "password";

  public NewMail(Frame parent, ContactManager contactManager, DefaultListModel<EmailMessage> sentMessagesModel) {
    super(parent, "Utwórz nowy mail", true);
    this.contactManager = contactManager;
    this.sentMessagesModel = sentMessagesModel;
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    JLabel toLabel = new JLabel("DO:");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(toLabel, constraints);

    toField = new JTextField(20);
    toField.setEditable(false);
    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    add(toField, constraints);

    JButton selectButton = new JButton("WYBIERZ");
    constraints.gridx = 3;
    constraints.gridy = 0;
    constraints.gridwidth = 1;
    add(selectButton, constraints);

    selectButton.addActionListener(e -> {
      SelectContact selectContact = new SelectContact(this, contactManager.getContacts());
      selectContact.setVisible(true);
      String selectedContact = selectContact.getSelectedContact();
      if (selectedContact != null) {
        toField.setText(selectedContact);
      }
    });

    JLabel subjectLabel = new JLabel("TEMAT:");
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(subjectLabel, constraints);

    subjectField = new JTextField(20);
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 3;
    add(subjectField, constraints);

    messageArea = new JTextArea("Dzień dobry,\nTo jest wiadomość testowa.\n\nPozdrawiam", 10, 30);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    JScrollPane messageScrollPane = new JScrollPane(messageArea);
    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 4;
    constraints.fill = GridBagConstraints.BOTH;
    add(messageScrollPane, constraints);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    JButton sendButton = new JButton("WYŚLIJ");
    JButton cancelButton = new JButton("ODRZUĆ");

    buttonPanel.add(sendButton);
    buttonPanel.add(cancelButton);

    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 4;
    constraints.fill = GridBagConstraints.NONE;
    add(buttonPanel, constraints);

    sendButton.addActionListener(e -> {
      EmailMessage emailMessage = sendEmail();
      if (emailMessage != null) {
        sentMessagesModel.addElement(emailMessage);
      }
      dispose();
    });

    cancelButton.addActionListener(e -> dispose());

    pack();
    setLocationRelativeTo(null); // Centrowanie okna dialogowego
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  private EmailMessage sendEmail() {
    String to = toField.getText();
    String subject = subjectField.getText();
    String message = messageArea.getText();

    Email email = EmailBuilder.startingBlank()
        .from(name, from)
        .to("Odbiorca", to)
        .withSubject(subject)
        .withPlainText(message)
        .buildEmail();

    Mailer mailer = MailerBuilder
        .withSMTPServer("smtp.gmail.com", 587, from, password)
        .withTransportStrategy(TransportStrategy.SMTP)
        .withSessionTimeout(10 * 1000)
        .buildMailer();

    try {
      mailer.sendMail(email);
      return new EmailMessage(to, subject, message);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Nie udało się wysłać wiadomości: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
}
