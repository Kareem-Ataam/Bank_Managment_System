class Address {
    private String city;
    private String street;
    private String zip;

    public Address(String city, String street, String zip) {
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "[" + city +", "+ street+", "+ ", "+ zip + ']';
    }
}
public class Client {
    private String name;
    private String age;
    private String id;
    private String phone;
    private Account account;
    //Composition
    private Address address;

    public Client(String name, String age, String id, String phone, Account account, Address address) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.phone = phone;
        this.account = account;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public Account getAccount(){return  account;}
}
