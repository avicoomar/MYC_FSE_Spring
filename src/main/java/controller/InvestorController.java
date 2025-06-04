package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/investor")
public class InvestorController{
		
	@GetMapping("/testInvestor")
	@ResponseBody	
	public String testInvestor(){
		return "Test Investor";
	}
	
	@GetMapping("/companies")
	@ResponseBody	
	public String getAllCompanies(){
		//TODO: List Companies that investors can invest in & their POCs - Entrepreneurs 
		return "ALl the listed companies at MYC";
	}
	
}
