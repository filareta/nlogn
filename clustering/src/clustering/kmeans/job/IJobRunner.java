package clustering.kmeans.job;

public interface IJobRunner {

	void setup();
	
	void run();
	
	void cleanup();
}
