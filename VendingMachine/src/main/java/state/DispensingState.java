package state;

import enums.Coin;
import org.pm.VendingMachine;

public class DispensingState extends VendingMachineState
{
    public DispensingState(VendingMachine vendingMachine)
    {
        super(vendingMachine);
    }

    @Override
    public void insertCoin(Coin coin)
    {
        System.out.println("Please wait, dispensing in progress.");
    }

    @Override
    public void selectItem(String itemName)
    {
        System.out.println("Please wait, dispensing in progress.");
    }

    @Override
    public void dispense()
    {
        System.out.println("Dispensing item...");
    }

    @Override
    public void refund()
    {
        System.out.println("Dispensing item.Cannot refund at this stage.");
    }
}
