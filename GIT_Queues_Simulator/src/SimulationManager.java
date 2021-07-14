import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    private int timeSimulation;
    private int numberOfClients;    //numar clienti
    private int numberOfServers;    //numar cozi
    private int maxProcessingTime;  //timp max servire
    private int minProcessingTime;  //timp min servire
    private int maxArrivalTime;     //timp max sosire
    private int minArrivalTime;     //timp min sosire
    private Scheduler scheduler;
    private List<Task> generatedTasks;  //lista de clienti in care se genereaza random arrivalTime,id si proccesingTime
    private Scheduler.SelectionPolicy selectionPolicy;
    private DisplayLogOfEvents frame;
    private double averageWaitingTime;//timp mediu de asteptare la cozi
    private double averageServiceTime;//timp mediu de servire
    private int peakHour;//moment de timp cel mai aglomerat


    public SimulationManager(int maxTasksPerServer, int timeS, int noClients, int noServers,
                             int maxProcTime, int minProcTime, int maxArrivalT, int minArrivalT, Scheduler.SelectionPolicy policy)
    {
        maxProcessingTime=maxProcTime;
        minProcessingTime=minProcTime;
        timeSimulation=timeS;
        numberOfClients=noClients;
        numberOfServers=noServers;
        maxArrivalTime=maxArrivalT;
        minArrivalTime=minArrivalT;
        averageWaitingTime=0;
        averageServiceTime=0;
        peakHour=0;
        generatedTasks=new ArrayList<>();
        selectionPolicy=policy;
        scheduler=new Scheduler(numberOfServers,maxTasksPerServer,selectionPolicy);
        frame=new DisplayLogOfEvents();
        frame.setVisible(true);

        generateNRandomTasks();

        Collections.sort(generatedTasks);
        System.out.println("sortare:");
        for(int i=0;i<numberOfClients;i++)
            System.out.println(generatedTasks.get(i).getArrivalTime());

    }

    private void generateNRandomTasks()
    {   Random rand=new Random();
        for(int i=0;i<numberOfClients;i++)
        {
            int processingTime=rand.nextInt(maxProcessingTime-minProcessingTime+1)+minProcessingTime;
            int arrivalTime= rand.nextInt(maxArrivalTime-minArrivalTime+1)+minArrivalTime;
            int id=i+1;
            generatedTasks.add(new Task(arrivalTime,processingTime,id));
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void showWaitingClients(String fileName, SimulationArea simArea)
    {
        String content="";
        for(Task t:generatedTasks)
        {
            content+="(id=" + t.getID() + " arrivalT=" + t.getArrivalTime() + " procT=" + t.getRemainingProcessingTime() + ")\n";
            System.out.print("(id=" + t.getID() + " arrivalT=" + t.getArrivalTime() + " procT=" + t.getRemainingProcessingTime() + ")\n");

        }
        if(generatedTasks.size()==0)    //verificare daca toti sunt clientii au fost procesati
        {
            System.out.println("No more waiting clients!");
            content+="No more waiting clients!\n";

        }
        simArea.addText(content);
        appendStrToFile(fileName, content);

    }

    public static void appendStrToFile(String fileName,
                                       String str)
    {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public void run()
    {
        int currentTime=0;
        int noProcessedTasks=0;
        boolean emptyServiceQueues=false,emptyWaitingClientQueue=false;
        int nrMaxClientsPerTime=0;
        SimulationArea simArea=new SimulationArea();


        String fileName= "logOfEvents.txt";
        String content=null;
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName));
            out.close();
        }
        catch (IOException e) {
            System.out.println("Exception Occurred" + e);
        }


                //simularea se incheie cand timpul se termina sau
                //lista cu clientii neprocesati e goala si cozile de servire sunt goale
            while (currentTime<=timeSimulation && (emptyServiceQueues==false || emptyWaitingClientQueue==false))
            {
                System.out.println("\nTIME "+currentTime);
                System.out.println("Waiting clients:");

                content="\nTIME "+currentTime+"\n"+"Waiting clients:\n";
                appendStrToFile(fileName, content);
                simArea.addText(content);

                if(generatedTasks.size()!=0)
                    {
                        while (generatedTasks.size()!=0 && generatedTasks.get(0).getArrivalTime() == currentTime)
                        {
                            //inainte de a adauga in server,se va obtine waitingPeriod al serverului ales
                           int waitingPer=scheduler.dispatchTask(generatedTasks.get(0));
                           averageWaitingTime+=waitingPer;

                            noProcessedTasks++;
                            averageServiceTime+=generatedTasks.get(0).getProcessingTime();
                            generatedTasks.remove(0);
                        }

                    }

                    showWaitingClients(fileName,simArea);

                //aflare care moment de timp e cel mai aglomerat
                    if(scheduler.countClientsForAllServers()>nrMaxClientsPerTime)
                    {
                        nrMaxClientsPerTime=scheduler.countClientsForAllServers();
                        peakHour=currentTime;

                    }

                    if(generatedTasks.size()==0)
                        emptyWaitingClientQueue=true;
                    if( scheduler.showClientsPerServers(fileName,simArea))
                      emptyServiceQueues=true;
                    else
                        emptyServiceQueues=false;


                frame.setTextArea(simArea.getSimulationTextArea());
                simArea.emptyStringBuilder();
                    currentTime++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        averageWaitingTime/=noProcessedTasks;
        averageServiceTime/=noProcessedTasks;

        System.out.println("Average waiting time="+averageWaitingTime);
        System.out.println("Average service time="+averageServiceTime);
        System.out.println("Peak hour="+peakHour);

        simArea.addText("\nAverage waiting time="+averageWaitingTime+"\n");
        simArea.addText("Average service time="+averageServiceTime+"\n");
        simArea.addText("Peak hour="+peakHour+"\n");
        frame.setTextArea(simArea.getSimulationTextArea());

        appendStrToFile(fileName, "\nAverage waiting time="+averageWaitingTime+"\n");
        appendStrToFile(fileName, "Average service time="+averageServiceTime+"\n");
        appendStrToFile(fileName, "Peak hour="+peakHour+"\n");
        scheduler.stopServers();
    }
}
