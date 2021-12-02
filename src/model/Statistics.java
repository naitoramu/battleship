package model;

public class Statistics {

    private int statsID;
    private int userID;
    private int numberOfWins;
    private int numberOfLosts;

    public Statistics(/* int statsID, int userID, */ int numberOfWins, int numberOfLosts) {
        // this.setStatsID(statsID);
        // this.setUserID(userID);
        this.numberOfWins = numberOfWins;
        this.numberOfLosts = numberOfLosts;
    }

    public int getStatsID() {
        return statsID;
    }

    public void setStatsID(int statsID) {
        this.statsID = statsID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void incrementNumberOfWins() {
        this.numberOfWins++;
    }

    public int getNumberOfLosts() {
        return numberOfLosts;
    }

    public void incrementNumberOfLosts() {
        this.numberOfLosts++;
    }

    @Override
    public String toString() {
        return "[Wins/Losts] : " + numberOfWins + "/" + numberOfLosts;
    }

}
