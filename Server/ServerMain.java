import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Responser responser = new Responser();
        //歌詞と時間データのローディング
        responser.loadLyricsList("data/music/konpeki_easy/lyrics.txt");
        responser.loadTimeList("data/music/konpeki_easy/time.txt");
        //クライアントの要求に応じてゲームデータを送信
        responser.sendGameData();

        //クライアントはゲームをプレイ中・・・
        
        //スコアの受信・登録
        responser.registerScore();

        //ランキングの送信

    }
}