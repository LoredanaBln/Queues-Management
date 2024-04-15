package model;

import controller.statistics.Statistics;
import view.SimulationFrame;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CashRegister implements Runnable {

    private Queue<Client> clients;
    private AtomicInteger waitingPeriod;
    private SimulationFrame simulationFrame;
    private Integer index;

    public CashRegister(SimulationFrame simulationFrame, Integer index) {
        clients = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
        this.simulationFrame = simulationFrame;
        this.index = index;
    }

    public void addClient(Client client) {
        clients.offer(client);
        waitingPeriod.addAndGet(client.getServiceTime());
        Statistics.addToWaitingTime(waitingPeriod.get());
    }

    public Queue<Client> getClients() {
        return clients;
    }

    public String clientsAtCashRegister() {
        StringBuilder clientsString = new StringBuilder();

        if (!clients.isEmpty()) {
            for (Client client : clients) {
                clientsString.append(client.toString());
                clientsString.append(" ");
            }
        }
        return clientsString.toString();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (!clients.isEmpty()) {
                    try {
                        Client client = clients.peek();
                        Thread.sleep(1000);

                        while (client.getServiceTime() > 1) {
                            // simulationFrame.updateProgressBars(client.getInitialServiceTime(), client.getServiceTime(), this.index, client.getID());
                            client.setServiceTime(client.getServiceTime() - 1);
                            waitingPeriod.decrementAndGet();
                            Thread.sleep(1000);
                        }
                        clients.poll();
                        waitingPeriod.decrementAndGet();
                        // simulationFrame.updateProgressBars(client.getInitialServiceTime(), 0, this.index, client.getID());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
