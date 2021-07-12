package notebook.backend.security.jwt;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import notebook.backend.security.service.UserDetailsImpl;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration-time}")
	private String jwtExpirationTime;
	
	public String generateJwtToken(Authentication authentication) throws ParseException {
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusSeconds(Long.parseLong(jwtExpirationTime)/1000L);
		Date expiration = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
	}
	
	public boolean validateJwtToken(String authToken) throws ExpiredJwtException{
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e);
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e);
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e);
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e);
		} 
		
		return false;
	}
	
}
