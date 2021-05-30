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
import com.tfgrafsalcas1.airus.services.AvionService;
import com.tfgrafsalcas1.airus.services.StateVectorService;
import com.tfgrafsalcas1.airus.services.StateVectorsService;

import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableScheduling
public class StateVectorsController {

	private static final String VIEWS_LIST_STATEVECTORS = "state_vectors/state_vectors";
	private static final String VIEWS_FORM_STATEVECTORS = "state_vectors/form_state_vectors";
	private static final String VIEWS_SAVE_STATEVECTORS = "state_vectors/save_state_vectors";
	private static final String ERROR = "error";


	private static StateVectorsService stateVectorsService;
	private static StateVectorService stateVectorService;
	private static AvionService avionService;

	@Autowired
	public StateVectorsController(StateVectorsService stateVectorsService, StateVectorService stateVectorService, AvionService avionService) {
		this.stateVectorsService = stateVectorsService;
		this.stateVectorService = stateVectorService;
		this.avionService = avionService;
	}

	@GetMapping(value = "/stateVectors")
	public static String listStateVectors(Model model, Date begin, Date end) throws IOException {
		try{
			if(begin!=null && end!=null){
				System.out.println(begin + " / " + end);
				List<StateVectors> states = new ArrayList<>();
				for (StateVectors s : stateVectorsService.listStateVectors()){
					if(s.getTime().compareTo(begin)>0 && s.getTime().compareTo(end)<0){
						states.add(s);
					}
				}
				model.addAttribute("states", states);
			}
			return VIEWS_LIST_STATEVECTORS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}
/*
	@Scheduled(fixedRate = 30000)
    public void saveStateVectors() throws IOException {
        try{
			StateVectors states = new StateVectorsApi("Salas", "rapols07").getStates(0, null);
			Collection<StateVector> list = states.getStateVector();
			System.out.println("Number of states: " + states.getStateVector().size());
			for(StateVector state: list){
				stateVectorService.saveStateVector(state);
			}
			for(StateVector state: list){
				if(avionService.getAvion(state.getAvion().getIcao24())==null){
					avionService.saveAvion(state.getAvion());
					stateVectorService.saveStateVector(state);
				}else{
					stateVectorService.saveStateVector(state);
				}
			}
			stateVectorsService.saveStateVectors(states);
		}
		catch(Exception e) {
			System.out.println("No se han podido guardar estados de vectores. Mensaje de error:" + e.getMessage());
		}
    }
*/
}
