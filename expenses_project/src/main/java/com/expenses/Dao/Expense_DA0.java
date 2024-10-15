package com.expenses.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expenses.Models.Expense;

public interface Expense_DA0 extends JpaRepository<Expense,Long> {
	

	 @Query("SELECT e FROM Expense e WHERE e.user.id = :userId")
     List<Expense> findByUserId(@Param("userId") Long userId);
	
}
