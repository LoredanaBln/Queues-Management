package view;

import controller.SimulationManager;
import model.CashRegister;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JButton start;

    private int simulationTime;
    private int maxServiceTime;
    private int minServiceTime;
    private int numberOfCashRegisters;
    private int numberOfClients;
    private int minimumArrivalTime;
    private int maximumArrivalTime;


    public SimulationFrame() {
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame("Queue management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#058A8E"));

        inputPanel = new ImagePanel("people_shopping1.jpg");
        inputPanel.setLayout(new GridLayout(8, 2, 20, 5));
        inputPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

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
        start.setFont(new Font("Garamond", Font.BOLD, 18));
        start.setForeground(Color.WHITE);
        start.setBorder(new RoundBorder(15));
        start.setBackground(Color.decode("#7D72A0"));
        start.setPreferredSize(inputPanel.getPreferredSize());
        inputPanel.add(start);

        cashRegistersPanel = new JPanel(new GridBagLayout());
        cashRegistersPanel.setBorder(BorderFactory.createTitledBorder("  "));

        GridBagConstraints cashRegConstraints = new GridBagConstraints();
        cashRegConstraints.gridx = 0;
        cashRegConstraints.gridy = 0;
        cashRegConstraints.weightx = 0.20;
        cashRegConstraints.weighty = 1.0;
        cashRegConstraints.fill = GridBagConstraints.BOTH;
        cashRegConstraints.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints clientQueuesConstraints = new GridBagConstraints();
        clientQueuesConstraints.gridx = 1;
        clientQueuesConstraints.gridy = 0;
        clientQueuesConstraints.weightx = 0.80;
        clientQueuesConstraints.weighty = 1.0;
        clientQueuesConstraints.fill = GridBagConstraints.BOTH;
        clientQueuesConstraints.insets = new Insets(6, 5, 5, 5);

        JPanel clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));

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
        frame.setVisible(true);
        ;
    }


    public void addStartButtonListener(SimulationManager simulationManager) {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readInputValuesAndCreateProgressBars();
                if (validateInput()) {
                    simulationManager.initializeSimulation();
                }
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
            cashRegPanel.add(cashRegisterEntry);
            cashRegPanel.add(Box.createVerticalStrut(8));
        }

        cashRegPanel.revalidate();
        cashRegPanel.repaint();
    }

    public void updateClientsAtCashRegisters(List<CashRegister> cashRegisters) {
        JPanel clientsPanel = (JPanel) cashRegistersPanel.getComponent(1);
        clientsPanel.removeAll();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));

        for (CashRegister cashRegister : cashRegisters) {
            JPanel panelForCashReg = new JPanel();
            panelForCashReg.setLayout(new BoxLayout(panelForCashReg, BoxLayout.Y_AXIS));
            panelForCashReg.setAlignmentY(Component.CENTER_ALIGNMENT);


            String clients = cashRegister.clientsAtCashRegister();

            JLabel label = new JLabel(clients);
            label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));

            label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
            label.setForeground(Color.BLACK);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));


            panelForCashReg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            panelForCashReg.add(Box.createVerticalStrut(1));
            panelForCashReg.add(label);

            clientsPanel.add(panelForCashReg);
            clientsPanel.add(Box.createVerticalStrut(10));
        }
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    private void addLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Garamond", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        inputPanel.add(label);
    }

    private void addTextField(JTextField textField, String labelText) {
        textField.setFont(new Font("Garamond", Font.PLAIN, 18));
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

    public boolean validateInput() {
        if (minimumArrivalTime > maximumArrivalTime) {
            JOptionPane.showMessageDialog(this.frame, "Minimum arrival time should be smaller than maximum arrival time", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (minServiceTime > maxServiceTime) {
            JOptionPane.showMessageDialog(this.frame, "Minimum service time should be smaller than maximum service time", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (maximumArrivalTime > simulationTime) {
            JOptionPane.showMessageDialog(this.frame, "Maximum arrival time should be smaller than simulation time", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (minimumArrivalTime > simulationTime) {
            JOptionPane.showMessageDialog(this.frame, "Minimum arrival time should be smaller than simulation time", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

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