import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.FileUtils;

public class PredictNames_GlobleWithSoundex extends SpecialPairsGolbleEditDistance {

	HashMap<String, String> nameMaps = new HashMap<>();
	HashMap<String, String> testMap = new HashMap<>();

	public int count = 0;
	int cores = 9;

	public static void main(String[] args) {
		new PredictNames_GlobleWithSoundex().run(args);
	}

	@Override
	public void run(String[] args) {
		int[] ans = new int[19];
		try {
			List<String> names = FileUtils.readLines(new File("testFiles/names.txt"));
			List<String> tests = FileUtils.readLines(new File("testFiles/train.txt"));

			Map<Double, Integer> results = new HashMap<Double, Integer>();
			specialPairs = FileUtils.readLines(new File("testFiles/specialPairs.txt"));

			generateNameMap(names, tests);
			evaluate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateNameMap(List<String> names, List<String> tests) {
		ThreadPoolManager poolManager = new ThreadPoolManager();
		poolManager.init(cores);
		int length = tests.size() / cores;
		for (int i = 0; i < cores; i++) {
			count = i;
			poolManager.submitThread(new Runnable() {

				@Override
				public void run() {
					System.out.println(count);
					boolean set = false;
					double currentResult = 0;
					int end = (count + 1) * length;
					if (count == cores - 1) {
						end = tests.size();
					}
					for (int j = count * length; j < end; j++) {
						String test = tests.get(j);
						test = test.split("	")[0];
						for (String name : names) {
							double result = calculateValue(test, name);
							
							if (result > currentResult) {
								currentResult = result;
								nameMaps.put(test, name);
								set=true;
							}
						}
						if (!set) {
							nameMaps.put(test, "");
						}
						set = false;
						currentResult = 0;
					}
					poolManager.decrease();
				}
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while (poolManager.count != 0){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		poolManager.fixedThreadPool.shutdown();
		
	}
	
	double calculateValue(String word1, String word2){
		double distance = minDistance(word1, word2);
		double length_1 = word1.length();
		double length_2 = word2.length();
		double length = Math.max(length_1, length_2);
		int similarity = difference(word1, word2);
		double result = -distance/length * 0.6 + similarity/4.0 * 0.4;
		return result;
	}

	
	private void evaluate() {
		// TODO Auto-generated method stub
		List<String> strs;
		try {
			strs = FileUtils.readLines(new File("testFiles/train.txt"));
			int hits = 0;
			double sum = 0;
			int failedCount = 0;

			for (String string : strs) {
				String[] temp = string.split("	");
				testMap.put(temp[0], temp[1]);
			}
			for (String string2 : nameMaps.keySet()) {
				if (nameMaps.get(string2).equals(testMap.get(string2))) {
					hits++; // 49
				}
				sum += 1; // 1960
				if (nameMaps.get(string2)=="") {
					failedCount++;
				}
			}
			System.out.println(hits / sum);
			ArrayList<String> list = new ArrayList<String>();
			list.add("hits : "+hits);
			list.add("sum  : "+sum);
			list.add("fail : "+ failedCount);
			list.add("ratio: "+hits/sum );
			for(String key : nameMaps.keySet()){
				list.add(key +"  "+ nameMaps.get(key));
			}
			FileUtils.writeLines(new File("testFiles/predictions_GlobalWithSoundex.txt"),list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
