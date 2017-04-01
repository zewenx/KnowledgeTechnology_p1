import java.io.File;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.FileUtils;

public class SoundexMethod {
	static Soundex mSoundex = new Soundex();

	public static void main(String[] args) {

		new SoundexMethod().run(args);
	}
	public void run(String[] args) {
		int [] ans = new int[10];
		GlobalEditDistance globleEditDistance = new GlobalEditDistance();
		try {
			List <String> strs = FileUtils.readLines(new File("testFiles/train.txt"));
			for (String string : strs) {
				String[]temp = string.split("	");
				int similarity = difference(temp[0], temp[1]);
//				if (similarity == 4) {
//					ans[globleEditDistance.minDistance(temp[0], temp[1])]++;
//				}
				ans[similarity]++;
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
		for(int i = 0 ;i<10;i++){
			
			System.out.println(ans[i]+"  "+ans[i]/sum);
		}
	}

	
	// return 0-4 , 4 refers to extremely similar
	int difference(String str1,String str2){
		try {
			return mSoundex.difference(str1, str2);
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
