package ru.preference.game;

import java.util.ArrayList;
import java.util.HashMap;

class Score {

    private PreferenceGame currentGame;
    private ArrayList<GamerScore> scoreSheet;

    class GamerScore {
        private Gamer gamer;
        private int poolPoints;
        private HashMap<Gamer, Integer> whistPoints;
        private int dumpPoints;

        GamerScore(Gamer g, int gamersNumber) {
            gamer = g;
            poolPoints = 0;
            whistPoints = new HashMap<>();

            for (Gamer anotherGamer : currentGame.getGamers()) {
                if (anotherGamer != gamer) whistPoints.put(anotherGamer, 0);
            }
            dumpPoints = 0;
        }

        public int getPoolPoints() {
            return poolPoints;
        }

        public HashMap<Gamer, Integer> getWhistPoints() {
            return whistPoints;
        }

        public int getDumpPoints() {
            return dumpPoints;
        }

        @Override
        public String toString() {
            String str = "";
            for (int w : whistPoints.values()) {
                str += w + " ";
            }
            return "Счет игрока " + gamer.getName() + " Пуля " + poolPoints + " Гора " + dumpPoints + " Висты " + str + "\n";
        }
    }

    Score(PreferenceGame game) {
        currentGame = game;
        scoreSheet = new ArrayList<>();
        for (Gamer g : currentGame.getGamers()) {
            scoreSheet.add(new GamerScore(g, currentGame.getGamers().size()));
        }
    }

    public void addToPool(Gamer g, int value) {
        GamerScore gamerScore;
        int i = 0;
        do {
            gamerScore = scoreSheet.get(i);
            i++;
        } while (gamerScore.gamer != g);
        gamerScore.poolPoints += value;
    }

    public void addToWhistPoints(Gamer g, int value, Gamer gamerToWhist) {
        GamerScore gamerScore;
        int i = 0;
        do {
            gamerScore = scoreSheet.get(i);
            i++;
        } while (gamerScore.gamer != g);
        gamerScore.whistPoints.put(gamerToWhist, gamerScore.whistPoints.get(gamerToWhist) + value);
    }

    public void addToDumpPoints(Gamer g, int value) {
        GamerScore gamerScore;
        int i = 0;
        do {
            gamerScore = scoreSheet.get(i);
            i++;
        } while (gamerScore.gamer != g);
        gamerScore.dumpPoints += value;
    }


    public ArrayList<GamerScore> getScoreSheet() {
        return scoreSheet;
    }

    public GamerScore getGamerScore(Gamer g) {
        int i = 0;
        while (scoreSheet.get(i).gamer != g) {
            i++;
        }
        return scoreSheet.get(i);

    }

    @Override
    public String toString() {
        String str = "";
        for (GamerScore gScore : scoreSheet) {
            str += gScore.toString();
        }
        return str;
    }
}
