package ru.preference.game;

import java.util.ArrayList;

public class Gamer {
    private PreferenceGame currentGame;
    private String name;
    private ArrayList<Card> currentCards = new ArrayList<Card>();
    private int trickNumber = 0;

    Gamer(PreferenceGame game, String gamerName) {
        currentGame = game;
        name = gamerName;
    }

    protected void receiveCards() {

        for (int i = 0; i < 10; i++) {
            currentCards.add(currentGame.removeFromDeck((int) Math.round(Math.random() * (currentGame.getDeck().size() - 1))));
        }
        System.out.println(name + " карты раздачи получены");
        trickNumber = 0;
    }

    protected void proposeContract() {
        System.out.println("Игрок " + name + " предложил контракт");
    }

    protected void receiveTalon() {

        while (currentGame.getDeck().size() > 0) {
            currentCards.add(currentGame.removeFromDeck((int) Math.round(Math.random() * (currentGame.getDeck().size() - 1))));
        }

        System.out.println("Игрок " + name + " получил прикуп");
    }

    protected void discard() {
        System.out.println("Игрок " + name + " сбросил карту");
        currentGame.addToDeck(currentCards.remove((int) Math.round(Math.random() * (currentCards.size() - 1))));
    }

    protected void receiveTrick() {
        System.out.println(name + " взял взятку");
        trickNumber++;
    }

    public ArrayList<Card> getCurrentCards() {
        return currentCards;
    }

    public String getName() {
        return name;
    }

    public int getTrickNumber() {
        return trickNumber;
    }
}
