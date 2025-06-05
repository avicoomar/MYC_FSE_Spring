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

@Controller
@RequestMapping("/auth")
public class AuthController{
	
	@Autowired 
	UserRepo repo;
	
	@Autowired
	JWTUtil jwtutil;
	
	@PostMapping("/signin")
	@ResponseBody	
	public String signin(@RequestBody UsernamePassword usernamePasswordReceived){
		
		List<User> users = repo.findByUsername(usernamePasswordReceived.getUsername());
		
		Map<String,Object> claims = new HashMap<String, Object>();
		
		for (User user : users){
			if(usernamePasswordReceived.getPassword().equals(user.getPassword())){
				claims.put("userid", user.getUserid());	
				claims.put("role", user.getRole());	
				return jwtutil.createToken(claims);
			}
		}
		
		return "Invalid username password";
	}
	
	@PostMapping("/signup")
	@ResponseBody	
	public String signup(@RequestBody User user){
		if(repo.findByUsername(user.getUsername()).size() > 0)
			return "User already exists";
		repo.save(user);
		Map<String,Object> claims = new HashMap<String, Object>();
		claims.put("userid", user.getUserid());
		claims.put("role", user.getRole());
		return jwtutil.createToken(claims);
	}
	
}
