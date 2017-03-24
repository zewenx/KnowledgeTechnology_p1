import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class PredictNames_Globle extends ModifiedGlobleEditDistance {
	
	HashMap<String, String> nameMaps = new HashMap<>();
	HashMap<String, String> testMap = new HashMap<>();
	
	public static void main(String[] args) {
		new PredictNames_Globle().run(args);
	}
	
	@Override
	public void run(String[] args) {

		int [] ans = new int[19];
		try {
			List <String> names = FileUtils.readLines(new File("testFiles/names.txt"));
			List <String> tests = FileUtils.readLines(new File("testFiles/test.txt"));
			
			generateNameMap(names,tests);
			
			evaluate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateNameMap(List<String> names, List<String> tests) {
		double currentScore = 100;
		for (String test : tests) {
			test = test.split("	")[0];
			for(String name : names){
				
				double distance = minDistance(test,name);
				if (distance<currentScore) {
					currentScore = distance;
					nameMaps.put(test, name);
				}
				
			}
			currentScore =100;
		}
	}

	private void evaluate() {
		// TODO Auto-generated method stub
		List<String> strs;
		try {
			strs = FileUtils.readLines(new File("testFiles/train.txt"));
			int hits = 0 ;
			double sum =0;
			
			for (String string : strs) {
				String[]temp = string.split("	");
				testMap.put(temp[0], temp[1]);
			}
			for(String string2 : nameMaps.keySet()){
				if(nameMaps.get(string2).equals(testMap.get(string2))){
					hits ++;  //49
				}
				sum+=1;      //1960
			}
			System.out.println(hits/sum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
