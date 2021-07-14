import java.util.List;

public class ShortestTimeStrategy implements Strategy{

    public int addTask(List<Server> servers,Task t)
    {//returnez waitingPeriod al serverului la care va fi plasat clientul
        // pentru a afla cat a asteptat clientul pana sa fie plasat la server
        int minWaitingPeriod=servers.get(0).getWaitingPeriod().get();
        Server chosenServer=servers.get(0);
        int waitingPeriodOfChosenServer;

        for(Server s:servers)
        {
            if(s.getWaitingPeriod().get()<minWaitingPeriod)
            {
                minWaitingPeriod=s.getWaitingPeriod().get();
                chosenServer=s;

            }
        }

        waitingPeriodOfChosenServer=chosenServer.getWaitingPeriod().intValue();
        chosenServer.addTask(t);
        return waitingPeriodOfChosenServer;
    }
}
