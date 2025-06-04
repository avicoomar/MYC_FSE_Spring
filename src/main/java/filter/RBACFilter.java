package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Base64;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RBACFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		
		//If request for /auth, then skip rbac check & allow request to go to next Filter (also done to avoid null pointer exception in token.split line below
		if(((HttpServletRequest)request).getRequestURI().startsWith("/MYC_FSE_Spring-1/frontcontroller/auth/")){	
			chain.doFilter(request, response);
			return;
		}
		
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		byte[] payloadDecoded = Base64.getDecoder().decode(token.split("\\.")[1]);
		String payload = new String(payloadDecoded,StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> payloadMap = mapper.readValue(payload, Map.class);
		String role = (String)(payloadMap.get("role"));
		

		if(((HttpServletRequest)request).getRequestURI().startsWith("/MYC_FSE_Spring-1/frontcontroller/investor/")){ 
			if(role.equalsIgnoreCase("INVESTOR")){
				chain.doFilter(request, response);
				return;
			}
			else{
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		if(((HttpServletRequest)request).getRequestURI().startsWith("/MYC_FSE_Spring-1/frontcontroller/entrepreneur/")){ 
			if(role.equalsIgnoreCase("ENTREPRENEUR")){
				chain.doFilter(request, response);
				return;
			}
			else{
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		chain.doFilter(request, response); //allow all other requests apart from /investor & /entrepreneur to go through

	}
}
