package web.prices.observatory.models;

import java.util.List;

public class ProductOutput {
    private int start;
    private int count;
    private Long total;
    private List<Product> products;

    public ProductOutput(){}

    public ProductOutput(int start, int count, Long total, List<Product> products){
        this.start = start;
        this.count = count;
        this.total = total;
        this.products = products;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
