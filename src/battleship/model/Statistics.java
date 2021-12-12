package battleship.model;

public class Statistics {

    private final String DIFFICULTY_LEVEL;
    private int numberOfGames;
    private int numberOfWins;
    private int numberOfLosts;
    private int numberOfShoots;
    private int numberOfHits;

    public Statistics(String difficultyLevel, int numberOfGames, int numberOfWins, int numberOfLosts,
            int numberOfShoots, int numberOfHits) {
        this.DIFFICULTY_LEVEL = difficultyLevel;
        this.numberOfGames = numberOfGames;
        this.numberOfWins = numberOfWins;
        this.numberOfLosts = numberOfLosts;
        this.numberOfShoots = numberOfShoots;
        this.numberOfHits = numberOfHits;
    }

    public String getDifficultyLevel() {
        return DIFFICULTY_LEVEL;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void incrementNumberOfGames() {
        this.numberOfGames++;
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

    public int getNumberOfShoots() {
        return numberOfShoots;
    }

    public void incrementNumberOfShoots() {
        this.numberOfShoots++;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void incrementNumberOfHits() {
        this.numberOfHits++;
    }

    public int getAccuracy() {

        if (this.numberOfShoots == 0) {
            return 0;
        } else {
            double numberOfShoots = this.numberOfShoots;
            double numberOfHits = this.numberOfHits;
            double accuracy = numberOfHits / numberOfShoots * 100;
            return (int) Math.round(accuracy);
        }
    }

    @Override
    public String toString() {
        return "Difficulty level: " + getDifficultyLevel() + "\n" +
                "(games|wins|losts|shoots|hits) : " +
                getNumberOfGames() + '|' +
                getNumberOfWins() + '|' +
                getNumberOfLosts() + '|' +
                getNumberOfShoots() + '|' +
                getNumberOfHits() + "\n";
    }

}
