import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class NGramsDistance {
	
	public static void main(String[] args) {
		
		new NGramsDistance().run(args);
	}

	public void run(String[] args) {
		int [] ans = new int[19];
		try {
			List <String> strs = FileUtils.readLines(new File("testFiles/train.txt"));
			for (String string : strs) {
				String[]temp = string.split("	");
				System.out.println(new Ngram().getSimilarity(temp[0], temp[1], 2));			
				
			}
//			output(ans);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void output(int[] ans) {
		double sum =0;
		for (int i : ans) {
			sum+=i;
		}
		for(int i = 0 ;i<19;i++){
			System.out.println(ans[i]/sum);
		}
	}
}
