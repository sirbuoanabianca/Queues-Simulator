import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class StartSimulation extends JFrame
{
    private JButton btnNewFrame,btnClear;
    private JPanel panel1,panel2,panel3;
    private JLabel noTasks;
    private JLabel noQueues;
    private JLabel simulationTime;
    private JLabel maxTasksPerServer;
    private JLabel maxArrTime;
    private JLabel minArrTime;
    private JLabel maxProcT;
    private JLabel minProcT;
    private JTextField nrCl;
    private JTextField nrQ;
    private JTextField sim;
    private JTextField maxTasks;
    private JTextField minArr;
    private JTextField maxArr;
    private JTextField minProc;
    private JTextField maxProc;
    private SimulationManager manager;

    public StartSimulation()
    {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        //set frame title
        setTitle("Simulation Queues");
        //set default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //set bounds of the frame
        setBounds(100, 100, 514, 380);
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(4, 2));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 2));
        panel3 = new JPanel();

        noTasks = new JLabel(" Number of Clients :");
        noTasks.setFont(new Font("Serif", Font.BOLD, 14));
        noQueues = new JLabel("Number of Queues :");
        noQueues.setFont(new Font("Serif", Font.BOLD, 14));
        simulationTime = new JLabel(" Simulation time :");
        simulationTime.setFont(new Font("Serif", Font.BOLD, 14));
        maxTasksPerServer = new JLabel(" Max clients/queue :");
        maxTasksPerServer.setFont(new Font("Serif", Font.BOLD, 14));
        maxArrTime = new JLabel(" Max arrival time :");
        maxArrTime.setFont(new Font("Serif", Font.BOLD, 14));
        minArrTime = new JLabel(" Min arrival time :");
        minArrTime.setFont(new Font("Serif", Font.BOLD, 14));
         maxProcT = new JLabel(" Max processing time :");
        maxProcT.setFont(new Font("Serif", Font.BOLD, 14));
        minProcT = new JLabel(" Min processing time :");
        minProcT.setFont(new Font("Serif", Font.BOLD, 14));

        nrCl = new JTextField(8);
        nrCl.setFont(new Font("Serif", Font.BOLD, 16));
        nrQ = new JTextField(8);
        nrQ.setFont(new Font("Serif", Font.BOLD, 16));
        sim = new JTextField(8);
        sim.setFont(new Font("Serif", Font.BOLD, 16));
        maxTasks = new JTextField(8);
        maxTasks.setFont(new Font("Serif", Font.BOLD, 16));
        minArr = new JTextField(8);
        minArr.setFont(new Font("Serif", Font.BOLD, 16));
        maxArr = new JTextField(8);
        maxArr.setFont(new Font("Serif", Font.BOLD, 16));
        minProc = new JTextField(8);
        minProc.setFont(new Font("Serif", Font.BOLD, 16));
        maxProc = new JTextField(8);
        maxProc.setFont(new Font("Serif", Font.BOLD, 16));

        btnNewFrame = new JButton("Start");
        String s[]={"Minimum waiting time strategy","Shortest queue strategy"};
        JComboBox cb=new JComboBox(s);
        cb.setBounds(50, 50,90,20);
        btnClear=new JButton("Clear");


        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
            }
        });

        btnNewFrame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                int selected=cb.getSelectedIndex();
                Scheduler.SelectionPolicy policy=null;
                if(selected==0)
                {
                    policy=Scheduler.SelectionPolicy.SHORTEST_TIME;
                    System.out.println("----Am ales waiting TIMe strategy----" );
                }
                else
                {
                    if(selected==1)
                    {
                        policy=Scheduler.SelectionPolicy.SHORTEST_QUEUE;
                        System.out.println("----Am ales shortest queue strategy----" );
                    }
                }

                 manager = new SimulationManager(Integer.parseInt(maxTasks.getText()), Integer.parseInt(sim.getText())
                                            ,Integer.parseInt(nrCl.getText()), Integer.parseInt(nrQ.getText()),
                                             Integer.parseInt(maxProc.getText()), Integer.parseInt(minProc.getText()),
                                            Integer.parseInt(maxArr.getText()), Integer.parseInt(minArr.getText()),policy);
                Thread t = new Thread(manager);
                t.start();

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        btnNewFrame.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        btnNewFrame.setBounds(180, 195, 85, 29);

        panel1.add(noTasks);
        panel1.add(nrCl);
        panel1.add(noQueues);
        panel1.add(nrQ);
        panel1.add(simulationTime);
        panel1.add(sim);
        panel1.add(maxTasksPerServer);
        panel1.add(maxTasks);

        panel2.add(minArrTime);
        panel2.add(minArr);
        panel2.add(maxArrTime);
        panel2.add(maxArr);
        panel2.add(minProcT);
        panel2.add(minProc);
        panel2.add(maxProcT);
        panel2.add(maxProc);

        panel3.add(btnNewFrame);
        panel3.add(cb);
        panel3.add(btnClear);

        add(panel1, "West");
        add(panel2,"East");
        add(panel3,"South");

        setVisible(true);
        setResizable(false);
        this.setLocationRelativeTo(null);

    }

    public void clearTextFields()
    {
        nrCl.setText("");
        nrQ.setText("");
        sim.setText("");
        maxTasks.setText("");
        minArr.setText("");
        maxArr.setText("");
        minProc.setText("");
        maxProc.setText("");
    }

}
