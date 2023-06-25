package BankOperation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/DisplayAccount.html")
public class Display extends HttpServlet {

	Connection con;
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/j2ee", "root", "Tejas");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Statement stmt;
		ResultSet rs;
		PrintWriter pw=resp.getWriter();
		
		String query="select * from bank_info";
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			pw.print("<table border='2' style='width:600px'>");
			pw.print("<tr style='font-size:larger'>");
			pw.print("<th>USER ID</th>");
			pw.print("<th>USER Name</th>");
			pw.print("<th>USER ACCOUNT NUMBER</th>");
			pw.print("</tr>");
			while(rs.next()){
				int id=rs.getInt(1);
				String name=rs.getString(2);
				String account_no=rs.getString(3);			
				
				pw.print("<tr style='font-size:larger'>");
				pw.print("<td>"+id+"</td>");
				pw.print("<td>"+name+"</td>");
				pw.print("<td>"+account_no+"</td>");				
				pw.print("</tr>");
				
			}
			pw.print("</table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
