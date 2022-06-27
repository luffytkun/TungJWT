package tung.com.jwt.service.impl;

;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tung.com.jwt.entity.User;
import tung.com.jwt.respository.UserRepo;
import tung.com.jwt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;



  public UserServiceImpl(UserRepo userRepo,
      PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }



  @Override
  public List<User> getAllU() {
    return userRepo.findAll();
  }

  @Override
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public User updateUser(long id, User user) {
    return null;
  }
}
