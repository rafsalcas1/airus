package com.tfgrafsalcas1.airus.services;

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
		rastroRepository.save(rastro);
	}

	public List<Rastro> getRastroAvion(Avion avion) {
		List<Rastro> rastros = rastroRepository.getAllByAvion(avion);
		return rastros;
	}
}
