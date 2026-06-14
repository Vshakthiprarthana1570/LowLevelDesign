package state;

import enums.Coin;
import org.pm.VendingMachine;

public class HasMoneyState extends VendingMachineState
{
    public HasMoneyState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    public void insertCoin(Coin coin) {
        System.out.println("Amount already received.Please wait for the dispensal");
    }

    @Override
    public void selectItem(String code) {
        System.out.println("Item already selected. Please wait for the dispensal");
    }

    public void dispense()
    {
        vendingMachine.setVendingMachineState(new DispensingState(vendingMachine));
        vendingMachine.dispenseItem();
    }
    public void refund() {
        vendingMachine.refundBalance();
        vendingMachine.reset();
        vendingMachine.setVendingMachineState(new IdleState(vendingMachine));
    }
}
