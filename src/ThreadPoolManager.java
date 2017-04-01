import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	ExecutorService fixedThreadPool;
	int count = 0;
	int cores = 0;
	
	void init(int cores){
		this.cores = cores;
		fixedThreadPool = Executors.newFixedThreadPool(cores);
	}
	void submitThread(Runnable runnable){
		increase();
//		while(count >= cores );
		fixedThreadPool.execute(runnable);
	}
	
	synchronized void increase(){
		count ++;
	}
	
	synchronized void decrease(){
		count --;;
	}
}
