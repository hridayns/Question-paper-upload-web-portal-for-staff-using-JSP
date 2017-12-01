import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
 
public class HomeController extends HttpServlet {
 
    public static final String LOGIN = "/WEB-INF/index.jsp";
    public static final String WEB_PORTAL = "WebPortal";
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(session.getAttribute("user") == null || session.getAttribute("error") != null) {
            request.getRequestDispatcher(LOGIN).forward(request,response);
        }
        else {
            response.sendRedirect(WEB_PORTAL);
        }
    }
}