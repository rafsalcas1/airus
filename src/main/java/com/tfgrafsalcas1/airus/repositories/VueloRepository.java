package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.Vuelo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VueloRepository extends MongoRepository<Vuelo, String> {
    
}
