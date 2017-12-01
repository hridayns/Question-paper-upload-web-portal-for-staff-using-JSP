import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PaperList extends HttpServlet {
 
    public static final String LOGIN = "/test";
    public static final String PAPER_LIST = "/WEB-INF/paperList.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                HttpSession session = request.getSession();
                if(session.getAttribute("user") != null) {
                    request.getRequestDispatcher(PAPER_LIST).forward(request,response);
                }
                else {
                    response.sendRedirect(LOGIN);
                }
    }
}