package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.WayPoint;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WayPointRepository extends MongoRepository<WayPoint, String> {
    
}
