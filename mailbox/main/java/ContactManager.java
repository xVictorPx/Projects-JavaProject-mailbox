import java.util.HashSet;
import java.util.Set;

public class ContactManager {
  private Set<Contact> contacts;

  public ContactManager() {
    contacts = new HashSet<>();
  }

  public void addContact(Contact contact) {
    contacts.add(contact);
  }

  public boolean contactExists(String email) {
    return contacts.stream().anyMatch(contact -> contact.getEmail().equals(email));
  }

  public Set<Contact> getContacts() {
    return contacts;
  }
}
