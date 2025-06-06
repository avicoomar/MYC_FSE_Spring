package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import repository.UserRepo;
import model.User;
import model.UsernamePassword;
import util.JWTUtil;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController{
	
	@Autowired 
	UserRepo repo;
	
	@Autowired
	JWTUtil jwtutil;
	
	@PostMapping("/signin")
	@ResponseBody	
	public String signin(@RequestBody UsernamePassword usernamePasswordReceived, HttpServletResponse response){
		
		List<User> users = repo.findByUsername(usernamePasswordReceived.getUsername());
		
		Map<String,Object> claims = new HashMap<String, Object>();
		
		for (User user : users){
			if(usernamePasswordReceived.getPassword().equals(user.getPassword())){
				
				String refreshToken = user.getRefreshToken(); //Get user's refresh token
				
				//Set a cookie called "refresh_token"
				Cookie cookie1 = new Cookie("refresh_token", refreshToken);
				cookie1.setHttpOnly(true);
				cookie1.setPath("/");
				response.addCookie(cookie1);
				
				//Set JWT Claims
				claims.put("userid", user.getUserid());	
				claims.put("role", user.getRole());
				
				return jwtutil.createToken(claims);
			}
		}
		
		return "Invalid username password";
	}
	
	@PostMapping("/signup")
	@ResponseBody	
	public String signup(@RequestBody User user, HttpServletResponse response){
		if(repo.findByUsername(user.getUsername()).size() > 0)
			return "User already exists";
		
		String refreshToken = UUID.randomUUID().toString(); //Generate refresh token
		
		//Save user along with his refresh token in db
		user.setRefreshToken(refreshToken);
		repo.save(user);

		//Set a cookie called "refresh_token"
		Cookie cookie1 = new Cookie("refresh_token", refreshToken);
		cookie1.setHttpOnly(true);
		cookie1.setPath("/");
		response.addCookie(cookie1);
				
		//Set JWT Claims
		long userid = (repo.findByUsername(user.getUsername())).get(0).getUserid();
		User.Role role = (repo.findByUsername(user.getUsername())).get(0).getRole();
		Map<String,Object> claims = new HashMap<String, Object>();
		claims.put("userid", userid);
		claims.put("role", role);
		
		return jwtutil.createToken(claims);
	}
	
	
	//API for browser(frontend) to refresh jwt token (that expired after 3 mins), since the frontend cannot always call /signin to get new JWT Token
	@GetMapping("/refresh-token")
	@ResponseBody	
	public String refreshToken(HttpServletRequest request){
		
		//Get "refresh_token" cookie
		Cookie[] cookies = request.getCookies();
		String refreshToken = "";
		for(Cookie cookie : cookies){
			if(cookie.getName().equalsIgnoreCase("refresh_token"))
				refreshToken = cookie.getValue();
		}
		
		
		List<User> users = repo.findByRefreshToken(refreshToken); //Find user by refresh_token (chances of finding multiple users are astronomically low) 
		
		//Validate refresh_token of user & return new jwt token if valid
		for(User user : users){
			if(refreshToken.equalsIgnoreCase(user.getRefreshToken())){
				Map<String,Object> claims = new HashMap<String, Object>();
				claims.put("userid", user.getUserid());
				claims.put("role", user.getRole());
				return jwtutil.createToken(claims);
			}
				
		}
		return "JWT token cannot be refreshed";
	}
	
	//API for browser(frontend) to clear it's RefreshToken cookie
	@GetMapping("/signout")
	@ResponseBody	
	public void signout(HttpServletResponse response){
		Cookie cookie1 = new Cookie("refresh_token", "");
		cookie1.setPath("/");
		cookie1.setHttpOnly(true);
		cookie1.setMaxAge(0);
		response.addCookie(cookie1);
	}
	
}
