package com.tfgrafsalcas1.airus.services;

import java.util.List;

import com.tfgrafsalcas1.airus.documents.User;
import com.tfgrafsalcas1.airus.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
	private static UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		userRepository.save(user);
	}

	@Transactional
	public void findUserById(int id) throws DataAccessException {
		userRepository.findUserById(id);
	}

	@Transactional
	public static List<User> findAll() throws DataAccessException {
		return userRepository.findAll();
	}
}
