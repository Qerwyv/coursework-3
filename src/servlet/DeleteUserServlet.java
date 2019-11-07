package servlet;

import database.Database;
import model.User;
import model.Users;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(86400);
        if (session.getAttribute("login") == null) {
            resp.sendRedirect("access-denied.jsp");
            return;
        }
        if (req.getParameterMap().isEmpty()) {
            resp.sendRedirect("mylist");
        } else {
            int userId = Integer.parseInt(req.getParameter("id"));
            Database db = new Database();
            db.connectToDatabase();
            try {
                String sql = "DELETE FROM user WHERE id = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.terminateConnection();
            resp.setHeader("Refresh", "0;mylist");
        }
    }
}
