package GUI;

import ATM.ATM;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;


/* level 0 - read Item
 * level 1 - select Service
 * level 2 - check
 *       2.1 - inputPassword
 *       2.2 - getBalance
 *       2.3 - returnItem
 * level 3 - deposit
 *       3.1 - inputCash
 *       3.2 - getBalance
 *       3.3 - returnItem
 * level 4 - withdraw
 *       4.1 - inputPassword
 *       4.2 - selectNation
 *       4.3 - inputAmount
 *       4.4 - returnItem
 *       4.5 - getBalance
 * level 5 - transfer
 *       5.1 - inputPassword
 *       5.2 - inputTransfer
 *       5.3 - inputAmount
 *       5.4 - getBalance
 *       5.5 - returnItem
 * level 6 - issue Traffic Card
 * 		6.1 - inputPassword
 * 	   6.2 - inputDateRange //
 * 	   6.3 - agreement      //
 * 	   6.4 - returnItem
 * 	   6.5 - getBalance
 *
 *	Exception 1. - CancelTransaction
 *	Exception 2. - returnItem
 */

public class GUIController extends JFrame{
   //Size
   private final int PWD_SIZE = 4;
   private final int CASH_STRING = 3;
   private final int AID_SIZE = 4;
   private final int TC_DATE_SIZE = 2;


	//path mode
	private boolean path = true;
	//path_1 : IDE
	private String path_1 = "code/src/main/java/ATM/management.txt";
	//path_2 : .jar
	private String path_2 = "management.txt";
	//atm system
	private ATM atm;

	//Main Frame
	private JFrame mainBox;

	//sub Frame :Management
	private JFrame subBox;

	//input password
	private char [] input_pw = new char[PWD_SIZE];
	private int pw_i;

	//insert cash
	private char [] input_cash = new char[CASH_STRING];
	private int cash_i;
	private String ptn_s;

	//input Transfer accountid
	private char [] input_id = new char[AID_SIZE];
	private int id_i;

	//input Date Range
	private char [] input_date = new char[TC_DATE_SIZE];
	private int date_i;

	//Language Mode (= bank nation)
   private final int MODE_KOR = 0;
   private final int MODE_ENG = 1;
	private int mode;

	//destID
	private String dest_name;

	//Transaction Mode
	private final int MODE_WAIT = 0;
	private final int MODE_CHECK = 1;
   private final int MODE_DEPOSIT = 2;
   private final int MODE_WITHDRAW = 3;
   private final int MODE_TRANSFER = 4;
   private final int MODE_ISSUE = 5;
   private final int MODE_ADMIN = 9;
	private int t_mode;

	//insert cash
	private int bill_count;
	private String [] bill;
	private int b;





	//Level


	public static void main(String[] args) {

		GUIController guic = new GUIController();

		System.out.println("Hi..!");
	}

	//GUI Main Frame initialization
	public GUIController() {
		readyReadCard();
		managementGUI();
	}

	//level 0...
	//Exception 1. 자릿 수
	public void readyReadCard() {
		t_mode = MODE_WAIT;
		atm = new ATM();

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		//Level 0... read item...
		JPanel notice_p, insert_p, enter_p;
		JLabel notice_k, notice_e;
		JTextField insert_ItemType;
		JTextField insert_ItemId;
		JTextField insert_ItemBank;
		JTextField insert_ItemAccountId;
		JButton enter;

		//Frame Layout set
		mainBox.setLayout(new GridLayout(3, 1,10,10));

		//Panel
		notice_p = new JPanel();
		insert_p = new JPanel();
		enter_p = new JPanel();

		//Panel Set
		notice_p.setLayout(new FlowLayout());
		insert_p.setLayout(new FlowLayout());
		enter_p.setLayout(new FlowLayout());

		//Components
		notice_k = new JLabel("카드나 통장을 넣어주세여 :)", SwingConstants.CENTER);
		notice_e = new JLabel("please insert Card or Book", SwingConstants.CENTER);


		insert_ItemType = new JTextField("ItemType");
		insert_ItemId = new JTextField("ItemId");
		insert_ItemBank = new JTextField("ItemBank");
		insert_ItemAccountId = new JTextField("ItemAccountId");
		insert_ItemType.setSize(100, 40);
		insert_ItemId.setSize(100, 40);
		insert_ItemBank.setSize(100, 40);
		insert_ItemAccountId.setSize(100, 40);

		enter = new JButton("Read Item");
		enter.setSize(200, 50);


		//button set
		enter.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int ItemType, ItemId, ItemAccountId;
						String ItemBank;

						//first exception handle
						//test length
						if(insert_ItemType.getText().length() != 1){
							insert_ItemType.setText("1");
							insert_ItemBank.setText("citi");
							insert_ItemId.setText("90000");
							insert_ItemAccountId.setText("4000");
							notice_k.setText("다른 카드나 통장을 넣어주세욥...");
							notice_e.setText("plz insert another card or book");
						}
						else if(insert_ItemId.getText().length() != 4 && insert_ItemId.getText().length() != 5){
							insert_ItemType.setText("1");
							insert_ItemBank.setText("citi");
							insert_ItemId.setText("90000");
							insert_ItemAccountId.setText("4000");
							notice_k.setText("다른 카드나 통장을 넣어주세욥...");
							notice_e.setText("plz insert another card or book");
						}
						else if(insert_ItemAccountId.getText().length() != 4){
							insert_ItemType.setText("1");
							insert_ItemBank.setText("citi");
							insert_ItemId.setText("90000");
							insert_ItemAccountId.setText("4000");
							notice_k.setText("다른 카드나 통장을 넣어주세욥...");
							notice_e.setText("plz insert another card or book");
						}
						//bank name
						else if(!(insert_ItemBank.getText().equals("kb") || insert_ItemBank.getText().equals("shinhan") || insert_ItemBank.getText().equals("citi") || insert_ItemBank.getText().equals("bankofamerica"))){
							insert_ItemType.setText("1");
							insert_ItemBank.setText("citi");
							insert_ItemId.setText("90000");
							insert_ItemAccountId.setText("4000");
							notice_k.setText("다른 카드나 통장을 넣어주세욥...");
							notice_e.setText("plz insert another card or book");
						}
						else{
							try {
								ItemType = Integer.parseInt(insert_ItemType.getText());
								ItemId = Integer.parseInt(insert_ItemId.getText());
								ItemBank = insert_ItemBank.getText();
								ItemAccountId = Integer.parseInt(insert_ItemAccountId.getText());

								//pass
								int result = atm.readItem(ItemType, ItemId, ItemBank, ItemAccountId);
								if(result < 0){
									insert_ItemType.setText("1");
									insert_ItemBank.setText("citi");
									insert_ItemId.setText("90000");
									insert_ItemAccountId.setText("4000");
									notice_k.setText("다른 카드나 통장을 넣어주세욥...");
									notice_e.setText("plz insert another card or book");
								}
								else if(result == 0){
									mode = 0;
									//mode = ATM.readItem() -> return mode
									//if mode : 0(KOR) / 1(ENG) / else -> cancel Transaction
									mainBox.dispose();
									selectService();
								}
								else if(result == 1){
									mode = 1;
									//mode = ATM.readItem() -> return mode
									//if mode : 0(KOR) / 1(ENG) / else -> cancel Transaction
									mainBox.dispose();
									selectService();
								}
								else{
									System.out.println("WTF??");
								}
							}
							catch(NumberFormatException nfe){
								insert_ItemType.setText("1");
								insert_ItemBank.setText("citi");
								insert_ItemId.setText("90000");
								insert_ItemAccountId.setText("4000");
								notice_k.setText("다른 카드나 통장을 넣어주세욥...");
								notice_e.setText("plz insert another card or book");
							}
						}
					}
				}
		);

		//Components adding
		notice_p.add(notice_k);
		notice_p.add(notice_e);
		insert_p.add(insert_ItemType, RIGHT_ALIGNMENT);
		insert_p.add(insert_ItemId, RIGHT_ALIGNMENT);
		insert_p.add(insert_ItemBank, RIGHT_ALIGNMENT);
		insert_p.add(insert_ItemAccountId, RIGHT_ALIGNMENT);
		enter_p.add(enter);


		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(insert_p);
		mainBox.add(enter_p);


		//visible
		mainBox.setVisible(true);

	}

	//level 1...
	public void selectService(){

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		//Level 1... select Service...
		JPanel select;
		JButton check, deposit, withdraw, transfer, issueTC;

		//Frame Layout set
		mainBox.setLayout(new GridLayout(3, 1,10,10));

		//Panel set
		select = new JPanel();
		select.setLayout(new GridLayout( 2, 3, 3, 3));

		//components.
		if(mode == MODE_KOR){
			check = new JButton("예금 조회");
			deposit = new JButton("입금");
			withdraw = new JButton("출금");
			transfer = new JButton("송금");
			issueTC = new JButton("교통카드 발급");
		}
		else{
			check = new JButton("Check");
			deposit = new JButton("Deposit");
			withdraw = new JButton("Withdraw");
			transfer = new JButton("Transfer");
			issueTC = new JButton("Issue trafficCard");
		}


		//button set
		check.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainBox.dispose();
						t_mode = MODE_CHECK;
						atm.selectService(MODE_CHECK);
						inputPassword();
					}
				}
		);
		deposit.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainBox.dispose();
						t_mode = MODE_DEPOSIT;
						atm.selectService(MODE_DEPOSIT);
						inputCash();
					}
				}
		);
		withdraw.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainBox.dispose();
						t_mode = MODE_WITHDRAW;
						atm.selectService(MODE_WITHDRAW);
						inputPassword();
					}
				}
		);
		transfer.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainBox.dispose();
						t_mode = MODE_TRANSFER;
						atm.selectService(MODE_TRANSFER);
						inputPassword();
					}
				}
		);
		issueTC.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainBox.dispose();
						t_mode = MODE_ISSUE;
						atm.selectService(MODE_ISSUE);
						inputPassword();
					}
				}
		);

		//Components add on Panel
		select.add(check);
		select.add(deposit);
		select.add(withdraw);
		select.add(transfer);
		select.add(issueTC);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}

			}
		});
		select.add(cancel);

		//Panel add on Frame
		mainBox.add(select);

		//visible
		mainBox.setVisible(true);



	}

/*	level 2 - check
 *       2.1 - inputPassword
 *       2.2 - getBalance (거래금액 / 수수료 / 잔액)
 *       2.3 - returnItem
 */
	//level 2.1 - inputPassword

	public void inputPassword(){

		input_pw[0] = '_';
		input_pw[1] = '_';
		input_pw[2] = '_';
		input_pw[3] = '_';
		pw_i = 0;

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(3, 1,10,10));

		//Panel
		JPanel notice_p, insert_p;
		//Panel set
		notice_p = new JPanel();
		insert_p = new JPanel();
		notice_p.setLayout(new FlowLayout());
		insert_p.setLayout(new GridLayout(5,3,1,1));


		//components
		JLabel notice, ptn; //ptn : print 'X' pressed input
		JButton [] btn = new JButton[9];

		//Label set
		if(mode == MODE_KOR){
			notice = new JLabel("비 밀 번 호 :)", SwingConstants.CENTER);
		}
		else{
			notice = new JLabel("PASS WORD :)", SwingConstants.CENTER);
		}

		ptn = new JLabel("_ _ _ _", SwingConstants.CENTER);


		//Label add on Panel
		notice_p.add(notice);
		notice_p.add(ptn);

		//1~9 pwd Buttons set & add on Panel
		for(int i = 1; i < 10; i++){
			btn[i-1] = new JButton(String.valueOf(i));

			int finalI = i;

			//button add ActionFunction
			btn[i-1].addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String ptn_s = "";

							//input password
							if(pw_i < PWD_SIZE){
								input_pw[pw_i] = Character.forDigit(finalI, 10);
								for(int j = 0; j < input_pw.length; j++){
									System.out.print(input_pw[j]);
								}
								System.out.println();
								pw_i++;
							}

							//next step
							if(pw_i == PWD_SIZE){
								//confirm()
								mainBox.dispose();

								if(t_mode == MODE_CHECK){
									String pw = new String(input_pw, 0, input_pw.length);

									System.out.println(Integer.parseInt(pw));

									//pass
									if(atm.confirm(Integer.parseInt(pw))){
										printReceipt(0,0, atm.getBalance());
									}
									//non-pass
									else{
										if(mode == MODE_KOR){
											canceled("잘못된 비밀번호 입력");
										}
										else{
											canceled("Wrong Password");
										}

									}
								}
								else if(t_mode == MODE_DEPOSIT){
									//can't
								}
								else if(t_mode == MODE_WITHDRAW){
									String pw = new String(input_pw, 0, input_pw.length);

									System.out.println(Integer.parseInt(pw));

									//pass
									if(atm.confirm(Integer.parseInt(pw))){
										selectNation();
									}
									//non-pass
									else{
										if(mode == MODE_KOR){
											canceled("잘못된 비밀번호 입력");
										}
										else{
											canceled("Wrong Password");
										}
									}
								}
								else if(t_mode == MODE_TRANSFER){

									String pw = new String(input_pw, 0, input_pw.length);

									System.out.println(Integer.parseInt(pw));

									//pass
									if(atm.confirm(Integer.parseInt(pw))){
										inputTransfer();
									}
									//non-pass
									else{
										if(mode == MODE_KOR){
											canceled("잘못된 비밀번호 입력");
										}
										else{
											canceled("Wrong Password");
										}
									}
								}
								else if(t_mode == MODE_ISSUE){

									String pw = new String(input_pw, 0, input_pw.length);

									System.out.println(Integer.parseInt(pw));

									//pass
									if(atm.confirm(Integer.parseInt(pw))){
										inputDateRange();
									}
									//non-pass
									else{
										if(mode == MODE_KOR){
											canceled("잘못된 비밀번호 입력");
										}
										else{
											canceled("Wrong Password");
										}
									}
								}
								else{
									//can't
								}
							}


							//print pressed amount
							for(int j = 0; j < pw_i; j++){
								ptn_s += "X" + " ";
							}
							for(int j = 4; j > pw_i; j--){
								ptn_s += "_ "+ " ";
							}

							ptn.setText(ptn_s);
						}
					}
			);

			//Button add on panel
			insert_p.add(btn[i-1]);
		}

		//0, BS, CR set
		JButton zero, backSpace, clearInput;
		zero = new JButton("0");
		backSpace = new JButton("<-");
		clearInput = new JButton("CLEAR");


		//button add ActionFunction
		zero.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";

						//input password
						if(pw_i < 4){
							input_pw[pw_i] = Character.forDigit(0, 10);
							System.out.println(input_pw);
							for(int j = 0; j < input_pw.length; j++){
								System.out.print(input_pw[j]);
							}
							System.out.println();
							pw_i++;
						}

						//next step
						if(pw_i == PWD_SIZE){
							//confirm()
							mainBox.dispose();

							if(t_mode == MODE_CHECK){
								String pw = new String(input_pw, 0, input_pw.length);

								System.out.println(Integer.parseInt(pw));

								//pass
								if(atm.confirm(Integer.parseInt(pw))){
									printReceipt(0,0, atm.getBalance());
								}
								//non-pass
								else{
									if(mode == MODE_KOR){
										canceled("잘못된 비밀번호 입력");
									}
									else{
										canceled("Wrong Password");
									}
								}
							}
							else if(t_mode == MODE_DEPOSIT){
								//can't
							}
							else if(t_mode == MODE_WITHDRAW){
								String pw = new String(input_pw, 0, input_pw.length);

								System.out.println(Integer.parseInt(pw));

								//pass
								if(atm.confirm(Integer.parseInt(pw))){
									selectNation();
								}
								//non-pass
								else{
									if(mode == MODE_KOR){
										canceled("잘못된 비밀번호 입력");
									}
									else{
										canceled("Wrong Password");
									}
								}
							}
							else if(t_mode == MODE_TRANSFER){

								String pw = new String(input_pw, 0, input_pw.length);

								System.out.println(Integer.parseInt(pw));

								//pass
								if(atm.confirm(Integer.parseInt(pw))){
									inputTransfer();
								}
								//non-pass
								else{
									if(mode == MODE_KOR){
										canceled("잘못된 비밀번호 입력");
									}
									else{
										canceled("Wrong Password");
									}
								}
							}
							else if(t_mode == MODE_ISSUE){

								String pw = new String(input_pw, 0, input_pw.length);

								System.out.println(Integer.parseInt(pw));

								//pass
								if(atm.confirm(Integer.parseInt(pw))){
									inputDateRange();
								}
								//non-pass
								else{
									if(mode == MODE_KOR){
										canceled("잘못된 비밀번호 입력");
									}
									else{
										canceled("Wrong Password");
									}
								}
							}
							else{
								//can't
							}
						}

						//print pressed amount
						for(int j = 0; j < pw_i; j++){
							ptn_s += "X" + " ";
						}
						for(int j = 4; j > pw_i; j--){
							ptn_s += "_ "+ " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		backSpace.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";
						//exist password input -> remove  else : do Nothing.
						if(pw_i > 0){
							pw_i -= 1;
							input_pw[pw_i] = '_';
						}


						//print pressed amount
						for(int j = 0; j < pw_i; j++){
							ptn_s += "X" + " ";
						}
						for(int j = 4; j > pw_i; j--){
							ptn_s += "_ "+ " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		clearInput.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						//reset
						String ptn_s = "";
						pw_i = 0;
						input_pw[0] = '_';
						input_pw[1] = '_';
						input_pw[2] = '_';
						input_pw[3] = '_';

						//print pressed amount
						for(int j = 0; j < pw_i; j++){
							ptn_s += "X" + " ";
						}
						for(int j = 4; j > pw_i; j--){
							ptn_s += "_ "+ " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		//0, BS, CR add on Panel
		insert_p.add(backSpace);
		insert_p.add(zero);
		insert_p.add(clearInput);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}

			}
		});
		insert_p.add(cancel);


		//panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(insert_p);

		//visible
		mainBox.setVisible(true);

	}

	//level 2.2 - getBalance (거래금액 / 수수료 / 잔액)
	public void printReceipt(int _amount, int _fee, int _balance){

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(1, 2,10,10));

		//Panel set
		JPanel button_p = new JPanel();
		JPanel print_p = new JPanel();

		//Panel Layout set
		button_p.setLayout(new GridLayout(10, 1, 10, 10));
		print_p.setLayout(new GridLayout(3, 1, 10, 10));

		//components - label
		JLabel amount, fee, balance;
		//KOR
		if(mode == MODE_KOR){
			    amount = new JLabel("거 래  금 액 : " + Integer.toString(_amount) + " 원");
			       fee = new JLabel("수   수   료 : " + Integer.toString(_fee) + " 원");
			   balance = new JLabel("거래 후  잔액 : " + Integer.toString(_balance) + " 원");
		}//ENG
		else{
			amount = new JLabel("Transaction Amount : " + Integer.toString(_amount) + " $");
			fee = new JLabel("Fee : " + Integer.toString(_fee) + " $");
			balance = new JLabel("Balance : " + Integer.toString(_balance) + " $");
		}

		//label add on panel
		print_p.add(amount);
		print_p.add(fee);
		print_p.add(balance);

		//components - button
		JButton print, skip;
		//KOR
		if(mode == MODE_KOR){
			print = new JButton("명세표 출력");
			skip = new JButton("메인 화면으로");
		}//ENG
		else {
			print = new JButton("Receipt Print");
			skip = new JButton("Skip");
		}

		//button add ActionFunction
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainBox.dispose();

				if(atm.printReceipt()){
					System.out.println("뽑혔습니당><");
					readyReadCard();
					getReceipt(_amount, _fee, _balance);
				}
				else{
					if(mode == MODE_KOR){
						canceled("명세표 용지가 부족합니다.");
					}
					else{
						canceled("Not enough Receipt");
					}
				}
			}
		});
		skip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("메인화면으로고고!");
				mainBox.dispose();
				readyReadCard();
			}
		});

		//button add on panel
		button_p.add(print);
		button_p.add(skip);

		//panel add on Frame
		mainBox.add(button_p);
		mainBox.add(print_p);

		//visible
		mainBox.setVisible(true);

		//Return card
		returnCard();
		atm.checkResource();
	}


/* level 3 - deposit
 *
 */
 	//3.1 - inputCash
	public void inputCash(){

		/*	KOR
		 * 000 - 1000
		 * 001 - 5000
		 * 010 - 10000
		 * 011 - 50000
		 * ENG
		 * 100 - 10
		 * 101 - 50
		 * 110 - 100
		 */

		//input set
		bill = new String[100];
		bill_count = 0;
		b = 0;	// kor:1 / eng:2

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(2, 1,10,10));

		//Panel set
		JPanel notice_p = new JPanel();
		JPanel input_p = new JPanel();

		//Panel Layout set
		notice_p.setLayout(new GridLayout(1, 1, 10, 10));
		input_p.setLayout(new GridLayout(5, 4, 10, 10));


		//components - label
		JLabel notice;
		JLabel ptn = new JLabel("");
		ptn_s = "";

		//KOR
		if(mode == MODE_KOR){
			notice = new JLabel("현금을 넣어주세욥");
		} //ENG
		else {
			notice = new JLabel("insert cash plz");
		}

		//components - button
		JButton [] input_kor = new JButton[4];	//1000, 5000, 10000, 50000 원
		JButton [] input_eng = new JButton[3]; //10, 50, 100 $
		JButton enter;
		if(mode == MODE_KOR){
			enter = new JButton("닫기");
		}
		else{
			enter = new JButton("Close");
		}

		//Kor cash input button
		input_kor[0] = new JButton("1000원");
		input_kor[1] = new JButton("5000원");
		input_kor[2] = new JButton("10000원");
		input_kor[3] = new JButton("50000원");

		//Eng cash input button
		input_eng[0] = new JButton("10$");
		input_eng[1] = new JButton("50$");
		input_eng[2] = new JButton("100$");

		//button add Action
		input_kor[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 1){
					bill[bill_count] = "000";
					bill_count++;
					b = 1;

					ptn_s += " 1000원";
					ptn.setText(ptn_s);
				}
			}
		});
		input_kor[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 1){
					bill[bill_count] = "001";
					bill_count++;
					b = 1;

					ptn_s += " 5000원";
					ptn.setText(ptn_s);
				}

			}
		});
		input_kor[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 1){
					bill[bill_count] = "010";
					bill_count++;
					b = 1;

					ptn_s += " 10000원";
					ptn.setText(ptn_s);
				}
			}
		});
		input_kor[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 1){
					bill[bill_count] = "011";
					bill_count++;
					b = 1;

					ptn_s += " 50000원";
					ptn.setText(ptn_s);
				}

			}
		});
		//eng

		input_eng[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 2){
					bill[bill_count] = "100";
					bill_count++;
					b = 2;

					ptn_s += " 10$";
					ptn.setText(ptn_s);
				}
			}
		});
		input_eng[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 2){
					bill[bill_count] = "101";
					bill_count++;
					b = 2;

					ptn_s += " 50$";
					ptn.setText(ptn_s);
				}

			}
		});
		input_eng[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bill_count > 98){
					System.out.println("Stopppppp!! insert plz");
				}
				//defense mix bill (kor + eng)
				else if(b == 0 || b == 2){
					bill[bill_count] = "110";
					bill_count++;
					b = 2;

					ptn_s += " 100$";
					ptn.setText(ptn_s);
				}

			}
		});

		//end of inserting cash
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				bill[bill_count] = null;

				int amount = atm.insertCash(bill);
				System.out.println("insertCash Amount : "+amount);

				if(amount > 0){
					mainBox.dispose();
					printReceipt(amount, 0, atm.getBalance());
				}//atm cash full!
				else{
					if(mode == MODE_KOR){
						canceled("ATM 현금이 꽉찼습니다.");
					}
					else{
						canceled("Fulled of cash.");
					}
				}

			}
		});


		//components add on panel
		notice_p.add(notice);
		notice_p.add(ptn);
		for(int i = 0; i < 4; i++){
			input_p.add(input_kor[i]);
		}
		for(int i = 0; i < 3; i++){
			input_p.add(input_eng[i]);
		}
		input_p.add(enter);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}
			}
		});

		//reset button
		JButton reset = new JButton();
		if(mode == MODE_KOR){
			reset.setText("재설정");
		}
		else{
			reset.setText("reset");
		}
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bill = new String[100];
				bill_count = 0;
				b = 0;
				ptn_s = "";
				ptn.setText(ptn_s);
			}
		});

		input_p.add(cancel);
		input_p.add(reset);

		//add panel on Frame
		mainBox.add(notice_p);
		mainBox.add(input_p);

		//visible
		mainBox.setVisible(true);

	}

/*
 * level 4 - withdraw
 */
	//4.2 - selectNation
	public void selectNation(){
		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(3, 2,10,10));

		//Panel set
		JPanel notice_p = new JPanel();
		JPanel select_p = new JPanel();
		notice_p.setLayout(new GridLayout(1, 1, 10, 10));
		select_p.setLayout(new GridLayout(2, 2, 10, 10));

		//Label set
		JLabel notice;
		if(mode == MODE_KOR){
			notice = new JLabel("뽑으실 현금의 종류를 선택해주세요. :)");
		}
		else{
			notice = new JLabel("plz select to withdraw cash. :)");
		}
		//Label add on Panel
		notice_p.add(notice);

		//Button set
		JButton select_k, select_e;
		if(mode == MODE_KOR){
			select_k = new JButton("원");
			select_e = new JButton("달러");
		}
		else{
			select_k = new JButton("Won");
			select_e = new JButton("Dollar");
		}

		//Button add Action
		select_k.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.selectNation(0);
				mainBox.dispose();
				inputAmount(0);
			}
		});
		select_e.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.selectNation(1);
				mainBox.dispose();
				inputAmount(1);
			}
		});

		//Button add on Panel
		select_p.add(select_k);
		select_p.add(select_e);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}
			}
		});
		select_p.add(cancel);

		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(select_p);

		//visible
		mainBox.setVisible(true);

	}

	//inputAmount
	public void inputAmount(int withdrawType){

		input_cash[0] = '_';
		input_cash[0] = '_';
		input_cash[0] = '_';
		cash_i = 0;

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(3, 1,10,10));

		//Panel set
		JPanel notice_p = new JPanel();
		JPanel input_p = new JPanel();

		//Panel Layout set
		notice_p.setLayout(new FlowLayout());
		input_p.setLayout(new GridLayout(6, 3, 10, 10));

		//Label set
		JLabel notice, ptn, p_unit, destName;
		if(mode == MODE_KOR){
			notice = new JLabel("거래 금액을 입력해주세요       ");
		}
		else{
			notice = new JLabel("How much do you want?        ");
		}

		ptn = new JLabel("_ _ _ ");

		//withdraw
		if(t_mode == MODE_WITHDRAW) {
			//한화 출금
			if (withdrawType == 0) {
				p_unit = new JLabel("  만 원");
			} else {
				p_unit = new JLabel(" 0 $");
			}
		}//transfer
		else if(t_mode == MODE_TRANSFER){
			//수신인 표시


			//한국 계좌
			if (mode == MODE_KOR){
				p_unit = new JLabel(" 만 원");
				destName = new JLabel("수신인 : " + dest_name + "\t\t");
			} else {
				p_unit = new JLabel(" 0 $");
				destName = new JLabel("Receiver : " + dest_name + "\t");
			}
			notice_p.add(destName);
		}
		else{
			p_unit = new JLabel("???");
		}

		//Label add on Panel
		notice_p.add(notice);
		notice_p.add(ptn);
		notice_p.add(p_unit);


		//Button set
		JButton [] btn = new JButton[9];

		//1~9 pwd Buttons set & add on Panel
		for(int i = 1; i < 10; i++){
			btn[i-1] = new JButton(String.valueOf(i));

			int finalI = i;

			//button add ActionFunction
			btn[i-1].addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String ptn_s = "";

							//input cash
							if(cash_i < 3){
								input_cash[cash_i] = Character.forDigit(finalI, 10);
								for(int j = 0; j < input_cash.length; j++){
									System.out.print(input_cash[j]);
								}
								cash_i++;
							}

							//next step
							if(cash_i == 3){
								//confirm()
								mainBox.dispose();

								//withdraw
								if(t_mode == MODE_WITHDRAW){
									String str = new String(input_cash, 0, input_cash.length);
									int amount = Integer.parseInt(str);
									//만 원 단위
									if(withdrawType == 0){
										amount = amount * 10000;
										System.out.println("withdraw amount : "+amount);
									}
									//10$ 단위
									else{
										amount = amount * 10;
										System.out.println("withdraw amount : "+amount);
									}

									amount = atm.enterAmount(amount);

									//출금 성공
									if(amount >= 0){
										//한국 계좌
										if(mode == MODE_KOR){
											printReceipt(amount, 1000, atm.getBalance());
										}
										else{
											printReceipt(amount, 1, atm.getBalance());
										}
									}//출금 실패
									//ATM 현금 부족
									else if(amount == -1){
										if(mode == MODE_KOR){
											canceled("ATM 기기의 현금이 부족합니다.");
										}
										else{
											canceled("Not enough cash in ATM");
										}
									}
									//계좌 잔고 부족
									else if(amount == -2){
										if(mode == MODE_KOR){
											canceled("계좌 잔액이 부족합니다.");
										}
										else{
											canceled("Not enough account balance");
										}
									}
									//ㄴㄴ 이러지말자
									else if(amount == -999){
										canceled("LOLOLLLL");
									}
									else if(amount == -9999){
										canceled("!!!!!!!!!");
									}
									else{
										canceled("????????");
									}
								}
								//transfer
								else if(t_mode == MODE_TRANSFER){
									mainBox.dispose();

									String str = new String(input_cash, 0, input_cash.length);
									int amount = Integer.parseInt(str);

									//한국 계좌
									if(withdrawType == 0) {
										amount = amount * 10000;
										System.out.println("transfer amount : "+amount);
									}//해외 계좌
									else{
										amount = amount * 10;
										System.out.println("transfer amount : "+amount);
									}

									amount = atm.enterAmount(amount);

									//송금 성공
									if(amount > 0){
										//한국 계좌
										if(mode == MODE_KOR){
											printReceipt(amount, 1000, atm.getBalance());
										}
										else{
											printReceipt(amount, 1, atm.getBalance());
										}
									}
									//송금 실패
									//계좌 잔고 부족
									else if(amount == -2){
										if(mode == MODE_KOR){
											canceled("계좌 잔액이 부족합니다.");
										}
										else{
											canceled("Not enough account balance");
										}
									}
									//ㄴㄴ 이러지말자
									else{
										canceled("impossibleLOLOLLLL");
									}

								}
								else{
									//WTF?
									System.out.println("Error : in inputAmount");
								}


							}


							//print pressed amount
							for(int j = 0; j < cash_i; j++){
								ptn_s += input_cash[j] + " ";
							}
							for(int j = 3; j > cash_i; j--){
								ptn_s += "_" + " ";
							}

							ptn.setText(ptn_s);
						}
					}
			);

			//Button add on panel
			input_p.add(btn[i-1]);
		}

		//0, BS, CR set
		JButton zero, backSpace, clearInput;
		zero = new JButton("0");
		backSpace = new JButton("<-");
		clearInput = new JButton("CLEAR");


		//button add ActionFunction
		zero.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";

						//input cash
						if(cash_i < 3){
							input_cash[cash_i] = Character.forDigit(0, 10);
							for(int j = 0; j < input_cash.length; j++){
								System.out.print(input_cash[j]);
							}
							cash_i++;
						}

						//next step
						if(cash_i == 3){
							//confirm()
							mainBox.dispose();

							//withdraw
							if(t_mode == MODE_WITHDRAW){
								String str = new String(input_cash, 0, input_cash.length);
								int amount = Integer.parseInt(str);
								//만 원 단위
								if(withdrawType == 0){
									amount = amount * 10000;
								}
								//10$ 단위
								else{
									amount = amount * 10;
								}

								amount = atm.enterAmount(amount);
								System.out.println("withdraw amount : "+amount);


								//출금 성공
								if(amount >= 0){
									//한국 계좌
									if(mode == MODE_KOR){
										printReceipt(amount, 1000, atm.getBalance());
									}
									else{
										printReceipt(amount, 1, atm.getBalance());
									}
								}//출금 실패
								//ATM 현금 부족
								else if(amount == -1){
									if(mode == MODE_KOR){
										canceled("ATM 기기의 현금이 부족합니다.");
									}
									else{
										canceled("Not enough cash in ATM");
									}
								}
								//계좌 잔고 부족
								else if(amount == -2){
									if(mode == MODE_KOR){
										canceled("계좌 잔액이 부족합니다.");
									}
									else{
										canceled("Not enough account balance");
									}
								}
								//ㄴㄴ 이러지말자
								else if(amount == -999){
									canceled("impossibleLOLOLLLL");
								}
								else if(amount == -9999){
									canceled("TTTQQQQQQQQ");
								}
								else{
									canceled("????????");
								}
							}
							//transfer
							else if(t_mode == MODE_TRANSFER){
								mainBox.dispose();

								String str = new String(input_cash, 0, input_cash.length);
								int amount = Integer.parseInt(str);

								//한국 계좌
								if(withdrawType == 0) {
									amount = amount * 10000;
								}//해외 계좌
								else{
									amount = amount * 10;
								}

								amount = atm.enterAmount(amount);
								System.out.println("transfer amount : "+amount);

								//송금 성공
								if(amount > 0){
									//한국 계좌
									if(mode == MODE_KOR){
										printReceipt(amount, 1000, atm.getBalance());
									}
									else{
										printReceipt(amount, 1, atm.getBalance());
									}
								}
								//송금 실패
								//계좌 잔고 부족
								else if(amount == -2){
									if(mode == MODE_KOR){
										canceled("계좌 잔액이 부족합니다.");
									}
									else{
										canceled("Not enough account balance");
									}
								}
								//ㄴㄴ 이러지말자
								else{
									canceled("impossibleLOLOLLLL");
								}

							}
							else{
								//WTF?
								System.out.println("Error : in inputAmount");
							}


						}


						//print pressed amount
						for(int j = 0; j < cash_i; j++){
							ptn_s += input_cash[j] + " ";
						}
						for(int j = 3; j > cash_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		backSpace.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";
						//exist password input -> remove  else : do Nothing.
						if(cash_i > 0){
							cash_i -= 1;
							input_cash[cash_i] = '_';
						}


						//print pressed amount
						for(int j = 0; j < cash_i; j++){
							ptn_s += input_cash[j] + " ";
						}
						for(int j = 3; j > cash_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		clearInput.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						//reset
						String ptn_s = "";
						cash_i = 0;

						input_cash[0] = '_';
						input_cash[1] = '_';
						input_cash[2] = '_';

						//print pressed amount
						for(int j = 0; j < cash_i; j++){
							ptn_s += input_cash[j] + " ";
						}
						for(int j = 3; j > cash_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);
		//0, BS, CR add on Panel
		input_p.add(backSpace);
		input_p.add(zero);
		input_p.add(clearInput);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}
			}
		});
		input_p.add(cancel);

		//enter Button
		JButton enter = new JButton();
		if(mode == MODE_KOR){
			enter.setText("확인");
		}
		else{
			enter.setText("Enter");
		}
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String ptn_s = "";

				//next step
				if(cash_i > 0){
					//confirm()
					mainBox.dispose();

					//withdraw
					if(t_mode == MODE_WITHDRAW){
						String str = new String(input_cash, 0, cash_i);
						int amount = Integer.parseInt(str);
						//만 원 단위
						if(withdrawType == 0){
							amount = amount * 10000;
						}
						//10$ 단위
						else{
							amount = amount * 10;
						}

						amount = atm.enterAmount(amount);
						System.out.println("withdraw amount : "+amount);

						//출금 성공
						if(amount >= 0){
							//한국 계좌
							if(mode == MODE_KOR){
								printReceipt(amount, 1000, atm.getBalance());
							}
							else{
								printReceipt(amount, 1, atm.getBalance());
							}
						}//출금 실패
						//ATM 현금 부족
						else if(amount == -1){
							if(mode == MODE_KOR){
								canceled("ATM 기기의 현금이 부족합니다.");
							}
							else{
								canceled("Not enough cash in ATM");
							}
						}
						//계좌 잔고 부족
						else if(amount == -2){
							if(mode == MODE_KOR){
								canceled("계좌 잔액이 부족합니다.");
							}
							else{
								canceled("Not enough account balance");
							}
						}
						//ㄴㄴ 이러지말자
						else if(amount == -999){
							canceled("impossibleLOLOLLLL");
						}
						else if(amount == -9999){
							canceled("TTTQQQQQQQQ");
						}
						else{
							canceled("????????");
						}
					}
					//transfer
					else if(t_mode == MODE_TRANSFER){
						mainBox.dispose();

						String str = new String(input_cash, 0, cash_i);

						int amount = Integer.parseInt(str);


						//한국 계좌
						if(withdrawType == 0) {
							amount = amount * 10000;
						}//해외 계좌
						else{
							amount = amount * 10;
						}

						amount = atm.enterAmount(amount);
						System.out.println("transfer amount : "+amount);

						//송금 성공
						if(amount > 0){
							//한국 계좌
							if(mode == MODE_KOR){
								printReceipt(amount, 1000, atm.getBalance());
							}
							else{
								printReceipt(amount, 1, atm.getBalance());
							}
						}
						//송금 실패
						//계좌 잔고 부족
						else if(amount == -2){
							if(mode == MODE_KOR){
								canceled("계좌 잔액이 부족합니다.");
							}
							else{
								canceled("Not enough account balance");
							}
						}
						//ㄴㄴ 이러지말자
						else{
							canceled("impossibleLOLOLLLL");
						}

					}
					else{
						//WTF?
						System.out.println("Error : in inputAmount");
					}


				}


				//print pressed amount
				for(int j = 0; j < cash_i; j++){
					ptn_s += input_cash[j] + " ";
				}
				for(int j = 3; j > cash_i; j--){
					ptn_s += "_" + " ";
				}

				ptn.setText(ptn_s);
			}
		});
		input_p.add(enter);

		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(input_p);
		//Frame visible set
		mainBox.setVisible(true);
	}

/*
 *	level 5 - transfer
 */
	//level 5.2 - inputTransfer : // 수신은행 수신계좌
	public void inputTransfer(){

		input_id[0] = '_';
		input_id[1] = '_';
		input_id[2] = '_';
		input_id[3] = '_';
		id_i = 0;

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(2, 1,10,10));


		//Panel set
		JPanel notice_p = new JPanel();
		JPanel select_p = new JPanel();
		JPanel button_p = new JPanel();

		//Panel Layout set
		notice_p.setLayout(new GridLayout(2, 2, 10, 10));
		select_p.setLayout(new GridLayout(2, 2, 10, 10));
		button_p.setLayout(new GridLayout(4, 3, 10, 10));

		//Label set
		JLabel notice_b, notice_a;		//bank & AccountID
		JLabel ptn_b, ptn_a;				//input bank & accountID
		ptn_b = new JLabel("kb");
		ptn_a = new JLabel("account id");

		if(mode == MODE_KOR){
			notice_b = new JLabel("수신 계좌 은행");
			notice_a = new JLabel("수신 계좌 번호");
		}
		else{
			notice_b = new JLabel("Receiver Bank");
			notice_a = new JLabel("Receiver Account ID");
		}

		//Label add on Panel
		notice_p.add(notice_b);
		notice_p.add(notice_a);
		notice_p.add(ptn_b);
		notice_p.add(ptn_a);


		//ComboBox set
		String[] boxMenu = {"kb", "shinhan", "citi", "bankofamerica"};
		JComboBox bank = new JComboBox(boxMenu);
		bank.setSelectedIndex(0);
		bank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox temp = (JComboBox) e.getSource();
				String str = (String)temp.getSelectedItem();
				ptn_b.setText(str);
			}
		});

		//ComboBox add on Panel
		select_p.add(bank);

		//Button set
		JButton [] btn = new JButton[9];

		//1~9 pwd Buttons set & add on Panel
		for(int i = 1; i < 10; i++){
			btn[i-1] = new JButton(String.valueOf(i));

			int finalI = i;

			//button add ActionFunction
			btn[i-1].addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String ptn_s = "";

							//input cash
							if(id_i < 4){
								input_id[id_i] = Character.forDigit(finalI, 10);
								for(int j = 0; j < input_id.length; j++){
									System.out.print(input_id[j]);
								}
								id_i++;
							}

							//next step
							if(id_i == 4){
								//confirm()
								String bank = ptn_b.getText();
								System.out.println(bank);
								String id = new String(input_id, 0, input_id.length);

								// destAccount check
								dest_name = atm.destAccount(bank, Integer.parseInt(id));
								if(dest_name != null){
									mainBox.dispose();

									if(mode == MODE_KOR){
										inputAmount(0);
									}
									else{
										inputAmount(1);
									}

								}//no exist
								else{
									if(mode == MODE_KOR){
										canceled("해당 계좌가 존재하지 않습니다.");
									}
									else{
										canceled("Wrong Account (bank or id)");
									}
								}
							}


							//print pressed amount
							for(int j = 0; j < id_i; j++){
								ptn_s += input_id[j] + " ";
							}
							for(int j = 4; j > id_i; j--){
								ptn_s += "_" + " ";
							}

							ptn_a.setText(ptn_s);
						}
					}
			);

			//Button add on panel
			button_p.add(btn[i-1]);
		}

		//0, BS, CR set
		JButton zero, backSpace, clearInput;
		zero = new JButton("0");
		backSpace = new JButton("<-");
		clearInput = new JButton("CLEAR");


		//button add ActionFunction
		zero.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";

						//input password
						if(id_i < 4){
							input_id[id_i] = Character.forDigit(0, 10);
							for(int j = 0; j < input_id.length; j++){
								System.out.print(input_id[j]);
							}
							id_i++;
						}

						//next step
						if(id_i == 4){
							//confirm()
							String bank = ptn_b.getText();
							String id = new String(input_id, 0, input_id.length);

							// destAccount check
							dest_name = atm.destAccount(bank, Integer.parseInt(id));

							if(dest_name != null){
								mainBox.dispose();

								if(mode == MODE_KOR){
									inputAmount(0);
								}
								else{
									inputAmount(1);
								}

							}//no exist
							else{
								if(mode == MODE_KOR){
									canceled("해당 계좌가 존재하지 않습니다.");
								}
								else{
									canceled("Wrong Account (bank or id)");
								}
							}
						}

						//print pressed amount
						for(int j = 0; j < id_i; j++){
							ptn_s += input_id[j] + " ";
						}
						for(int j = 4; j > id_i; j--){
							ptn_s += "_" + " ";
						}

						ptn_a.setText(ptn_s);
					}
				}
		);

		backSpace.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";
						//exist password input -> remove  else : do Nothing.
						if(id_i > 0){
							id_i -= 1;
							input_id[id_i] = '_';
						}


						//print pressed amount
						for(int j = 0; j < id_i; j++){
							ptn_s += input_id[j] + " ";
						}
						for(int j = 4; j > id_i; j--){
							ptn_s += "_" + " ";
						}

						ptn_a.setText(ptn_s);
					}
				}
		);

		clearInput.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						//reset
						String ptn_s = "";
						id_i = 0;

						input_id[0] = '_';
						input_id[1] = '_';
						input_id[2] = '_';
						input_id[3] = '_';

						//print pressed amount
						for(int j = 0; j < id_i; j++){
							ptn_s += input_id[j] + " ";
						}
						for(int j = 4; j > id_i; j--){
							ptn_s += "_" + " ";
						}

						ptn_a.setText(ptn_s);
					}
				}
		);

		//0, BS, CR add on Panel
		button_p.add(backSpace);
		button_p.add(zero);
		button_p.add(clearInput);

		select_p.add(button_p);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}
			}
		});
		select_p.add(cancel);

		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(select_p);

		//Frame visible set
		mainBox.setVisible(true);
	}

/*
 * level 6 - issue traffic card
 */
	//level 6.2 - inputDateRange //
	public void inputDateRange(){
		date_i = 0;
		input_date[0] = '_';
		input_date[1] = '_';

		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(1, 2,10,10));

		//Panel set
		JPanel notice_p = new JPanel();
		JPanel button_p = new JPanel();

		//Panel Layout set
		notice_p.setLayout(new FlowLayout());
		button_p.setLayout(new GridLayout(5, 3, 10, 10));

		//Label set
		JLabel notice_L, ptn, notice_R;

		if(mode == MODE_KOR ){
			notice_L = new JLabel("사용 기한(1~99) :");
			ptn = new JLabel("_ _ ");
			notice_R = new JLabel( " 일 (ex : 오늘 하루는 1일)");
		}
		else{
			notice_L = new JLabel("use dateRange(1~99)");
			ptn = new JLabel("_ _ ");
			notice_R = new JLabel( " day (ex : just one day is 1 day)");
		}

		//Label add on Panel
		notice_p.add(notice_L);
		notice_p.add(ptn);
		notice_p.add(notice_R);

		//Button set
		JButton [] btn = new JButton[9];

		//1~9 pwd Buttons set & add on Panel
		for(int i = 1; i < 10; i++){
			btn[i-1] = new JButton(String.valueOf(i));

			int finalI = i;

			//button add ActionFunction
			btn[i-1].addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String ptn_s = "";

							//input cash
							if(date_i < 2){
								input_date[date_i] = Character.forDigit(finalI, 10);
								for(int j = 0; j < input_date.length; j++){
									System.out.print(input_date[j]);
								}
								date_i++;
							}

							//next step
							if(date_i == 2){
								String str = new String(input_date, 0, input_date.length);

								mainBox.dispose();
								//
								if(atm.setDataRange(Integer.parseInt(str))){
									agreement();
								}
								//card 부족
								else{
									if(mode == MODE_KOR){
										canceled("교통 카드를 발급할 수 없습니다. 0일을 선택하셨거나, ATM기기에 카드가 부족합니다.");
									}
									else{
										canceled("Can't issue Traffic Card. 0 day or not enough traffic Card in ATM ");
									}
								}

							}


							//print pressed amount
							for(int j = 0; j < date_i; j++){
								ptn_s += input_date[j] + " ";
							}
							for(int j = 2; j > date_i; j--){
								ptn_s += "_" + " ";
							}

							ptn.setText(ptn_s);
						}
					}
			);

			//Button add on panel
			button_p.add(btn[i-1]);
		}

		//0, BS, CR set
		JButton zero, backSpace, clearInput;
		zero = new JButton("0");
		backSpace = new JButton("<-");
		clearInput = new JButton("CLEAR");


		//button add ActionFunction
		zero.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";

						//input password
						if(date_i < 2){
							input_date[date_i] = Character.forDigit(0, 10);
							for(int j = 0; j < input_date.length; j++){
								System.out.print(input_date[j]);
							}
							date_i++;
						}

						//next step
						if(date_i == 2){
							String str = new String(input_date, 0, input_date.length);

							mainBox.dispose();
							if(atm.setDataRange(Integer.parseInt(str))){
								agreement();
							}
							else{
								if(mode == MODE_KOR){
									canceled("ATM 기기의 소지 Traffic Card가 부족합니다.");
								}
								else{
									canceled("Not enough Traffic Card in ATM");
								}
							}

						}

						//print pressed amount
						for(int j = 0; j < date_i; j++){
							ptn_s += input_date[j] + " ";
						}
						for(int j = 2; j > date_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		backSpace.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String ptn_s = "";
						//exist password input -> remove  else : do Nothing.
						if(date_i > 0){
							date_i -= 1;
							input_date[date_i] = '_';
						}


						//print pressed amount
						for(int j = 0; j < date_i; j++){
							ptn_s += input_date[j] + " ";
						}
						for(int j = 2; j > date_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		clearInput.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						//reset
						String ptn_s = "";
						date_i = 0;

						input_date[0] = '_';
						input_date[1] = '_';

						//print pressed amount
						for(int j = 0; j < date_i; j++){
							ptn_s += input_date[j] + " ";
						}
						for(int j = 2; j > date_i; j--){
							ptn_s += "_" + " ";
						}

						ptn.setText(ptn_s);
					}
				}
		);

		//0, BS, CR add on Panel
		button_p.add(backSpace);
		button_p.add(zero);
		button_p.add(clearInput);

		//cancel button
		JButton cancel = new JButton();
		if(mode == MODE_KOR){
			cancel.setText("거래 취소");
		}
		else{
			cancel.setText("Cancel Transaction");
		}
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode == MODE_KOR){
					canceled("요청");
				}
				else{
					canceled("asked");
				}
			}
		});
		button_p.add(cancel);

		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(button_p);

		//Frame visible set
		mainBox.setVisible(true);
	}

	//level 6.3 - agreement      //
	public void agreement(){
		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(2, 1,10,10));
		//Panel set
		JPanel notice_p = new JPanel();
		JPanel button_p = new JPanel();
		//Panel Layout set
		notice_p.setLayout(new GridLayout(1, 1, 10, 10));
		button_p.setLayout(new FlowLayout());


		//TextField set
		String note;
		if(mode == MODE_KOR){
			note = "카드 발급 비용 : 3000원(3$) 동의하시겠습니까? \n ....... 약관 .......";
		}
		else{
			note = "Issue charge : 3000원(3$) do you agree? \n ....... Agreement .......";
		}
		JTextField agreementNote = new JTextField(note);
		agreementNote.setEnabled(false);

		//TF add on Panel
		notice_p.add(agreementNote);

		//Button set
		JButton btn_y, btn_n;
		if(mode == MODE_KOR){
			btn_y = new JButton("동의");
			btn_n = new JButton("동의안함");
		}
		else{
			btn_y = new JButton("Agree");
			btn_n = new JButton("Disagree");
		}

		//Button add Action
		btn_y.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainBox.dispose();
				if(atm.agreement()){
					//한국 계좌
					if(mode == MODE_KOR){
						printReceipt(3000, 0, atm.getBalance());
					}
					//해외 계좌
					else{
						printReceipt(3, 0, atm.getBalance());
					}
				}
				//잔액 부족
				else{
					if(mode == MODE_KOR){
						canceled("계좌의 잔액이 부족합니다.");
					}
					else{
						canceled("Not enough Account Balance");
					}
				}

			}
		});
		btn_n.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainBox.dispose();
				if(mode == MODE_KOR){
					canceled("거절되었습니다");
				}
				else{
					canceled("Rejeceted");
				}
			}
		});

		//Button add on Panel
		button_p.add(btn_y);
		button_p.add(btn_n);

		//Panel add on Frame
		mainBox.add(notice_p);
		mainBox.add(button_p);
		//Frame visible set
		mainBox.setVisible(true);
	}

	//Exceptional Case
	public void returnCard(){
		//Frame set
		JFrame rtnCard = new JFrame("return Card");
		rtnCard.setSize(200, 100);

		//Panel set
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1, 10, 10));

		//Label set
		JLabel note = new JLabel("카드가 반환되었습니당 :)");

		//add
		panel.add(note);
		rtnCard.add(panel);

		//Frame visible set
		rtnCard.setVisible(true);
	}

	public void getReceipt(int _amount, int _fee, int _balance){
		//Frame set
		JFrame rtnCard = new JFrame("return Card");
		rtnCard.setSize(400, 200);

		//Panel set
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1, 10, 10));

		String result1, result2, result3;

		//Label set
		if(mode == MODE_KOR){
			result1 = "거 래 금 액 : " +  Integer.toString(_amount) + " 원";
			result2 = "수  수  료 : " +  Integer.toString(_fee) + " 원";
			result3	= "거래 후 잔액 : " +  Integer.toString(_balance) + " 원";
		}
		else{
			result1 = "Transaction Amount : " +  Integer.toString(_amount) + " $";
			result2 = "Fee : " +  Integer.toString(_fee) + " $";
			result3	= "Balance : " +  Integer.toString(_balance) + " $";
		}


		JLabel note1 = new JLabel(result1);
		JLabel note2 = new JLabel(result2);
		JLabel note3 = new JLabel(result3);

		//add
		panel.add(note1);
		panel.add(note2);
		panel.add(note3);
		rtnCard.add(panel);

		//Frame visible set
		rtnCard.setVisible(true);
	}

	public void canceled(String reason){
		mainBox.dispose();
		//Main Frame renew
		{
			//main Panel
			mainBox = new JFrame("Global ATM");
			//Panel Setting
			mainBox.setSize(600,600);
			mainBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//Frame Layout set
		mainBox.setLayout(new GridLayout(2, 1,10,10));

		//panel set
		JPanel notice_p = new JPanel();
		JPanel button_p = new JPanel();

		//panel layout set
		notice_p.setLayout(new GridLayout(1, 1, 10, 10));
		button_p.setLayout(new GridLayout(1, 1, 10, 10));

		//label set
		JLabel notice;
		if(mode == MODE_KOR){
			notice = new JLabel(reason + ") 거래가 취소 되었습니다 :(");
		}
		else{
			notice = new JLabel(reason + ") Canceled Transaction :0");
		}
		//label add on panel
		notice_p.add(notice);

		//Button set
		JButton btn;
		if(mode == MODE_KOR){
			btn = new JButton("메인 화면으로");
		}
		else{
			btn = new JButton("Go to main");
		}

		//button action
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainBox.dispose();
				readyReadCard();
			}
		});

		//button add on panel
		button_p.add(btn);

		//panel add on frame
		mainBox.add(notice_p);
		mainBox.add(button_p);

		//visible
		mainBox.setVisible(true);

		returnCard();
	}

	public void managementGUI(){
		//sub Box : Management GUI
		//Frame set
		subBox = new JFrame("Global ATM_management");
		//Frame Setting
		subBox.setSize(400,200);
		subBox.setLocation(1400, 0);
		//Frame Layout set
		subBox.setLayout(new GridLayout(1, 1,10,10));

		int admin_id = this.atm.getAdminID();

		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1,10,10));

		//Components
		JLabel notice = new JLabel("admin ID");
		JTextField input_adminID = new JTextField(Integer.toString(admin_id));
		JButton btn = new JButton("Access");

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//admin access
				if(input_adminID.getText().equals(Integer.toString(admin_id))){
					if(Desktop.isDesktopSupported()){
						try {
							if(path){
								Desktop.getDesktop().open(new File(path_1));
							}else{
								Desktop.getDesktop().open(new File(path_2));
							}

							end();
							subBox.dispose();
						} catch (IOException e1) {
							System.out.println("IOException File open. in Desktop");
							//mistake! path setting
							if(!path) {
								System.out.println("mistake! path setting");

								path = !path;

								try{
									if(path){
										Desktop.getDesktop().open(new File(path_1));
									}else{
										Desktop.getDesktop().open(new File(path_2));
									}

									end();
									subBox.dispose();
								}catch (Exception e_e){
									System.out.println("gg..!!!!!!!!!!!");
								}
							}
						}
					}
				}
				//can't access
				else{
					notice.setText("Wrong ID");
				}
			}
		});

		//components add on panel
		panel.add(notice);
		panel.add(input_adminID);
		panel.add(btn);

		//panel add on frame
		subBox.add(panel);
		subBox.setVisible(true);

	}

	public void end(){
		//Frame
		JFrame push = new JFrame("Global ATM_push");
		push.setSize(200, 100);
		push.setLocation(1400,0);
		push.setLayout(new GridLayout(1, 1, 10, 10));
		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1, 10, 10));
		//Components
		JLabel notice = new JLabel("Management.txt 수정 후 push를 눌려주세요 :)");
		JButton btn = new JButton("push");

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.end();
				push.dispose();
				managementGUI();
			}
		});

		//add on panel
		panel.add(notice);
		panel.add(btn);
		//add on frame
		push.add(panel);

		push.setVisible(true);
	}
}