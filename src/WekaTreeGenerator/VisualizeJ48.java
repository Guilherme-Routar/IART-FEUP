package WekaTreeGenerator;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

/**
 * 
 * @author routar
 *
 */
public class VisualizeJ48 {
	public static void main(String args[]) throws Exception {

		//Reading data from the file provided
		Instances dataset = new Instances(new BufferedReader(new FileReader(args[0])));
		
		//Enabling non predictive attributes removal (url, timedelta and shares)
		Remove attRemoval = new Remove();
		String urlIndex = "1,", timedeltaIndex = "2,", sharesIndex = "8";
		attRemoval.setAttributeIndices(urlIndex + timedeltaIndex + sharesIndex);

		//Adding filter (removed attributes)
		FilteredClassifier filter = new FilteredClassifier();
		filter.setFilter(attRemoval);
		
		//Generating decision model (tree)
		J48 j48 = new J48();
		filter.setClassifier(j48);
		dataset.setClassIndex(dataset.numAttributes() - 1);
		filter.buildClassifier(dataset);

		//Displaying decision model (tree)
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
}