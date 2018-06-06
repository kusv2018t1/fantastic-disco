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

	//parsing index Accountid	Bookid	Bookpwd
	private final int PARSING_AID = 4;
	private final int TAB_SIZE = 2;
	private final int ID_SIZE = 5;
	private final int PWD_SIZE = 4;
	private final int PARSING_CID = PARSING_AID + TAB_SIZE + ID_SIZE; // 4 + 2 + 5 = 11
	private final int PARSING_CPWD = PARSING_CID + TAB_SIZE + PWD_SIZE; // 10 + 2 + 4 = 17

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
				if(getStr.substring(0, PARSING_AID).equals(String.valueOf(aid))) {
					if(index == count) {
						cid = Integer.parseInt(getStr.substring(PARSING_AID + TAB_SIZE, PARSING_CID));
						cpwd = Integer.parseInt(getStr.substring(PARSING_CID + TAB_SIZE, PARSING_CPWD));
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
						if(getStr.substring(0, PARSING_AID).equals(String.valueOf(aid))) {
							if(index == count) {
								cid = Integer.parseInt(getStr.substring(PARSING_AID + TAB_SIZE, PARSING_CID));
								cpwd = Integer.parseInt(getStr.substring(PARSING_CID + TAB_SIZE, PARSING_CPWD));
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
			System.out.println("can't read textFile (Card.txt) please check path.");
		}
	}
	public int getCid() {
		return cid;
	}
	public int getCpwd() {
		return cpwd;
	}

}
