package com.tfgrafsalcas1.airus.services;

import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.repositories.AvionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvionService {
    
	private static AvionRepository avionRepository;

	@Autowired
	public AvionService(AvionRepository avionRepository) {
		this.avionRepository = avionRepository;
	}
	
	public void saveAvion(Avion avion) {
		avionRepository.save(avion);
	}
	
	public Avion getAvion(String icao24) {
		Avion avion = avionRepository.findByIcao24(icao24);
		return avion;
	}

}
