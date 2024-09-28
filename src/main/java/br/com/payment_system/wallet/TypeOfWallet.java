package br.com.payment_system.wallet;

public enum TypeOfWallet {

    COMUM(1), LOJISTA(2);

    private int value;

    TypeOfWallet(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
