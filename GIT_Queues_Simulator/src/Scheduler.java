import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> serverList;//lista de cozi
    private Strategy strategy;//strategia aleasa

    public enum SelectionPolicy{
        SHORTEST_QUEUE,SHORTEST_TIME
    }

    public Scheduler(int maxNoServers,int maxTasksPerServer,SelectionPolicy policy)
    {
        changeStrategy(policy);
        serverList=new ArrayList<>();
        for(int i=0;i<maxNoServers;i++)
        {
            Server server=new Server(maxTasksPerServer);
            serverList.add(server);
            Thread t=new Thread(server);
            t.start();
        }
    }


    public int countClientsForAllServers()
    {   int countClientsForAllServers=0;

            for(Server s:serverList)
                countClientsForAllServers+=s.getNoOfClients();

        return countClientsForAllServers;
    }

    public boolean showClientsPerServers(String fileName,SimulationArea simArea)
    {
        int i=0;
        boolean allServersAreEmpty=true;
        for(Server s:serverList)
        {
            System.out.print("Coada "+i+": ");
            SimulationManager.appendStrToFile(fileName, "Coada "+i+": ");
            simArea.addText("Coada "+i+": ");

            if(s.showClientsOfServer(fileName,simArea))  //daca acest server are clienti in coada
                allServersAreEmpty=false;   //nu toate serverele sunt libere

            i++;
        }
        return allServersAreEmpty;

    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy=new ShortestQueueStrategy();
        if(policy == SelectionPolicy.SHORTEST_TIME)
            strategy=new ShortestTimeStrategy();
    }

    public void stopServers()
    {
        for(Server s:serverList)
            s.stopServer();
    }

    public int dispatchTask(Task t)
    {int waitingPeriodOfChosenServer=strategy.addTask(serverList,t);
        return waitingPeriodOfChosenServer;
    }


}
