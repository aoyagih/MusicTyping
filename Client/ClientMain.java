import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class ClientMain {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException,
            InterruptedException {
        Player player = new Player("", 0);
        GameData gameData = new GameData(player);
        GameMaster gameMaster = new GameMaster();
        Requester requester = new Requester();
        
        gameMaster.explain();  //ゲーム説明

        gameMaster.say("LOADING FILES...");
        requester.requestGameData(gameData);  //サーバから音楽データをダウンロード
        gameMaster.say("LOAD SUCCESSFUL!");
        Thread.sleep(2000);

        gameMaster.startGame(gameData);  //ゲームスタート
        gameMaster.say("FINISH!");  //ゲーム終了
        
        //プレイヤーの名前を入力
        gameMaster.say("PLEASE INPUT YOUR NAME.");
        Scanner sc = new Scanner(System.in);
        String playerName = sc.nextLine();
        sc.close();
        player.setName(playerName);
        
        gameMaster.showScore(player);  //スコアの表示
        requester.sendScore(player);  //サーバーに今回のスコアを送信
        //ランキングの表示
        //requester.showRanking();

        gameMaster.say("THANK YOU FOR PLAYING!");
    }
}