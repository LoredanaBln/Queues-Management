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

    public CashRegister() {
        clients = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
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
        int ok = 1;
        if (!clients.isEmpty()) {
            for (Client client : clients) {
                if (ok == 1) {
                    clientsString.append(client.first());
                    clientsString.append("   ");
                    ok = 0;
                } else {
                    clientsString.append(client.other());
                    clientsString.append("   ");
                }
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
                            client.setServiceTime(client.getServiceTime() - 1);
                            waitingPeriod.decrementAndGet();
                            Thread.sleep(1000);
                        }
                        clients.poll();
                        waitingPeriod.decrementAndGet();
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
