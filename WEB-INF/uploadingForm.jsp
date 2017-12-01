<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
    <html>
   <head>
      <title>File Uploading Form</title>
      <link href="css/style.css" type="text/css" rel="stylesheet" />
   </head>
   
   <body>

   <ul class="nav-menu">
      <% 
      String role = (String)session.getAttribute("role");
      if(role.equals("teacher")) { %>
      <li><a class="active" href="WebPortal">Upload a file</a></li>
      <% } %>
      <li><a href="questionPapers">List of uploaded Files</a></li>
      <li><a href="Logout">Logout</a></li>
   </ul> 

   <%
            out.println("Hello, " + session.getAttribute("dept") + " " + session.getAttribute("role") + " " + session.getAttribute("user") + " AKA " + session.getAttribute("name"));
   %>
      <div id="uploadForm">
         <form action = "UploadServlet" method = "post" enctype = "multipart/form-data">
         Upload paper here: <input type = "file" name = "file" size = "50" />
            <input type = "submit" value = "Upload File" />
         </form>

            <%
            if(session.getAttribute("message") != null) {
               out.println(session.getAttribute("message"));
               session.removeAttribute("message");
            }
/*         if(session.getAttribute("db_update") != null) {
            out.println(session.getAttribute("db_update"));
            session.removeAttribute("db_update");
         }*/
            %>
      </div>
   </body>
   
</html>