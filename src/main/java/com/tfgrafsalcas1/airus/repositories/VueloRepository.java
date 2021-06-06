package com.tfgrafsalcas1.airus.repositories;

import java.util.List;

import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Vuelo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VueloRepository extends MongoRepository<Vuelo, String> {
    List<Vuelo> findAllByAvion(Avion avion);
    Vuelo getById(String id);
}
