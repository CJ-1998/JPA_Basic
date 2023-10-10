package hellojpa.jpql;

import javax.persistence.Embeddable;

@Embeddable
public class Address1 {
    private String city;
    private String street;
    private String zipcode;
}
