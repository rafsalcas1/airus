package com.tfgrafsalcas1.airus.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tfgrafsalcas1.airus.deserialize.OpenSkyFlightDeserializer;
import com.tfgrafsalcas1.airus.documents.Flight;
import com.tfgrafsalcas1.airus.documents.Flights;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.*;

public class FlightApi {
/*
	private final boolean authenticated;

	private final ObjectMapper mapper;

	private final OkHttpClient okHttpClient;

	public FlightApi() {
		this(null, null);
	}

	public FlightApi(String username, String password) {
		mapper = new ObjectMapper();
		SimpleModule sm = new SimpleModule();
		sm.addDeserializer(Collection.class, new OpenSkyFlightDeserializer());
		mapper.registerModule(sm);

		authenticated = username != null && password != null;

		if (authenticated) {
			okHttpClient = new OkHttpClient.Builder()
					.build();
		} else {
			okHttpClient = new OkHttpClient();
		}
	}

	private Collection<Flight> getResponse(String baseUri) throws IOException {
		HttpUrl parsedUrl = HttpUrl.parse(baseUri);
		if (parsedUrl == null) {
			throw new MalformedURLException("Could not parse uri " + baseUri);
		}

		HttpUrl.Builder urlBuilder = parsedUrl.newBuilder();
		Request req = new Request.Builder()
				.url(urlBuilder.build())
				.build();

		Response response = okHttpClient.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Could not get OpenSky Flights, response " + response);
		}

		String contentType = response.header("Content-Type");
		Charset charset = null;
		if (contentType != null) {
			MediaType mediaType = MediaType.parse(contentType);
			if (mediaType != null) {
				charset = mediaType.charset();
			}
		}
		if (charset != null) {
			return mapper.readValue(new InputStreamReader(response.body().byteStream(), charset), Collection.class);
		} else {
			throw new IOException("Could not read charset in response. Content-Type is " + contentType);
		}
	}

	private Collection<Flight> getOpenSkyFlight(String baseUri) throws IOException {
		try {
			return getResponse(baseUri);
		} catch (MalformedURLException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Invalid URI. Please report a bug");
		} catch (JsonParseException | JsonMappingException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Could not parse JSON Data. Please report a bug");
		}
	}

	public Collection<Flight> getFlights(String username, String password, int begin, int end) throws IOException {
		return getOpenSkyFlight("https://"+username+":"+password+"@opensky-network.org/api/flights/all?begin="+begin+"&end="+end);
	}
*/
	private static final String HOST = "opensky-network.org";
	private static final String API_ROOT = "https://" + HOST + "/api";

	private enum REQUEST_TYPE {
		GET_STATES,
		GET_MY_STATES
	}

	private final boolean authenticated;

	private final ObjectMapper mapper;

	private final OkHttpClient okHttpClient;
	private final Map<REQUEST_TYPE, Long> lastRequestTime;

	private static class BasicAuthInterceptor implements Interceptor {
		private final String credentials;

		BasicAuthInterceptor(String username, String password) {
			credentials = Credentials.basic(username, password);
		}

		@Override
		public Response intercept(Chain chain) throws IOException {
			Request req = chain.request()
					.newBuilder()
					.header("Authorization", credentials)
					.build();
			return chain.proceed(req);
		}
	}

	/**
	 * Create an instance of the API for anonymous access.
	 */
	public FlightApi() {
		this(null, null);
	}

	/**
	 * Create an instance of the API for authenticated access
	 * @param username an OpenSky username
	 * @param password an OpenSky password for the given username
	 */
	public FlightApi(String username, String password) {
		lastRequestTime = new HashMap<>();
		// set up JSON mapper
		mapper = new ObjectMapper();
		SimpleModule sm = new SimpleModule();
		sm.addDeserializer(Flights.class, new OpenSkyFlightDeserializer());
		mapper.registerModule(sm);

		authenticated = username != null && password != null;

        if (authenticated) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(username, password))
                    .build();
        } else {
            okHttpClient = new OkHttpClient();
        }
	}

	/** Make the actual HTTP Request and return the parsed response
	 * @param baseUri base uri to request
	 * @param nvps name value pairs to be sent as query parameters
	 * @return parsed states
	 * @throws IOException if there was an HTTP error
	 */
    private Flights getResponse(String baseUri) throws IOException {
        HttpUrl parsedUrl = HttpUrl.parse(baseUri);
        if (parsedUrl == null) {
			throw new MalformedURLException("Could not parse uri " + baseUri);
		}

        HttpUrl.Builder urlBuilder = parsedUrl.newBuilder();
        Request req = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        Response response = okHttpClient.newCall(req).execute();
        if (!response.isSuccessful()) {
			throw new IOException("Could not get OpenSky Vectors, response " + response);
		}
        String contentType = response.header("Content-Type");
        Charset charset = null;
        if (contentType != null) {
            MediaType mediaType = MediaType.parse(contentType);
            if (mediaType != null) {
				charset = mediaType.charset();
			}
        }
        if (charset != null) {
            return mapper.readValue(new InputStreamReader(response.body().byteStream(), charset), Flights.class);
        } else {
            throw new IOException("Could not read charset in response. Content-Type is " + contentType);
        }
    }

	/**
	 * Get states from server and handle errors
	 * @throws IOException if there was an HTTP error
	 */
	private Flights getOpenSkyFlights(String baseUri) throws IOException {
		System.out.println(baseUri);
		try {
			return getResponse(baseUri);
		} catch (MalformedURLException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Invalid URI. Please report a bug");
		} catch (JsonParseException | JsonMappingException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Could not parse JSON Data. Please report a bug");
		}
	}

	/**
	 * Retrieve state vectors for a given time. If time == 0 the most recent ones are taken.
	 * Optional filters might be applied for ICAO24 addresses.
	 *
	 * @param time Unix time stamp (seconds since epoch).
	 * @param icao24 retrieve only state vectors for the given ICAO24 addresses. If {@code null}, no filter will be applied on the ICAO24 address.
	 * @return {@link OpenSkyStates} if request was successful, {@code null} otherwise or if there's no new data/rate limit reached
	 * @throws IOException if there was an HTTP error
	 * https://USERNAME:PASSWORD@opensky-network.org/api
	 */
	public Flights getFlights(int begin, int end) throws IOException {
		return getOpenSkyFlights(API_ROOT + "/flights/all?begin=" + begin + "&end=" + end);
	}
}
