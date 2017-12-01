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
      <li><a href="WebPortal">Upload a file</a></li>
      <% } %>
      <li><a class="active" href="questionPapers">List of uploaded Files</a></li>
      <li><a href="Logout">Logout</a></li>
   </ul> 

<%@ page import="java.util.*" %>
<%@ page import="javax.sql.*;" %>
<% 

java.sql.Connection con;
java.sql.ResultSet rs;
java.sql.PreparedStatement pst;

con=null;
pst=null;
rs=null;

String url= "jdbc:mysql://localhost:3306/test";
String id= "root";
String pass = "";

try{
Class.forName("com.mysql.jdbc.Driver");
con = java.sql.DriverManager.getConnection(url, id, pass);
}catch(ClassNotFoundException cnfex){
cnfex.printStackTrace();
}

String sql = "";

if(role.equals("teacher")) {
   sql = "select * from test.upload_file_tb WHERE uploaded_by = ?";
}
else if(role.equals("hod")) {
   sql = "select * from test.upload_file_tb WHERE dept = ?";
}
else {
   sql = "select * from test.upload_file_tb";
}
%>
<table id="paperList">

<%
try{
   int x = 1;
   if(role.equals("teacher")) {
   %>
<tr>
   <th>File No.</th>   
   <th>File name</th>
   <th>Download Link</th>      
   <th>View Link</th>
</tr>
   <%
      pst = con.prepareStatement(sql);
      pst.setString(1,(String)session.getAttribute("user"));
      rs = pst.executeQuery();
      while( rs.next() ){
      %><tr>
      <td><%= x %></td>   
      <td><%= rs.getString("file_name") %></td>
      <td><a href='<%= rs.getString("file_url") %>'>Download</a></td>      
      <td><a href='<%= rs.getString("file_view") %>' target="_blank">View</a></td>
      </tr>
      <%
      x = x + 1;
      }
   }
   else if(role.equals("hod")) {
   %>
<tr>
   <th>File No.</th>   
   <th>File name</th>
   <th>Download Link</th>      
   <th>View Link</th>
   <th>Approval Status</th>
</tr>
   <%
      pst = con.prepareStatement(sql);
      pst.setString(1,(String)session.getAttribute("dept"));
      rs = pst.executeQuery();
      while( rs.next() ){
      %><tr>
      <td><%= x %></td>   
      <td><%= rs.getString("file_name") %></td>
      <td><a href='<%= rs.getString("file_url") %>'>Download</a></td>      
      <td><a href='<%= rs.getString("file_view") %>' target="_blank">View</a></td>
      <td>
         <% if(rs.getInt("approval_status") != 1) { %>
         <form action="ApproveServlet" method="post">
            <input type="hidden" name="file_id" value='<%= rs.getInt("ID") %>' />
            <input type="submit" value="Approve" />
         </form>
         <% } else { %>
            &#10004; Approved
         <% } %>
      </td>
      </tr>
      <%
      x = x + 1;
      }
   }
   else {
    %>
<tr>
   <th>File No.</th>   
   <th>File name</th>
   <th>View Link</th>
</tr>
   <%
      pst = con.prepareStatement(sql);
      rs = pst.executeQuery();
      while( rs.next() ){
      %><tr>
      <td><%= x %></td>   
      <td><%= rs.getString("file_name") %></td>
      <td><a href='<%= rs.getString("file_view") %>' target="_blank">View</a></td>
      </tr>
      <%
      x = x + 1;
      }
   }
}
catch(Exception e){e.printStackTrace();}
finally{
   if(rs!=null) rs.close();
   if(pst!=null) pst.close();
   if(con!=null) con.close();
}

%>
</table>

</body>

</html>