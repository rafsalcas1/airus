package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.tfgrafsalcas1.airus.api.StateVectorsApi;
import com.tfgrafsalcas1.airus.documents.StateVector;
import com.tfgrafsalcas1.airus.documents.StateVectors;
import com.tfgrafsalcas1.airus.services.StateVectorService;
import com.tfgrafsalcas1.airus.services.StateVectorsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StateVectorsController {

	private static final String VIEWS_LIST_STATEVECTORS = "state_vectors/list_state_vectors";
	private static final String VIEWS_FORM_STATEVECTORS = "state_vectors/form_state_vectors";
	private static final String VIEWS_SAVE_STATEVECTORS = "state_vectors/save_state_vectors";

	private static StateVectorsService stateVectorsService;
	private static StateVectorService stateVectorService;

	@Autowired
	public StateVectorsController(StateVectorsService stateVectorsService, StateVectorService stateVectorService) {
		this.stateVectorsService = stateVectorsService;
		this.stateVectorService = stateVectorService;
	}

	@GetMapping(value = "/stateVectors/save")
	public static String formStateVectors(final Map<String, Object> model) {
		StateVectors stateVectors = new StateVectors();
		model.put("stateVectors", stateVectors);
		return VIEWS_FORM_STATEVECTORS;
	}

	@PostMapping(path = "/stateVectors/save")
	public String saveStateVectors(final StateVectors stateVectors) {
		this.stateVectorsService.saveStateVectors(stateVectors);
		return VIEWS_SAVE_STATEVECTORS;
	}

	@GetMapping(value = "/stateVector/list")
	public static String listStateVectors(final Map<String, Object> model) throws IOException {
        StateVectors states = new StateVectorsApi("Salas", "rapols07").getStates(0, null);
		Collection<StateVector> list = states.getStateVector();
        System.out.println("Number of states: " + states.getStateVector().size());
		for(StateVector state: list){
			stateVectorService.saveStateVector(state);
		} 
		stateVectorsService.saveStateVectors(states);
		return VIEWS_LIST_STATEVECTORS;
	}
    
}
