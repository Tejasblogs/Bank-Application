package BankOperation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/addAccount")
public class AddAccount extends HttpServlet {
	
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name=req.getParameter("name");
		String accNo=req.getParameter("accNo");
		long acc=Long.parseLong(accNo);
		String inbal=req.getParameter("initialBal");
		double bal=Double.parseDouble(inbal);
		
		PreparedStatement pstmt=null;
		
				
		String query="insert into bank_info(user_name,account_number,account_balance) values(?,?,?)";
		
		
		
			try {
				pstmt=con.prepareStatement(query);
				
				pstmt.setString(1, name);
				pstmt.setLong(2, acc);
				pstmt.setDouble(3, bal);
				
				int count=pstmt.executeUpdate();
				PrintWriter pw=resp.getWriter();
				pw.print("<h1 style='color:blue'>"+count+" Record inserted</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("Index.html");
				rd.include(req, resp);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	


	}
}

