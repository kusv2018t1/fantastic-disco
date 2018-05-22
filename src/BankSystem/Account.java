package BankSystem;

import Item.*;

public class Account {
	// id -> aid
	private int aid;
	private int balance;
	private String name;
	private int tcid;
	// card
	Card[] card = new Card[2];
	// book
	Book book;
	// card amount
	private int cardAmount = 2;

	// name : _name account id : _aid balance : _bal trafficCard id : tcid
	Account(String _bankID, String _name, int _aid, int _bal, int _tcid) {
		name = _name;
		aid = _aid;
		balance = _bal;
		tcid = _tcid;

		// card & book
		card[0] = new Card(_bankID,_aid,0);
		card[1] = new Card(_bankID,_aid,1);
		book = new Book(_bankID,_aid);
	}
	
	// get name
	public String get_name() {
		return name;
	}

	// get account id
	public int get_aid() {
		return aid;
	}

	// get balance
	public int get_balance() {
		return balance;
	}

	public int get_tcid() {
		return tcid;
	}

	// set balance
	public void set_balance(int _bal) {
		this.balance = _bal;
	}

	// get Item Id
	public int[] getItemID(int type) {
		int[] _cards=new int[cardAmount], _book=new int[cardAmount];
		
		if (type == 1) { // card
			for (int i = 0; i < cardAmount; i++) {
				_cards[i] = card[i].getCid();
			}
			return _cards;
		} else { // book
			_book[0] = book.getBid();
			return _book;
		}
	}

	// get PassWord
	public int[] getPwd(int type) {
		int[] _cards=new int[cardAmount], _book=new int[cardAmount];
		
		if (type == 1) {// card
			for (int i = 0; i < cardAmount; i++)
				_cards[i] = card[i].getCpwd();
			return _cards;
		}
		else {// book
			_book[0] = book.getBid();
			return _book;
		}
	}

	// link Traffic Card and Account
	public boolean addLink(int _tcid) {
		this.tcid = _tcid;
		return true;
	}
}
