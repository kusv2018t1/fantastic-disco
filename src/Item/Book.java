package Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Book {
	private int bid;
	private int bpwd;
	//file I/O // diagram no exist
	private File book, path;
	private FileReader fr;
	private BufferedReader br;
	//int aid -> diagram 수정 필
	public Book(int aid) {
		String getStr = null;
		//file I/O
		//format : Accountid	Bookid	Bookpwd
		//bid = Book.txt ~-> indexing aid,index
		//bpwd = Book.txt ~-> indexing aid,index
		try {
			path = new File("src/Item");
			book = new File(path.getAbsolutePath() + "/Book.txt");
			fr = new FileReader(book);
			br = new BufferedReader(fr);
			
			System.out.println("linked file success");
			
			while((getStr = br.readLine()) != null) {
				//finding..
				//System.out.println(String.valueOf(aid));
				if(getStr.substring(0, 4).equals(String.valueOf(aid))) {
					//System.out.println("bid : " + getStr.substring(6, 10));
					bid = Integer.parseInt(getStr.substring(6, 10));
					//System.out.println("bpwd : " + getStr.substring(12, 16));
					bpwd = Integer.parseInt(getStr.substring(12, 16));
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//bid = Book.txt ~-> indexing aid.
	public int getBid() {
		return bid;
	}
	//bpwd = Book.txt ~-> indexing aid.
	public int getBpwd() {
		return bpwd;
	}	
}
