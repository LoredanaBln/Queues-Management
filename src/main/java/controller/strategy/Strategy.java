package controller.strategy;

import model.CashRegister;
import model.Client;

import java.util.List;

public interface Strategy {
    void addClient(List<CashRegister> cashRegisterList, Client client);
}
