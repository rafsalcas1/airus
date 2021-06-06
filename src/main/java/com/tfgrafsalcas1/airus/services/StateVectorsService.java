package com.tfgrafsalcas1.airus.services;

import java.util.Collection;
import java.util.Date;

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
		stateVectorsRepository.insert(stateVectors);
	}

	@Transactional
	public Collection<StateVectors> listStateVectors() throws DataAccessException {
		return stateVectorsRepository.findAll();
	}

	@Transactional
	public StateVectors getStateVectorsTime(Date t) throws DataAccessException {
		return stateVectorsRepository.findByTime(t);
	}

	@Transactional
	public StateVectors getStateVectorsDirecto() throws DataAccessException {
		return stateVectorsRepository.findAll().stream().findFirst().get();
	}
    
}
