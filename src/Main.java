import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<Contact> contacts = new HashSet<Contact>();
        contacts.add(new Contact("Lewis", "07514 262801"));
        contacts.add(new Contact("Will", "07444 234391"));
        contacts.add(new Contact("Jack", "07167 254790"));
        contacts.add(new Contact("Struan", "07343 259865"));
        contacts.add(new Contact("John", "07565 286343"));

        ContactPage contactPage = new ContactPage();
        contactPage.launch(contacts);
    }

}