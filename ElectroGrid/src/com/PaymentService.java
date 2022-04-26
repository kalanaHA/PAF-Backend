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

import model.Payment;

@Path("/Payments")

public class PaymentService {

	Payment paymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments() {
		return paymentObj.readPayments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("PaymentDate") String PaymentDate,
			@FormParam("CardNumber") String CardNumber, @FormParam("Amount") String Amount,
			@FormParam("PaymentType") String PaymentType) {
		String output = paymentObj.insertPayment(PaymentDate, CardNumber, Amount, PaymentType);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		// Read the values from the JSON object
		String PaymentID = paymentObject.get("PaymentID").getAsString();
		String PaymentDate = paymentObject.get("PaymentDate").getAsString();
		String CardNumber = paymentObject.get("CardNumber").getAsString();
		String Amount = paymentObject.get("Amount").getAsString();
		String PaymentType = paymentObject.get("PaymentType").getAsString();
		String output = paymentObj.updatePayment(PaymentID, PaymentDate, CardNumber, Amount, PaymentType);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String PaymentID = doc.select("PaymentID").text();
		String output = paymentObj.deletePayment(PaymentID);
		return output;
	}

}
