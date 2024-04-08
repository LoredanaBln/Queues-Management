package model;

public class Client implements Comparable<Client>{
    private Integer ID;
    private Integer arrivalTime;
    private Integer serviceTime;
    private int initialServiceTime;

    public Client() {
    }

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "\n" + '(' +ID + ", " + arrivalTime + ", " + serviceTime + ')';
    }


    @Override
    public int compareTo(Client o) {
        return this.arrivalTime.compareTo(o.getArrivalTime());
    }

    public int getInitialServiceTime() {
        return initialServiceTime;
    }

    public void setInitialServiceTime(int initialServiceTime) {
        this.initialServiceTime = initialServiceTime;
    }
}
