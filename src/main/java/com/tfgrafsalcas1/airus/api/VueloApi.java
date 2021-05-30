package com.tfgrafsalcas1.airus.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tfgrafsalcas1.airus.deserialize.OpenSkyVueloDeserializer;
import com.tfgrafsalcas1.airus.documents.Vuelos;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class VueloApi {

	private static final String HOST = "opensky-network.org";
	private static final String API_ROOT = "https://" + HOST + "/api";

	private final boolean authenticated;

	private final ObjectMapper mapper;

	private final OkHttpClient okHttpClient;

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
	public VueloApi() {
		this(null, null);
	}

	/**
	 * Create an instance of the API for authenticated access
	 * @param username an OpenSky username
	 * @param password an OpenSky password for the given username
	 */
	public VueloApi(String username, String password) {
		// set up JSON mapper
		mapper = new ObjectMapper();
		SimpleModule sm = new SimpleModule();
		sm.addDeserializer(Vuelos.class, new OpenSkyVueloDeserializer());
		mapper.registerModule(sm);

		authenticated = username != null && password != null;

        if (authenticated) {
            okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
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
    private Vuelos getResponse(String baseUri) throws IOException {
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
            return mapper.readValue(new InputStreamReader(response.body().byteStream(), charset), Vuelos.class);
        } else {
            throw new IOException("Could not read charset in response. Content-Type is " + contentType);
        }
    }

	/**
	 * Get states from server and handle errors
	 * @throws IOException if there was an HTTP error
	 */
	private Vuelos getOpenSkyFlights(String baseUri) throws IOException {
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
	public Vuelos getFlights(int begin, int end) throws IOException {
		return getOpenSkyFlights(API_ROOT + "/flights/all?begin=" + begin + "&end=" + end);
	}
}
