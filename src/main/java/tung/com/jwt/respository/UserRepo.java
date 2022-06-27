package tung.com.jwt.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tung.com.jwt.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  @Query(nativeQuery = true, value = "select * from users where user_name like ?1")
  User findByUsername(String username);

  @Query(nativeQuery = true, value = "select * from users where id = ?1")
  User findByIda(String username);
}
