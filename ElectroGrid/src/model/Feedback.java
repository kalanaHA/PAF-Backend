package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Feedback {

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}


	// insert method
	public String insertFeedback(String email, String contact_num, String user_name, String comment) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into feedbacks (`FeedbackID`,`Email`,`Contact_Num`,`User_Name`,`Comment`)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, contact_num);
			preparedStmt.setString(4, user_name);
			preparedStmt.setString(5, comment);

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readFeedbacks() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Email</th><th>Contact_Num</th>" + "<th>User_Name</th>"
					+ "<th> Comment</th></tr>";

			String query = "select * from feedbacks";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String FeedbackID = Integer.toString(rs.getInt("FeedbackID"));
				String Email = rs.getString("Email");
				String Contact_Num = rs.getString("Contact_Num");
				String User_Name = rs.getString("User_Name");
				String Comment = rs.getString("Comment");

				// Add into the html table
				output += "<tr><td>" + Email + "</td>";
				output += "<td>" + Contact_Num + "</td>";
				output += "<td>" + User_Name + "</td>";
				output += "<td>" + Comment + "</td>";

			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Feedbacks .";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateFeedback(String ID, String email, String contact_num, String user_name, String comment)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = " update feedbacks set Email= ? , Contact_Num = ? , User_Name = ? , Comment = ?  where FeedbackID = ? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, contact_num);
			preparedStmt.setString(3, user_name);
			preparedStmt.setString(4, comment);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteFeedback(String FeedbackID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from feedbacks where FeedbackID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(FeedbackID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}

