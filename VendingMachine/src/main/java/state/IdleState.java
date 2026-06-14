package state;

import enums.Coin;
import org.pm.VendingMachine;

public class IdleState extends VendingMachineState
{
    public IdleState(VendingMachine vendingMachine)
    {
        super(vendingMachine);
    }
    public void insertCoin(Coin coin)
    {
        System.out.println("Please select an item before inserting coins.");
    }

    public void selectItem(String code)
    {
        if(!vendingMachine.getInventory().isAvailable(code))
        {
            System.out.println("Selected item is out of stock. Please select another item.");
            return;
        }
        vendingMachine.setVendingMachineState(new HasSelectionState(vendingMachine));
        vendingMachine.setSelectedItemCode(code);
        System.out.println("Item " + code + " selected. Please insert coins.");
    }

    public void dispense()
    {
        System.out.println("No item selected. Please select an item before dispensing.");
    }
    public void refund()
    {
        System.out.println("No item selected.Please select items before clicking refund");
    }
}
