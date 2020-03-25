import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import net.minidev.json.JSONAware;

public class Test {
	public static void main(String[] args) throws Exception {
//		System.out.println("014612000000004".substring(6));
		
		File file = new File("D:\\activate.txt");
		BufferedReader br = null;
		try {
			int cartonNo = 0;
			int start = 0;
			int current = 0;
			int end = 0;
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				String[] str = line.split("	");
				int cNo = Integer.valueOf(str[0].substring(6));
				int pNo = Integer.valueOf(str[1]);
				if(cartonNo == 0) {
					cartonNo = cNo;
					start = pNo;
					current = pNo;
					continue;
				}
				if(cartonNo == cNo && pNo - current != 1) {
					System.out.println(cartonNo+"("+start+"~"+current+")");
					start = pNo;
					current = pNo;
					continue;
				}
				if(cartonNo != cNo) {
					System.out.println(cartonNo+"("+start+"~"+current+")");
					cartonNo = cNo;
					start = pNo;
					current = pNo;
					continue;
				}
				current = pNo;
			}
			System.out.println(cartonNo+"("+start+"~"+current+")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		
	}
}
