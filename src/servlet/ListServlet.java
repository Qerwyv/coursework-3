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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mylist")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("emailExists");
        session.removeAttribute("phoneNumberExists");
        session.setMaxInactiveInterval(86400);
        Database db = new Database();
        db.connectToDatabase();
        ResultSet rs = null;
        try {
            rs = Database.getConnection().createStatement().executeQuery("SELECT id, name, surname, phone_number, email, registration FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> listOfUsersDB = new ArrayList<>();
        while (true) {
            try {
                if (!rs.next()) break;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String registration = rs.getString("registration");
                User user = new User(id, name, surname, phoneNumber, email, registration);
                listOfUsersDB.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        session.setAttribute("listOfUsersDB", listOfUsersDB);
        session.setAttribute("listOfUsers", Users.getListOfUsersStatic());
        db.terminateConnection();
        if (!req.getParameterMap().isEmpty()) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username.equals("admin") && password.equals("service")) { //success
                session.setAttribute("login", "success");
                Users.readFromXml();
                session.setAttribute("listOfUsers", Users.getListOfUsersStatic());
                req.getRequestDispatcher("list.jsp").forward(req, resp);
                resp.setHeader("Refresh", "0;mylist");
            } else {
                resp.setHeader("Refresh", "0;redirect-to-login-page.jsp");
            }
        } else if (session.getAttribute("login") == null) {
            resp.sendRedirect("access-denied.jsp");
            return;
        } else if (session.getAttribute("login").equals("success")) {
            session.removeAttribute("error");
            resp.sendRedirect("list.jsp");
        }
    }
}

