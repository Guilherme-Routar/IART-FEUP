import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import WekaTreeGenerator.VisualizeJ48;
import weka.classifiers.Evaluation;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;

public class test extends JFrame {
	
	private static String PATH = System.getProperty("user.dir");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {

		// ########## TITLE ##########

		setTitle("Online News Popularity Predictor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		
		// ####################
		

		// ########## MAIN PANEL #############

		JPanel mainPanel = new JPanel();
		mainPanel.setForeground(SystemColor.textHighlight);
		mainPanel.setBackground(Color.DARK_GRAY);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// App title
		JLabel lblTitle = new JLabel("Online News Popularity Predictor");
		lblTitle.setEnabled(false);
		lblTitle.setBackground(Color.DARK_GRAY);
		lblTitle.setOpaque(true);
		lblTitle.setForeground(Color.LIGHT_GRAY);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Bitstream Charter", Font.BOLD | Font.ITALIC, 38));

		// Horizontal separator
		JSeparator separator1 = new JSeparator();
		separator1.setForeground(SystemColor.inactiveCaption);
		separator1.setBackground(Color.LIGHT_GRAY);
		
		// ####################
		

		// ########## SETS PANEL ##########

		// Panel
		JPanel setsPanel = new JPanel();
		setsPanel.setBackground(Color.LIGHT_GRAY);
		
		// Title
		JLabel lblPersonalizeSets = new JLabel("Personalize Sets");
		lblPersonalizeSets.setFont(new Font("FreeSerif", Font.BOLD, 20));

		// Horizontal separator 
		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.BLACK);
		separator2.setBackground(Color.BLACK);
		
		// Checkbox for pre configured sets
		JCheckBox chckbxPreConfiguredSet = new JCheckBox("Pre Configured Sets");
		chckbxPreConfiguredSet.setBackground(Color.LIGHT_GRAY);
		chckbxPreConfiguredSet.setSelected(true);
		
		// Checkbox for random sets generation
		JCheckBox chckbxGenerateRandomSets = new JCheckBox("Generate Random Sets");
		chckbxGenerateRandomSets.setBackground(Color.LIGHT_GRAY);
		
		// Checkbox to load sets
		JCheckBox chckbxLoadSets = new JCheckBox("Load Sets");
		chckbxLoadSets.setBackground(Color.LIGHT_GRAY);
		
		// Making sure only 1 checkbox is selected
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(chckbxPreConfiguredSet);
		buttonGroup.add(chckbxGenerateRandomSets);
		buttonGroup.add(chckbxLoadSets);
		
		// Button to load a training set
		JButton btnLoadTrainingSet = new JButton("Load Training Set");
		btnLoadTrainingSet.setForeground(Color.WHITE);
		btnLoadTrainingSet.setBackground(Color.DARK_GRAY);
		btnLoadTrainingSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
			}
		});

		// Button to load a testing set
		JButton btnLoadTestingSet = new JButton("Load Testing Set");
		btnLoadTestingSet.setForeground(Color.WHITE);
		btnLoadTestingSet.setBackground(Color.DARK_GRAY);
		btnLoadTestingSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		// ####################
		
		
		// ########## PARAMETERS PANEL ##########

		//Panel
		JPanel parametersPanel = new JPanel();
		parametersPanel.setBackground(Color.LIGHT_GRAY);

		// parametersPanel title
		JLabel lblSetYourParameters = new JLabel("Set Your Parameters");
		lblSetYourParameters.setFont(new Font("FreeSerif", Font.BOLD, 20));

		// Horizontal separator
		JSeparator separator3 = new JSeparator();
		separator3.setForeground(Color.BLACK);
		separator3.setBackground(Color.BLACK);

		// Pruning checkbox
		JCheckBox chckbxPruning = new JCheckBox("Pruning");
		chckbxPruning.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxPruning.setToolTipText("Enable tree pruning (usually better results)");
		chckbxPruning.setForeground(Color.BLACK);
		chckbxPruning.setBackground(Color.LIGHT_GRAY);

		// Label for minimum number of objects
		JLabel lblMinNumberOfObjects = new JLabel("Minimum number of objects");
		lblMinNumberOfObjects.setToolTipText("Set the minimum number of objects per leaf  ");
		lblMinNumberOfObjects.setForeground(Color.BLACK);
		JTextField textField = new JTextField();
		textField.setText("1");
		textField.setColumns(10);
		
		// Label for results
		JLabel lblResults = new JLabel("Results");
		lblResults.setFont(new Font("FreeSerif", Font.BOLD, 20));

		// Label for correctly predicted instances
		JLabel lblCorrectlyPredictedInstances = new JLabel("Correctly predicted instances: ");
		JLabel lblCorrectlyPredictedInstances_input = new JLabel();

		// Label for incorrectly predicted instances
		JLabel lblIncorrectlyPredictedInstances = new JLabel("Incorrectly predicted instances:");
		JLabel lblIncorrectlyPredictedInstances_input = new JLabel();
		
		// Label for incorrectly predicted instances
		//JLabel lblrelativeAbsoluteError = new JLabel("Relative absolute error:");
		//JLabel lblrelativeAbsoluteError_input = new JLabel();

		// Button to view the generated tree
		JButton btnViewTree = new JButton("View Tree");
		btnViewTree.setForeground(Color.WHITE);
		btnViewTree.setBackground(Color.DARK_GRAY);

		// Button to generate the decision model
		JButton btnGenerateDecisionModel = new JButton("Generate Decision Model");
		btnGenerateDecisionModel.setForeground(Color.WHITE);
		btnGenerateDecisionModel.setBackground(Color.DARK_GRAY); 
		btnGenerateDecisionModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					VisualizeJ48 j48 = new VisualizeJ48();
					String[] datasets;
					
					// Sets handling
					if (chckbxPreConfiguredSet.isSelected()) {
						datasets = new String[] {
								"newTraining.arff",
								"newTesting.arff"
						};
						j48.updateDatasets(datasets);
					}
					else if (chckbxGenerateRandomSets.isSelected())
						System.out.println("random set");
					else 
						System.out.println("loading set");

					// Handling pruning
					if (chckbxPruning.isSelected())
						j48.enablePruning();
					else
						j48.disablePruning();

					// Handling minimum number of objects
					j48.setMinNumOfObjects(textField.getText());
					
					j48.buildModel();
					
					Evaluation eval = j48.evaluateModel();
					//eval.crossValidateModel(classifier, data, numFolds, random, forPredictionsPrinting);
					System.out.println("eval int = " + eval.correct());
					DecimalFormat df = new DecimalFormat("#.00"); 	
					//eval.
					lblCorrectlyPredictedInstances_input.setText(eval.correct() + "   " + df.format(eval.pctCorrect()) + "%");
					lblIncorrectlyPredictedInstances_input.setText(eval.incorrect() + "   " + df.format(eval.pctIncorrect()) + "%");
					System.out.println(eval.toSummaryString());


				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// ####################
		
		
		// ########## LAYOUTS ##########

		GroupLayout gl_contentPane = new GroupLayout(mainPanel);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 754, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 735, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addComponent(parametersPanel, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addGap(39)
							.addComponent(setsPanel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(parametersPanel, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
							.addGap(128))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(setsPanel, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
							.addGap(181))))
		);

		GroupLayout gl_panel_1 = new GroupLayout(setsPanel);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(49)
							.addComponent(separator2, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(58)
							.addComponent(lblPersonalizeSets))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(38)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxLoadSets)
								.addComponent(chckbxGenerateRandomSets)
								.addComponent(chckbxPreConfiguredSet)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(47)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnLoadTestingSet, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
								.addComponent(btnLoadTrainingSet, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(40)
					.addComponent(lblPersonalizeSets)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxPreConfiguredSet, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(chckbxGenerateRandomSets)
					.addGap(18)
					.addComponent(chckbxLoadSets)
					.addGap(31)
					.addComponent(btnLoadTrainingSet)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoadTestingSet)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		setsPanel.setLayout(gl_panel_1);
		
		GroupLayout gl_panel = new GroupLayout(parametersPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(23)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addComponent(chckbxPruning)
									.addComponent(lblMinNumberOfObjects))
								.addGap(31)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(88)
								.addComponent(lblSetYourParameters))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(53)
								.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(81)
								.addComponent(btnGenerateDecisionModel))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(156)
								.addComponent(lblResults)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnViewTree)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblCorrectlyPredictedInstances)
									.addComponent(lblIncorrectlyPredictedInstances)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCorrectlyPredictedInstances_input, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
								.addComponent(lblIncorrectlyPredictedInstances_input, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(34)
					.addComponent(lblSetYourParameters)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(chckbxPruning)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMinNumberOfObjects)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addComponent(btnGenerateDecisionModel)
					.addGap(59)
					.addComponent(lblResults)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCorrectlyPredictedInstances_input)
						.addComponent(lblCorrectlyPredictedInstances))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIncorrectlyPredictedInstances_input)
						.addComponent(lblIncorrectlyPredictedInstances))
					.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
					.addComponent(btnViewTree)
					.addContainerGap())
		);
		parametersPanel.setLayout(gl_panel);
		mainPanel.setLayout(gl_contentPane);

	}
}
