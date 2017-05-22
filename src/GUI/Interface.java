package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FileProcessor.FileConverter;
import J48Weka.J48Engine;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Interface extends JFrame {
	
	private static String PATH = System.getProperty("user.dir");

	
	// ########## LAUNCH APPLICATION ##########
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Interface() {
		
		initComponents();
	}
	
	public void initComponents() {
		
		// Initializing engine
		J48Engine j48 = new J48Engine();

		// ########## TITLE ##########

		setTitle("Online News Popularity Predictor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 750);
		
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
		
		// Checkbox for cross-validation evaluation
		JCheckBox chckbxCrossvalidation_1 = new JCheckBox("Cross-Validation");
		chckbxCrossvalidation_1.setForeground(Color.BLACK);
		chckbxCrossvalidation_1.setBackground(Color.LIGHT_GRAY);
		
		JTextField textField_1 = new JTextField();
		textField_1.setText("10");
		textField_1.setToolTipText("Number of folds");
		textField_1.setColumns(10);
		
		// Making sure only 1 checkbox is selected
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(chckbxPreConfiguredSet);
		buttonGroup.add(chckbxGenerateRandomSets);
		buttonGroup.add(chckbxCrossvalidation_1);
		
		// ####################
		
		
		// ########## PARAMETERS PANEL ##########

		//Panel
		JPanel parametersPanel = new JPanel();
		parametersPanel.setBackground(Color.LIGHT_GRAY);

		// Label title
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
		lblCorrectlyPredictedInstances_input.setForeground(new Color(0, 128, 0));

		// Label for incorrectly predicted instances
		JLabel lblIncorrectlyPredictedInstances = new JLabel("Incorrectly predicted instances:");
		JLabel lblIncorrectlyPredictedInstances_input = new JLabel();
		lblIncorrectlyPredictedInstances_input.setForeground(new Color(255, 0, 0));
		
		// Label for relative absolute error
		JLabel lblRelativeAbsoluteError = new JLabel("Relative absolute error:");
		JLabel lblRelativeAbsoluteError_input = new JLabel();
		
		// Label for total number of objects
		JLabel lblTotalNumberOf = new JLabel("Total number of instances:");
		JLabel lblTotalNumberOf_input = new JLabel();
		
		// Button to view the generated tree
		JButton btnViewTree = new JButton("View Tree");
		btnViewTree.setForeground(Color.WHITE);
		btnViewTree.setBackground(Color.DARK_GRAY);
		btnViewTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					j48.displayTree();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		// Button to generate the decision model
		JButton btnGenerateDecisionModel = new JButton("Generate Decision Model");
		btnGenerateDecisionModel.setForeground(Color.WHITE);
		btnGenerateDecisionModel.setBackground(Color.DARK_GRAY); 
		btnGenerateDecisionModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String[] datasets;
					
					// Pre Configured Set option is selected
					// These sets are the most successful combination generated randomly
					// Used by default 
					if (chckbxPreConfiguredSet.isSelected()) {
						datasets = new String[] {
								"trainingSet.arff",
								"testingSet.arff"
						};
						j48.updateDatasets(datasets);
					}
					// Generate Random Sets option is selected
					else if (chckbxGenerateRandomSets.isSelected()) {
						FileConverter FC = new FileConverter();
						FC.generateRandomSets();
						datasets = new String[] {
								"randomTrainingSet.arff",
								"randomTestingSet.arff"
						};
						j48.updateDatasets(datasets);
					}
					// Cross Validation option is selected
					else if (chckbxCrossvalidation_1.isSelected()) {
						System.out.println("Cross validation");
						datasets = new String[] {
								"trainingSet.arff"
						};
						j48.enableCrossValidation();
						j48.updateDatasets(datasets);
					}

					// Handling pruning
					if (chckbxPruning.isSelected())
						j48.enablePruning();
					else
						j48.disablePruning();

					// Handling minimum number of objects
					j48.setMinNumOfObjects(textField.getText());
					
					// Building classifier
					j48.buildClassifier();
					
					// Evaluating model generated
					Evaluation eval = j48.evaluateModel(Integer.parseInt(textField_1.getText()));
					
					// Updating results
					DecimalFormat df = new DecimalFormat("#.00"); 
					lblCorrectlyPredictedInstances_input.setText((int) eval.correct() + "   " + df.format(eval.pctCorrect()) + "%");
					lblIncorrectlyPredictedInstances_input.setText((int) eval.incorrect() + "   " + df.format(eval.pctIncorrect()) + "%");
					lblRelativeAbsoluteError_input.setText(String.valueOf(df.format(eval.relativeAbsoluteError())) + "%");
					lblTotalNumberOf_input.setText(String.valueOf((int) eval.numInstances()));

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	
		JButton btnViewConfusionMatrix = new JButton("Vew Confusion Matrix");
		btnViewConfusionMatrix.setForeground(Color.WHITE);
		btnViewConfusionMatrix.setBackground(Color.DARK_GRAY);
		btnViewConfusionMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Evaluation eval = j48.evaluateModel(2);
					JOptionPane jd = new JOptionPane();
					jd.showInputDialog(eval.toMatrixString());
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
							.addGap(48)
							.addComponent(setsPanel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(parametersPanel, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(152)
							.addComponent(setsPanel, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)))
					.addGap(48))
		);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);

		GroupLayout gl_panel_1 = new GroupLayout(setsPanel);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(49)
							.addComponent(separator2, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(58)
							.addComponent(lblPersonalizeSets))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(chckbxCrossvalidation_1)
									.addGap(18)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
								.addComponent(chckbxGenerateRandomSets)
								.addComponent(chckbxPreConfiguredSet))))
					.addContainerGap(18, Short.MAX_VALUE))
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
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxCrossvalidation_1)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(182, Short.MAX_VALUE))
		);
		setsPanel.setLayout(gl_panel_1);
	
		
		GroupLayout gl_panel = new GroupLayout(parametersPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(88)
							.addComponent(lblSetYourParameters))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTotalNumberOf)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(btnViewTree, Alignment.TRAILING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblCorrectlyPredictedInstances)
										.addPreferredGap(ComponentPlacement.RELATED))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblIncorrectlyPredictedInstances)
										.addPreferredGap(ComponentPlacement.RELATED))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblRelativeAbsoluteError)
										.addPreferredGap(ComponentPlacement.RELATED))))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblIncorrectlyPredictedInstances_input, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
										.addComponent(lblCorrectlyPredictedInstances_input, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(61)
									.addComponent(lblTotalNumberOf_input))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(44)
									.addComponent(lblRelativeAbsoluteError_input, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(53)
							.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxPruning)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblMinNumberOfObjects)
									.addGap(35)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
							.addGap(230))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(102)
							.addComponent(btnViewConfusionMatrix))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(82)
							.addComponent(btnGenerateDecisionModel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(108)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(158)
							.addComponent(lblResults)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(34)
							.addComponent(lblSetYourParameters)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMinNumberOfObjects)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(139)
							.addComponent(chckbxPruning)))
					.addGap(18)
					.addComponent(btnGenerateDecisionModel)
					.addGap(78)
					.addComponent(lblResults)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCorrectlyPredictedInstances)
						.addComponent(lblCorrectlyPredictedInstances_input))
					.addGap(17)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIncorrectlyPredictedInstances)
						.addComponent(lblIncorrectlyPredictedInstances_input))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRelativeAbsoluteError)
						.addComponent(lblRelativeAbsoluteError_input))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalNumberOf)
						.addComponent(lblTotalNumberOf_input))
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(btnViewTree)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnViewConfusionMatrix)
					.addGap(24))
		);
		parametersPanel.setLayout(gl_panel);
		mainPanel.setLayout(gl_contentPane);

	}
}
