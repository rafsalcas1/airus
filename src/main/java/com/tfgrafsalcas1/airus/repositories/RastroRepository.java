package com.tfgrafsalcas1.airus.repositories;

import java.util.List;

import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Rastro;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RastroRepository  extends MongoRepository<Rastro, String> {

    List<Rastro> getAllByAvion(Avion avion);
    Rastro getById(String id);
    
}
