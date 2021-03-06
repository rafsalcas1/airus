package com.tfgrafsalcas1.airus.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Flight;
import com.tfgrafsalcas1.airus.documents.Flights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class OpenSkyFlightDeserializer extends StdDeserializer<Flights> {
	
	public OpenSkyFlightDeserializer() {
		super(Flights.class);
	}

	private Collection<Flight> deserializeFlights(JsonParser jp) throws IOException {
		ArrayList<Flight> result = new ArrayList<>();

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.END_ARRAY) {
				break;
			}

			Flight sv = new Flight();
			System.out.println(jp.currentName());
			sv.setIcao24(jp.nextTextValue());
			System.out.println(sv.getIcao24());
			jp.nextToken();
			sv.setFirstSeen((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstDepartureAirport(jp.nextTextValue());
			jp.nextToken();
			sv.setLastSeen((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstArrivalAirport(jp.nextTextValue());
			jp.nextToken();
			sv.setCallsign(jp.nextTextValue());
			jp.nextToken();
			sv.setEstDepartureAirportHorizDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstDepartureAirportVertDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstArrivalAirportHorizDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstArrivalAirportVertDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setDepartureAirportCandidatesCount((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setArrivalAirportCandidatesCount((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));

			//consume END_OBJECT
			jp.nextToken();
			//next "START_OBJECT"
			jp.nextToken();

			result.add(sv);
		}

		return result;
	}

	@Override
    public Flights deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		System.out.println("b");
		System.out.println(jp.getCurrentToken());
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_ARRAY) {
			throw dc.mappingException(Flights.class);
		}
		System.out.println("3");
		try {
			Flights res = new Flights();
			System.out.println("3");
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_ARRAY; jp.nextToken()) {
				res.setFlights(deserializeFlights(jp));
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(Flights.class);
		}
	}

}
