package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/entrepreneur")
public class EntrepreneurController{
		
	@GetMapping("/testEntrepreneur")
	@ResponseBody	
	public String testEntrepreneur(){
		return "Test Entrepreneur";
	}
	
	@GetMapping("/investors")
	@ResponseBody	
	public String getEntrepreneurInvestors(){
		//TODO: List Investors & their contact details that are ready to invest in a entrepreneur's company 
		return "All the investors registered at MYC that wanna invest";
	}
	
	@GetMapping("/companies")
	@ResponseBody	
	public String getEntrepreneurCompanies(){
		//TODO:List the particular entrepreneur's companies
		return "All the companies owned by the Entrepreneur";
	}
	
}
