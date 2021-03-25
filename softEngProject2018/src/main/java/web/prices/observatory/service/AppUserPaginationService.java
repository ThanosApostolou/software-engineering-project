package web.prices.observatory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.repositories.AppUserRepository;

@Service
public class AppUserPaginationService {

    @Autowired AppUserRepository appUserRepository;

    public Page<AppUser> findAppUserJsonDataByCondition(String orderBy, String direction, int start, int count, String status){
        Pageable pageable = null;
        Page<AppUser> data = null;

        if (direction.equals("ASC")){
            pageable = PageRequest.of(start, count, Sort.by(orderBy).ascending());
        }
        if (direction.equals("DESC")) {
            pageable = PageRequest.of(start, count, Sort.by(orderBy).descending());

        }

        final Page<AppUser> appUserPage = getAppUserPage(status, pageable, data);
        return appUserPage;
    }

    public Page<AppUser> findAppUserJsonDataNotSorted(int start, int count, String status){
        Pageable pageable = PageRequest.of(start,count);
        Page<AppUser> data = null;

        final Page<AppUser> appUserPage = getAppUserPage(status, pageable, data);
        return appUserPage;
    }

    private Page<AppUser> getAppUserPage(String status, Pageable pageable, Page<AppUser> data) {
        if (status.equals("ALL")) {
            data = appUserRepository.findAll(pageable);
        }
        if (status.equals("ENABLED")){
            data = appUserRepository.findByIsEnabled(Boolean.TRUE, pageable);
        }
        if (status.equals("DISABLED")){
            data = appUserRepository.findByIsEnabled(Boolean.FALSE, pageable);
        }

        return data;
    }
}
