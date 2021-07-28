package web.prices.observatory.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.prices.observatory.Exceptions.NotFoundException;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.models.Product;
import web.prices.observatory.models.ProductOutput;
import web.prices.observatory.repositories.ProductRepository;
import web.prices.observatory.repositories.UserRepository;
import web.prices.observatory.service.ProductPaginationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping("/observatory/api")
public class ProductsRestController {

   @Autowired
   private ProductRepository productRepository;

   @Autowired
   private  ProductPaginationService productPaginationService;

   @Autowired
   private UserRepository userRepository;

   @GetMapping("/products")
    public ProductOutput getProducts(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                     @RequestParam(name = "count", defaultValue = "20") Integer count,
                                     @RequestParam(name = "status", defaultValue = "ACTIVE") String status,
                                     @RequestParam(name = "sort", required = false) String sort){

       Page<Product> result;

       if (sort!=null) {
           String[] sortParams = sort.split("\\|");
           String sortBy = sortParams[0];
           String direction = sortParams[1];
          result = productPaginationService.findJsonDataByCondition(sortBy, direction, start, count, status);
       }
       else{

           result = productPaginationService.findJsonDataNotSorted(start,count,status);

       }

       return new ProductOutput(result.getPageable().getPageNumber(),
               result.getPageable().getPageSize(),result.getTotalElements(),result.getContent());


   }

   @PostMapping("/products")
    public Product addNewProduct(@RequestBody Product product){
       product.setWithdrawn(false);
       return productRepository.save(product);
   }

   @GetMapping("/products/{id}")
    public Product getOneProduct(@PathVariable Long id){
       return productRepository.findById(id).orElseThrow(()-> new NotFoundException());
   }


   @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
       Optional<Product> productOpt = productRepository.findById(id);
       if (productOpt.isPresent()) {
           product.setId(id);
           return productRepository.save(product);
       }
       else throw new NotFoundException();
   }

   @PatchMapping("/products/{id}")
   public Product partiallyUpdateProduct(@PathVariable Long id, @RequestBody Map<String, Object> fields){
       Optional<Product> productOpt = productRepository.findById(id);
       if (productOpt.isPresent()) {
           Product product = productOpt.get();

           fields.forEach((k, v) -> {
               switch (k) {
                   case "name":
                       product.setName((String) v);
                       break;
                   case "description":
                       product.setDescription((String) v);
                       break;
                   case "tags":
                       product.setTags((List<String>) v);
                       break;
                   case "withdrawn":
                       product.setWithdrawn((Boolean) v);
                       break;
                   case "category":
                       product.setCategory((String) v);
                       break;
               }
           });

           return productRepository.save(product);
       }
       else throw new NotFoundException();
   }



   @DeleteMapping("/products/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id){
       Optional<Product> productOpt = productRepository.findById(id);
       if (productOpt.isPresent()) {
           //find current username
           String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           //get user by username
           AppUser appUser = userRepository.findByUsername(username);
           
           Product product = productOpt.get();

           if (appUser.getAdmin()) {
               productRepository.delete(product);
           } else {
               product.setWithdrawn(true);
               productRepository.save(product);
           }

           Map<String, String> okMessage = new HashMap<>();
           okMessage.put("message", "ok");

           return okMessage;
       }
       else throw new NotFoundException();
   }

}
