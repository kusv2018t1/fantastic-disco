package ATM;

import Item.TrafficCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//전체적으로 바뀐 것은 ok를 모두 boolean type으로 해서 method의 타입이 바뀌었다.

public class ATM {

    private File bootATM, path;
    private FileReader fr;
    private BufferedReader br;
    private Bank[] bank = new Bank[4];
    private TrafficCard tCard;
    private int[] cashAmount = new int[4];
    private int trafficCardAmount;
    private int receiptAmount ;
    private int usingAccountID;
    private int usingBankID;
   /* private int destTransAccountID;*/
    private int transactionAmount = 0;
    private int languageMode;
    private int ATMadminID;
    private int rate;
    //출금 원/달러인지 확인
    private int ATMnation;

    public static void main(String[] args){
        ATM atm = new ATM();
    }

    public void ATM()
    {

        bootATM();
        bankDataDownload();
    }



    public boolean readItem(int itemType, int itemID,String bankID , int accountID)
   {
       //return value  : ok
       boolean ok ;
       //kb(usingBankID 0 ) 한국 은행(한국 0)
       if(bankID.equals("kb"))
       {
           usingBankID = 0;
           languageMode = 0;
       }
       //shinhan (usingBankID 1 ) 한국 은행(한국 0)
       else if(bankID.equals("shinhan"))
       {
           usingBankID = 1;
           languageMode = 0;
       }
       //city (usingBankID 2 ) 외국 은행(미국 1)
       else if(bankID.equals("citi"))
       {
           usingBankID = 2;
           languageMode = 1;
       }
       //bankofAmerican (usingBankID 3) 외국 은행(미국 1)
       else if(bankID.equals("bankofAmerican"))
       {
           usingBankID = 3;
           languageMode = 1;
       }else return false;

       ok= bank[usingBankID].validCheck(itemType,itemID , accountID);

       usingAccountID = accountID;

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

   public int confirm(int pwd)
   {
        if(bank[usingBankID].confirm(pwd))
            return transactionAmount;
        else return 0;

   }

   public boolean insertCash(String[] bill)
   {

       //insertCash를 통해 읽는 총 돈, deposit 하는 값
       int money = 0 ;

       //들어온 bill의 갯수 만큼 돈다.
       for(int i =0;i<bill.length;i++) {
           //bill의 1번쨰 자리가 0이면 한국 돈
           if (bill[i].substring(0, 1).equals("0")) {
               switch (Integer.parseInt(bill[i].substring(1, 3))) {
                   //1000원 bill
                   case 0:
                       money += 1000;
                       break;
                   //5000원 bill
                   case 1:
                       money += 5000;
                       break;
                   //10000원 bill cashAmount[0]- 10000원 양이 250보다 적으면  증가
                   case 2:
                       money += 10000;
                       if (cashAmount[0] < 250)
                           cashAmount[0]++;
                       else return false;//ATM안 돈이 가득 차서 더 못들어간다.
                       break;
                   //50000원  bill cashAmount[1]- 50000원 양이 250보다 적으면  증가
                   case 3:
                       money += 50000;
                       if (cashAmount[1] < 250)
                           cashAmount[1]++;
                       else return false;//ATM안 돈이 가득 차서 더 못들어간다.
                       break;
               }
           }
           //bill의 1번쨰 자리가 1이면 외국돈
           else if (bill[i].substring(0, 1).equals("0")) {
               switch (Integer.parseInt(bill[i].substring(1, 3))) {
                   //10달러 bill cashAmount[2]- 10달러 양이 250보다 적으면  증가
                   case 0:
                       money += 10 * rate;
                       if (cashAmount[2] < 250)
                           cashAmount[2]++;
                       else return false ;//ATM안 돈이 가득 차서 더 못들어간다.
                       break;
                   //20달러 bill
                   case 1:
                       money += 20 * rate;
                       break;
                   //100달러 bill cashAmount[3]- 100달러 양이 250보다 적으면  증가
                   case 2:
                       money += 100 * rate;
                       if (cashAmount[3] < 250)
                           cashAmount[3]++;
                       else return false; //ATM안 돈이 가득 차서 더 못들어간다.
                       break;
               }
           }

       }

       //만약 입금하려는 계좌가 외국계좌일 경우,(languagemode ==1) 환율로 나눠준다.
       if (languageMode == 1)
           money = money / rate;

       //return 값 ok / deposit이 제대로 되었는가.
      boolean ok;
       ok = bank[usingBankID].deposit(money);

       System.out.print("money :" + money);

       return ok;

   }

    public boolean enterAmount(int money)
   {

       boolean ok;
       if(transactionAmount==3)
       {
           if(ATMnation==1)
           {
               if(cashAmount[3]<money/100||cashAmount[2]<(money%100)/10)
                   return false;
           }
           else if(ATMnation==0)
           {
               if(cashAmount[1]<money/50000||cashAmount[0]<(money%50000)/10000)
                   return false;
           }
           else return false;

           ok = bank[usingBankID].withDraw(money);
       }
       else if(transactionAmount==4)
       {
           // money는 송금의 경우 무조건 외국계좌면 달러를 입력하고 한국 계좌이면 한화를 입력한다
           ok = bank[usingBankID].Transfer(money);
       }
       else return false;

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
       tCard = new TrafficCard();
       tCard.setDateRange(date_range);
       return;
   }

   public int agreement(boolean approval)
   {
       int tcid = tCard.getTcid();
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
           //여기서 교통카드 발급 GUI
       }

       return 1;
   }

   public boolean destAccount(String bankID , int accountID)
   {
       int id;
       String name ;
       if(bankID.equals("kb"))
       {
           id= 0;
       }
       //shinhan (usingBankID 1 ) 한국 은행
       else if(bankID.equals("shinhan"))
       {
           id = 1;
       }
       //city (usingBankID 2 ) 외국 은행
       else if(bankID.equals("citi"))
       {
           id = 2;
       }
       //bankofAmerican (usingBankID 3) 외국 은행
       else if(bankID.equals("bankofAmerican"))
       {
           id = 3;
       }else return false;

        name = bank[id].checkAccount(bankID,usingAccountID);

        //gui 이름이 떠야 한다.
       System.out.println("name : "+name);

       return true;
   }

   public void readManagementItem(int adminID)
   {
       if(adminID == ATMadminID)
       {
           //관리자 모드 시작
       }
   }

   private void checkResource()
   {
       for(int i = 0;i<4;i++)
       {
           if(cashAmount[i]<10||cashAmount[i]>200)
           {
               //관리자에게 알람
               return ;
           }
       }
       if(trafficCardAmount<2||receiptAmount<10)
       {
           //관리자에게 알람
           return ;
       }

       return ;
   }


   private int bankDataDownload()
   {
       //bank생성
       //한국 은행 생성
       bank[0] = new Bank("kb");
       bank[1] = new Bank("shinhan");
       //외국 은행 생성
       bank[2] = new Bank("city");
       bank[3] = new Bank("bankofAmerican");
       return 0;
   }

   private void bootATM()
   {
       //txt파일로 부터 ATM안 현금, 교통카드 . 명세표 용지 량을 받아온다.
       try
       {
           path = new File("src/ATM");
           bootATM = new File(path.getAbsolutePath() + "/management.txt");
           fr = new FileReader(bootATM);
           br = new BufferedReader(fr);

           String getStr = br.readLine();
           int cash= Integer.parseInt(getStr);
           cashAmount[0] =cash;
           cashAmount[1] =cash;
           cashAmount[2] =cash;
           cashAmount[3] =cash;
           getStr = br.readLine();
           receiptAmount  = Integer.parseInt(getStr);
           getStr = br.readLine();
           trafficCardAmount = Integer.parseInt(getStr);
           getStr = br.readLine();
           ATMadminID = Integer.parseInt(getStr);
           getStr = br.readLine();
           rate = Integer.parseInt(getStr);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

   public void end()
   {
       //manager가 txt파일을 바꾼다고 생각하고 bootATM을 해서 ATM안 새로운 item갯수를 넣는다.
       bootATM();
   }

}
