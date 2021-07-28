package web.prices.observatory.models;

import java.util.List;

public class ShopsOutput {
    private int start;
    private int count;
    private Long total;
    private List<OneShopOutput> shops;

    public ShopsOutput(){}

    public ShopsOutput(int start, int count, Long total, List<OneShopOutput> shops){
        this.start = start;
        this.count = count;
        this.total = total;
        this.shops = shops;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setShops(List<OneShopOutput> shops) {
        this.shops = shops;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public Long getTotal() {
        return total;
    }

    public List<OneShopOutput> getShops() {
        return shops;
    }
}
