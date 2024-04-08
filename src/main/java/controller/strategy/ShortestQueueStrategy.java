package controller.strategy;

import model.CashRegister;
import model.Client;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addClient(List<CashRegister> cashRegisterList, Client client) {
        CashRegister minCashRegisterQueue = cashRegisterList.get(0);
        for (CashRegister cashReg : cashRegisterList) {
            if (cashReg.getClients().size() < minCashRegisterQueue.getClients().size()) {
                minCashRegisterQueue = cashReg;
            }
        }
        minCashRegisterQueue.addClient(client);
    }
}
