import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GlobalEditDistance {
	
	public static void main(String[] args) {
		new GlobalEditDistance().run(args);
	}

	public void run(String[] args) {
		int [] ans = new int[19];
		try {
			List <String> strs = FileUtils.readLines(new File("testFiles/train.txt"));
			for (String string : strs) {
				String[]temp = string.split("	");
				ans[minDistance(temp[0], temp[1])]++;
			}
			output(ans);
			
		} catch (Exception e) {
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

	public int minDistance(String word1, String word2) {
        int length_1 = word1.length();
        int length_2 = word2.length();
        if (length_1==0|length_2==0){
            return length_1+length_2;
        }


        int [][] ans = new int [length_1+1][length_2+1];
        init(ans,length_1,length_2);
        for (int i =1;i<=length_1;i++){
            for (int j = 1;j<=length_2;j++){
                if (word1.toLowerCase().charAt(i-1) == word2.charAt(j-1))
                    ans[i][j]=getMinAns(ans[i-1][j-1]-1,ans[i-1][j]+1,ans[i][j-1]+1);
                else {
                    ans[i][j]=getMinAns(ans[i-1][j-1]+1,ans[i-1][j]+1,ans[i][j-1]+1);
                }
            }
        }
//        System.out.println(ans[length_1][length_2]);
        return ans[length_1][length_2];

    }

    private int getMinAns(int i, int i1, int i2) {
        int ans = Math.min(i,i1);
        return Math.min(ans,i2);
    }

    private void init(int[][] ans,int length_1,int length_2) {
        for (int i =0;i<length_1+1;i++){
            for (int j = 0;j<length_2+1;j++){
                ans[i][j] = 0;;
                ans[0][j] = j;
            }
            ans[i][0]= i;
        }
    }
}
