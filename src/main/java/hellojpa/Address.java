package hellojpa;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getStreet() {
        return street;
    }

//    public void setCity(String city) {
//        this.city = city;
//    }

//    public void setStreet(String street) {
//        this.street = street;
//    }

//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
}
