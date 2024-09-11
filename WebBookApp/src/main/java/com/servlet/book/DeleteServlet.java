package com.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
    
    private static final String sql = "DELETE FROM BOOKDATA WHERE ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Get ID from request parameter
        int id = Integer.parseInt(req.getParameter("id"));

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");

            // Prepare the SQL statement
            ps = con.prepareStatement(sql);

            // Set the ID parameter for the SQL query
            ps.setInt(1, id);

            // Execute the SQL update
            int count = ps.executeUpdate();
            
            if (count == 1) {
                pw.println("<h2>Book deleted successfully!</h2>");
                pw.println("<button><a href=\"./index.html\">Home</a></button>");
                pw.println("<button><a href=\"booklist\">Show Books</a></button>");
            } else {
                pw.println("<h2>Book delete failed! No book found with ID = " + id + "</h2>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            pw.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            // Close the resources
            try {
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

