package Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
	public Card(int aid, int index) {
		String getStr = null;
		int count = 0;
		//file I/O
		//format :  Accountid	Cardid	Cardpwd
		//cid = Card.txt ~-> indexing aid,index
		//cpwd = Card.txt ~-> indexing aid,index
		try {
			path = new File("src/Item");
			card = new File(path.getAbsolutePath() + "/Card.txt");
			fr = new FileReader(card);
			br = new BufferedReader(fr);
			
			System.out.println("linked file success");
			
			while((getStr = br.readLine()) != null) {
				//finding..
				//System.out.println(String.valueOf(aid));
				if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
					if(index == count) {
						System.out.println("cid : " + getStr.substring(6, 10));
						cid = Integer.parseInt(getStr.substring(6, 10));
						System.out.println("cpwd : " + getStr.substring(12, 16));
						cpwd = Integer.parseInt(getStr.substring(12, 16));
					}
					count++;
				}
			}
			
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
