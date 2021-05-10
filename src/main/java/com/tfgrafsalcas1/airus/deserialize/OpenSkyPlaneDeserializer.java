package com.tfgrafsalcas1.airus.deserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Plane;
import com.tfgrafsalcas1.airus.documents.WayPoints;

public class OpenSkyPlaneDeserializer extends StdDeserializer<Plane> {
    
    public OpenSkyPlaneDeserializer() {
		super(Plane.class);
	}

	private Collection<WayPoints> deserializePath(JsonParser jp) throws IOException {
		ArrayList<WayPoints> result = new ArrayList<>();
		System.out.println("7");

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.START_ARRAY) {
				continue;
			}
			if (next == JsonToken.END_OBJECT) {
				break;
            }
			WayPoints wp = new WayPoints();
			System.out.println(jp.getLongValue());
            Date time = new Date((long)(jp.getLongValue())*1000);
			System.out.println(time);
			wp.setTime(time);
			System.out.println(jp.getDoubleValue());
			wp.setLatitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			System.out.println(jp.getDoubleValue());
			wp.setLongitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			System.out.println(jp.getDoubleValue());
			wp.setBaroAltitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			System.out.println(jp.getDoubleValue());
			wp.setTrueTrack((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			System.out.println(jp.getDoubleValue());
			wp.setOnGround(jp.nextBooleanValue());
			System.out.println(jp.nextBooleanValue());
			System.out.println(jp.nextToken());

			result.add(wp);
		}
		return result;
	}

    @Override
	public Plane deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
		System.out.println("1");
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw dc.mappingException(Plane.class);
		}
		try {
			Plane res = new Plane();
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
				if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
					if ("icao24".equalsIgnoreCase(jp.getCurrentName())) {
						System.out.println("2");
						String icao24 = ((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getText() : null));
						res.setIcao24(icao24);
						System.out.println(icao24);
                    } else if ("callsign".equalsIgnoreCase(jp.getCurrentName())) {
						System.out.println("3");
						String callsign = ((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getText() : null));
						res.setCallsign(callsign);
						System.out.println(callsign);
					} else if ("startTime".equalsIgnoreCase(jp.getCurrentName())) {
						System.out.println("4");
						double t1 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
						Date startTime = new Date((long)(t1)*1000);
						res.setStarTime(startTime);
						System.out.println(startTime);
					} else if ("endTime".equalsIgnoreCase(jp.getCurrentName())) {
						System.out.println("5");
						double t2 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
						Date endTime = new Date((long)(t2)*1000);
						res.setEndTime(endTime);
						System.out.println(endTime);
					} else if ("path".equalsIgnoreCase(jp.getCurrentName())) {
						System.out.println("6");
						jp.nextToken();
						res.setPath(deserializePath(jp));
					} else {
						jp.nextToken();
					}
				}
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(Plane.class);
		}
	}
}
