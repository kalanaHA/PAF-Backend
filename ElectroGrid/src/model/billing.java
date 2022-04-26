package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class billing {

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
	public String insertbilling(String amount, String unit, String price, String cr, String date) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into billings (`billID`,`billAmount`,`billUnit`,`unitPrice`,`billCR`,`billDate`)"
				+ " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, amount);
			preparedStmt.setString(3, unit);
			preparedStmt.setString(4, price);
			preparedStmt.setString(5, cr);
			preparedStmt.setString(6, date);

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readbillings() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Bill Amount</th><th>Bill Unit</th>" + "<th>Unit Price</th>"
					+ "<th> CR</th><th>Bill Date</th></tr>";

			String query = "select * from billings";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String BillID = Integer.toString(rs.getInt("billID"));
				String BillAmount = rs.getString("billAmount");
				String BillUnit = rs.getString("billUnit");
				String UnitPrice = rs.getString("unitPrice");
				String BillCR = rs.getString("billCR");
				String BillDate = rs.getString("billDate");

				// Add into the html table
				output += "<tr><td>" + BillAmount + "</td>";
				output += "<td>" + BillUnit + "</td>";
				output += "<td>" + UnitPrice + "</td>";
				output += "<td>" + BillCR + "</td>";
				output += "<td>" + BillDate + "</td>";

			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the billings .";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatebilling(String ID, String amount, String unit, String price, String cr, String date)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = " update billings set billAmount= ? , billUnit = ? , unitPrice = ? , billCR = ? , billDate = ?  where billID = ? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, amount);
			preparedStmt.setString(2, unit);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, cr);
			preparedStmt.setString(5, date);
			preparedStmt.setInt(6, Integer.parseInt(ID));

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

	public String deletebilling(String BillID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from billings where billID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(BillID));
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

