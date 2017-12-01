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
 
/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
public class ApproveServlet extends HttpServlet {
    
    public static final String LIST_OF_UPLOADS = "questionPapers";

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        Connection con;
        Statement s;
        ResultSet rs;
        PreparedStatement pst;

        con=null;
        s=null;
        pst=null;
        rs=null;
        
        HttpSession session = request.getSession(false);

        int fileId = Integer.parseInt(request.getParameter("file_id"));
        int updatedRows = -1;

        try {
                String url= "jdbc:mysql://localhost:3306/test";
                String id= "root";
                String pass = "";
                try{
                Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, id, pass);
                }catch(ClassNotFoundException cnfex){
                    cnfex.printStackTrace();
                }
                String sql = "UPDATE test.upload_file_tb SET approval_status = ? WHERE ID = ?";

                try{
                    pst = con.prepareStatement(sql);
                    pst.setInt(1,1);
                    pst.setInt(2,fileId);
                    updatedRows = pst.executeUpdate();
                    if(updatedRows == 1) {
                        session.setAttribute("db_update","db updated");
                    } else {
                        session.setAttribute("db_update","db update failed");
                    }
                }
                catch(Exception e){
                    session.setAttribute("error",e);
                }
                   
        } catch (Exception ex) {
            session.setAttribute("message",
                    "There was an error: " + ex.getMessage());
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }
        finally {
            try {
                if(rs!=null) rs.close();
                if(pst!=null) pst.close();
                if(con!=null) con.close();
            } catch(SQLException e) {

            }
            
        }
        // redirects client to message page
        response.sendRedirect(LIST_OF_UPLOADS);

        //getServletContext().getRequestDispatcher("/uploadingForm.jsp").forward(request, response);
    }
}