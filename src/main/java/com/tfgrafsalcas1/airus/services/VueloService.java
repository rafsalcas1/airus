package com.tfgrafsalcas1.airus.services;

import java.util.List;

import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.documents.Vuelos;
import com.tfgrafsalcas1.airus.repositories.VueloRepository;
import com.tfgrafsalcas1.airus.repositories.VuelosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VueloService {
    
	private static VueloRepository flightRepository;
	private static VuelosRepository flightsRepository;

	@Autowired
	public VueloService(VueloRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Transactional
	public void saveFlight(Vuelo flight) throws DataAccessException {
		flightRepository.save(flight);
	}

	public List<Vuelo> vuelosUsuario(String nombre) {
		//Vuelos vuelos = flightsRepository.findAll().stream().filter(x->x.getUsuario().equals(nombre));
		return null;
	}
    
}
