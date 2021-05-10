package com.tfgrafsalcas1.airus.services;

import com.tfgrafsalcas1.airus.documents.Plane;
import com.tfgrafsalcas1.airus.repositories.PlaneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaneService {
    
	private static PlaneRepository planeRepository;

	@Autowired
	public PlaneService(PlaneRepository planeRepository) {
		this.planeRepository = planeRepository;
	}

	@Transactional
	public void savePlane(Plane plane) throws DataAccessException {
		planeRepository.save(plane);
	}
}
