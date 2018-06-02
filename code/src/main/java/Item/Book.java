package Item;

import java.io.*;

public class Book {
	private int bid;
	private int bpwd;
	//file I/O // diagram no exist
	private File book;
	private FileReader fr;
	private BufferedReader br;
	//int aid -> diagram 수정 필

	//path mode
	private boolean path = true;
	//path_1 : IDE
	private String path_1 = "code/src/main/java/Item/";
	//path_2 : .jar
	private  String path_2 = "";


	public Book(String bank, int aid) {
		String getStr = null;
		bid = 0000;
		bpwd = 0000;
		//file I/O
		//format : Accountid	Bookid	Bookpwd
		//bid = shinhan_Book.txt ~-> indexing aid,index
		//bpwd = shinhan_Book.txt ~-> indexing aid,index

		try {
			if(path){
				book = new File(path_1+bank +"_Book.txt");
			}
			else{
				book = new File(path_2 + bank +"_Book.txt");
			}

			fr = new FileReader(book);
			br = new BufferedReader(fr);

			
			while((getStr = br.readLine()) != null) {
				//finding..
				if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
					bid = Integer.parseInt(getStr.substring(6, 10));
					bpwd = Integer.parseInt(getStr.substring(12, 16));
					break;
				}
			}
			//exception
			if(bid == 0000) {
				System.out.println("Error: No book...");
			}
			
		}catch(FileNotFoundException fne){
			if(!path) {
				System.out.println("mistake! path setting");

				path = !path;

				try{
					if(path){
						book = new File(path_1+bank +"_Book.txt");
					}
					else{
						book = new File(path_2 + bank +"_Book.txt");
					}

					fr = new FileReader(book);
					br = new BufferedReader(fr);


					while((getStr = br.readLine()) != null) {
						//finding..
						if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
							bid = Integer.parseInt(getStr.substring(6, 10));
							bpwd = Integer.parseInt(getStr.substring(12, 16));
							break;
						}
					}
					//exception
					if(bid == 0000) {
						System.out.println("Error: No book...");
					}
				}catch (Exception e_e){
					System.out.println("...T .. T");
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//bid = shinhan_Book.txt ~-> indexing aid.
	public int getBid() {
		return bid;
	}
	//bpwd = shinhan_Book.txt ~-> indexing aid.
	public int getBpwd() {
		return bpwd;
	}	
}
