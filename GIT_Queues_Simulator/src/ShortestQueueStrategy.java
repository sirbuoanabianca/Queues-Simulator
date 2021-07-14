import java.util.List;

public class ShortestQueueStrategy implements Strategy{

    public int addTask(List<Server> servers,Task t)
    {//returnez waitingPeriod al serverului la care va fi plasat clientul
        // pentru a afla cat a asteptat clientul pana sa fie servit

        int minSizeOfQueue=servers.get(0).getTaskListLength();
        Server chosenServer=servers.get(0);
        int waitingPeriodOfChosenServer;

        for(Server s:servers)
        {
            if(s.getTaskListLength()<minSizeOfQueue)
            {
                minSizeOfQueue=s.getTaskListLength();
                chosenServer=s;

            }
        }
        waitingPeriodOfChosenServer=chosenServer.getWaitingPeriod().intValue();
        chosenServer.addTask(t);
        return waitingPeriodOfChosenServer;
    }
}
