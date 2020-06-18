import java.util.*;

public class GameData {
    private ArrayList<String> lyricsList = new ArrayList<>();  //歌詞リスト
    private ArrayList<Long> timeList = new ArrayList<>();      //各小節の長さのリスト

    private String currentLyrics;

    private Player player;

    GameData(Player player){
        this.player = player;
    }

    public ArrayList<String> getLyricsList() {
        return lyricsList;
    }
    public ArrayList<Long> getTimeList() {
        return timeList;
    }
    public String getCurrentLyrics() {
        return currentLyrics;
    }
    public Player getPlayer() {
        return player;
    }

    public void setLyricsList(ArrayList<String> lyricsList) {
        this.lyricsList = lyricsList;
    }
    public void setTimeList(ArrayList<Long> timeList) {
        this.timeList = timeList;
    }
    public void setCurrentLyrics(String currentLyrics) {
        this.currentLyrics = currentLyrics;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}