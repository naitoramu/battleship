package battleship.test;

import java.util.List;

import battleship.model.User;
import battleship.model.Statistics;
import battleship.util.DBUtil;

public class DBTest {

    public static void main(String[] args) {
        DBUtil db = new DBUtil();
        db.increaseStatistics(1, "easy", 36, 10, false);

        db.closeConnection();
    }
}