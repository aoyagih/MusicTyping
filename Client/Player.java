public class Player {
    private String name;
    private int score;

    Player(String n, int s){
        this.name = n;
        this.score = s;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore(){
        this.score++;
    }
}