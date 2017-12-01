import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.io.*;
 
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

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
public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        String view = request.getParameter("view");
        /*
        InputStream input = request.getInputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        response.setHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
        response.getOutputStream().write(output.toByteArray());*/
        if(fileName == null || fileName.equals("")){
            throw new ServletException("File Name can't be null or empty");
        }
        String filePath = request.getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::"+file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentLength((int) file.length());
        ServletOutputStream os = response.getOutputStream();


        
        if(view == null || !view.equals("yes")) {
            response.setContentType(mimeType != null? mimeType:"application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
            System.out.println("File downloaded at client successfully");
        }
        else {
            response.setContentType(mimeType != null? mimeType:"application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
        }
    }
}