package BankSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Bank {
	// bank name
	String bankID;
	// Account list
	private ArrayList<Account> accountInfo = new ArrayList<>();
	// Cache
	private Account A;
	private Bank Dest;
	private int type;
	// File IO
	File file;
	// card number
	private int cardNum;

	//path mode
	private boolean path = true;
	//path_1 : IDE
	private String path_1 = "code/src/main/java/BankSystem/";
	//path_2 : .jar
	private String path_2 = "";

	//Index Num (Bank.txt)
	private final int NAME_INDEX = 0;
	private final int AID_INDEX = 1;
	private final int BALANCE_INDEX = 2;
	private final int TCID_INDEX = 3;

	/* Initialization
	 * Loading Text file "Bank.txt"
	 * Setting attributes
	 */
	public Bank(String _bankName) {
		String bankFile_path;
		bankID = _bankName;

		if(path){
			bankFile_path = path_1 + bankID + ".txt";
			this.file = new File(bankFile_path);
		}
		else{
			bankFile_path = path_2 + bankID + ".txt";
			this.file = new File(bankFile_path);
		}

		String[] splitedStr = null;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "euc-kr"));
			String line = null;
			splitedStr = null;

			// read file line by line
			while ((line = reader.readLine()) != null) {
				splitedStr = null;
				splitedStr = line.split("\t");

				for (int i = 0; i < splitedStr.length; i++)
					splitedStr[i] = splitedStr[i].trim();

				// put chunked Data to Account list (Account composition : Name AccountID
				// Balance)
				this.accountInfo.add(new Account(bankID, splitedStr[NAME_INDEX], Integer.valueOf(splitedStr[AID_INDEX]),
						Integer.valueOf(splitedStr[BALANCE_INDEX]), Integer.valueOf(splitedStr[TCID_INDEX])));

			}
			reader.close();
		} catch (FileNotFoundException fnf) {
			//mistake! path setting
			if(!path) {
				System.out.println("mistake! path setting");

				path = !path;

				if(path){
					bankFile_path = path_1 + bankID + ".txt";
					this.file = new File(bankFile_path);
				}
				else{
					bankFile_path = path_2 + bankID + ".txt";
					this.file = new File(bankFile_path);
				}
			}
			splitedStr = null;

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "euc-kr"));
				String line = null;
				splitedStr = null;

				// read file line by line
				while ((line = reader.readLine()) != null) {
					splitedStr = null;
					splitedStr = line.split("\t");

					for (int i = 0; i < splitedStr.length; i++)
						splitedStr[i] = splitedStr[i].trim();

					// put chunked Data to Account list (Account composition : Name AccountID
					// Balance)
					this.accountInfo.add(new Account(bankID, splitedStr[NAME_INDEX], Integer.valueOf(splitedStr[AID_INDEX]),
							Integer.valueOf(splitedStr[BALANCE_INDEX]), Integer.valueOf(splitedStr[TCID_INDEX])));

				}
				reader.close();
			}catch (FileNotFoundException fne_e){
				System.out.println("can't read textFile (Bank.txt) please check path.(1)");
			}catch (IOException ie){
				System.out.println("can't read textFile (Bank.txt) please check path.(2)");
			}

		} catch (IOException e) {
			System.out.println("can't read textFile (Bank.txt) please check path.(3)");
		}
	}

	/* Method
	 * Description : 해당 계좌 정보가 유효한지 검사한다.
	 */
	public boolean validCheck(int _itemType, int _itemID, int _accountID) {
		// loop until find the right Account
		type = _itemType;
		for (Account value : this.accountInfo) {
			// Account Id check
			System.out.println(value.get_aid() + " : " + _accountID);
			if (value.get_aid() == _accountID) {
				// card
				if (_itemType == 1) {
					for (int i = 0; i < 2; i++) {
						// Item Id check
						System.out.println(value.getItemID(_itemType)[i] + " : " + _itemID);
						if (value.getItemID(_itemType)[i] == _itemID) {
							A = value;
							cardNum = i;
							return true;
						}
					}
				}
				// book
				else {
					if (value.getItemID(_itemType)[0] == _itemID) {
						A = value;
						return true;
					}
				}
			}
		}
		return false;
	}

	/* Method
	 * Description : 비밀번호가 올바른지 검사한다.
	 */
	public boolean confirm(int _pwd) {
		if (type == 1) {    // card
			if (A.getPwd(type)[cardNum] == _pwd) {// Account Id check
				return true;
			}
		} else {
			if (A.getPwd(type)[0] == _pwd)
				return true;
		}
		return false;
	}

	/* Method
	 * Description : 계좌에 발급하는 교통카드 id를 연동한다.
	 */
	public boolean linkAccount(int _tcid) {
		if (A.addLink(_tcid))
			return true;
		else
			return false;
	}

	/* Method
	 * Description : 계좌 잔액을 읽어온다.
	 */
	public int getBalance() {
		// write data
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.file)));

			for (Account value : accountInfo) {
				pw.println(value.get_name() + "\t" + value.get_aid() + "\t" + value.get_balance() + "\t"
						+ value.get_tcid());
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("can't write textFile (Bank.txt) please check path.");
		}
		return A.get_balance();
	}

	/* Method
	 * Description : 교통 카드 발급을 진행한다.
	 */
	public boolean chargeTrafficCard(int _money) {
		return this.withdraw(_money);
	}

	/* Method
	 * Description : 출금을 진행한다.
	 */
	public boolean withdraw(int _money) {
		int bal;
		// verify sufficient fund

		System.out.println("Account Balance : " + A.get_balance());
		bal = A.get_balance() - _money;
		if (bal >= 0) {
			A.set_balance(bal);
			return true;
		}
		return false;
	}

	/* Method
	 * Description : 입금을 진행한다.
	 */
	public boolean deposit(int _money) {
		A.set_balance(A.get_balance() + _money);
		return true;
	}

	/* Method
	 * Description : 송금 대상의 계좌가 유효한지 검사한다.
	 */
	public String checkAccount(String _bankID, int _accountID) {
		Dest = new Bank(_bankID);
		// this account != dest account
		//**change && -> ||  &&-> it can't transfer even if it isn't same account becase of bank
		for (Account value : Dest.accountInfo) {
			if (value.get_aid() == _accountID) {
				Dest.A = value;
				return value.get_name();
			}
		}
		return null;
	}

	/* Method
	 * Description : 송금을 진행한다.
	 */
	public boolean transfer(int _money) {
		int changedMoney = 0;

		if(this.bankID.equals("citi") || this.bankID.equals("bankofamerica")){
			if(Dest.bankID.equals("kb") || Dest.bankID.equals("shinhan")){
				changedMoney = (_money * 1000)-1000;
			} else{
				changedMoney = _money-1;
			}
		} else{
			if(Dest.bankID.equals("citi") || Dest.bankID.equals("bankofamerica")){
				changedMoney = (_money / 1000)-1;
			} else{
				changedMoney = _money-1000;
			}
		}

		// renew my Balance & Dest Balance
		if ((this.withdraw(_money)) && (Dest.deposit(changedMoney))) {
			if (this.bankID.equals(Dest.bankID)) {
				for (Account value : accountInfo) {
					if (value.get_name().equals(Dest.A.get_name()))
						value.set_balance(Dest.A.get_balance());
				}
				return true;
			} else{
				PrintWriter pw;
				try {
					pw = new PrintWriter(new BufferedWriter(new FileWriter(Dest.file)));

					for (Account value : Dest.accountInfo) {
						pw.println(value.get_name() + "\t" + value.get_aid() + "\t" + value.get_balance() + "\t"
								+ value.get_tcid());
					}
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("can't read textFile (management.txt) please check path.");
				}
				return true;
			}
		} else {
			return false;
		}
	}
}