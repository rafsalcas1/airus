package com.tfgrafsalcas1.airus.services;

import java.util.Date;
import java.util.List;

import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Rastro;
import com.tfgrafsalcas1.airus.repositories.RastroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RastroService {
    
	private static RastroRepository rastroRepository;

	@Autowired
	public RastroService(RastroRepository rastroRepository) {
		this.rastroRepository = rastroRepository;
	}

	@Transactional
	public void saveRastro(Rastro rastro) throws DataAccessException {
		rastroRepository.insert(rastro);
	}

	public List<Rastro> getRastroAvion(Avion avion) {
		List<Rastro> rastros = rastroRepository.getAllByAvion(avion);
		return rastros;
	}

	public Rastro getRastro(String id) {
		Rastro rastro = rastroRepository.getById(id);
		return rastro;
	}

    public Rastro existeRastro(String icao24, Date t) {
		try{
			Rastro rastro = rastroRepository.findAll().stream().filter(x->x.getAvion().getIcao24().equals(icao24)).filter(x->x.getStarTime().equals(t)).findFirst().get();
			return rastro;
		}catch(Exception e) {
			Rastro rastro = null;
			return rastro;
		}
    }
}
