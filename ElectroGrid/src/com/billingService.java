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

import model.billing;

@Path("/billings")
public class billingService {

	billing billingObj = new billing();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readbillings() {
		return billingObj.readbillings();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertbilling(@FormParam("billAmount") String billAmount,
			@FormParam("billUnit") String billUnit, @FormParam("unitPrice") String unitPrice,
			@FormParam("billCR") String billCR, @FormParam("billDate") String billDate) {
		String output = billingObj.insertbilling(billAmount, billUnit, unitPrice, billCR, billDate);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatebilling(String billingData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(billingData).getAsJsonObject();
		// Read the values from the JSON object
		String BillID = productObject.get("BillID").getAsString();
		String BillAmount = productObject.get("BillAmount").getAsString();
		String BillUnit = productObject.get("BillUnit").getAsString();
		String UnitPrice = productObject.get("UnitPrice").getAsString();
		String BillCR = productObject.get("BillCR").getAsString();
		String BillDate = productObject.get("BillDate").getAsString();
		String output = billingObj.updatebilling(BillID, BillAmount, BillUnit, UnitPrice,
				BillCR, BillDate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletebilling(String billingData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(billingData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String BillID = doc.select("BillID").text();
		String output = billingObj.deletebilling(BillID);
		return output;
	}
}

