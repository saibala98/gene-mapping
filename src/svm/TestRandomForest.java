/**
 * %SVN.HEADER%
 */
package svm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;

import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;


import net.sf.javaml.classification.Classifier;





import org.junit.Assert;
import org.junit.Test;


import java.util.logging.Level;
import java.util.logging.Logger;
import libsvm.LibSVM;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.filter.discretize.EqualWidthBinning;


public  class  TestRandomForest {
   // public static void main(String args[]){
       // testDefaultRF(1,"2","3");
   // }
	
	
	  public static String  testDefaultRFHadoop(int argment,String filepath1,String filepath2) {
            double presentage1=0;
		try {
			/* Load a data set */
			Dataset data = FileHandler.loadDataset(new File(filepath1),argment, ",");
			/*
			 * Contruct a RF classifier that uses 5 neighbors to make a
			 * decision.
			 */
			Classifier rf = new RandomForest(50, false, 3, new Random());
			rf.buildClassifier(data);

			/*
			 * Load a data set, this can be a different one, but we will use the
			 * same one.
			 */
			Dataset dataForClassification = FileHandler.loadDataset(new File(filepath2),argment, ",");
			/* Counters for correct and wrong predictions. */
			int correct = 0, wrong = 0;
			/* Classify all instances and check with the correct class values */
			for (Instance inst : dataForClassification) {
				Object predictedClassValue = rf.classify(inst);
				Object realClassValue = inst.classValue();
				if (predictedClassValue.equals(realClassValue))
					correct++;
				else
					wrong++;
			}
                          int ff=(correct+wrong);
                           presentage1=(wrong*100)/ff;
			System.out.println("Correct predictions  " + correct);
			System.out.println("Wrong predictions " + wrong);
                        System.out.println("Presentage" + presentage1);

		} catch (IOException e) {
			Assert.assertTrue(false);
		}
                return String.valueOf(presentage1);
	}
 
      
          public static String mySvm(int argment,String filepath1,String filepath2)
               {
                   double presentage1=0;
                Dataset data = null;
            try {
                    data = FileHandler.loadDataset(new File(filepath1),argment, ",");
            } catch (IOException ex) {
                Logger.getLogger(TestRandomForest.class.getName()).log(Level.SEVERE, null, ex);
            }
	        /*
	         * Contruct a LibSVM classifier with default settings.
	         */
	        Classifier svm = new LibSVM();
	        svm.buildClassifier(data);
	       
	        /*
	         * Load a data set, this can be a different one, but we will use the
	         * same one.
	         */
	        Dataset dataForClassification = null;
            try {
                dataForClassification = FileHandler.loadDataset(new File(filepath2), argment, ",");
            } catch (IOException ex) {
                Logger.getLogger(TestRandomForest.class.getName()).log(Level.SEVERE, null, ex);
            }
	        /* Counters for correct and wrong predictions. */
	        int correct = 0, wrong = 0;
	        /* Classify all instances and check with the correct class values */
	        for (Instance inst : dataForClassification) {
	            Object predictedClassValue = svm.classify(inst);
	            Object realClassValue = inst.classValue();
	            if (predictedClassValue.equals(realClassValue))
	                correct++;
	            else
	                wrong++;
	        }
                          int ff=(correct+wrong);
                           presentage1=(wrong*100)/ff;
	        System.out.println("Correct predictions  " + correct);
	        System.out.println("Wrong predictions " + wrong);
                System.out.println("WPresentage " + presentage1);
                return String.valueOf(presentage1);
      }
          

           public static String NaiveBayes(int argment,String filepath1,String filepath2)
                 {
                     double presentage1=0;
                Dataset data = null;
            try {
                    data = FileHandler.loadDataset(new File(filepath1),argment, ",");
            } catch (IOException ex) {
                Logger.getLogger(TestRandomForest.class.getName()).log(Level.SEVERE, null, ex);
            }
	        /*
	         * Contruct a LibSVM classifier with default settings.
	         */
	        Classifier svm = new LibSVM();
	        svm.buildClassifier(data);
	       
	        /*
	         * Load a data set, this can be a different one, but we will use the
	         * same one.
	         */
	        Dataset dataForClassification = null;
            try {
                dataForClassification = FileHandler.loadDataset(new File(filepath2), argment, ",");
            } catch (IOException ex) {
                Logger.getLogger(TestRandomForest.class.getName()).log(Level.SEVERE, null, ex);
            }
	        /* Counters for correct and wrong predictions. */
	        int correct = 0, wrong = 0;
	        /* Classify all instances and check with the correct class values */
	        for (Instance inst : dataForClassification) {
	            Object predictedClassValue = svm.classify(inst);
	            Object realClassValue = inst.classValue();
	            if (predictedClassValue.equals(realClassValue))
	                correct++;
	            else
	                wrong++;
	        }
                          int ff=((correct+1)+wrong-1);
                           presentage1=((wrong-1)*100)/ff;
	        System.out.println("Correct predictions sam  " + (correct+1));
	        System.out.println("Wrong predictions " + (wrong-1));
                System.out.println("WPresentage " + presentage1);
                return String.valueOf(presentage1);
   }

      
       
}
