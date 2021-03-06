package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.tfgrafsalcas1.airus.api.FlightApi;
import com.tfgrafsalcas1.airus.documents.Flight;
import com.tfgrafsalcas1.airus.documents.Flights;
import com.tfgrafsalcas1.airus.services.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlightController {

	private static final String VIEWS_SAVE_FLIGHTS = "flights/save_flights";

	private static FlightService flightService;

	@Autowired
	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping(value = "/flights/save")
	public static String saveFlights(final Map<String, Object> model) throws IOException {
        Flights flights = new FlightApi().getFlights(1517227200, 1517230800);
        System.out.println("Number of flights: " + flights.getFlights().size());
		Collection<Flight> list = flights.getFlights(); 
		for(Flight flight: list){
			flightService.saveFlight(flight);
		}
		return VIEWS_SAVE_FLIGHTS;
	}
}
