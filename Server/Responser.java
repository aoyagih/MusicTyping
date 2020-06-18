import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class Responser{
    private static ArrayList<String> lyricsList = new ArrayList<>();  //歌詞リスト
    private static ArrayList<Long> timeList = new ArrayList<>();      //各小節の長さのリスト

    //歌詞データをファイルからArrayListにロード
    public void loadLyricsList(String lylicsFilePath) throws FileNotFoundException {
        Responser.lyricsList.add("");
        Scanner s = new Scanner(new File(lylicsFilePath));
        Responser.lyricsList.clear();
        while (s.hasNext()){
            Responser.lyricsList.add(s.nextLine());
        }
        s.close();
    }

    //各小節の長さのデータをファイルからArrayListにロード
    public void loadTimeList(String timeFilePath) throws FileNotFoundException {
        Responser.timeList.add(0L);
        Scanner s = new Scanner(new File(timeFilePath));
        Responser.timeList.clear();
        while (s.hasNext()){
            Responser.timeList.add(s.nextLong());
        }
        s.close();
    }

    public void sendGameData() throws IOException {
        try {
            ServerSocket s = new ServerSocket(8080); // ソケットを作成する
            Socket socket = s.accept();  // コネクション設定要求を待つ

            try {
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                //歌詞リストを送信
                objectOutput.writeObject(Responser.lyricsList);
                //時間の長さリストを送信
                objectOutput.writeObject(Responser.timeList);      
                
                //音楽ファイルの送信
                FileInputStream fis = new FileInputStream("data/music/konpeki_easy/sound.wav");
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                objectOutput.writeObject(buffer); 
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } 
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void registerScore() throws IOException {
        ServerSocket s = new ServerSocket(8080); // ソケットを作成する
        Socket socket = s.accept();  // コネクション設定要求を待つ
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String p_name = in.readLine();
        String p_score = in.readLine();


        //key=ID, value=score
        Map<Integer,Integer> scores = new HashMap<Integer,Integer>();
        ArrayList<String> playerNameList = new ArrayList<String>();
        
        Scanner s2 = new Scanner(new File("data/ranking/konpeki.txt"));
        int count = 1;
        
        int id = -1, score = -1;
        String name = "";
        while (s2.hasNext()){
            if(count % 3 == 1){
                id = Integer.parseInt(s2.next());
            }else if(count % 3 == 2){
                score = Integer.parseInt(s2.next());
            }else{
                name = s2.next();
                scores.put(id,score);
                playerNameList.add(name);
            }
            count++;
        }

        try{
            int nextId = playerNameList.size()+1;
            File file = new File("data/ranking/konpeki.txt");
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(nextId + " " + p_score + " " + p_name + "\n");
            filewriter.close();
            scores.put(nextId, Integer.parseInt(p_score));
            playerNameList.add(p_name);
        }catch(IOException e){
            System.out.println(e);
        }

        // 2.Map.Entryのリストを作成する
        List<Entry<Integer, Integer>> list_entries = new ArrayList<Entry<Integer, Integer>>(scores.entrySet());
        
        // 6. 比較関数Comparatorを使用してMap.Entryの値を比較する（降順）
        Collections.sort(list_entries, new Comparator<Entry<Integer, Integer>>() {
            public int compare(Entry<Integer, Integer> obj1, Entry<Integer, Integer> obj2) {
                //降順
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);  //送信バッファ設定

        String[] ordinalNumber = {"1st", "2nd", "3rd", "4th", "5th"};
        out.println("--- RANKING, PLAYER, SCORE ---");
        for(int i=0; i<5; i++){
            out.println(ordinalNumber[i] + ", "  + playerNameList.get(list_entries.get(i).getKey()-1) + ", " + list_entries.get(i).getValue());
        }
        s.close();
    }

}