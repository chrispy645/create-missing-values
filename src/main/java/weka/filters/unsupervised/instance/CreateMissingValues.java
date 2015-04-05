package weka.filters.unsupervised.instance;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.SimpleBatchFilter;

public class CreateMissingValues extends SimpleBatchFilter {
	
	private static final long serialVersionUID = -3563512913640467458L;
	
	private long m_seed = 0;
	private Random m_random = new Random(m_seed);
	private double m_probMissing = 0.1;

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Instances determineOutputFormat(Instances inputFormat)
			throws Exception {
		return inputFormat;
	}
	
	public long getSeed() {
		return m_seed;
	}
	
	public void setSeed(long seed) {
		m_seed = seed;
	}
	
	public double getProbMissing() {
		return m_probMissing;
	}
	
	public void setProbMissing(double probMissing) {
		this.m_probMissing = probMissing;
	}

	@Override
	protected Instances process(Instances data) throws Exception {
		for(int i = 0; i < data.numInstances(); i++) {
			Instance inst = data.get(i);
			for(int j = 0; j < inst.numAttributes(); j++) {
				if(j == inst.classIndex())
					continue;
				if( m_random.nextDouble() < m_probMissing ) {
					inst.setMissing(j);
				}
			}
		}
		return data;
	}
	
	@Override
	public void setOptions(String[] options) throws Exception {
		String seed = Utils.getOption("S", options);
		if(seed.length() != 0) { 
			setSeed( Long.parseLong(seed) );
		}
		String prob = Utils.getOption("P", options);
		if(prob.length() != 0) {
			setProbMissing( Double.parseDouble(prob) );
		}
	}
	
	@Override
	public String[] getOptions() {
		Vector<String> result = new Vector<String>();
		result.add("-S");
		result.add( "" + getSeed() );
		result.add("-P");
		result.add( "" + getProbMissing() );
		Collections.addAll(result, super.getOptions());
	    return result.toArray(new String[result.size()]);
	}
	
	public static void main(String[] argv) {
		runFilter(new CreateMissingValues(), argv);
	}

	
	
}
