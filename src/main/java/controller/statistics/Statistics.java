package controller.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {
    public static int totalWaitingTime = 0;
    public static int totalServiceTime = 0;
    public static int peakHour = 0;
    public static int maxNumberOfClients = 0;
    private static final String LOG_FILE = "simulation_log.txt";

    public static synchronized void addToWaitingTime(int waitingTimePerCashRegister){
        totalWaitingTime += waitingTimePerCashRegister;
    }

    public static synchronized void addServiceTime(int serviceTimePerClient){
        totalServiceTime += serviceTimePerClient;
    }

    public static void getPeakHour(int totalNumberOfClients, int time){
        if(totalNumberOfClients > maxNumberOfClients){
            maxNumberOfClients = totalNumberOfClients;
            peakHour = time;
        }
    }

    public static void writeToFile(String averageWaitingTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.newLine();
            writer.write(String.valueOf(averageWaitingTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
