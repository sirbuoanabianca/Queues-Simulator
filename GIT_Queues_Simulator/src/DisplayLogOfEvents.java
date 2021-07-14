import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

    public class DisplayLogOfEvents extends JFrame
    {
        private JPanel contentPane;
        private JTextArea textArea;

        public DisplayLogOfEvents()
        {
            setTitle("DisplayLogOfEvents");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(200, 100, 400, 630);

            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);

            textArea = new JTextArea(30,30);
            textArea.setFont(new Font("Serif", Font.BOLD, 13));
            JScrollPane scroll = new JScrollPane (textArea,
                                 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


            contentPane.add(scroll);
        }

        public void setTextArea(String s)
        {

            textArea.setText(textArea.getText()+s+"\n");

        }
    }

