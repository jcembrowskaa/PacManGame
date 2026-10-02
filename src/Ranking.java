import java.io.*;
import java.util.ArrayList;

public class Ranking implements Serializable {
    private final String nickname;
    private final int score;
    private static final String rankingFile = "ranking.dat";

    public Ranking(String nickname, int score){

        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;}
    public int getScore() {
        return score; }



    public static ArrayList<Ranking> loadRanking() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(rankingFile));
            ArrayList<Ranking> rankingList = (ArrayList<Ranking>) in.readObject();
            in.close();
            rankingList.sort((a, b) -> Integer.compare(b.getScore(), a.getScore())); // sortuj malejąco
            return rankingList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static void addToRanking(Ranking ranking) {
        ArrayList<Ranking> list = loadRanking();
        list.add(ranking);
        saveRanking(list);
    }

    public static void saveRanking(ArrayList<Ranking> ranking) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rankingFile));
            out.writeObject(ranking);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


