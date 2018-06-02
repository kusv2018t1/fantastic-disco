package Item;

import java.io.*;

public class Card {
	private int cid;
	private int cpwd;
	
	//file I/O // diagram no exist
	private File card;
	private FileReader fr;
	private BufferedReader br;

	//path mode
	private boolean path = true;
	//path_1 : IDE
	private String path_1 = "code/src/main/java/Item/";
	//path_2 : .jar
	private  String path_2 = "";
	
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
			if(path){
				card = new File(path_1+bank + "_Card.txt");
			}
			else{
				card = new File(path_2+bank + "_Card.txt");
			}
			fr = new FileReader(card);
			br = new BufferedReader(fr);


			while((getStr = br.readLine()) != null) {
				//finding..
				if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
					if(index == count) {
						cid = Integer.parseInt(getStr.substring(6, 11));
						cpwd = Integer.parseInt(getStr.substring(12, 16));
						break;
					}
					count++;
				}
			}
			//exception
			if(cid == 0000) {
				System.out.println("Error: No card...");
			}
		}catch (FileNotFoundException fn){
			if(!path) {
				System.out.println("mistake! path setting");

				path = !path;

				try{
					if(path){
						card = new File(path_1+bank + "_Card.txt");
					}
					else{
						card = new File(path_2+bank + "_Card.txt");
					}
					fr = new FileReader(card);
					br = new BufferedReader(fr);


					while((getStr = br.readLine()) != null) {
						//finding..
						if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
							if(index == count) {
								cid = Integer.parseInt(getStr.substring(6, 11));
								cpwd = Integer.parseInt(getStr.substring(12, 16));
								break;
							}
							count++;
						}
					}
					//exception
					if(cid == 0000) {
						System.out.println("Error: No card...");
					}
				}catch (Exception e_e){
					System.out.println("..TTTTTTBBB");
				}
			}
		}
		catch(IOException e){
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
