package org.pm;

import entities.Inventory;
import entities.Item;
import enums.Coin;
import state.IdleState;
import state.VendingMachineState;

public class VendingMachine
{
    private static VendingMachine instance;
    private final Inventory inventory = new Inventory();
    private double balance;
    String selectedItemCode;
    private VendingMachineState vendingMachineState;

    private VendingMachine()
    {
        this.vendingMachineState = new IdleState(this);
        balance = 0.0;
    }

    public static synchronized VendingMachine getInstance()
    {
        if(instance == null)
        {
            instance = new VendingMachine();
        }

        return instance;
    }

    public void setVendingMachineState(VendingMachineState state)
    {
        this.vendingMachineState = state;
    }

    public void addItem(String code, String name,double price, int quantity)
    {
        Item item = new Item(code, name, price);
        inventory.addItem(code, item, quantity);
    }

    public void selectItem(String code)
    {
        vendingMachineState.selectItem(code);
    }
    public void setSelectedItemCode(String code)
    {
        selectedItemCode = code;
    }
    public void insertCoin(Coin coin)
    {
        vendingMachineState.insertCoin(coin);
    }
    public void dispense()
    {
        vendingMachineState.dispense();
    }

    public void dispenseItem() {
        Item item = inventory.getItem(selectedItemCode);
        if (item == null || inventory.getStock(selectedItemCode) <= 0) {
            System.out.println("Item not found or out of stock");
            return;
        }
        if (balance < item.getPrice()) {
            System.out.println("Insufficient balance");
            return;
        }

        inventory.reduceStock(selectedItemCode);
        balance -= item.getPrice();
        if (balance > 0) {
            System.out.println("Refunding the remaining amount");
        }

        reset();
        setVendingMachineState(new IdleState(this));
    }
    public void addBalance(double amount)
    {
        balance += amount;
    }
    public double getBalance()
    {
        return balance;
    }
    public void reset()
    {
        balance = 0;
        selectedItemCode = null;
    }

    public String getSelectedItemCode() {
        return selectedItemCode;
    }
    public Item getSelectedItem()
    {
         return inventory.getItem(selectedItemCode);
    }

    public void refundBalance() {
        System.out.println("Refunding: " + balance);
        balance = 0;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
