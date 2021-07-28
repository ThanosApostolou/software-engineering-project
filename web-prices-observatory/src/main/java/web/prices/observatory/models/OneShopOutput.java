package web.prices.observatory.models;

import java.util.List;

public class OneShopOutput {
    private Long id;
    private String name;
    private String address;
    private Double lng;
    private Double lat;
    private List<String> tags;
    private Boolean withdrawn;

    public OneShopOutput(){}

    public OneShopOutput(Long id, String name, String address, Double lng, Double lat, List<String> tags, Boolean withdrawn){
        this.id = id;
        this.name = name;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.tags = tags;
        this.withdrawn = withdrawn;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(Boolean withdrawn) {
        this.withdrawn = withdrawn;
    }
}
