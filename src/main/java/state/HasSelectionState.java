package state;

import enums.Coin;
import org.pm.VendingMachine;

public class HasSelectionState  extends  VendingMachineState
{
    public HasSelectionState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    public void insertCoin(Coin coin)
    {
        double amount = coin.getValue();
        vendingMachine.addBalance(amount);

        if(vendingMachine.getBalance() >= vendingMachine.getSelectedItem().getPrice())
        {
            System.out.println("Sufficient money received.");
            vendingMachine.setVendingMachineState(new HasMoneyState(vendingMachine));
            System.out.println("Inserted coin: " + coin + ". Current balance: " + vendingMachine.getBalance());
        }
    }

    public void selectItem(String code)
    {
        System.out.println("Item already selected. Please insert coins to proceed or cancel the selection.");
    }
    public void dispense()
    {
        System.out.println("Please insert sufficient coins before dispensing the item.");
    }

    public void refund()
    {
        vendingMachine.reset();
        vendingMachine.setVendingMachineState(new IdleState(vendingMachine));
        System.out.println("Selection cancelled. Refunding the inserted coins.");
    }
}
