import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class GameMaster extends Worker{

    //スレッドの停止
    public void parse(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ターミナル上でユーザー入力と区別できるように
    public void say(String s) {
        System.out.println("--- " + s + " ---");
    }

    //ゲーム説明などイントロダクション部分
    public void explain() {
        say("WELCOME TO MUSIC TYPING GAME!");
        parse(2000);

        say("PLEASE TYPE SHOWN LYRICS!");
        parse(2000);
    }


    //ゲームスタートさせる
    public void startGame(GameData gameData) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        say("ARE YOU READY?");
        parse(2000);
        for (int i = 3; i > 0; i--) {
            say(Integer.toString(i));    //カウントダウン
            parse(1000);
        }
        say("START!");
        //音楽ファイルの再生
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("download_music.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();

        //ゲームスタート
        for(int i=0; i < gameData.getLyricsList().size(); i++){
            gameData.setCurrentLyrics(gameData.getLyricsList().get(i));
            System.out.println(gameData.getLyricsList().get(i));
            System.out.print("▶︎ ");
            InputThread inputThread = new InputThread(gameData);
            inputThread.start();
            try{
                //各小節の時間だけスレッドを停止する
                Thread.sleep(gameData.getTimeList().get(i));
            }catch(InterruptedException e){
                System.out.println(e.toString());
            }
            inputThread.Halt();
            System.out.println();
            System.out.println("----------------------------------");
        }
        //音楽ファイルの停止
        clip.stop();
    }

    //スコアを表示
    public void showScore(Player player) throws InterruptedException {
        say("SCORE: " + player.getScore());
        Thread.sleep(1000);
    }

}