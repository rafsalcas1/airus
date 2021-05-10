package com.tfgrafsalcas1.airus.services;

import java.util.Collection;
import java.util.List;

import com.tfgrafsalcas1.airus.documents.StateVectors;
import com.tfgrafsalcas1.airus.repositories.StateVectorsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateVectorsService {
    
	private static StateVectorsRepository stateVectorsRepository;

	@Autowired
	public StateVectorsService(StateVectorsRepository stateVectorsRepository) {
		this.stateVectorsRepository = stateVectorsRepository;
	}

	@Transactional
	public void saveStateVectors(StateVectors stateVectors) throws DataAccessException {
		stateVectorsRepository.save(stateVectors);
	}

	@Transactional
	public Collection<StateVectors> listStateVectors() throws DataAccessException {
		return stateVectorsRepository.findAll();
	}
    
}
