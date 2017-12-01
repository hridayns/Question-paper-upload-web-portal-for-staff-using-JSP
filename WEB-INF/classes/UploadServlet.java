import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.*;
import javax.sql.*;
import java.sql.*;
 
/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "uploads";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * Upon receiving file upload submission, parses the request to read
     * upload data and saves the file on disk.
     */
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

        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
 
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        //String uploadPath = "C:\\xampp\\tomcat\\" + UPLOAD_DIRECTORY;   
         
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        HttpSession session = request.getSession(false);

        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
 
                        // saves the file on disk
                        item.write(storeFile);

                            int updatedRows = -1;
                            String url= "jdbc:mysql://localhost:3306/test";
                            String id= "root";
                            String pass = "";
                            try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con = DriverManager.getConnection(url, id, pass);
                            }catch(ClassNotFoundException cnfex){
                            cnfex.printStackTrace();
                            }
                            String sql = "INSERT INTO test.upload_file_tb (file_name,file_url,file_view,uploaded_by,dept,subject,approval_status) VALUES (?,?,?,?,?,?,?)";

                            StringBuilder fileURL = new StringBuilder();

                            fileURL.append(request.getScheme()).append("://").append(request.getServerName());
                            if (request.getServerPort() != 80 && request.getServerPort() != 443) {
                                fileURL.append(":").append(request.getServerPort());
                            }
                            fileURL.append(request.getContextPath()).append("/").append(UPLOAD_DIRECTORY).append("?fileName=").append(fileName);

                            StringBuilder fileViewURL = new StringBuilder(fileURL);
                            fileViewURL.append("&view=yes");

                            try{
                                pst = con.prepareStatement(sql);
                                pst.setString(1,fileName);
                                pst.setString(2,fileURL.toString());
                                pst.setString(3,fileViewURL.toString());
                                pst.setString(4,(String)session.getAttribute("user"));
                                pst.setString(5,(String)session.getAttribute("dept"));
                                pst.setString(6,(String)session.getAttribute("subject"));
                                pst.setInt(7,0);
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

                        session.setAttribute("message",
                            fileName + " has been uploaded at " + fileURL + " successfully!");
                        request.setAttribute("message",
                            "Upload has been done successfully!");
                    } 
                }
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
                if(s!=null) s.close();
                if(con!=null) con.close();
            } catch(SQLException e) {

            }
            
        }
        

        

        // redirects client to message page
        response.sendRedirect("WebPortal");

        //getServletContext().getRequestDispatcher("/uploadingForm.jsp").forward(request, response);
    }
}