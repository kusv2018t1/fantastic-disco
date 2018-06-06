package Item;

import java.io.*;

public class TrafficCard {
	private int tcid;
	private int end_date;
	private int linkedAID;

	//file I/O // diagram no exist
	private File trfc;
	private FileReader fr;
	private BufferedReader br;

	//temp
	private String[] temp;
	
	//tcid list index 
	//increment each time reading tcid
	//next time, next tcid
	//read id second line so change 0->-1
	private static int count = -1;

	//path mode
	private boolean path = true;
	//path_1 : IDE
	private String path_1 = "code/src/main/java/Item/TrafficCard.txt";
	//path_2 : .jar
	private String path_2 = "TrafficCard.txt";

	public TrafficCard() {
		String getStr = null;
		tcid = 0;
		end_date = 0;
		linkedAID = 0;

		//file I/O
		//format : TrafficCard.tcid
		//9000
		//9001
		//9010
		//9011
		temp = new String[getCardAmount()];

		try {
			if(path){
				trfc = new File(path_1);
			}
			else{
				trfc = new File(path_2);
			}
			fr = new FileReader(trfc);
			br = new BufferedReader(fr);

			//one line pass
			temp[0] = br.readLine();

			//get tcid
			if((getStr = br.readLine()) != null) {
				
				if(getStr.substring(0, 4) != null) {
					tcid = Integer.parseInt(getStr.substring(0, 4));
					System.out.println("tcid : "+tcid);
					//increment each time reading tcid
					count++;
				} else {
					System.out.println("Not matched Format...");
				} 
			} else {
				tcid = -1;
				System.out.println("No traffic card... count : " + count);
			}

			//save data
			for(int i = 1; i < temp.length; i++){
				temp[i] = br.readLine();
			}

		}catch (FileNotFoundException fn){
			if(!path) {
				System.out.println("mistake! path setting");

				path = !path;
				try{
					if(path){
						trfc = new File(path_1);
					}
					else{
						trfc = new File(path_2);
					}
					fr = new FileReader(trfc);
					br = new BufferedReader(fr);

					//one line pass
					temp[0] = br.readLine();

					//get tcid
					if((getStr = br.readLine()) != null) {

						if(getStr.substring(0, 4) != null) {
							tcid = Integer.parseInt(getStr.substring(0, 4));
							System.out.println("tcid : "+tcid);
							//increment each time reading tcid
							count++;
						} else {
							System.out.println("Not matched Format...");
						}
					} else {
						tcid = -1;
						System.out.println("No traffic card... count : " + count);
					}

					//save data
					for(int i = 1; i < temp.length; i++){
						temp[i] = br.readLine();
					}



				}catch (Exception e_e){
					System.out.println("TSDVDFVREVAVADFBGAG");
				}
			}
		}catch(IOException e){
			System.out.println("can't read textFile (TrafficCard.txt) please check path.");
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

		try {

			//renew file
			PrintWriter pw;
			pw = new PrintWriter(new BufferedWriter(new FileWriter(trfc)));

			for(int i = 0; i < temp.length; i++){
				pw.println(temp[i]);
			}

			pw.close();
		} catch (IOException e) {
			System.out.println("can't write textFile (TrafficCard.txt) please check path.");
		}
	}
	public int getCardAmount(){

		try {
			int Acount = 0;
			if(path){
				trfc = new File(path_1);
			}
			else{
				trfc = new File(path_2);
			}
			fr = new FileReader(trfc);
			br = new BufferedReader(fr);

			//pass one line
			br.readLine();

			//counting
			while(br.readLine() != null){
				Acount++;
			}

			System.out.println("tcard counting... "+Acount);
			return Acount;

		} catch (FileNotFoundException e) {
			System.out.println("can't read textFile (TrafficCard.txt) please check path.");
		} catch (IOException e) {
			System.out.println("can't read textFile (TrafficCard.txt) please check path.");
		}

		//Error
		return -1;
	}
}
