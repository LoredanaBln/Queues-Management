package controller;

import model.Client;

import java.util.*;

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

    public List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfClients; i++) {
            int id = i + 1;
            int arrival = random.nextInt(arrivalTime) + 1;
            int service = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
            Client client = new Client(id, arrival, service);
            clients.add(client);
        }
        Collections.sort(clients);
        return clients;
    }



}
