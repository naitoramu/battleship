package battleship.model;

public class User {

    private int userID;
    private String username;
    private String password;
    private Statistics easyLvlStatistics;
    private Statistics mediumLvlStatistics;
    private Statistics hardLvlStatistics;
    private String pathToGameRecord;

    
    public User(int userID, String username, String password, Statistics easyLvlStatistics,
            Statistics mediumLvlStatistics, Statistics hardLvlStatistics) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.easyLvlStatistics = easyLvlStatistics;
        this.mediumLvlStatistics = mediumLvlStatistics;
        this.hardLvlStatistics = hardLvlStatistics;
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

    public Statistics getHardLvlStatistics() {
        return hardLvlStatistics;
    }
    public void setHardLvlStatistics(Statistics hardLvlStatistics) {
        this.hardLvlStatistics = hardLvlStatistics;
    }

    public Statistics getMediumLvlStatistics() {
        return mediumLvlStatistics;
    }
    public void setMediumLvlStatistics(Statistics mediumLvlStatistics) {
        this.mediumLvlStatistics = mediumLvlStatistics;
    }

    public Statistics getEasyLvlStatistics() {
        return easyLvlStatistics;
    }
    public void setEasyLvlStatistics(Statistics easyLvlStatistics) {
        this.easyLvlStatistics = easyLvlStatistics;
    }

    public String getPathToGameRecord(){
        return this.pathToGameRecord;
    }
    public void setPathToGameRecord(String pathToGameRecord){
        this.pathToGameRecord = pathToGameRecord;
    }


    @Override
      public String toString() {
          return "\n---------------------------------------------------\n" +
            "|"+ userID +
            "|"+ username +
            "|"+ password + "|\n" + 
            getEasyLvlStatistics() + getMediumLvlStatistics() + getHardLvlStatistics();
      }

}
