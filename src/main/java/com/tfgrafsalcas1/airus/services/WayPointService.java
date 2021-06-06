package com.tfgrafsalcas1.airus.services;

import com.tfgrafsalcas1.airus.documents.WayPoint;
import com.tfgrafsalcas1.airus.repositories.WayPointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WayPointService {
    
	private static WayPointRepository wayPointRepository;

	@Autowired
	public WayPointService(WayPointRepository wayPointRepository) {
		this.wayPointRepository = wayPointRepository;
	}

	@Transactional
	public void saveWayPoint(WayPoint wayPoint) throws DataAccessException {
		wayPointRepository.insert(wayPoint);
	}
    
}
