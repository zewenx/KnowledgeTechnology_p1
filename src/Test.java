import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List <String> strs = FileUtils.readLines(new File("testFiles/train.txt"));
			for (String string : strs) {
				System.out.println(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
