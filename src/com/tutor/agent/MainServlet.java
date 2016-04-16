/* Copyright IBM Corp. 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tutor.agent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;





import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.tutor.agent.resources.utils.PeriodicTableReader;

@MultipartConfig
public class MainServlet extends HttpServlet {

	private static Logger logger = Logger
			.getLogger(MainServlet.class.getName());
	private static final long serialVersionUID = 1L;
	private static final String CONVERSATION = "/conversation";
	private static final String PROFILE = "/profile";

	private String serviceName = "dialog";

	// If running locally complete the variables below
	// with the information in VCAP_SERVICES

	private String baseURL = "https://gateway.watsonplatform.net/dialog/api";
	private String username = "bde2df45-df2b-4c8d-b884-c48000b3b400";
	private String password = "HmAOgnCFTn12";
	private String tutorDialogId = "7f66fae5-12b9-41d2-8aa7-eb15ef3a335c";

	PeriodicTableReader ptr = new PeriodicTableReader();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * Create and POST a request to the Dialog service
	 * 
	 * @param req
	 *            the Http Servlet request
	 * @param resp
	 *            the Http Servlet pesponse
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost:" + req.getPathInfo());

		req.setCharacterEncoding("UTF-8");

		String path = req.getPathInfo();
		Request request = null;
		try {
			if (path.equals(CONVERSATION))
				request = conversation(req);
			else if (path.equals(PROFILE))
				request = profile(req);
	
			Executor executor = Executor.newInstance().auth(username, password);
			Response response = executor.execute(request);

			HttpResponse httpResponse = response.returnResponse();
			resp.setStatus(httpResponse.getStatusLine().getStatusCode());

			ServletOutputStream servletOutputStream = resp.getOutputStream();
			httpResponse.getEntity().writeTo(servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Service error: " + e.getMessage(), e);
			resp.setStatus(HttpStatus.SC_BAD_GATEWAY);
		}
	}

	/**
	 * Create a /profile request
	 *
	 * @param req the HTTP request
	 * @return the request
	 * @throws URISyntaxException 
	 */
	private Request profile(HttpServletRequest req) throws URISyntaxException {
		String clientId = req.getParameter("client_id");
		String templateOption = req.getParameter("hello");
		System.out.println(clientId +"====="+templateOption);
		URI converseURI = new URI(baseURL + "/v1/dialogs/" + tutorDialogId + "/profile?client_id="+ clientId).normalize();
		
		return Request.Get(converseURI);
	}

	private Request conversation(HttpServletRequest req) throws URISyntaxException {
		// create the request
		
		
		String input = req.getParameter("input");
		System.out.println("input ======>"+input);
		
//		if(input.equalsIgnoreCase("ElementName")){
//			System.out.println(ptr.returnByElementMap());
//		}
//		else if(input.equalsIgnoreCase("Symbol")){
//			System.out.println(ptr.returnBySymbolMap());
//		}
//		else if(input.equalsIgnoreCase("Type")){
//			System.out.println(ptr.returnByTypeMap());
//		}
//		
//		
		
		String conversationId = req.getParameter("conversation_id");
		String clientId = req.getParameter("client_id");

		URI converseURI = new URI(baseURL + "/v1/dialogs/" + tutorDialogId + "/conversation").normalize();
		return Request.Post(converseURI).bodyForm(
			Form.form()
				.add("input", input)
				.add("client_id", clientId)
				.add("conversation_id", conversationId)
				.build());
	}

	@Override
	public void init() throws ServletException {
		super.init();
		if (System.getenv("DIALOG_ID") != null)
			tutorDialogId = System.getenv("DIALOG_ID");
		processVCAPServices();
	}

	/**
	 * If exists, process the VCAP_SERVICES environment variable in order to get
	 * the username, password and baseURL
	 */
	private void processVCAPServices() {
		logger.info("Processing VCAP_SERVICES");
		JSONObject sysEnv = getVCAPServices();
		if (sysEnv == null)
			return;
		logger.info("Looking for: " + serviceName);

		for (Object key : sysEnv.keySet()) {
			String keyString = (String) key;
			logger.info("found key: " + key);
			if (keyString.startsWith(serviceName)) {
				JSONArray services = (JSONArray) sysEnv.get(key);
				JSONObject service = (JSONObject) services.get(0);
				JSONObject credentials = (JSONObject) service
						.get("credentials");
				baseURL = (String) credentials.get("url");
				username = (String) credentials.get("username");
				password = (String) credentials.get("password");
				logger.info("baseURL  = " + baseURL);
				logger.info("username = " + username);
				logger.info("password = " + password);
			} else {
				logger.info("Doesn't match /^" + serviceName + "/");
			}
		}
	}

	/**
	 * Gets the <b>VCAP_SERVICES</b> environment variable and return it as a
	 * JSONObject.
	 * 
	 * @return the VCAP_SERVICES as Json
	 */
	private JSONObject getVCAPServices() {
		String envServices = System.getenv("VCAP_SERVICES");
		if (envServices == null)
			return null;
		JSONObject sysEnv = null;
		try {
			sysEnv = JSONObject.parse(envServices);
		} catch (IOException e) {
			// Do nothing, fall through to defaults
			logger.log(Level.SEVERE,
					"Error parsing VCAP_SERVICES: " + e.getMessage(), e);
		}
		return sysEnv;
	}
}
