package controller;

import controller.strategy.ShortestQueueStrategy;
import controller.strategy.ShortestTimeStrategy;
import controller.strategy.Strategy;
import model.CashRegister;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<CashRegister> cashRegisterList;
    private Integer noOfCashRegisters;
    private Integer maxNoOfClientsPerCashRegister;
    private Strategy strategy;

    public Scheduler(Integer noCashRegisters, Integer maxClientsPerCashRegister) {
        this.maxNoOfClientsPerCashRegister = maxClientsPerCashRegister;
        this.noOfCashRegisters = noCashRegisters;
        this.cashRegisterList = new ArrayList<>();

        for (int i = 0; i < noCashRegisters; i++) {
            CashRegister cashRegister = new CashRegister();
            cashRegisterList.add(cashRegister);
            Thread thread = new Thread(cashRegister);
            thread.start();
        }
        this.strategy = new ShortestTimeStrategy();
    }

    public void changeStrategy() {
        int shortestWaitingTime = Integer.MAX_VALUE;
        for (CashRegister cashRegister : cashRegisterList) {
            int currentWaitingTime = cashRegister.getWaitingPeriod().intValue();
            if (currentWaitingTime < shortestWaitingTime) {
                shortestWaitingTime = currentWaitingTime;
            }
        }
        if (shortestWaitingTime == Integer.MAX_VALUE) {
            strategy = new ShortestQueueStrategy();
        } else {
            strategy = new ShortestTimeStrategy();
        }
    }

    public void assignClientToCashRegister(Client client) {
        if (strategy != null) {
            changeStrategy();
            strategy.addClient(cashRegisterList, client);
        }
    }

    public List<CashRegister> getCashRegisterList() {
        return cashRegisterList;
    }
}
