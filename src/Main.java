public class Main {

    public static void main(String[] args) {
        Contact[] contacts = new Contact[100]; 

        for (int i = 0; i < contacts.length; i++) {
           contacts[i] = new Contact("test " + i, "2345678"); 
        }

        ContactPage contactPage = new ContactPage();
        contactPage.launch(contacts);
    }

}
