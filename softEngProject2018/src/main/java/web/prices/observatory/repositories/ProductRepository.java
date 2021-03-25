package web.prices.observatory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import web.prices.observatory.models.Product;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {

    @Override
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByWithdrawn(Boolean withdrawn, Pageable pageable);

}
