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
            private List<JLabel> progressBarsLabels;
            private JTextField simulationTimeField = new JTextField();
            private JTextField maxServiceTimeField = new JTextField();
            private JTextField minServiceTimeField = new JTextField();
            private JTextField numberOfCashRegistersField = new JTextField();
            private JTextField numberOfClientsField = new JTextField();
            private JTextField arrivalTimeField = new JTextField();
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
                inputPanel.setLayout(new GridLayout(8, 2, 10, 10)); // 2 columns, with gaps between columns and rows
                inputPanel.setBackground(Color.decode("#058A8E"));

                addLabel("Simulation Time:");
                addLabel("Maximum Service Time:");

                addTextField(simulationTimeField, "Simulation Time:");
                addTextField(maxServiceTimeField, "Maximum Service Time:");

                addLabel("Number of Cash Registers:");
                addLabel("Minimum Service Time:");
                addTextField(numberOfCashRegistersField, "Number of Cash Registers:");
                addTextField(minServiceTimeField, "Minimum Service Time:");

                addLabel("Number of Clients:");
                addLabel("Max arrival time:");
                addTextField(numberOfClientsField, "Number of Clients:");
                addTextField(arrivalTimeField, "Max arrival time:");

                addLabel("");
                addLabel("");
                addLabel("");
                addLabel("");

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

                //clearProgressBars();
                //createProgressBars();
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
                progressBarsLabels = new ArrayList<>();
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
                    progressBarsLabels.add(label);
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

            public void updateProgressBars(int initialServiceTime, int remainingServiceTime, int progressBarIndex, int clientID) {
                int progress = (initialServiceTime - remainingServiceTime) * 100 / initialServiceTime;
                if (progress >= 0 && progress <= 100 && progressBarIndex >= 0 && progressBarIndex < progressBars.size()) {
                    if (clientID >= 0) {
                        String labelText = "Cash Register " + (progressBarIndex + 1) + " - Client " + clientID;
                        progressBarsLabels.get(progressBarIndex).setText(labelText);
                    } else {
                        progressBarsLabels.get(progressBarIndex).setText("Cash Register " + (progressBarIndex + 1));
                    }
                    progressBars.get(progressBarIndex).setValue(progress);

                    if (remainingServiceTime <= 0) {
                        progressBars.get(progressBarIndex).setValue(0);
                    }
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
