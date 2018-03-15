package ru.preference.game;

class Card {
    private int value;
    private int suit;

    Card(int v, int s) {
        value = v;
        suit = s;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        String str = "Масть ";

        switch (suit) {
            case 1:
                str += "пики" + " Достоинство ";
                break;
            case 2:
                str += "трефы" + " Достоинство ";
                break;
            case 3:
                str += "бубны" + " Достоинство ";
                break;
            case 4:
                str += "червы" + " Достоинство ";
                break;
        }

        switch (value) {
            case 11:
                str += "Валет";
                break;
            case 12:
                str += "Дама";
                break;
            case 13:
                str += "Король";
                break;
            case 14:
                str += "Туз";
                break;
            default:
                str += value;
        }
        return str;
    }
}