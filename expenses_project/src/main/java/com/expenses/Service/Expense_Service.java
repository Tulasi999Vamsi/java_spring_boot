package com.expenses.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.Dao.*;
import com.expenses.Models.Expense;

@Service
public class Expense_Service 
{
	@Autowired
	private Expense_DA0 ex_dao;
	
	
	public Expense save(Expense expense)
	{
		return ex_dao.save(expense);
	}
	
	public void delete(Long id)
	{
		ex_dao.deleteById(id);
		
	}
	public List<Expense>findall()
	{
		return ex_dao.findAll();
	}
	
	 public List<Expense> getExpensesForUser(Long userId)
	 {
	        return ex_dao.findByUserId(userId); 
	 }
	

}
