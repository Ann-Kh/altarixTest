package ru.preference.game;

import java.util.*;

public class PreferenceGame {
    private int MAX_DEAL_NUMBER;
    private ArrayList<Dealing> deals = new ArrayList<>();
    private ArrayList<Gamer> gamers = new ArrayList<>();
    private Gamer cardsHandler = null;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();
    private Score score;

    public PreferenceGame(int maxDealNumber, int gamerNumber) throws Exception {

        if (gamerNumber < 3 || gamerNumber > 4) {
            throw new Exception("");
        }
        MAX_DEAL_NUMBER = maxDealNumber;

        for (int i = 0; i < 3; i++) {
            gamers.add(new Gamer(this, "Игрок " + i));
        }
        if (gamerNumber == 4) cardsHandler = new Gamer(this, "Игрок 4"); //пусть четвертый игрок всегда сдающий

        for (int value = 7; value <= 14; value++) {
            for (int suit = 1; suit <= 4; suit++) {
                deck.add(new Card(value, suit));
            }
        }

        for (int value = 6; value <= 10; value++) {
            for (int suit = 1; suit <= 5; suit++) {
                contracts.add(new Contract(value, suit));
            }
        }
        contracts.add(new Contract(11, 5));
        score = new Score(this);
    }

    public void startGame() throws Exception {
        for (int i = 0; i < MAX_DEAL_NUMBER; i++) {
            deals.add(new Dealing(this, i));
            deals.get(i).startDeal();
        }
    }

    public void getDealData(int dealNumber) {//API1
        Dealing dealing = deals.get(dealNumber);
        HashMap<Gamer, Card[]> initialCards = dealing.getInitialCards();

        for (Map.Entry<Gamer, Card[]> gamerCards : initialCards.entrySet()) {
            System.out.println();
            System.out.println("Карты игрока " + gamerCards.getKey().getName() + " полученные при раздаче");
            for (Card card : gamerCards.getValue()) {
                System.out.println("        " + card);
            }
        }
        System.out.println();
        dealing.showTalon();
        System.out.println("Тип игры " + dealing.getType().getTypeName());
    }

    public void getTradeData(int dealNumber) { //API2
        Dealing dealing = deals.get(dealNumber);
        System.out.println("Тип игры " + dealing.getType().getTypeName());
        dealing.showTalon();
    }

    public void getGamerTradeData(int dealNumber, Gamer gamer) {//API3
        Dealing dealing = deals.get(dealNumber);
        System.out.println("Игрок " + gamer.getName());
    }

    public void getPlayProcessingData(int dealNumber) { //API4
        Dealing dealing = deals.get(dealNumber);
        HashMap<Gamer, Integer> gamersTrickNumber = dealing.getGamersTrickNumber();
        for (Map.Entry<Gamer, Integer> gamerTrickNumber : gamersTrickNumber.entrySet()) {
            System.out.println("Игрок " + gamerTrickNumber.getKey().getName() + " взял " + gamerTrickNumber.getValue() + " взяток");
        }
    }

    public void getPlayResultData(int dealNumber) { //API5
        Dealing dealing = deals.get(dealNumber);
        HashMap<Gamer, Integer> gamersTrickNumber = dealing.getGamersTrickNumber();
        for (Map.Entry<Gamer, Integer> gamerTrickNumber : gamersTrickNumber.entrySet()) {
            System.out.println("Игрок " + gamerTrickNumber.getKey().getName() + " взял " + gamerTrickNumber.getValue() + " взяток");
        }
        System.out.println("Добавление к общему счету:\n" + dealing.getAdditionToTotalScore());
    }

    public void getDealingProcess(int dealNumber) { //API6
        Dealing dealing = deals.get(dealNumber);
        getDealData(dealNumber);
        GameType type = dealing.getType();
        if (type instanceof TrickPlay) {
            System.out.println(((TrickPlay) type).getCurrentContract());
            System.out.println("Игрок " + ((TrickPlay) type).getGamer().getName() + " заказал контракт");
        }
        if (type instanceof Misere) {
            System.out.println("Игрок " + ((Misere) type).getGamer().getName() + " заказал контракт");
        }
        getPlayResultData(dealNumber);
    }


    public void getDealScoreForGamer(int dealNumber, Gamer g) { //API7
        Dealing dealing = deals.get(dealNumber);
        Score dealingTotalScore = dealing.getDealingTotalScore();
        System.out.println(dealingTotalScore.getGamerScore(g));
    }

    public int getIntermediateGamerResultint(int dealNumber, Gamer g) { //API8
        Dealing dealing = deals.get(dealNumber);
        Score dealingTotalScore = dealing.getDealingTotalScore();
        Score.GamerScore gamerScore = dealingTotalScore.getGamerScore(g);
        int sum = gamerScore.getPoolPoints() - gamerScore.getDumpPoints();
        Collection<Integer> values = gamerScore.getWhistPoints().values();
        Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next();
        }
        System.out.println(sum);
        return sum;
    }


    public void getGamerStatistic(int dealNumber, Gamer g) { //API9
        Dealing dealing;
        int allPassNumber = 0, misereNumber = 0;
        System.out.println("Сыгранные игры ");
        for (int i = 0; i <= dealNumber; i++) {
            dealing = deals.get(i);
            GameType type = dealing.getType();

            if (type instanceof TrickPlay) {
                TrickPlay trickType = ((TrickPlay) type);
                System.out.println(trickType.getCurrentContract());
            }

            if (type instanceof Misere) {
                Misere misereType = ((Misere) type);
                if (misereType.getGamer() == g) {
                    HashMap<Gamer, Integer> gamersTrickNumber = dealing.getGamersTrickNumber();
                    if (gamersTrickNumber.get(g) == 0) misereNumber++;
                }
            }

            if (type instanceof AllPass) {
                AllPass passType = ((AllPass) type);
                HashMap<Gamer, Integer> gamersTrickNumber = dealing.getGamersTrickNumber();
                Integer gTricks = gamersTrickNumber.get(g);
                Collection<Integer> values = gamersTrickNumber.values();
                Iterator<Integer> iterator = values.iterator();

                int failedPassTrick = 0;
                while (iterator.hasNext()) {
                    if (iterator.next() < gTricks) {
                        failedPassTrick++;
                        break;
                    }
                }
                if (failedPassTrick == 0) allPassNumber++;
            }
        }
        System.out.println("Успешные мизеры " + misereNumber);
        System.out.println("Успешные распасы " + allPassNumber);
    }

    public void getIntermediateResult(int dealNumber) { //API10
        Dealing dealing = deals.get(dealNumber);
        Iterator<Gamer> gamerIterator = gamers.iterator();
        while (gamerIterator.hasNext()) {
            Gamer nextGamer = gamerIterator.next();
            System.out.printf("Игрок " + nextGamer.getName() + " ");
            getIntermediateGamerResultint(dealNumber, nextGamer);
        }
    }

    public ArrayList<Dealing> getDeals() {
        return deals;
    }

    public ArrayList<Gamer> getGamers() {
        return gamers;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Score getScore() {
        return score;
    }

    protected void addToDeck(Card newCard) {
        deck.add(newCard);
    }

    protected void removeFromDeck(Card newCard) {
        deck.remove(newCard);
    }

    protected Card removeFromDeck(int cardInd) {
        return deck.remove(cardInd);
    }


    protected void setScore(Score newScore) {
        score = newScore;
    }


}
