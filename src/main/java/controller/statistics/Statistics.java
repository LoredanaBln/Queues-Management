package controller.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {
    public static int totalWaitingTime = 0;
    private static final String LOG_FILE = "simulation_log.txt";

    public static synchronized void add(int waitingTimePerCashRegister){
        totalWaitingTime += waitingTimePerCashRegister;
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
