public class Contact {

    //Contact details depending on time could implement profile pictures
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //Getters and setters 
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }    

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}