package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.StateVectors;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateVectorsRepository extends MongoRepository<StateVectors, String> {
    
}
