package web.prices.observatory.models;


import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name="prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="price")
    private Double price;

    @Column(name="date")
    private LocalDate date;

    @Column(name="product_id")
    private Long product_id;

    @Column(name="shop_id")
    private Long shop_id;


    public Price() {}

    public Price(Long id, Double price, LocalDate date, Long product_id,  Long shop_id) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.product_id = product_id;
        this.shop_id = shop_id;
    }

    public Price(Double price, LocalDate date, Long product_id,  Long shop_id) {
        this.price = price;
        this.date = date;
        this.product_id = product_id;
        this.shop_id = shop_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}

    public LocalDate getDate() { return date;}

    public void setDate(LocalDate date) {this.date = date; }

    public Long getProduct_id() {return product_id;}

    public void setProduct_id( Long product_id) {this.product_id = product_id;}

    public Long getShop_id() {return shop_id;}

    public void setShop_id(Long shop_id) {this.shop_id = shop_id;}

}
