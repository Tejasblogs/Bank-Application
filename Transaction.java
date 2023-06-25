package BankOperation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/transaction")
public class Transaction extends HttpServlet {
	
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
		
		String accNO=req.getParameter("accNo");
		long accno=Long.parseLong(accNO);
		String amt=req.getParameter("amt");
		double amtt=Double.parseDouble(amt);
		String depo=req.getParameter("deposite");
		String with=req.getParameter("withdraw");		
		
		if(depo!=null) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT account_balance FROM bank_info WHERE account_number = ?");
			      ps.setLong(1, accno);
			      ResultSet rs = ps.executeQuery();
			      if (rs.next()) {
			        Double balance = rs.getDouble("account_balance");
			        if (balance >= amtt) {
			          ps = con.prepareStatement("UPDATE bank_info SET account_balance = ? WHERE account_number = ?");
			          ps.setDouble(1, balance+amtt);
			          ps.setLong(2, accno);
			          ps.executeUpdate();
			          PrintWriter pw=resp.getWriter();
			          pw.print("<h1 style='color:green'>DEPOSITE SUCCESSFULLY</h1>");
			          RequestDispatcher rd=req.getRequestDispatcher("Index.html");
						rd.include(req, resp);
			        } else {			        
			        	PrintWriter pw=resp.getWriter();
			        	pw.print("<h1 style='color:red'>Account not found</h1>");
			        	RequestDispatcher rd=req.getRequestDispatcher("Index.html");
						rd.include(req, resp);
			      	}
			      }
			    } catch (SQLException e) {
			      e.printStackTrace();
			    } 			  
			
		}else {
			try {
				  PreparedStatement ps = con.prepareStatement("SELECT account_balance FROM bank_info WHERE account_number = ?");
			      ps.setLong(1, accno);
			      ResultSet rs = ps.executeQuery();
			      if (rs.next()) {
			        Double balance = rs.getDouble("account_balance");
			        if (balance >= amtt) {
			          ps = con.prepareStatement("UPDATE bank_info SET account_balance = ? WHERE account_number = ?");
			          ps.setDouble(1, balance-amtt);
			          ps.setLong(2, accno);
			          ps.executeUpdate();			          
			          PrintWriter pw=resp.getWriter();
			          pw.print("<h1 style='color:blue'>WITHDRAW SUCCESSFULLY</h1>");
			          RequestDispatcher rd=req.getRequestDispatcher("Index.html");
			          rd.include(req, resp);
			        } else {
			        	PrintWriter pw=resp.getWriter();
				        pw.print("<h1 style='color:red'>Insufficient balance</h1>");
				        RequestDispatcher rd=req.getRequestDispatcher("Index.html");
						rd.include(req, resp);
			        }
			      } else {			        
			        PrintWriter pw=resp.getWriter();
			        pw.print("<h1 style='color:red'>Account not found</h1>");
			        RequestDispatcher rd=req.getRequestDispatcher("Index.html");
					rd.include(req, resp);
			      }
			    } catch (SQLException e) {
			      e.printStackTrace();
			    }
			resp.sendRedirect("Index.html");
		}
	}
}

