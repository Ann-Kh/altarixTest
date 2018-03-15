package ru.preference.game;

import java.util.ArrayList;
import java.util.HashMap;

class Dealing {
    private PreferenceGame currentGame;
    private int dealNumber;
    private GameType type;
    private Card[] talon = new Card[2];
    private HashMap<Gamer, Card[]> initialCards;
    private HashMap<Gamer, Integer> gamersTrickNumber;
    private Score additionToTotalScore;
    private Score dealingTotalScore;

    Dealing(PreferenceGame game, int number) {
        currentGame = game;
        dealNumber = number;
        initialCards = new HashMap<>();
        gamersTrickNumber = new HashMap<>();
    }

    protected void startDeal() throws Exception {
        System.out.println("\n Раздача " + dealNumber );
        giveCards();
        talon[0] = currentGame.getDeck().get(0);
        talon[1] = currentGame.getDeck().get(1);

        showTalon();
        doTrade();
        type.play();
        additionToTotalScore = type.calculateResult();
        for (int i = 0; i < currentGame.getGamers().size(); i++) {
            gamersTrickNumber.put(currentGame.getGamers().get(i), currentGame.getGamers().get(i).getTrickNumber());
        }
        dealingTotalScore = new Score(currentGame);
        Score currentScore = currentGame.getScore();
        System.out.println(currentScore);

        for (Gamer g : currentGame.getGamers()) {
            Score.GamerScore gamerScore = currentScore.getGamerScore(g);
            dealingTotalScore.addToPool(g, gamerScore.getPoolPoints());
            dealingTotalScore.addToDumpPoints(g, gamerScore.getDumpPoints());

            for (Gamer gamerToWhist : currentGame.getGamers()) {
                if (gamerToWhist != g)
                    dealingTotalScore.addToWhistPoints(g, gamerScore.getWhistPoints().get(gamerToWhist), gamerToWhist);
            }
        }

    }


    protected void giveCards() {
        for (int i = 0; i < currentGame.getGamers().size(); i++) {
            currentGame.getGamers().get(i).receiveCards();

            ArrayList<Card> currentCards = currentGame.getGamers().get(i).getCurrentCards();
            System.out.println("Полученные карты: " + currentCards);


            initialCards.put(currentGame.getGamers().get(i), currentCards.toArray(new Card[currentCards.size()]));
        }
    }

    protected void doTrade() throws Exception {
        int i = 1;
        while (i < 50) { //заглушка процесса торговли
            for (int j = 0; j < currentGame.getGamers().size(); j++) {
                currentGame.getGamers().get(j).proposeContract();
            }
            i = i * (int) Math.round(Math.random() * 9 + 1);
        }

        //случайное определение типа игры и игрока,заказавшего контракт
        int gType = (int) Math.round(Math.random() * 2) + 1;

        switch (gType) {
            case 1:
                type = new TrickPlay(currentGame, this, currentGame.getGamers().get((int) Math.round(Math.random() * (currentGame.getGamers().size() - 1))));
                break;
            case 2:
                type = new Misere(currentGame, this, currentGame.getGamers().get((int) Math.round(Math.random() * (currentGame.getGamers().size() - 1))));
                break;
            case 3:
                type = new AllPass(currentGame, this);
                break;

        }
    }


    protected void showTalon() {
        System.out.println("Прикуп " + talon[0] + " " + talon[1]);
    }

    public int getDealNumber() {
        return dealNumber;
    }

    public Card[] getTalon() {
        return talon;
    }

    public HashMap<Gamer, Card[]> getInitialCards() {
        return initialCards;
    }

    public GameType getType() {
        return type;
    }

    public HashMap<Gamer, Integer> getGamersTrickNumber() {
        return gamersTrickNumber;
    }

    public Score getAdditionToTotalScore() {
        return additionToTotalScore;
    }

    public Score getDealingTotalScore() {
        return dealingTotalScore;
    }
}

