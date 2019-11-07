package model;


import database.Database;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private static List<User> listOfUsers = new ArrayList<>();

    public static User getUserById(int requiredId) {
        for (User user : listOfUsers) {
            if (user.getId() == requiredId) {
                return user;
            }
        }
        System.out.println("Користувача з таким ID не існує"); //for debug purposes only
        return null;
    }

    public static void replaceUser(User newUser) throws JAXBException, FileNotFoundException {
        for (int i = 0; i < listOfUsers.size(); i++) {
            if (listOfUsers.get(i).getId() == newUser.getId()) {
                listOfUsers.set(i, newUser);
                Users objectToSave = new Users(listOfUsers);
                Users.saveToXML(objectToSave);
            }
        }
    }

    public Users() {
    }

    public Users(List<User> listOfUsers) {
        super();
        this.listOfUsers = listOfUsers;
    }


    public static void saveToXML(Users localList) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Users.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(localList, System.out);
        File out = new File("E:\\Million\\Java EE\\javaee_2\\test.xml");
        m.marshal(localList, out);
        System.err.println("Write to file: " + out.getAbsolutePath());
    }

    public static List<User> readFromXml() throws FileNotFoundException {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Users.class);
            Unmarshaller um = context.createUnmarshaller();
            Users users = (Users) um.unmarshal(new File("E:\\Million\\Java EE\\javaee_2\\test.xml"));
            Users.setListOfUsers(users.getListOfUsers());
            User userWithMaxId = listOfUsers.get(0);
            for (User user : listOfUsers) {
                if (user.getId() > userWithMaxId.getId()) {
                    userWithMaxId = user;
                }
            }
            User.setIdCounter(userWithMaxId.getId() + 1);
            return users.getListOfUsers();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) { // object testing
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public static List<User> getListOfUsersStatic() {
        return listOfUsers;
    }

    public static void setListOfUsers(List<User> listOfUsers) {
        Users.listOfUsers = listOfUsers;
    }
}
