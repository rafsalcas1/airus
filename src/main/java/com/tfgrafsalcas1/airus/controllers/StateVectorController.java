package com.tfgrafsalcas1.airus.controllers;

import java.util.Map;

import com.tfgrafsalcas1.airus.services.StateVectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StateVectorController {
    
    private static final String VIEWS_SHOW_STATEVECTOR = "state_vectors/show_state_vector";

    private StateVectorService stateVectorService;

    @Autowired
	public StateVectorController(StateVectorService stateVectorService) {
		this.stateVectorService = stateVectorService;
	}

	@GetMapping(value = "/stateVector/{id}")
	public static String showStateVector(final Map<String, Object> model) {
		return VIEWS_SHOW_STATEVECTOR;
	}
    
}
