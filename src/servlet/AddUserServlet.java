package servlet;

import database.Database;
import model.User;
import model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/AddUser")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(86400);
        if (session.getAttribute("login") == null) {
            resp.sendRedirect("access-denied.jsp");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(86400);
        if (session.getAttribute("login") == null) {
            resp.sendRedirect("access-denied.jsp");
            return;
        }
        if (req.getParameterMap().containsKey("name") && req.getParameterMap().containsKey("phoneNumber") &&
                req.getParameterMap().containsKey("surname") && req.getParameterMap().containsKey("email")) { // after submit
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String phoneNumber = req.getParameter("phoneNumber");
            String email = req.getParameter("email");
            User newUser = new User(name,surname,phoneNumber,email);
            session.setAttribute("newUser", newUser);
            Database db = new Database();
            db.connectToDatabase();
            try {
                String sql = "SELECT * FROM user WHERE phone_number = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, phoneNumber);
                ResultSet rsPhoneNumber = statement.executeQuery();
                if (rsPhoneNumber.next())
                    if (rsPhoneNumber.getString("phone_number").equals(phoneNumber) &&
                            rsPhoneNumber.getInt("id") != newUser.getId()) {
                        session.setAttribute("phoneNumberExists", phoneNumber);
                        rsPhoneNumber.close();
                    } else {
                        session.removeAttribute("phoneNumberExists");
                    }
                else
                    session.removeAttribute("phoneNumberExists");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String sql = "SELECT * FROM user WHERE email = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1,email);
                ResultSet rsEmail = statement.executeQuery();
                if (rsEmail.next())
                    if (rsEmail.getString("email").equals(email) &&
                            rsEmail.getInt("id") != newUser.getId()) {
                        session.setAttribute("emailExists", email);
                        rsEmail.close();
                    } else {
                        session.removeAttribute("emailExists");
                    }
                else
                    session.removeAttribute("emailExists");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (session.getAttribute("emailExists") != null ||
                    session.getAttribute("phoneNumberExists") != null) {
                db.terminateConnection();
                resp.sendRedirect("addUser.jsp");
                return;
            }
            session.removeAttribute("emailExists");
            session.removeAttribute("phoneNumberExists");
            try {
                String sql = "INSERT INTO user (name, surname, phone_number, email) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, phoneNumber);
                statement.setString(4, email);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.terminateConnection();
            session.removeAttribute("newUser");
            resp.sendRedirect("mylist");
            return;
        }
        req.getRequestDispatcher("addUser.jsp").forward(req, resp);
    }
}
