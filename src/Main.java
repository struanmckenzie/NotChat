public class Main {

    public static void main(String[] args) {
        Contact[] contacts = new Contact[100]; 

        for (int i = 0; i < contacts.length; i++) {
           contacts[i] = new Contact("Contact " + i, "2345678"); 
        }

        ContactPage.launch(contacts);
    }

}
