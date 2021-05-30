package com.tfgrafsalcas1.airus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tfgrafsalcas1.airus.controllers.HomeController;
import com.tfgrafsalcas1.airus.controllers.VueloController;
import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.services.VueloService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = VueloController.class)
public class VueloControllerTest {
  
	private static final String	TEST_VUELO_ID = "1";

	@Autowired
	private VueloController vueloController;
    
	@MockBean
	private VueloService vueloService;

	@Autowired
	private MockMvc	mockMvc;
    
	private Vuelo vuelo;

	@BeforeEach
	void setup() {

		this.vuelo = new Vuelo();
		this.vuelo.setId(TEST_VUELO_ID);
        this.vuelo.setIcao24("ab1234");
        this.vuelo.setFirstSeen(1621707913.0);
        this.vuelo.setLastSeen(1621708811.0);
        this.vuelo.setEstDepartureAirport("K6D6");
        this.vuelo.setEstArrivalAirport("KLAX");
        this.vuelo.setCallsign("SKW3580");
        this.vuelo.setDepartureAirportCandidatesCount(1.0);
        this.vuelo.setArrivalAirportCandidatesCount(8.0);

		BDDMockito.given(this.vueloService.getVuelo("ab1234")).willReturn(this.vuelo);

	}

    @Test
	void testVueloList1() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vuelos/")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("vuelos/vuelos"));
    }        

    @Test
	void testVueloList2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vuelos/").param("estDepartureAirport", "K6D6")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("vuelos")).andExpect(MockMvcResultMatchers.view().name("vuelos/vuelos"));
	}  

    @Test
	void testVueloSave1() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vuelos/buscar/")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("vuelos/buscar_vuelos"));
    }        

    @Test
	void testVueloSave2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vuelos/buscar/").param("begin", "1621972800").param("end", "1621980000")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("vuelos")).andExpect(MockMvcResultMatchers.view().name("vuelos/buscar_vuelos"));
	}


}
