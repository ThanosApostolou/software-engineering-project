package web.prices.observatory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class WebController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("current_page", "index.html");
        return "index";
    }
	
    @GetMapping("/find")
    public String find (@RequestParam(name = "start", defaultValue = "0") Integer start,
                        @RequestParam(name = "count", defaultValue = "20") Integer count,
                        @RequestParam(name = "geoDist", required = false) Integer geoDist,
                        @RequestParam(name = "geoLng", required = false) Double geoLng,
                        @RequestParam(name = "geoLat", required = false) Double geoLat,
                        @RequestParam(name = "dateFrom", defaultValue = "2000-01-01", required = false )
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                        @RequestParam(name = "dateTo", defaultValue="2019-03-06", required = false )
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                        @RequestParam(name = "shops",required = false) String shops,
                        @RequestParam(name = "products", required = false) String products,
                        @RequestParam(name = "tags", required = false) String tags,
                        @RequestParam(name = "sort", defaultValue = "price|ASC") String sort,
                        Model model) {
        model.addAttribute("current_page", "find.html");
        model.addAttribute("start", start);
        model.addAttribute("count", count);
        model.addAttribute("geoDist", geoDist);
        model.addAttribute("geoLng", geoLng);
        model.addAttribute("geoLat", geoLat);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("shops", shops);
        model.addAttribute("products", products);
        model.addAttribute("tags", tags);
        model.addAttribute("sort", sort);
        return "find";
    }

	@GetMapping("/browse_products")
    public String browse_products(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                  @RequestParam(name = "count", defaultValue = "20") Integer count,
                                  @RequestParam(name = "status", defaultValue = "ACTIVE") String status,
                                  @RequestParam(name = "sort", defaultValue = "id|DESC") String sort,
                                  Model model) {
        model.addAttribute("current_page", "browse_products.html");
        model.addAttribute("start", start);
        model.addAttribute("count", count);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);
        return "browse_products";
    }

    @GetMapping("/add_product")
    public String add_product(Model model) {
        model.addAttribute("current_page", "add_product.html");
        return "add_product";
    }

    @GetMapping("/show_product")
    public String show_product(@RequestParam(name="id", required=true) int id,
                               Model model) {
        model.addAttribute("current_page", "show_product.html");
        model.addAttribute("id", id);
        return "show_product";
    }

    @GetMapping("/browse_shops")
    public String browse_shops(@RequestParam(name = "start", defaultValue = "0") Integer start,
                               @RequestParam(name = "count", defaultValue = "20") Integer count,
                               @RequestParam(name = "status", defaultValue = "ACTIVE") String status,
                               @RequestParam(name = "sort", defaultValue = "id|DESC") String sort,
                               Model model) {
        model.addAttribute("current_page", "browse_shops.html");
        model.addAttribute("start", start);
        model.addAttribute("count", count);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);
        return "browse_shops";
    }

    @GetMapping("/add_shop")
    public String add_shop(Model model) {
        model.addAttribute("current_page", "add_shop.html");
        return "add_shop";
    }

    @GetMapping("/show_shop")
    public String show_shop(@RequestParam(name="id", required=true) int id,
                            Model model) {
        model.addAttribute("current_page", "show_shop.html");
        model.addAttribute("id", id);
        return "show_shop";
    }
	
	@GetMapping("/users")
    public String users (@RequestParam(name = "start", defaultValue = "0") Integer start,
                         @RequestParam(name = "count", defaultValue = "20") Integer count,
                         @RequestParam(name = "status", defaultValue = "ENABLED") String status,
                         @RequestParam(name = "sort", defaultValue = "id|DESC") String sort,
                         Model model) {
        model.addAttribute("current_page", "users.html");
        model.addAttribute("start", start);
        model.addAttribute("count", count);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);
        return "users";
    }
	
	@GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("current_page", "about.html");
        return "about";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("current_page", "account.html");
        return "account";
    }

}
