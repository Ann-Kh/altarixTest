package ru.preference.launcher;

import ru.preference.game.PreferenceGame;

import java.util.ArrayList;

public class GameLauncherS2 {
    public static void main(String[] args) {

        System.out.println("Пакеты");

        PreferenceGame game = null;
        try {
            game = new PreferenceGame(12, 4);
            game.startGame();

            for (int i = 0; i < 12; i++) {
                System.out.println("\nДанные о полном процессе розыгрыша раздачи №" + i);
                game.getDealingProcess(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
