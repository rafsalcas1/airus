package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.StateVector;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateVectorRepository extends MongoRepository<StateVector, String> {
    
}
