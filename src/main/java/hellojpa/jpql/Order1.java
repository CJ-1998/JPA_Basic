package hellojpa.jpql;

import javax.persistence.*;

@Entity
//@Table(name="ORDERS")
public class Order1 {
    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address1 address1;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID")
    private Product1 product1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Address1 getAddress1() {
        return address1;
    }

    public void setAddress1(Address1 address1) {
        this.address1 = address1;
    }

    public Product1 getProduct1() {
        return product1;
    }

    public void setProduct1(Product1 product1) {
        this.product1 = product1;
    }
}
