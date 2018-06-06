package Item;

import java.io.*;

/* Book.java
 * : 통장 객체
 * : Account 마다 1개의 객체를 가지고 있음
 * : 은행정보와 계좌정보를 인자값으로 생성자메소드를 지니고 있음
 * : txt파일로 미리 저장된 정보를 읽고, 전달한다.
 */

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

	//parsing index Accountid	Bookid	Bookpwd
	private final int PARSING_AID = 4;
	private final int TAB_SIZE = 2;
	private final int ID_SIZE = 4;
	private final int PWD_SIZE = 4;
	private final int PARSING_BID = PARSING_AID + TAB_SIZE + ID_SIZE;
	private final int PARSING_BPWD = PARSING_BID + TAB_SIZE + PWD_SIZE;


	/*
	 * Book()
	 *
	 */
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
				if(getStr.substring(0, PARSING_AID).equals(String.valueOf(aid))) {
					bid = Integer.parseInt(getStr.substring(PARSING_AID + TAB_SIZE, PARSING_BID));
					bpwd = Integer.parseInt(getStr.substring(PARSING_BID + TAB_SIZE, PARSING_BPWD));
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
						if(getStr.substring(0, PARSING_AID).equals(String.valueOf(aid))) {
							bid = Integer.parseInt(getStr.substring(PARSING_AID + TAB_SIZE, PARSING_BID));
							bpwd = Integer.parseInt(getStr.substring(PARSING_BID + TAB_SIZE, PARSING_BPWD));
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
			System.out.println("can't read textFile (Book.txt) please check path.");
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
