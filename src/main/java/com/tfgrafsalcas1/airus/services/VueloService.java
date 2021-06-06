package com.tfgrafsalcas1.airus.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.repositories.VueloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VueloService {
    
	private static VueloRepository flightRepository;

	@Autowired
	public VueloService(VueloRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Transactional
	public void saveFlight(Vuelo flight) throws DataAccessException {
		flightRepository.insert(flight);
	}

	public Collection<Vuelo> listVuelos() {
		Collection<Vuelo> vuelos = flightRepository.findAll();
		return vuelos;
	}
	
	public List<Vuelo> getVuelosAvion(Avion avion) {
		List<Vuelo> vuelos = flightRepository.findAllByAvion(avion);
		return vuelos;
	}	
	
	public Vuelo getVuelo(String id) {
		Vuelo vuelo = flightRepository.getById(id);
		return vuelo;
	}
    
}
