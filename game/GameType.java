package ru.preference.game;


abstract class GameType {

    protected Dealing currentDeal;
    protected PreferenceGame currentGame;
    protected String typeName;

    abstract void play();

    abstract Score calculateResult();

    public String getTypeName() {
        return typeName;
    }
}
