package com.tfgrafsalcas1.airus.services;

import com.tfgrafsalcas1.airus.documents.StateVector;
import com.tfgrafsalcas1.airus.repositories.StateVectorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateVectorService {
    
	private static StateVectorRepository stateVectorRepository;

	@Autowired
	public StateVectorService(StateVectorRepository stateVectorRepository) {
		this.stateVectorRepository = stateVectorRepository;
	}

	@Transactional
	public void saveStateVector(StateVector stateVector) throws DataAccessException {
		stateVectorRepository.insert(stateVector);
	}
    
}
