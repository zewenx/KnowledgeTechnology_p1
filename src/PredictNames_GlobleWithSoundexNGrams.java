import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class PredictNames_GlobleWithSoundexNGrams extends ModifiedGlobleEditDistance {
	
	HashMap<String, List<String>> nameMapsTemp = new HashMap<>();
	HashMap<String, String> nameMaps = new HashMap<>();
	HashMap<String, String> testMap = new HashMap<>();
	
	public static void main(String[] args) {
		new PredictNames_GlobleWithSoundexNGrams().run(args);
	}
	
	@Override
	public void run(String[] args) {

		int [] ans = new int[19];
		try {
			List <String> names = FileUtils.readLines(new File("testFiles/names.txt"));
			List <String> tests = FileUtils.readLines(new File("testFiles/test.txt"));
			
			generateTempNameMap(names,tests);
			generateNameMap(tests);
			evaluate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateTempNameMap(List<String> names, List<String> tests) {
		for (String test : tests) {
			test = test.split("	")[0];
			for(String name : names){
				
				double distance = minDistance(test,name);
				if (distance<0) {
					if (nameMapsTemp.containsKey(test)) {
						nameMapsTemp.get(test).add(name);
					}else{
						List<String> list = new ArrayList<>();
						list.add(name);
						nameMapsTemp.put(name, list);
					}
				}
				
			}
		}
	}

	private void generateNameMap( List<String> tests) {
		for(String key : tests){
			if (nameMapsTemp.get(key).size()==1) {
				nameMaps.put(key, nameMapsTemp.get(key).get(0));
			}else{
				
			}
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
