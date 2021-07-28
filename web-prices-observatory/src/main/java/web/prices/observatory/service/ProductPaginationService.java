package web.prices.observatory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.prices.observatory.models.Product;
import web.prices.observatory.repositories.ProductRepository;

@Service
public class ProductPaginationService {

    @Autowired ProductRepository productRepository;

    public Page<Product> findJsonDataByCondition(String orderBy, String direction, int start, int count, String status){
        Pageable pageable = null;
        Page<Product> data = null;

        if (direction.equals("ASC")){
            pageable = PageRequest.of(start, count, Sort.by(orderBy).ascending());
        }
        if (direction.equals("DESC")) {
            pageable = PageRequest.of(start, count, Sort.by(orderBy).descending());
        }

        return getProductPage(status, pageable, data);
    }

    public Page<Product> findJsonDataNotSorted(int start, int count, String status){
        Pageable pageable = PageRequest.of(start,count);
        Page<Product> data = null;

        return getProductPage(status, pageable, data);
    }

    private Page<Product> getProductPage(String status, Pageable pageable, Page<Product> data) {
        if (status.equals("ALL")) {
            data = productRepository.findAll(pageable);
        }
        if (status.equals("WITHDRAWN")){
            data = productRepository.findByWithdrawn(Boolean.TRUE, pageable);
        }
        if (status.equals("ACTIVE")){
            data = productRepository.findByWithdrawn(Boolean.FALSE, pageable);
        }

        return data;
    }
}
