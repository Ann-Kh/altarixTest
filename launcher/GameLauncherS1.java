package ru.preference.launcher;

import ru.preference.game.PreferenceGame;

public class GameLauncherS1 {

    public static void main(String[] args) {

        System.out.println("Сценарий 1");

        PreferenceGame game= null;
        try {
            game = new PreferenceGame(12,4);
            game.startGame();

            System.out.println();
            System.out.println("\nДанные раздачи №" + 2);
            game.getDealData(2);
            System.out.println("\nДанные раздачи №" + 5);
            game.getDealData(5);

            System.out.println("\nДанные о процессе торговли для раздачи №" + 4);
            game.getTradeData(4);

            System.out.println("\nДанные о процессе заявки игрока "+game.getGamers().get(0).getName()+". Раздача №" + 3);
            game.getGamerTradeData(3,game.getGamers().get(0));

            System.out.println("\nДанные о процессе розыгрыша раздачи №" + 7);
            game.getPlayProcessingData(7);

            System.out.println("\nДанные о результатах розыгрыша раздачи №" + 6);
            game.getPlayResultData(6);

            System.out.println("\nДанные о текущем состоянии пули, горы и вистах игрока " + game.getGamers().get(0).getName() + " после раздачи №" + 8);
            game.getDealScoreForGamer(8,game.getGamers().get(0));

            System.out.println("\nПромежуточный результат игрока " + game.getGamers().get(1).getName() + " после раздачи №" + 10);
            game.getIntermediateGamerResultint(10,game.getGamers().get(1));

            System.out.println("\nПолучение статистики по игроку " + game.getGamers().get(2).getName() + " после раздачи №" +10);
            game.getGamerStatistic(10, game.getGamers().get(2));

            System.out.println("\nПромежуточный результат всех игроков после раздачи №" + 11);
            game.getIntermediateResult(11);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }
}
