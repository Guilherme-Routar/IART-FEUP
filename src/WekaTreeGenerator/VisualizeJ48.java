package WekaTreeGenerator;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

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
	private static Classifier cls;
	
	public VisualizeJ48() {
		
		cls = new J48();
	}
	
	public static void updateDatasets(String[] datasetPath) throws Exception {
		
		DataSource trainSource = new DataSource(datasetPath[0]);
		DataSource testSource = new DataSource(datasetPath[1]);
		
		trainingSet = trainSource.getDataSet();
		trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
		testingSet = testSource.getDataSet();
		testingSet.setClassIndex(testingSet.numAttributes() - 1);
	}
	
	public static void enablePruning() {
		
		((J48) cls).setUnpruned(false); System.out.println("Pruning enabled");
	}
	
	public static void disablePruning() {
		
		((J48) cls).setUnpruned(true); System.out.println("Pruning disabled");
	}
	
	public static void setMinNumOfObjects(String numStr) {

		((J48) cls).setMinNumObj(Integer.parseInt(numStr)); System.out.println("Min Num of objs = " + numStr);
	}
	
	public static void buildModel() throws Exception {
		
		cls.buildClassifier(trainingSet);
	}
	
	public static Evaluation evaluateModel() throws Exception {
		
		Evaluation eval = new Evaluation(trainingSet);
		eval.evaluateModel(cls, testingSet);
		//System.out.println(eval.pctCorrect());
		//System.out.println(eval.pctIncorrect());
		return eval;
	}
	
	public static void displayTree() throws Exception {
		
		// Displaying decision model (tree)
		final javax.swing.JFrame jf = new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
		jf.setSize(1200, 700);
		jf.getContentPane().setLayout(new BorderLayout());
		TreeVisualizer tv = new TreeVisualizer(null, ((J48) cls).graph(), new PlaceNode2());
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

		//updateDatasets(dataset);
		//displayTree((J48) evaluateModel(buildModel()));
		
	}
}