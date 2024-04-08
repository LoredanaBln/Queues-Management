package controller.strategy;

import model.CashRegister;
import model.Client;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {

    @Override
    public void addClient(List<CashRegister> cashRegisterList, Client client) {
        CashRegister minTimeCashRegister = cashRegisterList.get(0);
        for (CashRegister cashReg : cashRegisterList) {
            if (cashReg.getWaitingPeriod().intValue() < minTimeCashRegister.getWaitingPeriod().intValue()) {
                minTimeCashRegister = cashReg;
            }
        }
        minTimeCashRegister.addClient(client);
    }
}
