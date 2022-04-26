package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Customer;

@Path("/Customers")

public class CustomerService {

	Customer customerObj = new Customer();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomers() {
		return customerObj.readCustomers();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("CustomerName") String CustomerName,
			@FormParam("CustomerEmail") String CustomerEmail, @FormParam("CustomerType") String CustomerType,
			@FormParam("CustomerContact") String CustomerContact) {
		String output = customerObj.insertCustomer(CustomerName, CustomerEmail, CustomerType, CustomerContact);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData) {
		// Convert the input string to a JSON object
		JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();
		// Read the values from the JSON object
		String CustomerID = customerObject.get("CustomerID").getAsString();
		String CustomerName = customerObject.get("CustomerName").getAsString();
		String CustomerEmail = customerObject.get("CustomerEmail").getAsString();
		String CustomerType = customerObject.get("CustomerType").getAsString();
		String CustomerContact = customerObject.get("CustomerContact").getAsString();
		String output = customerObj.updateCustomer(CustomerID, CustomerName, CustomerEmail, CustomerType,
				CustomerContact);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

		// Read the value from the element <CustomerID>
		String CustomerID = doc.select("CustomerID").text();
		String output = customerObj.deleteCustomer(CustomerID);
		return output;
	}

}
