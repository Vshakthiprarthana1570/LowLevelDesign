package state;

import enums.Coin;
import org.pm.VendingMachine;

public abstract class VendingMachineState
{
    public VendingMachine vendingMachine;

    public VendingMachineState(VendingMachine machine)
    {
        this.vendingMachine = machine;
    }
    public abstract void selectItem(String code);
    public abstract void insertCoin(Coin coin);
    public abstract void dispense();
    public abstract void refund();
}
