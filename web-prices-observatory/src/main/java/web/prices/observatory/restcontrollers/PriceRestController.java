package web.prices.observatory.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import web.prices.observatory.Exceptions.BadRequest;
import web.prices.observatory.models.*;
import web.prices.observatory.repositories.PriceRepository;
import web.prices.observatory.repositories.ProductRepository;
import web.prices.observatory.repositories.ShopRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/observatory/api/prices")
public class PriceRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("")
    public PriceOutput getPrices(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "count", defaultValue = "20") Integer count,
                                 @RequestParam(name = "geoDist", required = false) Integer geoDist,
                                 @RequestParam(name = "geoLng", required = false) Double geoLng,
                                 @RequestParam(name = "geoLat", required = false) Double geoLat,
                                 @RequestParam(name = "dateFrom", required = false )
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                 @RequestParam(name = "dateTo", required = false )
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                 @RequestParam(name = "shops",required = false) List<String> shops,
                                 @RequestParam(name = "products", required = false) List<String> products,
                                 @RequestParam(name = "tags", required = false) List<String> tags,
                                 @RequestParam(name = "sort", defaultValue = "price|ASC") String sort){

        List<Object[]> prices;
        List<OnePriceOutput> outputPrices = new ArrayList<>();

        String[] sortParams = sort.split("\\|");
        String sortBy = sortParams[0];
        String direction = sortParams[1];

        String shopList = getListOfIds(shops);
        String productList = getListOfIds(products);
        String tagList = getListOfTags(tags);

        long total = 0;

        int offset = 0;
        if (start != 0) {
            offset = start*count;
        }

        if ((dateFrom!= null && dateTo ==null)||(dateTo!= null && dateFrom ==null)){throw new BadRequest();}
        else if (dateFrom==null && dateTo==null) {
            dateFrom = LocalDate.now();
            dateTo = LocalDate.now();
        }

        if (geoDist!=null && geoLat!=null && geoLng!=null){//queries with distance calculation
            String refPoint = "ST_GeomFromText(\'POINT ("+geoLat+" "+geoLng +" )\', 4326)";
            String queryFindWithDist = "SELECT t.price, t.product_id as productId, p.name as productName," +
                    "t.shop_id,s.name as shopName, s.address, ST_Distance_Sphere(s.coordinates, " +refPoint+
                    "), t.date FROM prices t, products p, shops s WHERE " +
                    "ST_Distance_Sphere(s.coordinates, " +refPoint+") < "+ geoDist+ " AND "+
                    "t.product_id = p.id AND t.shop_id = s.id AND t.date>=\'"+dateFrom+"\' AND t.date<=\'"+dateTo+"\'" ;
            String queryFindTotal = "SELECT s.id FROM prices t, products p, shops s WHERE " +
                    "ST_Distance_Sphere(s.coordinates, " +refPoint+") < "+ geoDist+" AND t.product_id = p.id AND " +
                    "t.shop_id = s.id AND t.date>=\'"+dateFrom+"\' AND t.date<=\'"+dateTo+"\'";

            List<String> queries = checkTheLists(shopList, productList, tagList, queryFindWithDist, queryFindTotal);

            prices = entityManager.createNativeQuery(queries.get(0)+
                    "ORDER BY " + sortBy + " " + direction +" LIMIT "+ count +" OFFSET "+ offset).getResultList();
            total = entityManager.createNativeQuery(queries.get(1)).getResultList().size();

            for (Object[] objects: prices){
                List<String> shopTags = shopRepository.findTags(Long.valueOf(objects[3].toString()));
                List<String> productTags = productRepository.findById(Long.valueOf(objects[1].toString())).get().getTags();
                OnePriceOutput onePriceOutput = new OnePriceOutput((Double) objects[0], LocalDate.parse(objects[7].toString()),(String) objects[2],
                        Long.valueOf(objects[1].toString()), productTags, Long.valueOf(objects[3].toString()),
                        (String) objects[4], shopTags, (String) objects[5], ((Double) objects[6]).intValue());
                outputPrices.add(onePriceOutput);
            }

        }
        else if (geoDist==null && geoLat==null && geoLng==null){
            String queryFindWithoutDist = "SELECT t.price, t.product_id as productId, p.name as productName," +
            "t.shop_id,s.name as shopName, s.address, t.date FROM prices t, products p, shops s WHERE " +
                    "t.product_id = p.id AND t.shop_id = s.id AND t.date>=\'"+dateFrom+"\' AND t.date<=\'"+dateTo+"\'";
            String queryFindTotalNoDist ="SELECT s.id FROM prices t, products p, shops s WHERE " +
                    "t.product_id = p.id AND t.shop_id = s.id AND t.date>=\'"+dateFrom+"\' AND t.date<=\'"+dateTo+"\'" ;

            List<String> queries = checkTheLists(shopList, productList, tagList, queryFindWithoutDist, queryFindTotalNoDist);

            prices = entityManager.createNativeQuery(queries.get(0)+
                    "ORDER BY " + sortBy + " " + direction +" LIMIT "+ count +" OFFSET "+ offset).getResultList();
            total = entityManager.createNativeQuery(queries.get(1)).getResultList().size();

            for (Object[] objects: prices){
            List<String> shopTags = shopRepository.findTags(Long.valueOf(objects[3].toString()));
            List<String> productTags = productRepository.findById(Long.valueOf(objects[1].toString())).get().getTags();
            OnePriceOutput onePriceOutput = new OnePriceOutput((Double) objects[0],LocalDate.parse(objects[6].toString()),(String) objects[2],
                    Long.valueOf(objects[1].toString()), productTags, Long.valueOf(objects[3].toString()),
                    (String) objects[4], shopTags, (String) objects[5], null);
            outputPrices.add(onePriceOutput);
            }
        }
        else throw new BadRequest();


        return new PriceOutput(start, count, total, outputPrices);

    }

    private String getListOfIds(List<String> list){
        if (list == null) return "";
        String idList = "";
        for (String v : list){
            idList= idList + v +",";
        }
        if (idList != null) {
            idList = idList.substring(0, idList.length()-1);
        }
        return idList;
    }

    private String getListOfTags(List<String> list){
        if (list == null) return "";
        String idList = "";
        for (String v : list){
            idList= idList +"\'"+ v +"\',";
        }
        if (idList != null) {
            idList = idList.substring(0, idList.length()-1);
        }
        return idList;
    }

    private List<String> checkTheLists(String shopList, String productList, String tagList, String mainQuery,
                                       String getTotalQuery){
        List<String> returnQueries = new ArrayList<>();
        if (shopList!="") {
            mainQuery = mainQuery + " AND s.id IN ("+shopList+") ";
            getTotalQuery = getTotalQuery + " AND s.id IN ("+shopList+") ";
        }
        if (productList!=""){
            mainQuery = mainQuery + " AND p.id IN ("+productList+") ";
            getTotalQuery = getTotalQuery + " AND p.id IN (" + productList + ") ";
        }
        if (tagList!=""){
            mainQuery = mainQuery + "AND ((s.id IN (SELECT shop_id FROM shop_tags WHERE tags IN (" + tagList +"))) "+
                    "OR (p.id IN (SELECT product_id FROM product_tags WHERE tags IN ("+tagList+")))) ";
            getTotalQuery = getTotalQuery + "AND ((s.id IN (SELECT shop_id FROM shop_tags WHERE tags IN (" + tagList +"))) "+
                    "OR (p.id IN (SELECT product_id FROM product_tags WHERE tags IN ("+tagList+")))) ";
        }

        returnQueries.add(mainQuery);
        returnQueries.add(getTotalQuery);

        return returnQueries ;
    }

    @PostMapping("")
    public Map<String, String> postNewPrice(@RequestBody Map<String, Object> newPrice){
        List<OnePriceOutput> priceOutputs = new ArrayList<>();

        Double price = Double.valueOf((String) newPrice.get("price"));
        LocalDate dateFrom = LocalDate.parse(newPrice.get("dateFrom").toString());

        LocalDate dateTo = LocalDate.parse(newPrice.get("dateTo").toString());

        Long productId = Long.valueOf(newPrice.get("productId").toString());
        Long shopId = Long.valueOf(newPrice.get("shopId").toString());

        Price price1 = new Price(price,dateFrom,productId,shopId);
        Price price2 = new Price(price,dateTo,productId,shopId);

        priceRepository.save(price1);
        priceRepository.save(price2);

        Product product = productRepository.findById(productId).get();

        String query = "SELECT name, address FROM shops WHERE id = " + shopId;
        List<String[]> shopInfo = entityManager.createNativeQuery(query).getResultList();
        List<String> shopTags = shopRepository.findTags(shopId);

//        OnePriceOutput priceOutput1 = new OnePriceOutput(price, dateFrom, product.getName(),productId,
//                product.getTags(), shopId, shopInfo.get(0)[0].toString(), shopTags ,shopInfo.get(0)[1].toString(), null);
//        priceOutputs.add(priceOutput1);
//
//        OnePriceOutput priceOutput2 = new OnePriceOutput(price, dateTo, product.getName(),productId,
//                product.getTags(), shopId, shopInfo.get(0)[0], shopTags ,shopInfo.get(0)[1], null);
//        priceOutputs.add(priceOutput2);

//        return new PriceOutput(0, 20,Long.valueOf(2), priceOutputs);

        Map<String, String> okMessage = new HashMap<>();
        okMessage.put("message", "ok");

        return okMessage;

    }



}
