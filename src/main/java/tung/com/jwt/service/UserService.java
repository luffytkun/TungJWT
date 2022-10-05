package tung.com.jwt.service;

import java.util.List;
import tung.com.jwt.entity.User;

public interface UserService {
  List<User> getAllU();
  String save(User user);

  User updateUser(long id, User user);
}
