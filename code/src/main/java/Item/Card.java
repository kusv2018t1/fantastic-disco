package Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Card {
	private int cid;
	private int cpwd;
	
	//file I/O // diagram no exist
	private File card, path;
	private FileReader fr;
	private BufferedReader br;
	
	//int aid / int idex 수정 필
	public Card(String bank, int aid, int index) {
		String getStr = null;
		int count = 0;
		cid = 0000;
		cpwd = 0000;
		
		//file I/O
		//format :  Accountid	Cardid	Cardpwd
		//cid = shinhan_Card.txt ~-> indexing aid,index
		//cpwd = shinhan_Card.txt ~-> indexing aid,index
		try {
			path = new File("java/Item");
			card = new File(path.getAbsolutePath() + "/" + bank + "_Card.txt");
			fr = new FileReader(card);
			br = new BufferedReader(fr);
			
			//System.out.println("linked file success");
			
			while((getStr = br.readLine()) != null) {
				//finding..
				//System.out.println(String.valueOf(aid));
				if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
					if(index == count) {
						//System.out.println("cid : " + getStr.substring(6, 11));
						cid = Integer.parseInt(getStr.substring(6, 11));
						//System.out.println("cpwd : " + getStr.substring(12, 16));
						cpwd = Integer.parseInt(getStr.substring(12, 16));
						break;
					}
					count++;
				}
			}
			//exception
			if(cid == 0000) {
				//System.out.println("Error: No card...");
			}
			//System.out.println("end linking--------------------------");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public int getCid() {
		return cid;
	}
	public int getCpwd() {
		return cpwd;
	}

}
