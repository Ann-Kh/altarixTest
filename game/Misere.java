package ru.preference.game;

class Misere extends GameType {

    private Gamer gamer;

    Misere(PreferenceGame game, Dealing deal, Gamer g) {
        currentGame = game;
        currentDeal = deal;
        gamer = g;
        System.out.println("\n" + gamer.getName() + " заказал контракт");
        typeName = "мизер";
        System.out.println("Тип игры: " + typeName);
    }

    @Override
    void play() {
        currentDeal.showTalon();
        gamer.receiveTalon();
        gamer.discard();
        gamer.discard();

        while (currentGame.getDeck().size() != 32) {
            for (int i = 0; i < currentGame.getGamers().size(); i++) {
                currentGame.getGamers().get(i).discard();
            }

            //случайно определяем игрока,взявшего взятку
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
        if (gamer.getTrickNumber() > 0) {
            score.addToDumpPoints(gamer, 10);
            additionToScore.addToDumpPoints(gamer, 10);

        } else {
            score.addToPool(gamer, 10);
            additionToScore.addToDumpPoints(gamer, 10);
        }
        currentGame.setScore(score);
        return additionToScore;
    }

    public Gamer getGamer() {
        return gamer;
    }
}
