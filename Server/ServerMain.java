import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Responser responser = new Responser();
        //歌詞と時間データのローディング
        responser.loadLyricsList("data/music/konpeki/lyrics.txt");
        responser.loadTimeList("data/music/konpeki/time.txt");
        //クライアントの要求に応じてゲームデータを送信
        responser.sendGameData();

        //クライアントはゲームをプレイ中・・・
        
        //スコアの受信・登録・ランキングの送信
        responser.updateRanking();

    }
}