package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tfgrafsalcas1.airus.api.FlightApi;
import com.tfgrafsalcas1.airus.api.StateVectorsApi;
import com.tfgrafsalcas1.airus.documents.Perfil;
import com.tfgrafsalcas1.airus.documents.StateVectors;
import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.documents.Vuelos;
import com.tfgrafsalcas1.airus.services.VueloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VueloController {

	private static final String VIEWS_SAVE_FLIGHTS = "vuelos/guardar";
	private static final String VIEWS_SEARCH_FLIGHTS = "vuelos/buscar";

	private static VueloService flightService;

	@Autowired
	public VueloController(VueloService flightService) {
		this.flightService = flightService;
	} 
	
	@GetMapping(value = "/vuelos/buscar")
	public static String searchFlights(Model model) {
		return VIEWS_SEARCH_FLIGHTS;
	}

	@PostMapping(value = "/vuelos/guardar")
	public static String saveFlights(Model model, @ModelAttribute("vuelos") Perfil perfil) throws IOException {
        Vuelos flights = new FlightApi().getFlights(1517227200, 1517230800);
        System.out.println("Number of flights: " + flights.getFlights().size());
		Collection<Vuelo> list = flights.getFlights(); 
		for(Vuelo flight: list){
			flightService.saveFlight(flight);
		}
		return VIEWS_SAVE_FLIGHTS;
	}
}
