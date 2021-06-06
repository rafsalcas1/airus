package com.tfgrafsalcas1.airus.controllers;

import com.tfgrafsalcas1.airus.services.StateVectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StateVectorController {

    private StateVectorService stateVectorService;

    @Autowired
	public StateVectorController(StateVectorService stateVectorService) {
		this.stateVectorService = stateVectorService;
	}
    
}
