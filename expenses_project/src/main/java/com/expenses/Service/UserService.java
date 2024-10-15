package com.expenses.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.Dao.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    
    public Long findUserId(String name)
    {
    	return userRepo.findIdByUsername(name);
    }
    
   
}
