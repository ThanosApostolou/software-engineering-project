package web.prices.observatory.restcontrollers;

import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.prices.observatory.Exceptions.NotFoundException;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.models.OneShopOutput;
import web.prices.observatory.models.ShopsOutput;
import web.prices.observatory.repositories.ShopRepository;
import web.prices.observatory.repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.*;

@RestController
@RequestMapping("/observatory/api/shops")
public class ShopRestController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ShopsOutput getShops(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                @RequestParam(name = "count", defaultValue = "20") Integer count,
                                @RequestParam(name = "status", defaultValue = "ACTIVE") String status,
                                @RequestParam(name = "sort", required = false) String sort){

        int offset = 0;
        int withdr = 0;
        long total;
        List<Object[]> shops;
        List<OneShopOutput> outputShops = new ArrayList<>();

        if (start != 0) {
            offset = start*count;
        }

        if (status.equals("WITHDRAWN")) {
            withdr = 1;
        }
        else if (status.equals("ALL")){
            withdr = 2;
        }

        if (sort!=null) {
            String[] sortParams = sort.split("\\|");
            String sortBy = sortParams[0];
            String direction = sortParams[1];

            if (withdr < 2){
                shops = entityManager.createNativeQuery("select id, name,address,withdrawn, ST_Longitude(coordinates) " +
                        "AS 'lng', ST_Latitude(coordinates) AS 'lat' FROM shops " +
                "WHERE withdrawn = "+ withdr +" ORDER BY "+ sortBy +" "+ direction +" LIMIT "+ count +" OFFSET "+ offset).getResultList();

                List<Object[]> totalshops = entityManager.createNativeQuery("select * from shops " +
                        "WHERE withdrawn = " + withdr).getResultList();
                total = totalshops.size();

            }
            else{
                shops = entityManager.createNativeQuery("select id, name,address,withdrawn, ST_Longitude(coordinates) " +
                        "AS 'lng', ST_Latitude(coordinates) AS 'lat' FROM shops " +
                        "ORDER BY "+ sortBy +" "+ direction +" LIMIT "+ count +" OFFSET "+ offset).getResultList();
                List<Object[]> totalshops = entityManager.createNativeQuery("select * from shops").getResultList();
                total = totalshops.size();
            }

        }
        else {
            if (withdr < 2){
                shops = entityManager.createNativeQuery("select id, name,address,withdrawn, ST_Longitude(coordinates) " +
                        "AS 'lng', ST_Latitude(coordinates) AS 'lat' FROM shops " +
                        "WHERE withdrawn = "+ withdr +" LIMIT "+ count +" OFFSET "+ offset).getResultList();
                List<Object[]> totalshops = entityManager.createNativeQuery("select * from shops " +
                        "WHERE withdrawn = " + withdr).getResultList();
                total = totalshops.size();
            }
            else {
                shops = entityManager.createNativeQuery("select id, name,address,withdrawn, ST_Longitude(coordinates) " +
                        "AS 'lng', ST_Latitude(coordinates) AS 'lat' FROM shops LIMIT "+ count +" OFFSET "+ offset).getResultList();
                List<Object[]> totalshops = entityManager.createNativeQuery("select * from shops").getResultList();
                total = totalshops.size();
            }
        }

        for (Object[] objects: shops){
            List<String> tags = shopRepository.findTags(Long.valueOf(objects[0].toString()));
            OneShopOutput oneShopOutput = new OneShopOutput(Long.valueOf(objects[0].toString()), (String) objects[1], (String) objects[2],
                    (Double) objects[4], (Double) objects[5], tags, (Boolean) objects[3] );
            outputShops.add(oneShopOutput);
        }

        ShopsOutput shopsOutput = new ShopsOutput(start, count,total, outputShops);

        return shopsOutput;
    }

    @GetMapping("/{id}")
    public OneShopOutput getOneShop(@PathVariable Long id){
        OneShopOutput shop = getShopFromDB(id);
        if (shop==null){
            throw new NotFoundException();
        }
        return shop;
    }

    @PostMapping("")
    public OneShopOutput addNewShop(@RequestBody OneShopOutput oneShop) throws ParseException {

        String point = "POINT ("+ oneShop.getLat()+" " +oneShop.getLng()+")";
        shopRepository.insertShop(oneShop.getName(), oneShop.getAddress(), oneShop.getWithdrawn(), point);

        List<Object> id =  entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getResultList();
        Long idLong = Long.valueOf(id.get(0).toString());

        for (String tag: oneShop.getTags()){
            shopRepository.insertShopTag(idLong, tag);
        }

        oneShop.setId(idLong);

        return oneShop;
    }

    @PutMapping("/{id}")
    public OneShopOutput updateShop(@RequestBody OneShopOutput oneShop, @PathVariable Long id){
        Boolean shopExists = checkIfShopExists(id);
        if (shopExists) {
            String point = "POINT (" + oneShop.getLat() + " " + oneShop.getLng() + ")";
            shopRepository.updateShop(oneShop.getName(), oneShop.getAddress(), oneShop.getWithdrawn(), point, id);

            shopRepository.deleteTagsById(id);

            for (String tag : oneShop.getTags()) {
                shopRepository.insertShopTag(id, tag);
            }

            oneShop.setId(id);
            return oneShop;
        }
        else throw new NotFoundException();
    }


    @PatchMapping("/{id}")
    public  OneShopOutput partiallyUpdateShop(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Boolean shopExists = checkIfShopExists(id);
        if (shopExists) {
            fields.forEach((k, v) -> {
                switch (k) {
                    case "name":
                        shopRepository.updateName((String) v, id);
                        break;
                    case "address":
                        shopRepository.updateAddress((String) v, id);
                        break;
                    case "withdrawn":
                        shopRepository.updateWithdrawn((Boolean) v, id);
                        break;
                    case "lng":
                        shopRepository.updateLongitude((Double) v, id);
                        break;
                    case "lat":
                        shopRepository.updateLatitude((Double) v, id);
                        break;
                    case "tags":
                        shopRepository.deleteTagsById(id);

                        for (String tag : (List<String>) v) {
                            shopRepository.insertShopTag(id, tag);
                        }
                        break;
                }
            });
            return getShopFromDB(id);
        }
        else throw new NotFoundException();
    }

    @DeleteMapping("/{id}")
    public Map<String,String> deleteShop(@PathVariable Long id){
        Boolean shopExists = checkIfShopExists(id);
        if (shopExists) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AppUser appUser = userRepository.findByUsername(username);

            if (appUser.getAdmin()) {
                shopRepository.deleteShop(id);
            } else {
                shopRepository.updateWithdrawn(true, id);
            }

            Map<String, String> okMessage = new HashMap<>();
            okMessage.put("message", "ok");

            return okMessage;
        }
        else throw new NotFoundException();
    }


    private OneShopOutput getShopFromDB(Long id){
        String shop = shopRepository.findAllById(id);
        if (shop==null) {
            return null;
        }
        else {
            List<String> tags = shopRepository.findTags(id);
            String[] shopFields = shop.split(",");

            OneShopOutput oneShopOutput = new OneShopOutput();

            oneShopOutput.setId(id);
            oneShopOutput.setName(shopFields[0]);
            oneShopOutput.setAddress(shopFields[1]);
            oneShopOutput.setTags(tags);
            oneShopOutput.setWithdrawn(Boolean.valueOf(shopFields[2]));
            oneShopOutput.setLng(Double.valueOf(shopFields[3]));
            oneShopOutput.setLat(Double.valueOf(shopFields[4]));

            return oneShopOutput;
        }
    }

    private Boolean checkIfShopExists(Long id){
        String shop = shopRepository.findAllById(id);
        return shop != null;
    }

}
