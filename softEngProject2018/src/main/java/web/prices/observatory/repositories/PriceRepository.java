package web.prices.observatory.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import web.prices.observatory.models.Price;


@Repository
public interface PriceRepository extends PagingAndSortingRepository<Price,Long> {


}
