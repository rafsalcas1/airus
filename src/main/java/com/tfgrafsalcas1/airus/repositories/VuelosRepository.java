package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.Vuelos;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VuelosRepository extends MongoRepository<Vuelos, String> {
    
}