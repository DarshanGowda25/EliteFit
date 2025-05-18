package in.darshan.elite.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private final SecretKey sk ;


	   public JWTService(@Value("${jwt.secret}") String secretKey) {
	        this.sk = Keys.hmacShaKeyFor(secretKey.getBytes());
	    }

		
		
		


	public String genrerateToken(String email,String role) {
		Map<String, Object> claims =  new HashMap<>();
		claims.put("email",email);
		claims.put("role", role);
		
		return Jwts.builder()
		.claims()
		.add(claims)
		.subject(email)
		.issuedAt(new Date(System.currentTimeMillis()))
		.and()
		.signWith(sk)
		.compact();
				
	}

	public  String extractUserName(String token) {
		// TODO Auto-generated method stub
		Claims claims =  extractClaim(token);
		String userName = claims.getSubject();
		return userName;
	}


	public Claims extractClaim(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser()
				.verifyWith(sk)		
				.build()			 	
				.parseSignedClaims(token) 
				 .getPayload();   
				
		
	}



	public boolean validateToken(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String username = extractUserName(token);
		
		return (username.equals(userDetails.getUsername()));
	}
	
	public  String extractUserRole(String token) {
		// TODO Auto-generated method stub
		Claims claims =  extractClaim(token);
		String role = claims.get("role", String.class);
		System.out.println("role : "+ role);
		return role;
	}
	
	

	
	


	
		

}
