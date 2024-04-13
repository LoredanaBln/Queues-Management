    package view;

    import controller.SimulationManager;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;
    import java.util.List;

    public class SimulationFrame {
        private JFrame frame;
        private JPanel mainPanel;
        private JPanel inputPanel;
        private JPanel buttonPanel;
        private JLabel titleLabel;
        private JPanel titlePanel;

        private List<JProgressBar> progressBars;
        private JTextField simulationTimeField;
        private JTextField maxServiceTimeField;
        private JTextField minServiceTimeField;
        private JTextField numberOfCashRegistersField;
        private JTextField numberOfClientsField;
        private JTextField arrivalTimeField;
        private JButton start;

        private int simulationTime;
        private int maxServiceTime;
        private int minServiceTime;
        private int numberOfCashRegisters;
        private int numberOfClients;
        private  int arrivalTime;

        public SimulationFrame() {
            initializeFrame();
        }

        private void initializeFrame() {
            frame = new JFrame("Queue management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(Color.decode("#058A8E"));

            titlePanel = new JPanel();
            titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            titleLabel = new JLabel("Queues Management");
            titleLabel.setFont(new Font("Garamond", Font.BOLD, 26));
            titlePanel.setBackground(Color.decode("#058A8E"));
            titleLabel.setForeground(Color.WHITE);
            titlePanel.add(titleLabel);

            inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 1));
            inputPanel.setBackground(Color.decode("#058A8E"));

            addLabelAndTextField("Simulation Time:");
            addLabelAndTextField("Maximum Service Time:");
            addLabelAndTextField("Minimum Service Time:");
            addLabelAndTextField("Number of Cash Registers:");
            addLabelAndTextField("Number of Clients:");
            addLabelAndTextField("Max arrival time:");
            inputPanel.add(new JLabel(" "));

            buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.decode("#B1D7B7"));
            start = new JButton("Start simulation");
            start.setFont(new Font("Garamond", Font.BOLD, 16));
            start.setBackground(Color.decode("#058A8E"));
            start.setForeground(Color.WHITE);
            buttonPanel.add(start);

            mainPanel.add(titlePanel);
            mainPanel.add(inputPanel);
            mainPanel.add(buttonPanel);

            frame.add(new JScrollPane(mainPanel));
            frame.setVisible(true);

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
            arrivalTime = Integer.parseInt(arrivalTimeField.getText());

            clearProgressBars();
            createProgressBars();
        }


        private void addLabelAndTextField(String labelText) {
            JLabel label = new JLabel(labelText);
            label.setFont(new Font("Garamond", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            inputPanel.add(label);

            JTextField textField = new JTextField();
            textField.setFont(new Font("Garamond", Font.PLAIN, 16));
            textField.setBackground(Color.WHITE);
            inputPanel.add(textField);

            assignTextField(textField, labelText);
        }

        private void assignTextField(JTextField textField, String labelText) {
            switch (labelText) {
                case "Simulation Time:":
                    simulationTimeField = textField;
                    break;
                case "Maximum Service Time:":
                    maxServiceTimeField = textField;
                    break;
                case "Minimum Service Time:":
                    minServiceTimeField = textField;
                    break;
                case "Number of Cash Registers:":
                    numberOfCashRegistersField = textField;
                    break;
                case "Number of Clients:":
                    numberOfClientsField = textField;
                    break;
                case "Max arrival time:":
                    arrivalTimeField = textField;
                    break;
            }
        }

        public void createProgressBars() {
            progressBars = new ArrayList<>();
            JPanel progressPanel = new JPanel();
            progressPanel.setBackground(Color.decode("#B1D7B7"));
            progressPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            for (int i = 0; i < numberOfCashRegisters; i++) {
                JLabel label = new JLabel("Cash Register " + (i + 1));
                label.setFont(new Font("Garamond", Font.BOLD, 16));
                label.setForeground(Color.decode("#058A8E"));
                progressPanel.add(label, gbc);
                gbc.gridy++;
                JProgressBar progressBar = new JProgressBar();
                progressBar.setPreferredSize(new Dimension(750, 40));
                progressBars.add(progressBar);
                progressPanel.add(progressBars.get(i), gbc);
                gbc.gridy++;
            }
            mainPanel.add(progressPanel, BorderLayout.SOUTH);

            for (int i = 0; i < numberOfCashRegisters; i++) {
                this.progressBars.add(new JProgressBar());
            }
            frame.revalidate();
        }

        public void clearProgressBars() {
            if (progressBars != null) {
                JPanel progressPanel = (JPanel) mainPanel.getComponent(mainPanel.getComponentCount() - 1);
                progressPanel.removeAll();
                progressBars.clear();
                frame.revalidate();
                frame.repaint();
            }
        }


        public List<JProgressBar> getProgressBars() {
            return progressBars;
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
        public  int getArrivalTime(){return  arrivalTime;}

    }
