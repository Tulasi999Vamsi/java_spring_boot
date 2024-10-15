package com.expenses.Dao;
import com.expenses.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {
   
    User findByUsername(String username);
}
