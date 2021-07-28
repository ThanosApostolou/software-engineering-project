package web.prices.observatory.models;

import java.util.List;

public class PriceOutput {
    private int start;
    private int count;
    private Long total;
    private List<OnePriceOutput> prices;

    public PriceOutput() {}

    public PriceOutput(int start, int count, Long total, List<OnePriceOutput> prices) {
        this.start = start;
        this.count = count;
        this.total = total;
        this.prices = prices;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
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

    public List<OnePriceOutput> getPrices() { return prices; }

    public void setPrices(List<OnePriceOutput> prices) { this.prices = prices;}

}
