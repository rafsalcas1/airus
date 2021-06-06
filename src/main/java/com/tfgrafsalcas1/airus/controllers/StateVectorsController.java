package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.tfgrafsalcas1.airus.api.StateVectorsApi;
import com.tfgrafsalcas1.airus.documents.StateVector;
import com.tfgrafsalcas1.airus.documents.StateVectors;
import com.tfgrafsalcas1.airus.services.AvionService;
import com.tfgrafsalcas1.airus.services.StateVectorService;
import com.tfgrafsalcas1.airus.services.StateVectorsService;

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
	private static final String VIEWS_SHOW_STATEVECTORS = "state_vectors/mostrar_state_vectors";
	private static final String VIEWS_DIRECT_STATEVECTORS = "map";
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
	public static String listaStateVectors(Model model, String begin, String end) throws IOException {
		try{
			if(begin!=null && end!=null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
				Date t = dateFormat.parse(begin);
				Date t1 = dateFormat.parse(end);
				List<StateVectors> states = new ArrayList<>();
				for (StateVectors s : stateVectorsService.listStateVectors()){
					if(s.getTime().compareTo(t)>0 && s.getTime().compareTo(t1)<0){
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

	@GetMapping(value = "/stateVectors/mostrar")
	public static String mostrarStateVectors(Model model, String time) throws IOException, ParseException {
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
		Date t = dateFormat.parse(time);
		try{
			if(t!=null){
				Collection<StateVector> states = stateVectorsService.getStateVectorsTime(t).getStateVector();
				model.addAttribute("states", states);
			}
			return VIEWS_SHOW_STATEVECTORS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

	@GetMapping(value = "/stateVectors/directo")
	public static String directoStateVectors(Model model) throws IOException, ParseException {
		try{
			Collection<StateVector> states = stateVectorsService.getStateVectorsDirecto().getStateVector();
			model.addAttribute("tiempo", stateVectorsService.getStateVectorsDirecto().getTime());
			model.addAttribute("states", states);
			return VIEWS_DIRECT_STATEVECTORS;
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
			System.out.println("Guardado");
			stateVectorsService.saveStateVectors(states);
		}
		catch(Exception e) {
			System.out.println("No se han podido guardar estados de vectores. Mensaje de error:" + e.getMessage());
		}
    }
*/
}
