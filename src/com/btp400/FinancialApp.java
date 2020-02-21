package com.btp400;

import java.util.Arrays;
import java.util.Scanner;
import com.seneca.accounts.*;
import com.seneca.business.*;


public class FinancialApp {

    public static void loadBank( Bank bank ){
        bank.addAccount(new Chequing("John Doe","1234C",123.45,0.25,3));
        bank.addAccount(new Chequing("Mary Ryan","5678C",678.90,0.12,3));
        //todo:Add GIC accounts
    }
    public static void displayMenu( String bankName ){
        StringBuffer title = new StringBuffer("\nWelcome to").append(bankName).append(" Bank!");
        StringBuffer choice1 = new StringBuffer("1. Open an account.");
        StringBuffer choice2 = new StringBuffer("2. Close an account.");
        StringBuffer choice3 = new StringBuffer("3. Deposit money.");
        StringBuffer choice4 = new StringBuffer("4. Withdraw money.");
        StringBuffer choice5 = new StringBuffer("5. Display accounts.");
        StringBuffer choice6 = new StringBuffer("6. Display a tax statement.");
        StringBuffer choice7 = new StringBuffer("7. Exit.");

        System.out.println(title);
        System.out.println(choice1);
        System.out.println(choice2);
        System.out.println(choice3);
        System.out.println(choice4);
        System.out.println(choice5);
        System.out.println(choice6);
        System.out.println(choice7);
    }
    public static int menuChoice( ){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your choice> ");
        return s.nextInt();
    }

    public static void displayAccount( Account account){//todo:What's the use of this method?
        System.out.println("This is the details for Account number: "+account.getAccountNumber());
        System.out.println(account);
    }//todo: What is the use of this?

    public static Account openAcc(){//todo: add the option of exiting the function prematurely
        Scanner in= new Scanner(System.in);
        Account newAccount = null;

        boolean valid_args = false;

        while(!valid_args){
            System.out.println("Please enter the account type(CHQ/GIC)> ");
            String acc_type = in.nextLine();

            System.out.println("Please enter account information at one line");

            if(acc_type.equals("CHQ")||acc_type.equals("chq")){
                System.out.println("Format: Name;Account Number; Starting Balance; Service Charge; Max number of Transactions");
                System.out.println("ex. (John Doe; 1234; 567.89; 0.25; 3)");

                String chq_valuesString = in.nextLine();
                String[] chq_args = chq_valuesString.split(";");

                if(chq_args.length!= 5){
                    System.out.println("Invalid input. Please follow the format shown on screen");
                }else{
                    //this is vulnerable to type mismatch
                    newAccount = new Chequing(chq_args[0],chq_args[1].trim(),Double.parseDouble(chq_args[2].trim()),Double.parseDouble(chq_args[3].trim()),Integer.parseInt(chq_args[4].trim())   );
                    valid_args=true;

                }

            }else if (acc_type.equals("GIC")|| acc_type.equals("gic")){//todo:Include the creation of GIC accounts
                System.out.println("This is GIC account type input");
                valid_args = true;
            }else{
                System.out.println("invalid account type");
            }
        }

        return newAccount;


    }

    public static void closeAcc(Bank bank){//can expand to search by name
        Scanner in= new Scanner(System.in);

        System.out.println("Please enter the Account Number: ");
        String delAccNum= in.nextLine();
        System.out.println("Confirm delete of account with the number: "+delAccNum+" (Y/N)"); //do I have to use StringBuffer here?
        String res = in.nextLine();

        boolean valid_res = false;
        while(!valid_res){

            switch (res){
                case "Y":
                case"y":
                    Account a = bank.removeAccount(delAccNum);
                    if(a != null){
                        System.out.println("Account successfully deleted");
                    }else{
                        System.out.println("Account not found");
                    }
                    valid_res = true;
                    break;

                case "N":
                case "n":
                    valid_res=true;
                    System.out.println("Delete cancelled");
                    break;
                default:
                    System.out.println( "Invalid response, please enter \"Y\" or \"N\" ");


            }

        }


    }

    public static void depositMoney(Bank bank){//todo:Figure out how to access a specific instance of Account in bank to deposit into
        //make a copy of the bankarray
        //ask user for account num
        //deposit accordingly



        System.out.println("This is the depositMoney() method");
    }

    public static void withdrawMoney(Bank bank){
        System.out.println("This is the withdrawMoney() method");
    }

    public static void displayAccountChoice(Bank bank){
        Scanner in = new Scanner(System.in);
        boolean valid = false;

        while(!valid){
            System.out.println("Please choose one of the following options: ");
            System.out.println("a) display all accounts with the same account name");
            System.out.println("b) display all accounts with the same final balance");
            System.out.println("c) display all accounts opened at the bank");
            String option = in.nextLine();

            switch (option) {
                case "a":
                case "A":
                    System.out.println("Please enter the name to search by: ");
                    String nameSearch = in.nextLine();
                    System.out.println(Arrays.toString(bank.searchByAccountName(nameSearch))); //todo: Fix how this is outputted
                    valid = true;
                    break;
                case "b":
                case "B":
                    System.out.println("Please enter the balance to search by: ");
                    double balance = in.nextDouble();
                    System.out.println(Arrays.toString(bank.searchByBalance(balance)));
                    valid = true;
                    break;
                case "c":
                case "C":
                    System.out.println(Arrays.toString(bank.getAllAccounts()));
                    valid=true;
                    break;
                case "x":
                case "X":
                    System.out.println("Operation cancelled, returning to main menu");
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid response, enter a valid responce (a-b) or \"x\" to exit");
                    break;
            }
        }


    }

    public static void displayTax(){
        System.out.println("This is the displayTax() method");

    }



    public static void main(String[] args) {

        Bank myBank=new Bank();
        loadBank(myBank);

        int choice = 0;

        while (choice != 7){
            displayMenu(myBank.getBankName());
            choice=menuChoice();

            switch (choice){

                case 1: //Open an account - add GIC
                    if(myBank.addAccount(openAcc())){
                        System.out.println("Account successfully included");
                    }else{
                        System.out.println("Unable to add Account");
                    }
                    break;

                case 2: //Close an account - Complete
                    closeAcc(myBank);
                    break;
                case 3://Deposit money
                  depositMoney(myBank);
                    break;
                case 4://Withdraw money
                    withdrawMoney(myBank);
                    break;
                case 5://Display accounts - Formatting needs work
                    displayAccountChoice(myBank);
                    break;
                case 6://Display a tax statement
                    displayTax();
                    break;
                case 7://Exit
                    break;
                default:
                    System.out.println("invalid choice");
                    choice= menuChoice();

            }


        }
        System.out.println("Thank you for using our app!");

    }






}
