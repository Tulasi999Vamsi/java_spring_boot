package com.expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.Dao.UserRepository;
import com.expenses.Dao.UserRepository2;
import com.expenses.Models.Expense;
import com.expenses.Models.User;
import com.expenses.Models.UserEntity;
import com.expenses.Service.Expense_Service;
import com.expenses.Service.UserService;

@Controller
public class Expense_Controller
{
	@Autowired
	private Expense_Service exp_service;
	
	@Autowired
	private UserService user_serv;
	
	@Autowired
	private UserRepository2 userRepo;
	

	


	 @PostMapping("/save")
	    public String save(@ModelAttribute Expense expense, Model model) {
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    
		    // Retrieve the user entity
		    User currentUser = userRepo.findByUsername(username);
		    if (currentUser != null) {
		        expense.setUser(currentUser); // Set the user for the expense
		        exp_service.save(expense); // Save the expense
		    }
		    else
		    {
		    	System.out.println("user not there");
		    }


	       
	        return "redirect:/ByUser"; 
	    }
	
	@GetMapping("/delete")
	public String delete(@RequestParam("expense_id") Long expenseId)
	{
		exp_service.delete(expenseId);
		return "redirect:/ByUser";
	}
	

//	
	@GetMapping("/addExpense")
	public String add(Model model)
	{
		Expense exp=new Expense();
		model.addAttribute("newExpense",exp);
		return "addExpense";
		
	}
	@GetMapping
	public String home()
	{
		return "redirect:/login";
	}


	    
	  @GetMapping("/ByUser")
	    public String getUserExpenses(Model model) {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	 String username = authentication.getName(); 
	    	 Long cId=user_serv.findUserId(username);
	    	
	   
	    

	        // Fetch the user's expenses from the service
	        List<Expense> expenses = exp_service.getExpensesForUser(cId);
	        double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();

	        // Add expenses and total amount to the model
	        model.addAttribute("expenses", expenses);
	        model.addAttribute("totalAmount", totalAmount);

	        return "ExpensesDisplay"; 
	    }


}
