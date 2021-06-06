package com.tfgrafsalcas1.airus.repositories;

import java.util.Date;
import java.util.List;

import com.tfgrafsalcas1.airus.documents.StateVectors;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StateVectorsRepository extends MongoRepository<StateVectors, String> {
    
    StateVectors findByTime(Date time);
    //@Query(value = "{}", sort = "{ time : -1 }")
    List<StateVectors> findAllOrderByTime();
}
