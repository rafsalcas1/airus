package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.Plane;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaneRepository  extends MongoRepository<Plane, String> {
    
}
