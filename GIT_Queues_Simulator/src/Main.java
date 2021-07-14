import java.awt.*;
public class Main {

    public static void main(String[] args) {

        /* It posts an event (Runnable)at the end of Swings event list and is
		processed after all other GUI events are processed.*/
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    StartSimulation frame = new StartSimulation();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
}
