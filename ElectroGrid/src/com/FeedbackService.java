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

import model.Feedback;

@Path("/Feedbacks")
public class FeedbackService {

	Feedback feedbackObj = new Feedback();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readFeedbacks() {
		return feedbackObj.readFeedbacks();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFeedback(@FormParam("Email") String Email,
			@FormParam("Contact_Num") String Contact_Num, @FormParam("User_Name") String User_Name,
			@FormParam("Comment") String Comment) {
		String output = feedbackObj.insertFeedback(Email, Contact_Num, User_Name, Comment);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProduct(String feedbackData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(feedbackData).getAsJsonObject();
		// Read the values from the JSON object
		String FeedbackID = productObject.get("FeedbackID").getAsString();
		String Email = productObject.get("Email").getAsString();
		String Contact_Num = productObject.get("Contact_Num").getAsString();
		String User_Name = productObject.get("User_Name").getAsString();
		String Comment = productObject.get("Comment").getAsString();
		String output = feedbackObj.updateFeedback(FeedbackID, Email, Contact_Num, User_Name,
				Comment);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteFeedback(String feedbackData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(feedbackData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String FeedbackID = doc.select("FeedbackID").text();
		String output = feedbackObj.deleteFeedback(FeedbackID);
		return output;
	}
}

