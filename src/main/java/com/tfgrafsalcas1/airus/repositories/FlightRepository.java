package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.Flight;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, String> {
    
}
