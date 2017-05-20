package WekaTreeGenerator;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

/**
 * 
 * @author routar
 *
 */
public class VisualizeJ48 {
	
	private static Instances trainingSet, testingSet;
	
	public static void initDatasets(String[] datasetPath) throws Exception {
		
		DataSource trainSource = new DataSource(datasetPath[0]);
		DataSource testSource = new DataSource(datasetPath[1]);
		
		trainingSet = trainSource.getDataSet();
		trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
		testingSet = testSource.getDataSet();
		testingSet.setClassIndex(testingSet.numAttributes() - 1);
	}
	
	public static Classifier buildModel() throws Exception {
		
		Classifier cls = new J48();
		//Tree pruning
		((J48) cls).setMinNumObj(500);
		cls.buildClassifier(trainingSet);
		
		return cls;
	}
	
	public static Classifier evaluateModel(Classifier cls) throws Exception {
		
		Evaluation eval = new Evaluation(trainingSet);
		eval.evaluateModel(cls, testingSet);
		System.out.println(eval.toSummaryString("\nResults\n======", false));
		System.out.println("Graph = " + ((J48) cls).graph());
		return cls;
	}
	
	public static void displayTree(J48 j48) throws Exception {
		
		// Displaying decision model (tree)
		final javax.swing.JFrame jf = new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
		jf.setSize(1200, 700);
		jf.getContentPane().setLayout(new BorderLayout());
		TreeVisualizer tv = new TreeVisualizer(null, j48.graph(), new PlaceNode2());
		jf.getContentPane().add(tv, BorderLayout.CENTER);
		jf.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				jf.dispose();
			}
		});
		jf.setVisible(true);
		tv.fitToScreen();
	}
	
	public static void main(String dataset[]) throws Exception {

		initDatasets(dataset);
		displayTree((J48) evaluateModel(buildModel()));
		
	}
}