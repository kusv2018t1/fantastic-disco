package ATM;

import Item.TrafficCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ATM {

    private File bootATM, path;
    private FileReader fr;
    private BufferedReader br;
    private Bank[] bank = new Bank[4];
    private TrafficCard[] tCard;
    private int[] cashAmount = new int[4];
    private int trafficCardAmount;
    private int receiptAmount ;
    private int usingAccountID;
    private int usingBankID;
    //없어진 변수 들
/*    private boolean confirmedItem;
    private boolean confirmedPwd;*/
    private int destTransAccountID;
    private int transactionAmount = 0;
    private int languageMode;
   //**admin ID입력
    private int ATMadminID;
    //**환율
    private int rate;
    //**bill에 대한 기본 저장 값
    //한국돈은 1000 5000 10000 50000 외국돈은 10 20 100
    private String[] billcode = new String[7];

    //** 출금 원/달러인지 확인
    private int ATMnation;

    public static void main(String[] args){
        ATM atm = new ATM();
    }

    public void initATM()
    {

        bootATM();
        bankDataDownload();
    }



    public int readItem(int itemType , int itemID, int bankID ,int accountID )
   {
       int ok ;
       ok= bank[bankID].validCheck(itemType,itemID , accountID);

       //lagnuage = 1 은 외국계좌
       if(bankID>2)
       {
           languageMode = 1;
       }
       if(ok>=1)
       {
           usingBankID = itemID;
           usingAccountID =accountID;
       }
       return ok;
   }

   public void selectService(int service)
   {
       transactionAmount = service;

        switch(service)
        {
            //계좌조회
            case 1 :
                break ;
            //입금
            case 2 :
                break;
            //출금
            case 3 :
                break;
            //송금
            case 4 :
                break;
            //교통카드 발급
            case 5 :
                break;
        }

        return;
   }

   public int selectNation(int nation)
   {
       ATMnation = nation;
       //gui
       System.out.println("Nation :" + nation );
        //nation 1 = 달러 nation 0 = 한화
       return nation;
   }

   public int confirm(int itemType , int pwd)
   {
        int accept;
        accept  = bank[usingBankID].confirm(itemType , pwd , usingAccountID);

        if(accept==1)
            return transactionAmount;
        else return 0;

   }

   public int insertCash(String[] bill)
   {
       int money = 0 ;

       for(int j = 0;j<bill.length;j++)
       {
           for(int i =0; i< 7;i++) {
               if (billcode[i].equals(bill[j])) {
                   if (languageMode == 0) {
                       switch (i) {
                           case 0:
                               money += 1000;
                               break;
                           case 1:
                               money += 5000;
                               break;
                           case 2: //1만원권
                               if(cashAmount[0]<200)
                               {
                                   money += 10000;
                                   cashAmount[0]++;
                               }
                               else
                                   return 0;
                               break;
                           case 3: //5만원권
                               if(cashAmount[1]<200)
                               {
                                   money += 50000;
                                   cashAmount[1]++;
                               }
                               else return 0;
                               break;
                           case 4: //10달러
                               if(cashAmount[2]<250)
                               {
                                   money += 10*rate;
                                   cashAmount[2]++;
                               }
                               else return 0;
                               break;
                           case 5: //20달러
                               money += 20 * rate;
                               break;
                           case 6: //100달러
                               if(cashAmount[3]<250)
                               {
                                   money += 10*rate;
                                   cashAmount[3]++;
                               }
                               else return 0;
                               break;
                       }
                   } else {
                       switch (i) {
                           case 0:
                               money += 1000 / rate;
                               break;
                           case 1:
                               money += 5000 / rate;
                               break;
                           case 2:
                               if(cashAmount[0]<250)
                               {
                                   money += 10000/rate;
                                   cashAmount[0]++;
                               }
                               else return 0;
                               break;
                           case 3:
                               if(cashAmount[1]<250)
                               {
                                   money += 50000/rate;
                                   cashAmount[1]++;
                               }
                               else return 0;
                               break;
                           case 4:
                               if(cashAmount[2]<250)
                               {
                                   money += 10;
                                   cashAmount[2]++;
                               }
                               else return 0;
                               break;
                           case 5:
                               money += 20;
                               break;
                           case 6:
                               if(cashAmount[3]<250)
                               {
                                   money += 100;
                                   cashAmount[3]++;
                               }
                               else return 0;
                               break;
                       }
                   }
               }
           }
       }

      int ok;
       ok = bank[usingBankID].deposit(money);

       //
       System.out.print("money :" + money);

       return ok;

   }

    public int enterAmount(int money)
   {

       int ok;
       if(transactionAmount==3)
       {
           if(ATMnation==1)
           {
               if(cashAmount[3]<money/100||cashAmount[2]<(money%100)/10)
                   return 0;
           }
           else if(ATMnation==0)
           {
               if(cashAmount[1]<money/50000||cashAmount[0]<(money%50000)/10000)
                   return 0;
           }
           else return 0;

           ok = bank[usingBankID].withDraw(money,usingAccountID);
       }
       else if(transactionAmount==4)
           ok = bank[usingBankID].Transfer(money,destTransAccountID,usingAccountID);
       else return 0;

       return ok;
   }

   public int printReceipt(boolean wants)
   {
       int balance;
       if(wants)
       {
           balance =bank[usingBankID].getBalance();
       }

       //gui
       receiptAmount--;
       return 0;
   }

   public void setDataRange(int date_range)
   {
       tCard[0].setDateRange(date_range);
       return;
   }

   public int agreement(boolean approval)
   {
       int tcid = tCard[0].getTcid();
       int ok = bank[usingBankID].linkAccount(tcid);
       if(ok==1)
       {
           //교통카드 비용 3000원
           if(languageMode==0)
           {ok = bank[usingBankID].chargeTrafficCard(3000);}
           else  {ok = bank[usingBankID].chargeTrafficCard(3000/rate);}
       }

       if(ok==1)
       {
           trafficCardAmount--;
           tCard[0] = tCard[trafficCardAmount];
           tCard[trafficCardAmount] = null;
       }

       return 1;
   }

   public int destAccount(int bankID , int accountID)
   {
       String name ;
        name = bank[bankID].checkAccount(usingBankID,usingAccountID);
        destTransAccountID = accountID;

        //gui
       System.out.println("name : "+name);

       return 1;
   }

   public void readManagementItem(int adminID)
   {
       if(adminID == ATMadminID)
       {
           //관리자 모드 시작
       }
   }

   private int checkResource()
   {
       for(int i = 0;i<4;i++)
       {
           if(cashAmount[i]<10||cashAmount[i]>200)
           {
               //관리자에게 알람
               return 0;
           }
       }
       if(trafficCardAmount<2||receiptAmount<10)
       {
           //관리자에게 알람
           return 0;
       }

       return 1;
   }


   private int bankDataDownload()
   {
       tCard = new TrafficCard[trafficCardAmount];
       for(int i=0;i<4;i++)
       {
           bank[i] = new Bank();
       }

      for(int i = 0;i<trafficCardAmount;i++)
           tCard[i] = new TrafficCard();

       return 0;
   }

   //**인자 없앰+ void
   private void bootATM()
   {

       int cash =0,receipt=0 ,tcard=0 ;


       billcode[0] = "thousandwon";
       billcode[1] = "fivdthousandwon";
       billcode[2] = "tenthousandwon";
       billcode[3] = "fiftythousandwon";
       billcode[4] = "tendollor";
       billcode[5] = "twentydollor";
       billcode[6] = "ondhundreddollor";

       try
       {
           path = new File("src/ATM");
           bootATM = new File(path.getAbsolutePath() + "/management.txt");
           fr = new FileReader(bootATM);
           br = new BufferedReader(fr);

           String getStr = br.readLine();
           cash = Integer.parseInt(getStr);
           getStr = br.readLine();
           receipt = Integer.parseInt(getStr);
           getStr = br.readLine();
           tcard = Integer.parseInt(getStr);
           getStr = br.readLine();
           ATMadminID = Integer.parseInt(getStr);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }


       rate = 1000;
       cashAmount[0] = cash;
       cashAmount[1] = cash;
       cashAmount[2] = cash;
       cashAmount[3] = cash;
       receiptAmount = receipt;
       trafficCardAmount = tcard;


   }


   //**새 메소드
   public void end()
   {
       bootATM();
   }

}
