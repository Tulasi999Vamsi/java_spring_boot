package com.expenses.Dao;

import com.expenses.Models.User;
import com.expenses.Models.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
    UserEntity findByUsername(String username);
    
    @Query("SELECT u.id FROM UserEntity u WHERE u.username = :username")
    Long findIdByUsername(@Param("username") String username);

}
