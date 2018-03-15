package ru.preference.game;

class Contract {
    private int value;
    private int suit;
    private int price;
    private int whistTrick;

    Contract(int val, int s) throws Exception {
        if (val < 6 || val > 11) throw new Exception("Неверный контракт");
        if (s < 1 || s > 5) throw new Exception("Неверный контракт");
        if (val == 11 && s != 5) throw new Exception("Неверный контракт");

        value = val;
        suit = s;
        switch (value) {
            case 6:
                price = 2;
                whistTrick = 4;
                break;
            case 7:
                price = 4;
                whistTrick = 2;
                break;
            case 8:
                price = 6;
                whistTrick = 1;
                break;
            case 9:
                price = 8;
                whistTrick = 1;
                break;
            case 10:
                price = 10;
                whistTrick = 1;
                break;

            case 11: //мизер
                price = 10;
                whistTrick = 0;
                break;
        }
    }

    @Override
    public String toString() {

        String str = "Контракт ";

        if (value == 11) return str += "мизер";

        str += value + " ";
        switch (suit) {
            case 1:
                str += "пики";
                break;
            case 2:
                str += "трефы";
                break;
            case 3:
                str += "бубны";
                break;
            case 4:
                str += "червы";
                break;
            case 5:
                str += "без масти";
                break;
        }
        return str;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public int getPrice() {
        return price;
    }

    public int getWhistTrick() {
        return whistTrick;
    }
}