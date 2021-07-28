package web.prices.observatory.models;

import java.beans.IntrospectionException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OnePriceOutput {
    private Double price;
    private LocalDate date;
    private String productName;
    private Long productId;
    private List<String> productTags;
    private Long shopId;
    private String shopName;
    private List<String> shopTags;
    private String shopAddress;
    private Integer shopDist;

    public OnePriceOutput(){}

    public OnePriceOutput(Double price, LocalDate date, String productName, Long productId, List<String> productTags,
                          Long shopId, String shopName, List<String> shopTags, String shopAddress, Integer shopDist) {
        this.price = price;
        this.date = date;
        this.productName = productName;
        this.productId = productId;
        this.productTags = productTags;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopTags = shopTags;
        this.shopAddress = shopAddress;
        this.shopDist = shopDist;
    }

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public String getProductName() {return productName;}

    public void setProductName(String productName) {this.productName = productName;}

    public Long getProductId() {return productId;}

    public void setProductId(Long productId) {this.productId = productId;}

    public List<String> getProductTags() {return productTags;}

    public void setProductTags(List<String> productTags) {this.productTags = productTags;}

    public Long getShopId() {return shopId;}

    public void setShopId(Long shopId) {this.shopId = shopId;}

    public String getShopName() {return shopName;}

    public void setShopName(String shopName) {this.shopName = shopName;}

    public List<String> getShopTags() {return shopTags;}

    public void setShopTags(List<String> shopTags) {this.shopTags = shopTags;}

    public String getShopAddress() {return shopAddress;}

    public void setShopAddress(String shopAddress) {this.shopAddress = shopAddress;}

    public Integer getShopDist() {return shopDist;}

    public void setShopDist(Integer shopDist) {this.shopDist = shopDist;}

}


