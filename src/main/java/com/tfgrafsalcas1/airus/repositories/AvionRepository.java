package com.tfgrafsalcas1.airus.repositories;

import com.tfgrafsalcas1.airus.documents.Avion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvionRepository  extends MongoRepository<Avion, String> {
    Avion findByIcao24(String icao24);
}
