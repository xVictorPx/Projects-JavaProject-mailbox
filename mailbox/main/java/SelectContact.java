import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class SelectContact extends JDialog {

  private JList<String> contactList;
  private String selectedContact;

  public SelectContact(NewMail parent, Set<Contact> contacts) {
    super(parent, "Wybierz adresata", true);
    setLayout(new BorderLayout());

    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (Contact contact : contacts) {
      listModel.addElement(contact.getEmail());
    }

    contactList = new JList<>(listModel);
    JScrollPane listScrollPane = new JScrollPane(contactList);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton selectButton = new JButton("WYBIERZ");
    JButton cancelButton = new JButton("ANULUJ");

    buttonPanel.add(selectButton);
    buttonPanel.add(cancelButton);

    add(listScrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    selectButton.addActionListener(e -> {
      selectedContact = contactList.getSelectedValue();
      dispose();
    });

    cancelButton.addActionListener(e -> dispose());

    setSize(300, 200);
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public String getSelectedContact() {
    return selectedContact;
  }
}
