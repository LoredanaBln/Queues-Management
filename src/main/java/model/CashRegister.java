package model;
import java.util.Queue;
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
        int initialServiceTime = client.getServiceTime();
        waitingPeriod.addAndGet(initialServiceTime);
        client.setInitialServiceTime(initialServiceTime);

    }

    public Queue<Client> getClients() {
        return clients;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (!clients.isEmpty()) {
                try {
                    Client client = clients.peek();
                    //put a "poison" client to break while
                    Thread.sleep(1000);

                    while (client.getServiceTime() > 1) {
                        client.setServiceTime(client.getServiceTime() - 1);
                        waitingPeriod.decrementAndGet();
                        Thread.sleep(1000);
                    }
                    waitingPeriod.decrementAndGet();
                    clients.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
