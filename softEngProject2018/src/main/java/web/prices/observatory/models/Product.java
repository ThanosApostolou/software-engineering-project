package web.prices.observatory.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @Column(name="withdrawn")
    private Boolean withdrawn;

    @Column(name = "category")
    private String category;


    public Product(){}

    public Product(String name, String description, List<String> tags, Boolean withdrawn, String category){
        this.name = name;
        this.description = description;
        this.withdrawn = withdrawn;
        this.category = category;
        this.tags = tags;
    }

    public Product(Long id, String name, String description, List<String> tags,Boolean withdrawn, String category){
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.withdrawn = withdrawn;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(Boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
