package web.prices.observatory.restcontrollers;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.prices.observatory.Exceptions.NotAuthorized;
import web.prices.observatory.Exceptions.NotFoundException;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.models.AppUserOutput;
import web.prices.observatory.repositories.UserRepository;
import web.prices.observatory.repositories.AppUserRepository;
import web.prices.observatory.service.AppUserPaginationService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static web.prices.observatory.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/observatory/api")
public class UsersRestController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private  AppUserPaginationService appUserPaginationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users/sign_up")
    public Map<String, String> SignUpNewUser(@RequestBody AppUser appUser){
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setAdmin(false);
        appUser.setEnabled(true);
        userRepository.save(appUser);
        
        Map<String, String> okMessage = new HashMap<>();
        okMessage.put("message","ok");

        return okMessage;
    }

//    @PostMapping("/login")
//    public Map<String, String> loginUser(@RequestBody List<String> info){
//        AppUser appUser = userRepository.findByUsername(info.get(0));
//        Map<String,String> returnToken = new HashMap<>();
//
//        returnToken.put("token", TOKEN_PREFIX+appUser.getJwt());
//
//        return returnToken;
//    }

    @PostMapping("/logout")
    public Map<String, String> logoutUser(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser appUser = userRepository.findByUsername(username);

        appUser.setJwt("");
        userRepository.save(appUser);
        Map<String, String> okMessage = new HashMap<>();
        okMessage.put("message","ok");

        return okMessage;
    }

    @GetMapping("/users")
    public AppUserOutput getAppUser(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                    @RequestParam(name = "count", defaultValue = "20") Integer count,
                                    @RequestParam(name = "status", defaultValue = "ENABLED") String status,
                                    @RequestParam(name = "sort", required = false) String sort){

        //find current username
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get user by username
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser!=null) {
            if (appUser.getAdmin()) {
                Page<AppUser> result;
                if (sort != null) {
                    String[] sortParams = sort.split("\\|");
                    String sortBy = sortParams[0];
                    String direction = sortParams[1];
                    result = appUserPaginationService.findAppUserJsonDataByCondition(sortBy, direction, start, count, status);
                } else {
                    result = appUserPaginationService.findAppUserJsonDataNotSorted(start, count, status);
                }

                return new AppUserOutput(result.getPageable().getPageNumber(),
                        result.getPageable().getPageSize(), result.getTotalElements(), result.getContent());
            } else throw new NotAuthorized();
        }
        else throw new NotAuthorized();
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> deleteUser(@PathVariable Long id){
        //find current username
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get user by username
        AppUser appUser = userRepository.findByUsername(username);
        Optional<AppUser> userOpt;
        if (appUser!=null) {
            if (appUser.getAdmin()) {
                userOpt = userRepository.findById(id);
                if (userOpt.isPresent()) {
                    AppUser user = userOpt.get();

                    user.setEnabled(false);
                    userRepository.save(user);

                    Map<String, String> okMessage = new HashMap<>();
                    okMessage.put("message", "ok");
                    return okMessage;
                } else throw new NotFoundException();
            } else throw new NotAuthorized();
        }
        else throw new NotFoundException();
    }

}
