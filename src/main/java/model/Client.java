package model;

public class Client implements Comparable<Client> {
    private Integer ID;
    private Integer arrivalTime;
    private Integer serviceTime;

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

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Client o) {
        return this.arrivalTime.compareTo(o.getArrivalTime());
    }

    public String first(){
        return ID + "\uD83D\uDE01 " + serviceTime;
    }

    public String other(){
        return ID + "\uD83D\uDE34 " + serviceTime;
    }
}
