package test;

import java.util.List;

import model.User;
import model.Statistics;
import util.DBUtil;

public class DBTest {

    public static void main(String[] args) {
        DBUtil db = new DBUtil();
        // db.insertUser("karol", "92873847182");
        // db.insertUser("maciek", "92873847182");
        // db.insertUser("adam", "92873847182");

        List<User> users = db.selectUsers();

        System.out.println("User list: ");
        for (User user: users){
            System.out.println(user);
        }

        db.closeConnection();
    }
}