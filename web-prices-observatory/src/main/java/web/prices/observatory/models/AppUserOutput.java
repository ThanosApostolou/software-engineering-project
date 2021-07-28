package web.prices.observatory.models;

import java.util.List;

public class AppUserOutput {
    private int start;
    private int count;
    private Long total;
    private List<AppUser> users;

    public AppUserOutput(){}

    public AppUserOutput(int start, int count, Long total, List<AppUser> users){
        this.start = start;
        this.count = count;
        this.total = total;
        this.users = users;
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

    public List<AppUser> getAppUsers() {
        return users;
    }

    public void setAppUsers(List<AppUser> users) {
        this.users = users;
    }
}
