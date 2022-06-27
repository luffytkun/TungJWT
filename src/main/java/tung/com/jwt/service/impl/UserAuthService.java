package tung.com.jwt.service.impl;


import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tung.com.jwt.entity.CustomUserDetails;
import tung.com.jwt.entity.User;
import tung.com.jwt.respository.UserRepo;

@Service
@Slf4j
public class UserAuthService implements UserDetailsService {

  @Autowired
  UserRepo userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User inDB = userRepository.findByUsername(username);

    if (inDB == null) {
      log.error("User not found");
      throw new UsernameNotFoundException("User not found");
    } else {
      log.info("user found ", username);
      return new CustomUserDetails(inDB);
    }

  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> {
          log.error("User not found with id : " + id);
          return new UsernameNotFoundException("User not found with id : " + id);
        }
    );
    log.info("user found ", user);
    return new CustomUserDetails(user);

  }

}
