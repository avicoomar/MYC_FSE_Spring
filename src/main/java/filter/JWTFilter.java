package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		
		//If request for /auth, then skip jwt check & allow request to go to next Filter
		if(((HttpServletRequest)request).getRequestURI().startsWith("/MYC_FSE_Spring-1/frontcontroller/auth/")){	
			chain.doFilter(request, response);
			return;
		}
		
		String secret = "Sensationjaisesarsarahatsansanahaatgudgudahaatdagmaghaaatfararahaatthartharahatkapkakahaatjhatpatahaatjhanjhanahatkampkampahathilmilahatchamchamahatbijlibahatchanchalahatkampanahat";
		
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		try{
			String signatureRecieved = token.split("\\.")[2];
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			//Header Received
			byte[] headerDecoded = Base64.getDecoder().decode(token.split("\\.")[0]);
			String header = new String(headerDecoded,StandardCharsets.UTF_8);
			Map<String,Object> headerMap = mapper.readValue(header, Map.class);
			
			//Payload Received
			byte[] payloadDecoded = Base64.getDecoder().decode(token.split("\\.")[1]);
			String payload = new String(payloadDecoded,StandardCharsets.UTF_8);
			Map<String,Object> payloadMap = mapper.readValue(payload, Map.class);
			
			//Signature Algorithm Received
			SignatureAlgorithm sa = SignatureAlgorithm.forName((String)headerMap.get("alg"));
			
			
			String signatureCalculated = (Jwts.builder().addClaims(payloadMap).signWith(sa,secret).compact()).split("\\.")[2];
			
			
			if(signatureRecieved.equals(signatureCalculated) &&
				token.length()>0 && 
				(((System.currentTimeMillis()/1000)) - (Integer)payloadMap.get("iat") <= 180)){
				
				chain.doFilter(request, response); //allow request to go to next filter
				return;
					
			}			
			else{
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
		}
		catch(Exception e){
			System.out.println(e);
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}
}
