package tung.com.jwt.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tung.com.jwt.config.JwtTokenProvider;

import tung.com.jwt.entity.CustomUserDetails;
import tung.com.jwt.entity.LoginRequest;
import tung.com.jwt.entity.LoginResponse;
import tung.com.jwt.entity.User;
import tung.com.jwt.service.UserService;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  JwtTokenProvider tokenProvider;

  @Autowired
  AuthenticationManager authenticationManager;

  @PostMapping( "/logina")
  public LoginResponse authenticateUser( @RequestBody LoginRequest loginRequest) {

    // Xác thực từ username và password.
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUserName(),
            loginRequest.getPassword()
        )
    );

    // Nếu không xảy ra exception tức là thông tin hợp lệ
    // Set thông tin authentication vào Security Context
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Trả về jwt cho người dùng.
    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
    return new LoginResponse(jwt);
  }


  @PostMapping("/user")
  public User createUser(@RequestBody User user) {
    return this.userService.save(user);
  }


  @GetMapping(value = "/getAllDa")
  public List<User> getAll(){
    return userService.getAllU();
  }


}
