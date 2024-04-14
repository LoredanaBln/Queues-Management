package controller;

import model.Client;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClientGenerator{
    private Integer arrivalTime;
    private Integer maxServiceTime;
    private Integer minServiceTime;
    private Integer numberOfClients;

    public ClientGenerator(Integer arrivalTime, Integer maxServiceTime, Integer minServiceTime, Integer numberOfClients) {
        this.arrivalTime = arrivalTime;
        this.maxServiceTime = maxServiceTime;
        this.minServiceTime = minServiceTime;
        this.numberOfClients = numberOfClients;
    }

    public synchronized List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {
            int id = i + 1;
            int arrival = ThreadLocalRandom.current().nextInt(1, arrivalTime + 1);
            int service = ThreadLocalRandom.current().nextInt(minServiceTime, maxServiceTime + 1);
            Client client = new Client(id, arrival, service);
            clients.add(client);
        }
        Collections.sort(clients);
        return clients;
    }
}
