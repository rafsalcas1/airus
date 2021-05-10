package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tfgrafsalcas1.airus.api.StateVectorsApi;
import com.tfgrafsalcas1.airus.documents.StateVector;
import com.tfgrafsalcas1.airus.documents.StateVectors;
import com.tfgrafsalcas1.airus.services.StateVectorService;
import com.tfgrafsalcas1.airus.services.StateVectorsService;

import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableScheduling
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

	@GetMapping(value = "/stateVector/list")
	public static String listStateVectors(final Map<String, Object> model) throws IOException {
		Date date = new Date(2021, 04, 28, 22, 10);
		List<StateVectors> states = new ArrayList<>();
		for (StateVectors s : stateVectorsService.listStateVectors()){
			if(s.getTime().compareTo(date)<0){
				states.add(s);
			}
		}
		model.put("states", states);
		return VIEWS_LIST_STATEVECTORS;
	}

	@Scheduled(fixedRate = 30000)
    public void saveStateVectors() throws IOException {
        StateVectors states = new StateVectorsApi("Salas", "rapols07").getStates(0, null);
		Collection<StateVector> list = states.getStateVector();
        System.out.println("Number of states: " + states.getStateVector().size());
		for(StateVector state: list){
			stateVectorService.saveStateVector(state);
		} 
		stateVectorsService.saveStateVectors(states);
		LocalDateTime time = LocalDateTime.now();
        System.out.println("Hello!!! It is: " + time);
    }

}
