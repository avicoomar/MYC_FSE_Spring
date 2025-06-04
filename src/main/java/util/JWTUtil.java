package util;

import java.util.Map;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

@Component
public class JWTUtil{
	
	String secret = "Sensationjaisesarsarahatsansanahaatgudgudahaatdagmaghaaatfararahaatthartharahatkapkakahaatjhatpatahaatjhanjhanahatkampkampahathilmilahatchamchamahatbijlibahatchanchalahatkampanahat";
	
	public String createToken(Map<String, Object> claims){
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, secret).compact();
	}
}
