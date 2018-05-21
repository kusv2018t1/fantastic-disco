package Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Account_caller {
	public static void main(String[] args) {
		Book book;
		Card [] card = new Card[2];
		TrafficCard tc;
		int aid = 1011;
		
		//testing value
		//int [] account = {1001, 1011, 1110};
		System.out.println("-------------------Book--------------------------");
		book = new Book(aid);
		System.out.println("Account[" +aid + "] bid  :"+book.getBid());
		System.out.println("Account[" +aid + "] bpwd :"+book.getBpwd());
		System.out.println("-------------------Card--------------------------");
		for(int i = 0; i < card.length; i++) {
			card[i] = new Card(aid, i);
		}
		for(int i = 0; i < card.length; i++) {
			System.out.println("Account[" +aid + "] cid   :"+card[i].getCid());
			System.out.println("Account[" +aid + "] cpwd  :"+card[i].getCpwd());
		}
		System.out.println("-------------------trafficCard--------------------");
		tc = new TrafficCard();
		System.out.println("first tcid : "+tc.getTcid());
		tc = new TrafficCard();
		System.out.println("second tcid : "+tc.getTcid());
	}
}
