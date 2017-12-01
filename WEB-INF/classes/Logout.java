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
 


public class Logout extends HttpServlet {
    
    public static final String HOME = "HomeController";
    //public static final String LOGIN = "/WEB-INF/index.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
       /* HttpSession session = request.getSession();
        session.setAttribute("user",null);
        session.setAttribute("logout","yes");
        request.getRequestDispatcher(HOME).forward(request,response);*/
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();
        request.getRequestDispatcher(HOME).include(request,response);

        HttpSession session = request.getSession();
        session.invalidate();

        out.print("You are successfully logged out! Click <a href='/test'>here</a> to login again, or close this page.");
        out.close();
    }
}