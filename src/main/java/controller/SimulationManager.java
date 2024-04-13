package controller;

import controller.statistics.Logger;
import model.CashRegister;
import model.Client;
import view.SimulationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SimulationManager implements Runnable {
    private Scheduler scheduler = null;
    private List<Client> clients = new ArrayList<>();
    private ClientGenerator clientGenerator;
    private List<JProgressBar> progressBars;

    SimulationFrame simulationFrame;

    public Integer simulationTime = 0;
    public Integer arrivalTime = 0;
    public Integer maxServiceTime = 0;
    public Integer minServiceTime = 0;
    public Integer numberOfCashRegisters = 0;
    public Integer numberOfClients = 0;

    public SimulationManager() {
        this.simulationFrame = new SimulationFrame();
        simulationFrame.addStartButtonListener(this);
    }

    public synchronized void initializeSimulation() {
        this.simulationTime = simulationFrame.getSimulationTime();
        this.maxServiceTime = simulationFrame.getMaxServiceTime();
        this.minServiceTime = simulationFrame.getMinServiceTime();
        this.numberOfCashRegisters = simulationFrame.getNumberOfCashRegisters();
        this.numberOfClients = simulationFrame.getNumberOfClients();
        this.arrivalTime = simulationFrame.getArrivalTime();
        this.progressBars = new ArrayList<>();
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
            Logger.log("\nTime " + currentTime + "\nWaiting clients: ");

            for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    scheduler.assignClientToCashRegister(client);
                    iterator.remove();
                } else {
                    Logger.log("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                }
            }
            for (CashRegister cashRegister : scheduler.getCashRegisterList()) {
                Logger.log("\nQueue " + ": ");
                if (cashRegister.getClients().isEmpty()) {
                    Logger.log("closed");
                } else {
                    for (Client client : cashRegister.getClients()) {
                        Logger.log("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
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
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ignored) {
                }
            }
            SimulationManager simulationManager = new SimulationManager();
        });
    }
}
