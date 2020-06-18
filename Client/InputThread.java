import java.io.*;

public class InputThread extends Thread{
    private GameData gameData;
    private boolean _halt = false;
    private String input = "";  //ユーザーのタイピング入力
    private boolean answered = false;  //既に正解済みかどうか

    //コンストラクタ
    InputThread(GameData gameData){
        this.gameData = gameData;
    }
   
    public void Halt(){
        _halt = true;
    }

    public void run(){
        try{
            //キーボードの入力ストリームを取得
            BufferedReader com = new BufferedReader(new InputStreamReader(System.in)); 
           
            while(_halt != true){
                if(com.ready()){
                    input = com.readLine();
                    if(answered){
                        System.out.println("--- ALREADY ANSWERED ---");
                    }else{
                        if(input.equals(gameData.getCurrentLyrics())){
                            gameData.getPlayer().incrementScore();
                            System.out.println("--- GOOD!  Score:" + gameData.getPlayer().getScore() + " ---");
                            answered = true;
                        }else{
                            System.out.println("--- WRONG!  Score:" + gameData.getPlayer().getScore() + " ---");
                        }
                        
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }
}