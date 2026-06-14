package enums;

public enum Coin
{
    PENNY(1),
    NICKEL(5),
    DIME(10),
    QUARTER(25);

    private final double value;

    Coin(int value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
