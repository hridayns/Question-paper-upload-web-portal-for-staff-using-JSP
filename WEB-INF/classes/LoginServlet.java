import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import javax.sql.*;
import java.sql.*;
 

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        int match = -1;
        /*---------------CHECK IN DATABASE FOR VALID USER AND PASS START-------------------------*/

        Connection con;
        ResultSet rs;
        PreparedStatement pst;

        con=null;
        pst=null;
        rs=null;

        String url= "jdbc:mysql://localhost:3306/test";
        String id= "root";
        String pass = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, id, pass);
        }catch(Exception cnfex){
            cnfex.printStackTrace();
        }
        String sql = "SELECT * FROM test.users WHERE user = ? AND pass = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,password);
            rs = pst.executeQuery();
            if(rs.next()) {
                session.setAttribute("name",rs.getString("name"));
                session.setAttribute("dept",rs.getString("dept"));
                session.setAttribute("role",rs.getString("role"));
                session.setAttribute("subject",rs.getString("subject"));
                match = 1;
            }
        }
        catch(Exception e){
            session.setAttribute("error",e);
        }

        if(match == 1) {
            session.setAttribute("user",username);
            session.removeAttribute("error");
            response.sendRedirect("WebPortal");
        }
        else {
            session.setAttribute("error","Invalid username and password");
            response.sendRedirect("/test");      
        }
        /*---------------CHECK IN DATABASE FOR VALID USER AND PASS END--------------------------*/

        /*if(username.equals("admin") && password.equals("pass")) {
            session.setAttribute("user",username);
            response.sendRedirect("WebPortal");
        }
        else {
            response.sendRedirect("/test");      
        }*/
    }
}