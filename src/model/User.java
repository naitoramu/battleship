package model;

public class User {

    private int userID;
    private String username;
    private String password;
    private Statistics statistics;
    private String pathToGameRecord;

    public User(int userID, String username, String password, Statistics statistics) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.statistics = statistics;
    }
    public int getId() {
        return userID;
    }
    public void setId(int id) {
        this.userID = id;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Statistics getStatistics(){
        return this.statistics;
    }
    public void setStatistics(Statistics statistics){
        this.statistics = statistics;
    }

    public String getPathToGameRecord(){
        return this.pathToGameRecord;
    }
    public void setPathToGameRecord(String pathToGameRecord){
        this.pathToGameRecord = pathToGameRecord;
    }


    @Override
      public String toString() {
          return "["+userID+"] - "+username+" - "+password + "\n" + statistics;
      }

}
