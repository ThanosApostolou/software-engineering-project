package web.prices.observatory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.prices.observatory.models.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
    AppUser findByJwt(String jwt);
    //AppUser findByEmail(String email);

}
