package web.prices.observatory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import web.prices.observatory.models.AppUser;


@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUser,Long> {

    @Override
    Page<AppUser> findAll(Pageable pageable);

    Page<AppUser> findByIsEnabled(Boolean isEnabled, Pageable pageable);

}
