package tung.com.jwt.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tung.com.jwt.entity.CustomUserDetails;

@Component
@Slf4j
public class JwtTokenProvider {
    // đoạn JWT_SECRET là bí mật,  chỉ phía server mới biết
    private final String JWT_SECRET = "Tungk";

    //thời gian có hiệu lực của jwt
    private final long JWT_EXPIRATION = 604800000L;

    // tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetail){
        Date now = new Date();
        Date expriDate = new Date(now.getTime() + JWT_EXPIRATION);
        //tạo ra chuỗi json từ web token từ id của user
        return Jwts.builder()
                .setSubject(Long.toString(userDetail.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expriDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
     //lấy thông tin user từ jwt
    public Long getUserIdFromJWT( String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;

    }


}
