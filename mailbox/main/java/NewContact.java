import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class NewContact extends JDialog {

  private JTextField nameField;
  private JTextField surnameField;
  private JTextField emailField;

  public NewContact(Frame parent, ContactManager contactManager) {
    super(parent, "Utwórz nowy kontakt", true);
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel = new JLabel("Imię:");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(nameLabel, constraints);

    nameField = new JTextField(20);
    constraints.gridx = 1;
    constraints.gridy = 0;
    add(nameField, constraints);

    JLabel surnameLabel = new JLabel("Nazwisko:");
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(surnameLabel, constraints);

    surnameField = new JTextField(20);
    constraints.gridx = 1;
    constraints.gridy = 1;
    add(surnameField, constraints);

    JLabel emailLabel = new JLabel("Email:");
    constraints.gridx = 0;
    constraints.gridy = 2;
    add(emailLabel, constraints);

    emailField = new JTextField(20);
    constraints.gridx = 1;
    constraints.gridy = 2;
    add(emailField, constraints);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    JButton addButton = new JButton("Dodaj");
    JButton cancelButton = new JButton("Anuluj");

    buttonPanel.add(addButton);
    buttonPanel.add(cancelButton);

    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.CENTER;
    add(buttonPanel, constraints);

    addButton.addActionListener(e -> {
      if (validateFields()) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();

        if (contactManager.contactExists(email)) {
          showErrorDialog("Kontakt z tym adresem email już istnieje.");
        } else {
          contactManager.addContact(new Contact(name, surname, email));
          dispose();
        }
      }
    });

    cancelButton.addActionListener(e -> dispose());

    pack();
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  private boolean validateFields() {
    String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

    if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || emailField.getText().isEmpty()) {
      showErrorDialog("Wszystkie pola muszą być wypełnione.");
      return false;
    }

    if (!Pattern.matches(emailPattern, emailField.getText())) {
      showErrorDialog("Adres email jest nieprawidłowy.");
      return false;
    }

    return true;
  }

  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Nie można dodać nowego kontaktu", JOptionPane.ERROR_MESSAGE);
  }
}
