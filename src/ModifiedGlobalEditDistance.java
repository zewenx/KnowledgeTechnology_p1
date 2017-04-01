import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

public class ModifiedGlobalEditDistance{

	
	public static void main(String[] args) {
		new ModifiedGlobalEditDistance().run(args);
	}

	public void run(String[] args) {
		Map<Double, Integer> results = new HashMap<Double, Integer>();
		try {
			List <String> strs = FileUtils.readLines(new File("testFiles/train.txt"));
			for (String string : strs) {
				String[]temp = string.split("	");
				double result = minDistance(temp[0], temp[1]);
				if (results.containsKey(result)) {
					results.put(result, results.get(result)+1);
				}else{
					results.put(result, 1);
				}
				
			}
			output(results);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void output(Map<Double, Integer> results) {
		double sum =0;
		Collection<Double> keySet = results.keySet();
		Object[] keyArray = (Object[]) keySet.toArray();
		Arrays.sort(keyArray);
		for (Object i : keyArray) {
			sum+=results.get(i);
		}
		
		for(Object i : keyArray){
			System.out.println(i + "   "+ results.get(i)/sum);
		}
	}

	public double minDistance(String word1, String word2) {
        int length_1 = word1.length();
        int length_2 = word2.length();
        word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
        if (length_1==0|length_2==0){
            return length_1+length_2;
        }


        double [][] ans = new double [length_1+1][length_2+1];
        init(ans,length_1,length_2);
        for (int i =1;i<=length_1;i++){
            for (int j = 1;j<=length_2;j++){
                if (word1.charAt(i-1) == word2.charAt(j-1))
                    ans[i][j]=getMinAns(ans[i-1][j-1]-1,ans[i-1][j]+1.0,ans[i][j-1]+1.0);
                else {
                    ans[i][j]=getMinAns(ans[i-1][j-1]+1,ans[i-1][j]+1,ans[i][j-1]+1);
                }
            }
        }
//        System.out.println(ans[length_1][length_2]);
        return ans[length_1][length_2];

    }

    private double getMinAns(double i, double i1, double i2) {
    	double ans = Math.min(i,i1);
        return Math.min(ans,i2);
    }

    private void init(double[][] ans,int length_1,int length_2) {
        for (int i =0;i<length_1+1;i++){
            for (int j = 0;j<length_2+1;j++){
                ans[i][j] = 0;;
                ans[0][j] = j;
            }
            ans[i][0]= i;
        }
    }
}
