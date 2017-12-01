import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class WebPortal extends HttpServlet {
 
    public static final String LOGIN = "/test";
    public static final String UPLOAD_FORM = "/WEB-INF/uploadingForm.jsp";
    public static final String LIST_OF_UPLOADS = "questionPapers";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                HttpSession session = request.getSession();
                if(session.getAttribute("user") != null) {
                    String role = (String)session.getAttribute("role");
                    if(role.equals("dean") || role.equals("hod")) {
                        request.getRequestDispatcher(LIST_OF_UPLOADS).forward(request,response);
                    }
                    else {
                        request.getRequestDispatcher(UPLOAD_FORM).forward(request,response);
                    }
                }
                else {
                    response.sendRedirect(LOGIN);
                }
    }
}