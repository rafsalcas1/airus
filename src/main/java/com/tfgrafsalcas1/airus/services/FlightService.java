package com.tfgrafsalcas1.airus.services;

import com.tfgrafsalcas1.airus.documents.Flight;
import com.tfgrafsalcas1.airus.repositories.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightService {
    
	private static FlightRepository flightRepository;

	@Autowired
	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Transactional
	public void saveFlight(Flight flight) throws DataAccessException {
		flightRepository.save(flight);
	}
    
}
