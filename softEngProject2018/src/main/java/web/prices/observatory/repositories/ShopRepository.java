package web.prices.observatory.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.prices.observatory.models.Shop;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {

    @Query(value = "SELECT t.name, t.address, t.withdrawn, ST_Longitude(t.coordinates) AS 'lng', " +
            "ST_Latitude(t.coordinates) AS 'lat' FROM shops t WHERE t.id = :id", nativeQuery = true)
    String findAllById(@Param("id") Long id);

    @Query(value = "select tags FROM shop_tags WHERE shop_id = :id", nativeQuery = true)
    List<String> findTags(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "insert into shops (name, address, withdrawn, coordinates) values (:name, :address, :withdrawn, " +
            "ST_GEOMETRYFROMTEXT(:point, 4326 ))", nativeQuery = true)
    void insertShop(@Param("name") String name, @Param("address") String address,
                           @Param("withdrawn") Boolean withdrawn, @Param("point") String point);

    @Transactional
    @Modifying
    @Query(value = "insert into shop_tags values (:id, :tag)",nativeQuery = true)
    void insertShopTag(@Param("id") Long id, @Param("tag") String tag);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops SET name = :name, address = :address, withdrawn =  :withdrawn, " +
            "coordinates = ST_GEOMETRYFROMTEXT(:point, 4326 ) WHERE id = :id", nativeQuery = true)
    void updateShop(@Param("name") String name, @Param("address") String address,
                    @Param("withdrawn") Boolean withdrawn, @Param("point") String point, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM shop_tags WHERE shop_id = :id", nativeQuery = true)
    void deleteTagsById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops set name = :name where id = :id", nativeQuery = true)
    void updateName(@Param("name") String name, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops set address = :address where id = :id", nativeQuery = true)
    void updateAddress(@Param("address") String address, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops set withdrawn = :withdrawn where id = :id", nativeQuery = true)
    void updateWithdrawn(@Param("withdrawn") Boolean withdrawn, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops SET coordinates = ST_GEOMETRYFROMTEXT(ST_ASTEXT(ST_Longitude(coordinates, :lng)), 4326) " +
            "WHERE id = :id", nativeQuery = true)
    void updateLongitude(@Param("lng") Double lng, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shops SET coordinates = ST_GEOMETRYFROMTEXT(ST_ASTEXT(ST_Latitude(coordinates, :lat)), 4326) " +
            "WHERE id = :id", nativeQuery = true)
    void updateLatitude(@Param("lat") Double lat, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM shops WHERE id = :id", nativeQuery = true)
    void deleteShop(@Param("id") Long id);
}
