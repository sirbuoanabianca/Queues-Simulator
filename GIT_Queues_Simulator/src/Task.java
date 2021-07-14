import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable<Task>{
    private int arrivalTime;    //timpul cand clientul e gata sa mearga la coada
    private int processingTime; //durata servirii clientului
    private int ID;
    private int remainingProcessingTime;

    public Task(int arrivalTime, int processingTime, int ID) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.ID = ID;
        remainingProcessingTime=processingTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    //returneza true daca task-ul e finalizat
    public boolean decrementTimeReturnStatus()
    {
        if(remainingProcessingTime>0)
            remainingProcessingTime--;

        return remainingProcessingTime==0;
    }

    public int getRemainingProcessingTime() {
        return remainingProcessingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public int compareTo(Task o) {
        if(arrivalTime<o.getArrivalTime())
            return -1;
        else
        if(arrivalTime>o.getArrivalTime())
            return 1;
        else
            return 0;

    }

    public int getID() {
        return ID;
    }
}