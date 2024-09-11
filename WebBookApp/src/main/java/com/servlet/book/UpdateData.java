package com.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatescreen")
public class UpdateData extends HttpServlet {
    
    private static final String sql = "SELECT bookName, bookEdition, bookPrice FROM BOOKDATA WHERE ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Get ID from request
        int id = Integer.parseInt(req.getParameter("id"));

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");

            // Prepare the SQL statement
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // Execute the query
            rs = ps.executeQuery();

            // Check if the result set has data
            if (rs.next()) {
                // Generate the form with the retrieved data
                pw.println("<form action='updateurl?id=" + id + "' method='post'>");
                pw.println("<table border='1'>");

                pw.println("<tr>");
                pw.println("<th>Book Name</th>");
                pw.println("<td><input type='text' name='bookName' value='" + rs.getString("bookName") + "'></td>");
                pw.println("</tr>");

                pw.println("<tr>");
                pw.println("<th>Book Edition</th>");
                pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString("bookEdition") + "'></td>");
                pw.println("</tr>");

                pw.println("<tr>");
                pw.println("<th>Book Price</th>");
                pw.println("<td><input type='text' name='bookPrice' value='" + rs.getString("bookPrice") + "'></td>");
                pw.println("</tr>");

                pw.println("<tr>");
                pw.println("<td><input type='submit' value='Update'></td>");
                pw.println("<td><input type='reset' value='Cancel'></td>");
                pw.println("</tr>");

                pw.println("</table>");
                pw.println("</form>");
            } else {
                pw.println("<p>No book found with ID = " + id + "</p>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            pw.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
