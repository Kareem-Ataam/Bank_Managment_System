import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

abstract class Account {
    private int balance;
    private String accountNumber;
    private String pin;

    public Account(int balance, String accountNumber, String pin) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }


    public static boolean deleteAccount(ResultSet resultSet) throws SQLException {
                String option;
                System.out.println("Are You Sure!!!Your Account will be deleted(Y/N)");
                option = new Scanner(System.in).next();
                if(option.equalsIgnoreCase("y")) {
                    resultSet.deleteRow();
                    System.out.println("Your Account is deleted");
                    return true;
                }
                return false;
    }

    public static void showAccountInfo(ResultSet resultSet) throws SQLException
    {
                System.out.print("Name:"+resultSet.getString("Name")+
                        "\nAge:"+resultSet.getString("age")+
                        "\nPhone Number:"+resultSet.getString("Phone_Number")+
                        "\nBalance:"+resultSet.getInt("Balance")+
                        "\nCity:"+resultSet.getString("city")+
                        "\nStreet:" +resultSet.getString("street")+
                        "\nAccount Type:"+resultSet.getString("Account_Type")
                );
    }

    public static void editAccountType(ResultSet resultSet)throws SQLException
    {
        Scanner input = new Scanner(System.in);
        HashMap<Integer,String> accountTypes= new HashMap<>();
        accountTypes.put(1,"Checking");
        accountTypes.put(2,"Savings");
        accountTypes.put(3,"Current");
        accountTypes.put(4,"Trust");
        int option;
            System.out.println("1]"+accountTypes.get(1)+"\n2]"+accountTypes.get(2)+"\n3]"+accountTypes.get(3)+"\n4]"+accountTypes.get(4));
            System.out.println("Enter Your Option");
            option = input.nextInt();
            switch (option)
            {
                case 1->resultSet.updateString("Account_type",accountTypes.get(1));
                case 2->resultSet.updateString("Account_type",accountTypes.get(2));
                case 3->resultSet.updateString("Account_type",accountTypes.get(3));
                case 4->resultSet.updateString("Account_type",accountTypes.get(4));
                default -> {System.out.println("Invalid Option");option = 0;}
            }
            if(option != 0) {
                resultSet.updateRow();
                System.out.println("Your Account Type Is Changed");
            }
    }
    public static void transfer(ResultSet resultSet,double amount) throws SQLException
    {

        Scanner input = new Scanner(System.in);
        String accountNumber;boolean exit = true;
        //ResultSet resultSet1 = Connectivity.resultSet;
        String temp_acc_num = resultSet.getString("account_no");
        resultSet.first();
        System.out.println("Enter The Destination Account Number:");
        accountNumber = input.next();
        do {
            if (Connectivity.searchAccountNumber(accountNumber)) {
                resultSet.updateDouble("balance", (resultSet.getInt("balance") + (amount-(0.1*amount))));
                resultSet.updateRow();
                System.out.println("Transfer Is Done:)");
                exit = false;
            } else {
                System.out.println("Incorrect Account Number,Enter another:");
                accountNumber = input.next();
            }
            resultSet.next();
        }while (exit);
        Connectivity.searchAccountNumber(temp_acc_num);
    }
    public String toString(){
        return "["+accountNumber+", "+pin+", "+balance+"]";
    }
    public abstract void withdraw(int amount,String acc_num, ResultSet resultSet)throws SQLException;
    public abstract void deposit(int amount,String acc_num, ResultSet resultSet)throws SQLException;
}
