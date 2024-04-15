package view;

import controller.SimulationManager;
import model.CashRegister;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel cashRegistersPanel;


    private JTextField simulationTimeField = new JTextField();
    private JTextField maxServiceTimeField = new JTextField();
    private JTextField minServiceTimeField = new JTextField();
    private JTextField numberOfCashRegistersField = new JTextField();
    private JTextField numberOfClientsField = new JTextField();
    private JTextField maximumArrivalTimeField = new JTextField();
    private JTextField minimumArrivalTimeField = new JTextField();

    private List<List<Integer>> clientsAtCashRegisters;

    private JButton start;

    private int simulationTime;
    private int maxServiceTime;
    private int minServiceTime;
    private int numberOfCashRegisters;
    private int numberOfClients;
    private  int minimumArrivalTime;
    private  int maximumArrivalTime;


    public SimulationFrame() {
        initializeFrame();
    }
    private void initializeFrame() {
        frame = new JFrame("Queue management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#058A8E"));

        inputPanel = new ImagePanel("people_shopping1.jpg");
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        addLabel("Number of clients:");
        addLabel("Number of cash registers:");
        addTextField(numberOfClientsField, "Number of clients");
        addTextField(numberOfCashRegistersField, "Number of cash registers");

        addLabel("Minimum service time:");
        addLabel("Maximum service time:");
        addTextField(minServiceTimeField, "Minimum service time");
        addTextField(maxServiceTimeField, "Maximum service time");

        addLabel("Minimum arrival time:");
        addLabel("Maximum arrival time:");
        addTextField(minimumArrivalTimeField, "Minimum arrival time");
        addTextField(maximumArrivalTimeField, "Maximum arrival time");

        addLabel("Simulation Time:");
        addLabel("");
        addTextField(simulationTimeField, "Simulation Time");

        start = new JButton("Start simulation");
        start.setFont(new Font("Garamond", Font.BOLD, 16));
        start.setForeground(Color.WHITE);
        start.setBorder(new RoundBorder(15));
        start.setBackground(Color.decode("#7D72A0"));
        start.setPreferredSize(inputPanel.getPreferredSize());
        inputPanel.add(start);
//        addLabel("");
//        addLabel("");


        cashRegistersPanel = new JPanel(new GridBagLayout());
        cashRegistersPanel.setBorder(BorderFactory.createTitledBorder("Servers and Queues"));

        GridBagConstraints cashRegConstraints = new GridBagConstraints();
        cashRegConstraints.gridx = 0;
        cashRegConstraints.gridy = 0;
        cashRegConstraints.weightx = 0.25; // One quarter of the width
        cashRegConstraints.weighty = 1.0; // Fill the height
        cashRegConstraints.fill = GridBagConstraints.BOTH;
        cashRegConstraints.insets = new Insets(5, 5, 5, 5);

        // Constraints for clientQueuesPanel
        GridBagConstraints clientQueuesConstraints = new GridBagConstraints();
        clientQueuesConstraints.gridx = 1;
        clientQueuesConstraints.gridy = 0;
        clientQueuesConstraints.weightx = 0.75; // Three quarters of the width
        clientQueuesConstraints.weighty = 1.0; // Fill the height
        clientQueuesConstraints.fill = GridBagConstraints.BOTH;
        clientQueuesConstraints.insets = new Insets(5, 5, 5, 5);

        JPanel clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setBackground(Color.decode("#D9D9D9"));

        JPanel cashRegPanel = new JPanel();
        cashRegPanel.setLayout(new BoxLayout(cashRegPanel, BoxLayout.Y_AXIS));

        cashRegistersPanel.add(cashRegPanel, cashRegConstraints);
        cashRegistersPanel.add(clientsPanel, clientQueuesConstraints);

        JScrollPane scrollPane = new JScrollPane(cashRegistersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.add(inputPanel);
        scrollPanel.add(scrollPane);

        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);;
    }


    public void addStartButtonListener(SimulationManager simulationManager) {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readInputValuesAndCreateProgressBars();
                simulationManager.initializeSimulation();
            }
        });
    }

    private void readInputValuesAndCreateProgressBars() {
        simulationTime = Integer.parseInt(simulationTimeField.getText());
        maxServiceTime = Integer.parseInt(maxServiceTimeField.getText());
        minServiceTime = Integer.parseInt(minServiceTimeField.getText());
        numberOfCashRegisters = Integer.parseInt(numberOfCashRegistersField.getText());
        numberOfClients = Integer.parseInt(numberOfClientsField.getText());
        maximumArrivalTime = Integer.parseInt(maximumArrivalTimeField.getText());
        minimumArrivalTime = Integer.parseInt(minimumArrivalTimeField.getText());

        addCashRegisterPanels();
    }

    private void addCashRegisterPanels() {
        JPanel cashRegPanel = (JPanel) cashRegistersPanel.getComponent(0);
        cashRegPanel.removeAll();
        cashRegPanel.setLayout(new BoxLayout(cashRegPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < numberOfCashRegisters; i++) {
            JPanel cashRegisterEntry = new JPanel();
            cashRegisterEntry.setBackground(Color.decode("#7D72A0"));

            JLabel cashRegisterLabel = new JLabel("Cash Register " + (i + 1));
            cashRegisterLabel.setForeground(Color.WHITE);
            cashRegisterLabel.setFont(new Font("Garamond", Font.BOLD, 16));
            cashRegisterEntry.add(cashRegisterLabel);
            cashRegisterEntry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            cashRegisterEntry.setBorder(new RoundBorder(10));
            cashRegPanel.add(cashRegisterEntry);
            cashRegPanel.add(Box.createVerticalStrut(5));
        }

        cashRegPanel.revalidate();
        cashRegPanel.repaint();
    }

    public void updateClientsAtCashRegisters(List<CashRegister> cashRegisters) {
        JPanel clientsPanel = (JPanel) cashRegistersPanel.getComponent(1);
        clientsPanel.removeAll();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));

       for(CashRegister cashRegister : cashRegisters){
           JPanel panelForCashReg = new JPanel();
           String clients = cashRegister.clientsAtCashRegister();

           JLabel label = new JLabel(clients);
           label.setFont(new Font("Garamond", Font.BOLD, 16));
           label.setForeground(Color.BLACK);

           panelForCashReg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
           panelForCashReg.add(label);
           clientsPanel.add(panelForCashReg);
           clientsPanel.add(Box.createVerticalStrut(5));
       }
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    private void addLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Garamond", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        inputPanel.add(label);
    }

    private void addTextField(JTextField textField, String labelText) {
        textField.setFont(new Font("Garamond", Font.PLAIN, 16));
        textField.setBackground(Color.WHITE);
        textField.setBorder(new RoundBorder(10));
        inputPanel.add(textField);
        assignTextField(textField, labelText);
    }

    private void assignTextField(JTextField textField, String labelText) {
        switch (labelText) {
            case "Simulation time":
                simulationTimeField = textField;
                break;
            case "Maximum service time":
                maxServiceTimeField = textField;
                break;
            case "Minimum service time":
                minServiceTimeField = textField;
                break;
            case "Number of cash registers":
                numberOfCashRegistersField = textField;
                break;
            case "Number of clients":
                numberOfClientsField = textField;
                break;
            case "Maximum arrival time":
                maximumArrivalTimeField = textField;
                break;
            case "Minimum arrival time":
                minimumArrivalTimeField = textField;
                break;
        }
    }

//            public void createProgressBars() {
//                progressBars = new ArrayList<>();
//                progressBarsLabels = new ArrayList<>();
//                JPanel progressPanel = new JPanel();
//                progressPanel.setBackground(Color.decode("#B1D7B7"));
//                progressPanel.setLayout(new GridBagLayout());
//                GridBagConstraints gbc = new GridBagConstraints();
//                gbc.gridx = 0;
//                gbc.gridy = 0;
//                gbc.anchor = GridBagConstraints.WEST;
//                gbc.insets = new Insets(5, 5, 5, 5);
//
//                for (int i = 0; i < numberOfCashRegisters; i++) {
//                    JLabel label = new JLabel("Cash Register " + (i + 1));
//                    progressBarsLabels.add(label);
//                    label.setFont(new Font("Garamond", Font.BOLD, 16));
//                    label.setForeground(Color.decode("#058A8E"));
//                    progressPanel.add(label, gbc);
//                    gbc.gridy++;
//                    JProgressBar progressBar = new JProgressBar();
//                    progressBar.setPreferredSize(new Dimension(750, 40));
//                    progressBars.add(progressBar);
//                    progressPanel.add(progressBars.get(i), gbc);
//                    gbc.gridy++;
//                }
//                mainPanel.add(progressPanel, BorderLayout.SOUTH);
//
//                for (int i = 0; i < numberOfCashRegisters; i++) {
//                    this.progressBars.add(new JProgressBar());
//                }
//                frame.revalidate();
//            }
//
//            public void clearProgressBars() {
//                if (progressBars != null) {
//                    JPanel progressPanel = (JPanel) mainPanel.getComponent(mainPanel.getComponentCount() - 1);
//                    progressPanel.removeAll();
//                    progressBars.clear();
//                    frame.revalidate();
//                    frame.repaint();
//                }
//            }
//
//            public void updateProgressBars(int initialServiceTime, int remainingServiceTime, int progressBarIndex, int clientID) {
//                int progress = (initialServiceTime - remainingServiceTime) * 100 / initialServiceTime;
//                if (progress >= 0 && progress <= 100 && progressBarIndex >= 0 && progressBarIndex < progressBars.size()) {
//                    if (clientID >= 0) {
//                        String labelText = "Cash Register " + (progressBarIndex + 1) + " - Client " + clientID;
//                        progressBarsLabels.get(progressBarIndex).setText(labelText);
//                    } else {
//                        progressBarsLabels.get(progressBarIndex).setText("Cash Register " + (progressBarIndex + 1));
//                    }
//                    progressBars.get(progressBarIndex).setValue(progress);
//
//                    if (remainingServiceTime <= 0) {
//                        progressBars.get(progressBarIndex).setValue(0);
//                    }
//                }
//            }
//
//            public List<JProgressBar> getProgressBars() {
//                return progressBars;
//            }

    public synchronized int getSimulationTime() {
        return simulationTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public int getNumberOfCashRegisters() {
        return numberOfCashRegisters;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getMaximumArrivalTime() {
        return maximumArrivalTime;
    }
    public int getMinimumArrivalTime() {
        return minimumArrivalTime;
    }
}