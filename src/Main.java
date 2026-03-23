import java.util.ArrayList;
import java.util.List;

public class Main {

    public static ContactPage contactPage;

    static void main() {

        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            contacts.add(new Contact("Contact " + i, "234567364657"));
        }

        contactPage = new ContactPage(contacts, "TEMP NAME");
    }

}
