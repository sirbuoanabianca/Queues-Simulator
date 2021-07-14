import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> taskList;//lista de clienti din coada
    private AtomicInteger waitingPeriod;//timpul de asteptare al cozii
    private int maxClientsPerServer;//cati clienti pot astepta in acelasi timp la coada
    private boolean running;//true daca thread-ul ruleaza,false daca este oprit

    public Server(int maxClientsPerServer) {
        taskList = new ArrayBlockingQueue<Task>(maxClientsPerServer);
        waitingPeriod= new AtomicInteger(0);
        running=true;
        this.maxClientsPerServer=maxClientsPerServer;
    }

    public void addTask(Task t)
    {   if(taskList.size()<maxClientsPerServer)
        {
            taskList.add(t);
            waitingPeriod.addAndGet(t.getProcessingTime());
        }
    }

    public void stopServer()
    {
        running=false;
    }


    public boolean showClientsOfServer(String fileName,SimulationArea simArea)
    {//returneaza true daca are clienti altfel false
        String content=null;
        for(Task t:taskList) {

            content="(id=" + t.getID() + " arrivalT=" + t.getArrivalTime() + " procT=" + t.getRemainingProcessingTime() + ")\n";
            System.out.print(content);
            SimulationManager.appendStrToFile(fileName, content);
            simArea.addText(content);
        }

        if(taskList.size()==0) {
            System.out.println("closed");
            SimulationManager.appendStrToFile(fileName, "closed\n");
            simArea.addText("closed\n");
            return false;
        }
        return true;

    }

    public int getNoOfClients()
    {int noOfClients=0;
        for(Task t:taskList)
            noOfClients++;
        return noOfClients;

    }

    public void run()
    {
        while (running)
        {
            if(taskList.peek()!=null)
            {   //daca timpul ramas de procesare nu e terminat
                //sterg primul client din coada
                if(taskList.peek().decrementTimeReturnStatus())
                {
                    try {
                        taskList.take();
                    }
                    catch (InterruptedException e) {
                        System.out.println("Interrupted while waiting.");
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }

                }
                waitingPeriod.getAndAdd(-1);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTaskListLength()
    {
        return taskList.size();
    }
    public Task[] getTasks()
    {
        return (Task[]) taskList.toArray();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
