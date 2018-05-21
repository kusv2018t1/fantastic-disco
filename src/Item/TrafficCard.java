package Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TrafficCard {
	private int tcid;
	private int end_date;
	private int linkedAID;

	//file I/O // diagram no exist
	private File trfc, path;
	private FileReader fr;
	private BufferedReader br;
	
	//tcid list index 
	//increment each time reading tcid
	//next time, next tcid
	private static int count = 0;

	public TrafficCard() {
		String getStr = null;	
		tcid = 0;
		end_date = 0;
		linkedAID = 0;
		
		//for passing already using tcid
		int index = 0;

		//file I/O
		//format : TrafficCard.tcid
		//9000
		//9001
		//9010
		//9011

		try {
			path = new File("src/Item");
			trfc = new File(path.getAbsolutePath() + "/TrafficCard.txt");
			fr = new FileReader(trfc);
			br = new BufferedReader(fr);


			System.out.println("linked file success");

			//one line pass
			br.readLine();
			//pass already using tcid
			for(int i = 0; i < count; i++) {
				br.readLine();
			}
			//get tcid
			if((getStr = br.readLine()) != null) {
				
				if(getStr.substring(0, 4) != null) {
					tcid = Integer.parseInt(getStr.substring(0, 4));
					//increment each time reading tcid
					count++;
				} else {
					System.out.println("Not matched Format...");
				} 
			} else {
				System.out.println("No traffic card...");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public int getTcid() {
		return tcid;
	}
	public void setDateRange(int _end_date) {
		this.end_date = _end_date;
	}
	public void setAccountID(int accountID) {
		this.linkedAID = accountID;
	}
}
