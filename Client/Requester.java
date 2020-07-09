import java.net.*;
import java.util.*;
import java.io.*;

public class Requester extends Worker {
    //サーバーからゲームデータを取得する
    public void requestGameData(GameData gameData){
        try {       
            InetAddress addr = InetAddress.getByName("localhost");  // IPアドレスへの変換
            Socket socket = new Socket(addr, 8080);   // ポート番号を設定する
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            //歌詞リストを受信
            Object object = objectInput.readObject();
            gameData.setLyricsList((ArrayList<String>) object);
            //時間の長さのリストを受信
            Object object2 = objectInput.readObject();
            gameData.setTimeList((ArrayList<Long>) object2);
            //音楽ファイルの受信
            byte[] buffer = (byte[])objectInput.readObject();
            FileOutputStream fos = new FileOutputStream("download_music.wav");
            fos.write(buffer);

            fos.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //サーバーにスコアを送信する
    public void sendScore(Player player){
        try {
            String name = player.getName();
            String score = String.valueOf(player.getScore());

            InetAddress addr = InetAddress.getByName("localhost");  // IPアドレスへの変換
            Socket socket = new Socket(addr, 8080);   // ポート番号を設定する
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(name);     //プレイヤーの名前を送信
            out.println(score);    //プレイヤーのスコアを送信

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            for(int i=0; i<6; i++){
                String str = in.readLine();
                System.out.println(str);
                Thread.sleep(1000);
            }


            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRanking(){
        try{
            InetAddress addr = InetAddress.getByName("localhost");  // IPアドレスへの変換
            Socket socket = new Socket(addr, 8080);   // ポート番号を設定する

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            for(int i=0; i<6; i++){
                String str = in.readLine();
                Thread.sleep(100);
                System.out.println(str);
            }
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}