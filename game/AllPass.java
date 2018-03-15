package ru.preference.game;

class AllPass extends GameType {

    AllPass(PreferenceGame game, Dealing deal) {
        currentGame = game;
        currentDeal = deal;
        typeName = "распасы";
        System.out.println("Тип игры: " + typeName);
    }

    @Override
    void play() {
        currentDeal.showTalon();
        while (currentGame.getDeck().size() != 32) {
            for (int i = 0; i < currentGame.getGamers().size(); i++) {
                currentGame.getGamers().get(i).discard();
            }

            Gamer g = currentGame.getGamers().get((int) Math.round(Math.random() * (currentGame.getGamers().size() - 1))); //случайный выбор игрока, берущего взятку
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

            Score score = currentGame.getScore();
            score.addToDumpPoints(currentGame.getGamers().get(i), currentGame.getGamers().get(i).getTrickNumber());
            additionToScore.addToDumpPoints(currentGame.getGamers().get(i), currentGame.getGamers().get(i).getTrickNumber());
            currentGame.setScore(score);
        }
        return additionToScore;
    }
}