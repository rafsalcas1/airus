package com.tfgrafsalcas1.airus.repositories;

import java.util.List;

import com.tfgrafsalcas1.airus.documents.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
    public List<User> findAll();
    public User findUserById(int id);
}
