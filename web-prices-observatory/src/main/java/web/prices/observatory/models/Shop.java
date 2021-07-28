package web.prices.observatory.models;

import org.locationtech.jts.geom.Point;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @Column(name = "coordinates")
    private Point coordinates;

    @Column(name = "withdrawn")
    private Boolean withdrawn;

    public Shop(){}

    public Shop(String name,String address, List<String> tags, Point coordinates ,Boolean withdrawn){
        this.name = name;
        this.address = address;
        this.tags = tags;
        this.coordinates = coordinates;
        this.withdrawn = withdrawn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Point getCoordinates() {

        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setWithdrawn(Boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Boolean getWithdrawn() {
        return withdrawn;
    }

}
