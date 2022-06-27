package tung.com.jwt.entity;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
