import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

class Process {
    public int id;
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int finishTime;
    public int turnAroundTime;
    public int waitingTime;
    public int timeQuantum;

    Process(int id, String name, int arrivalTime, int burstTime, int timeQuantum) 
    {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.timeQuantum = timeQuantum;
    }
}

public class MicroProject 
{
    private JFrame frame;
    private JTextArea outputArea;
    private JTextField idField, nameField, atField, btField, tqField;
    private JButton addButton, fcfsButton, sjfButton, rrButton;
    private ArrayList<Process> processList = new ArrayList<>();

    public MicroProject() 
    {
        frame = new JFrame("Process Scheduler Simulator");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 7));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Process"));
        inputPanel.setBackground(new Color(230, 240, 255));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        idField = new JTextField(); idField.setFont(fieldFont);
        nameField = new JTextField(); nameField.setFont(fieldFont);
        atField = new JTextField(); atField.setFont(fieldFont);
        btField = new JTextField(); btField.setFont(fieldFont);
        tqField = new JTextField(); tqField.setFont(fieldFont);

        inputPanel.add(createLabel("ID:", labelFont)); inputPanel.add(idField);
        inputPanel.add(createLabel("Name:", labelFont)); inputPanel.add(nameField);
        inputPanel.add(createLabel("Arrival Time:", labelFont)); inputPanel.add(atField);
        inputPanel.add(createLabel("Burst Time:", labelFont)); inputPanel.add(btField);
        inputPanel.add(createLabel("Time Quantum:", labelFont)); inputPanel.add(tqField);

        addButton = new JButton("Add Process");
        addButton.setBackground(new Color(144,238,144));
        fcfsButton = new JButton("Run FCFS");
        sjfButton = new JButton("Run SJF");
        rrButton = new JButton("Run RR");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton); buttonPanel.add(fcfsButton);
        buttonPanel.add(sjfButton); buttonPanel.add(rrButton);

        inputPanel.add(new JLabel("")); // Empty cell
        inputPanel.add(buttonPanel);

        outputArea = new JTextArea(10, 50);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Process Log"));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputScroll, BorderLayout.CENTER);

        addButton.addActionListener(e -> addProcess());
        fcfsButton.addActionListener(e -> runFCFS());
        sjfButton.addActionListener(e -> runSJF());
        rrButton.addActionListener(e -> runRR());

        frame.setVisible(true);
    }

    private JLabel createLabel(String text, Font font) 
    {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public void addProcess() 
    {
        try 
        {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int at = Integer.parseInt(atField.getText());
            int bt = Integer.parseInt(btField.getText());
            int tq = Integer.parseInt(tqField.getText());

            Process p = new Process(id, name, at, bt, tq);
            processList.add(p);
            outputArea.append("Added Process: " + name + "\n");

            idField.setText(""); nameField.setText(""); atField.setText("");
            btField.setText(""); tqField.setText("");

        } catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(frame, "Enter valid input!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runFCFS() 
    {
        ArrayList<Process> list = new ArrayList<>(processList);
        list.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int time = 0;
        for (Process p : list) 
        {
            time = Math.max(time, p.arrivalTime);
            time += p.burstTime;
            p.finishTime = time;
            p.turnAroundTime = p.finishTime - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
        }
        displayProcess("FCFS Result", list);
    }

    public void runSJF() 
    {
        ArrayList<Process> list = new ArrayList<>(processList);
        list.sort(Comparator.comparingInt(p -> p.burstTime));

        int time = 0;
        for (Process p : list) 
        {
            time = Math.max(time, p.arrivalTime);
            time += p.burstTime;
            p.finishTime = time;
            p.turnAroundTime = p.finishTime - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
        }
        displayProcess("SJF Result", list);
    }

    public void runRR() 
    {
        ArrayList<Process> queue = new ArrayList<>();
        for (Process p : processList)
            queue.add(new Process(p.id, p.name, p.arrivalTime, p.burstTime, p.timeQuantum));

        int tq = processList.get(0).timeQuantum;
        int time = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        queue.sort(Comparator.comparingInt(p -> p.arrivalTime));
        readyQueue.add(queue.remove(0));

        while (!readyQueue.isEmpty() || !queue.isEmpty()) 
        {
            while (!queue.isEmpty() && queue.get(0).arrivalTime <= time) 
            {
                readyQueue.add(queue.remove(0));
            }

            if (readyQueue.isEmpty()) 
            {
                time = queue.get(0).arrivalTime;
                readyQueue.add(queue.remove(0));
            }

            Process current = readyQueue.poll();
            int execTime = Math.min(tq, current.remainingTime);
            current.remainingTime -= execTime;
            time += execTime;

            while (!queue.isEmpty() && queue.get(0).arrivalTime <= time) 
            {
                readyQueue.add(queue.remove(0));
            }

            if (current.remainingTime > 0) 
            {
                readyQueue.add(current);
            } else {
                current.finishTime = time;
                current.turnAroundTime = current.finishTime - current.arrivalTime;
                current.waitingTime = current.turnAroundTime - current.burstTime;
                processList.stream()
                        .filter(p -> p.id == current.id)
                        .findFirst()
                        .ifPresent(p -> {
                            p.finishTime = current.finishTime;
                            p.turnAroundTime = current.turnAroundTime;
                            p.waitingTime = current.waitingTime;
                        });
            }
        }

        displayProcess("RR Result", processList);
    }

    public void displayProcess(String title, List<Process> list) 
    {
        String[] columns = {"ID", "Name", "Arrival Time", "Burst Time", "Finish Time", "Turnaround Time", "Waiting Time"};
        String[][] data = new String[list.size()][7];

        for (int i = 0; i < list.size(); i++) 
        {
            Process p = list.get(i);
            data[i][0] = String.valueOf(p.id);
            data[i][1] = p.name;
            data[i][2] = String.valueOf(p.arrivalTime);
            data[i][3] = String.valueOf(p.burstTime);
            data[i][4] = String.valueOf(p.finishTime);
            data[i][5] = String.valueOf(p.turnAroundTime);
            data[i][6] = String.valueOf(p.waitingTime);
        }

        JTable table = new JTable(data, columns);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder(title));

        frame.getContentPane().add(tableScroll, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) 
    {
        new MicroProject();
    }
}