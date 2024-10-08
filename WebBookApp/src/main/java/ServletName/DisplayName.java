package ServletName;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/nameurl")
public class DisplayName extends GenericServlet {
	private static final long serialVersionUID = 1L;
	private static final String sql = "INSERT INTO BOOKDATA(bookName,bookEdition,bookPrice) VALUES(?,?,?)";

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// Setting content type
		res.setContentType("text/html");
		// PrintWriter to send response to the client
		PrintWriter pw = res.getWriter();

		// Retrieving form parameters
		String name = req.getParameter("name");
		
		pw.println("<h1>" + name + "</h1>");
	}
}
