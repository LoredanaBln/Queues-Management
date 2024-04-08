package controller;

import model.CashRegister;
import model.Client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private List<Client> clients = new ArrayList<>();
    private ClientGenerator clientGenerator;


    public Integer simulationTime = 60;
    public Integer arrivalTime = 40;
    public Integer maxServiceTime = 7;
    public Integer minServiceTime = 1;
    public Integer numberOfCashRegisters = 5;
    public Integer numberOfClients = 50;

    public SimulationManager() {
        this.clientGenerator = new ClientGenerator(arrivalTime, maxServiceTime, minServiceTime, numberOfClients);
        this.clients = clientGenerator.generateClients();
        this.scheduler = new Scheduler(numberOfCashRegisters, numberOfClients);

        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    @Override
    public synchronized void run() {
        int currentTime = 0;
        while (currentTime <= simulationTime) {
            System.out.print("\nTime " + currentTime + "\nWaiting clients: ");

            for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    scheduler.assignClientToCashRegister(client);
                    iterator.remove();
                } else {
                    System.out.print("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                }
            }
            for (CashRegister cashRegister : scheduler.getCashRegisterList()) {
                System.out.print("\nQueue " + ": ");
                if (cashRegister.getClients().isEmpty()) {
                    System.out.print("closed");
                } else {
                    for (Client client : cashRegister.getClients()) {
                        System.out.print("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                    }
                }
            }
            System.out.println();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentTime++;
        }
    }



    public static void main(String[] args) {
        SimulationManager simulationManager = new SimulationManager();
        Thread t = new Thread(simulationManager);
        t.start();
    }
}
