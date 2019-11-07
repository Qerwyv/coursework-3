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
import java.sql.Statement;


@WebServlet("/EditUser")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(86400); //24 hours
        if (session.getAttribute("login") == null) {
            resp.sendRedirect("access-denied.jsp");
            return;
        }
        User userForEdit = null;
        if (req.getParameterMap().containsKey("id")) { // before editing
            int userId = Integer.parseInt(req.getParameter("id"));
            Database db = new Database();
            db.connectToDatabase();
            try {
                String sql = "SELECT * FROM user WHERE id = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, userId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    userForEdit = User.createNewUser(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.terminateConnection();
            session.setAttribute("userForEdit", userForEdit);
            req.getRequestDispatcher("editUser.jsp").forward(req, resp);
        } else if (req.getParameterMap().containsKey("name") && req.getParameterMap().containsKey("phoneNumber") &&
                req.getParameterMap().containsKey("surname") && req.getParameterMap().containsKey("email")) { // after editing
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String phoneNumber = req.getParameter("phoneNumber");
            String email = req.getParameter("email");
            userForEdit = (User) session.getAttribute("userForEdit");
            userForEdit.setName(name);
            userForEdit.setSurname(surname);
            userForEdit.setPhoneNumber(phoneNumber);
            userForEdit.setEmail(email);
            Database db = new Database();
            db.connectToDatabase();
            try {
                String sql = "SELECT * FROM user WHERE phone_number = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, phoneNumber);
                ResultSet rsPhoneNumber = statement.executeQuery();
                if (rsPhoneNumber.next())
                    if (rsPhoneNumber.getString("phone_number").equals(phoneNumber) &&
                            rsPhoneNumber.getInt("id") != userForEdit.getId()) {
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
                            rsEmail.getInt("id") != userForEdit.getId()) {
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
                resp.sendRedirect("editUser.jsp");
                return;
            }
            session.removeAttribute("emailExists");
            session.removeAttribute("phoneNumberExists");
            try {
                String sql = "UPDATE user SET name = ?, surname = ?, phone_number = ?, email = ? WHERE id = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, phoneNumber);
                statement.setString(4, email);
                statement.setLong(5, userForEdit.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            session.removeAttribute("userForEdit");
            db.terminateConnection();
            resp.sendRedirect("mylist");
        }

    }
}