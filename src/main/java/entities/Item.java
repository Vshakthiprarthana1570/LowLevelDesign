package entities;

public class Item
{
    private final String code;
    private final String name;
    private final double price;

    public Item(String code, String name, double price)
    {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
