// Java project for CIS 18A
// Created by Kevin Nguyen
// This is a simple project for a Gift Card purchase
// Ver 0.0.1

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

class dataStorage{      // save data from user
    private double balance;

    void setBalance(double balance) {   // remember to set the new balance into .txt file
        this.balance = balance;
        saveBalance(this.balance);
    }
    double getBalance(){    // get balance when called
        double scanB;
        Scanner b = null;
        try{
            b = new Scanner(new File("balance.txt"));
        }
        catch(Exception e){
            System.out.println(e);
        }
        scanB = b.nextDouble();
        setBalance(scanB);
        b.close();
        return scanB;
    }
    void saveBalance(double s){     // save current balance to balance.txt
        PrintWriter save = null;
        try {
            save = new PrintWriter("balance.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        save.println(s);
        save.close();
    }
}

// This class record every transaction within one run
class calculator{
    double subtraction(double a, double b){
        double c = a - b;
        return c;
    }
}
class menu extends dataStorage{
    Scanner input = new Scanner(System.in);
    int usrInput;
    long usrCre;
    int counter1 = 0;
    int counter2 = 0;
    int counter3 = 0;
    String usrName;
    boolean access = false;

    // Main menu with options, once user entered a valid phone number
    void mainMenu(){
        calculator cal = new calculator(); // Use this class instead of simple operators
        receipt rec = new receipt();
        String menuC[] = {"\n1. \tAccount Balance", "2. \tDeposit", "3. \tGift Cards", "4. \tPrint Receipt", "0. \tExit\n\n<Choose> "};
        do{
            for(int i = 0; i < 5; i++) {
                System.out.println(menuC[i]); // print out array contents from the main menu
            }
            usrInput = input.nextInt();
            switch(usrInput){
                case 1:     // check your current balance
                    System.out.println("\nYour current balance is $" + getBalance());
                    System.out.println();
                    break;
                case 2:
                    double c;
                    System.out.print("Amount: ");
                    c = input.nextDouble();
                    setBalance(c + getBalance());
                    System.out.println("Successfully Deposited");
                    break;
                case 3: // gift card option
                    do{
                        System.out.println("\n1. \t$25 gift card");
                        System.out.println("2. \t$50 gift card");
                        System.out.println("3. \t$100 gift card");
                        System.out.print("0. \tBack\n\n<Choose> ");
                        usrInput = input.nextInt();

                        if(usrInput == 1){
                            if(getBalance() >= 25) {
                                setBalance(cal.subtraction(getBalance(), 25)); // This's too complicated for a simple subtraction
                                System.out.println("$25 gift card --- Transaction complete!");
                                counter1++;
                            }else System.out.println("INSUFFICIENT FUNDS. PLEASE DEPOSIT BEFORE CONTINUE!");
                        }if(usrInput == 2){
                            if(getBalance() >= 50) {
                                setBalance(cal.subtraction(getBalance(), 50)); // This one as well
                                System.out.println("$50 gift card --- Transaction complete!");
                                counter2++;
                            }else System.out.println("INSUFFICIENT FUNDS. PLEASE DEPOSIT BEFORE CONTINUE!");
                        }if(usrInput == 3){
                            if(getBalance() >= 100) {
                                setBalance(cal.subtraction(getBalance(), 100)); // Screw it, let make a third one
                                System.out.println("$100 gift card --- Transaction complete!");
                                counter3++;
                            }else System.out.println("INSUFFICIENT FUNDS. PLEASE DEPOSIT BEFORE CONTINUE!");
                        }
                    }while(usrInput != 0);
                    saveBalance(getBalance());
                    break;
                case 4: // Print out receipt
                    rec.printOut(counter1, counter2, counter3);
                    break;
                case 0: // exit the loop
                    access = false;
                    break;
            }
        } while (access != false);
    }

    // First menu user will see
    void menu0(){
        // menu loop
        do{
            System.out.println();
            System.out.println("1. \tLogin");
            System.out.println("2. \tRegister");
            System.out.print("0. \tExit\n\n<Choose> ");

            usrInput = input.nextInt();
            switch(usrInput){
                case 1:
                    menu1();
                    break;
                case 2:
                    menu2();
                    break;
            }
        } while (usrInput != 0);
        if(usrInput == 0) System.exit(0);

    }

    // Check for a valid phone number stored in data.txt file
    void menu1(){
        System.out.print("Enter your phone number: "); // input starts with 0 will not register correctly
        long id = input.nextLong();
        Scanner x = null;
        try{
            File tmpDir = new File("data.txt");
            boolean exists = tmpDir.exists();

            if(exists == true) x = new Scanner(new File("data.txt"));
            else{
                System.out.println("NO DATA FOUND!"); // prevent error file not found
                menu0();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        // scan data.txt specific set of values
        usrCre = x.nextLong();
        usrName = x.next();

        // if correct, welcome
        if(id == usrCre) {
            System.out.println("---Welcome, " + usrName + "! ---");
            access = true;
            mainMenu();
        }
        else {
            System.out.println("\n---Phone number not found. Register to use our services---\n");
            menu0();
        }
    }

    // Register menu that add phone number and name into data.txt file
    void menu2(){
        System.out.print("First Name: ");  // user input
        usrName = input.next();
        System.out.print("Phone number: "); // user input
        usrCre = input.nextLong();
        PrintWriter writer = null;
        PrintWriter creBalance = null;

        // catch some exceptions
        try {
            writer = new PrintWriter("data.txt", "UTF-8");
            creBalance = new PrintWriter("balance.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(usrCre + "\t" + usrName);
        writer.close();
        // create balance file
        creBalance.print(0);
        creBalance.close();
        System.out.println("Welcome " + usrName + "! " + usrCre);
        usrCre = 0;
        usrName = "";
    }
}

// write and store receipt
class receipt{
    void printOut(int c1, int c2, int c3){
        PrintWriter writeReceipt = null;
        try {
            writeReceipt = new PrintWriter("receipt.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // write receipt
        if(c1 > 0 || c2 > 0 || c3 > 0) {
            writeReceipt.println("\t   ----- OUR STORE RECEIPT -----");
            writeReceipt.println("Item \t\t\tQuantity \tTotal Paid");
            if (c1 > 0) writeReceipt.println("$25 gift card \t\t   " + c1 + " \t\t   $" + (c1 * 25));
            if (c2 > 0) writeReceipt.println("$50 gift card \t\t   " + c2 + " \t\t   $" + (c2 * 50));
            if (c3 > 0) writeReceipt.println("$100 gift card \t\t   " + c3 + " \t\t   $" + (c3 * 100));
            writeReceipt.println(" \t\t\t\t\t----------");
            writeReceipt.println(" \t\t\t\t\t   $" + (c1 * 25 + c2 * 50 + c3 * 100));
            writeReceipt.close();
            System.out.println("Your receipt is available in receipt.txt!");
        }else System.out.println("You need to purchase something first");   // prevent an empty receipt file with only preset text
    }
}
// Main class, not much to do here
public class project{
    public static void main(String args[]){
        menu mainMenu = new menu();
        mainMenu.menu0();
    }
}