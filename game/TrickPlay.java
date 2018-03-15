package ru.preference.game;

import java.util.ArrayList;

class TrickPlay extends GameType {
    private Gamer gamer;
    private Contract currentContract;
    private ArrayList<Gamer> whistGamers;

    TrickPlay(PreferenceGame game, Dealing deal, Gamer g) throws Exception {
        currentGame = game;
        currentDeal = deal;
        gamer = g;
        System.out.println("\n" + gamer.getName() + " заказал контракт");
//        gamer.proposeContract();
        currentContract = new Contract((int) (Math.random() * 4 + 6), (int) (Math.random() * 4 + 1)); //случайно заказываем контракт

        typeName = "на взятки";
        System.out.println("Тип игры: " + typeName);
        System.out.println(currentContract);
        whistGamers = new ArrayList<>();

        for (Gamer possibleWhist : currentGame.getGamers()) { //случайно определяем вистующих
            if (possibleWhist != gamer)
                if (Math.round(Math.random()) > 0) {
                    whistGamers.add(possibleWhist);
                    System.out.println(possibleWhist.getName() + " вистует");
                }
        }

        if (whistGamers.size() == 0) { //если никто не вистует "случайно"
            int i = currentGame.getGamers().indexOf(gamer);
            if (i >= 1) whistGamers.add(currentGame.getGamers().get(i - 1));
            else whistGamers.add(currentGame.getGamers().get(i + 1));
            System.out.println(whistGamers.get(0) + " вистует");
        }
    }

    void play() {
        gamer.receiveTalon();
        gamer.discard();
        gamer.discard();
        System.out.println();
        while (currentGame.getDeck().size() != 32) {
            for (int i = 0; i < currentGame.getGamers().size(); i++) {
                currentGame.getGamers().get(i).discard();
            }
            Gamer g = currentGame.getGamers().get((int) Math.round(Math.random() * (currentGame.getGamers().size() - 1)));
            g.receiveTrick();
            System.out.println();
        }
    }

    @Override
    Score calculateResult() {
        System.out.println("Подсчет результатов");
        Score additionToScore = new Score(currentGame);
        for (int i = 0; i < currentGame.getGamers().size(); i++) {
            System.out.println(currentGame.getGamers().get(i).getName() + " взял " + currentGame.getGamers().get(i).getTrickNumber() + " взяток");
        }

        Score score = currentGame.getScore();
        if (gamer.getTrickNumber() < currentContract.getValue()) {
            score.addToDumpPoints(gamer, (currentContract.getValue() - gamer.getTrickNumber()) * currentContract.getPrice());
            additionToScore.addToDumpPoints(gamer, (currentContract.getValue() - gamer.getTrickNumber()) * currentContract.getPrice());
        } else score.addToPool(gamer, currentContract.getPrice());

        int whist = 0;
        for (Gamer whGamer : whistGamers) {
            whist += whGamer.getTrickNumber();
        }

        if (whist < currentContract.getWhistTrick())
            for (Gamer whGamer : whistGamers) {
                score.addToDumpPoints(whGamer, whGamer.getTrickNumber() * currentContract.getPrice());
                additionToScore.addToDumpPoints(whGamer, whGamer.getTrickNumber() * currentContract.getPrice());
            }
        else
            for (Gamer whGamer : whistGamers) {
                score.addToWhistPoints(whGamer, whGamer.getTrickNumber() * currentContract.getPrice(), gamer);
                additionToScore.addToWhistPoints(whGamer, whGamer.getTrickNumber() * currentContract.getPrice(), gamer);
            }
        currentGame.setScore(score);

        return additionToScore;
    }

    public Contract getCurrentContract() {
        return currentContract;
    }

    public ArrayList<Gamer> getWhistGamers() {
        return whistGamers;
    }

    public Gamer getGamer() {
        return gamer;
    }
}