package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.Map;

import com.tfgrafsalcas1.airus.api.PlaneApi;
import com.tfgrafsalcas1.airus.documents.Plane;
import com.tfgrafsalcas1.airus.services.PlaneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaneController {

	private static final String VIEWS_SAVE_PLANE = "planes/guardar";

	private static PlaneService planeService;

	@Autowired
	public PlaneController(PlaneService planeService) {
		this.planeService = planeService;
	}
    
    @GetMapping(value = "/rastro/guardar")
	public static String listPlane(final Map<String, Object> model, String nombre) throws IOException {
		Plane plane = new PlaneApi().getPlane(1619910247, "ade18c");
        System.out.println("Number of waypoints: " + plane.getPath().size());
	    planeService.savePlane(plane);
		return VIEWS_SAVE_PLANE;
	}
}
