import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        String accountNum, pin;
        Connectivity.connection();
        String choice;
        System.out.println("Do yo have an account:(Y/N)");
        choice = input.next();
        if (choice.equalsIgnoreCase("y")) {
            do {
                System.out.println("Please Enter Your Account Number:");
                accountNum = input.next();
            } while (!Connectivity.searchAccountNumber(accountNum));

            do {
                System.out.println("Please Enter Your PIN:");
                pin = input.next();
            } while (!Connectivity.searchAccountPin(Encryption(pin), accountNum));
            boolean cont = true;
            int pick;
            while (cont) {
                System.out.println("\n\n1]View My balance\n2]Withdraw Cash\n3]Deposit Funds\n4]View all account details\n5]Delete An Account\n6]Edit My Account Type\n" +
                        "7]Transfer Between Two Accounts\n8]Exit");
                pick = input.nextInt();
                switch (pick) {
                    case 1 -> System.out.println("Your Current Balance:" + Connectivity.resultSet.getInt("balance"));
                    case 2 -> {
                        if (Connectivity.resultSet.getString("account_type").equals("Savings")) {
                            System.out.println("Enter the amount that you want to withdraw:");
                            int amount = input.nextInt();
                            Account savingsAccount = new SavingsAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            savingsAccount.withdraw(amount, accountNum, Connectivity.resultSet);
                        } else if (Connectivity.resultSet.getString("account_type").equals("Checking")) {
                            System.out.println("Enter the amount that you want to withdraw:");
                            int amount = input.nextInt();
                            CheckingAccount checkingAccount = new CheckingAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            checkingAccount.withdraw(amount, accountNum, Connectivity.resultSet);
                        } else if (Connectivity.resultSet.getString("account_type").equals("Trust")) {
                            System.out.println("Enter the amount that you want to withdraw:");
                            int amount = input.nextInt();
                            TrustAccount trustgAccount = new TrustAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            trustgAccount.withdraw(amount, accountNum, Connectivity.resultSet);
                        } else {
                            System.out.println("Enter the amount that you want to withdraw:");
                            int amount = input.nextInt();
                            CurrentAccount currentgAccount = new CurrentAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            currentgAccount.withdraw(amount, accountNum, Connectivity.resultSet);
                        }
                    }
                    case 3 -> {
                        System.out.println("Enter the amount that you want to deposit:");
                        int amount = input.nextInt();
                        if (Connectivity.resultSet.getString("account_type").equals("Savings")) {
                            Account savingsAccount = new SavingsAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            savingsAccount.deposit(amount, accountNum, Connectivity.resultSet);
                        } else if (Connectivity.resultSet.getString("account_type").equals("Checking")) {
                            Account checkingAccount = new CheckingAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            checkingAccount.deposit(amount, accountNum, Connectivity.resultSet);
                        } else if (Connectivity.resultSet.getString("account_type").equals("Trust")) {
                            Account trustgAccount = new TrustAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            trustgAccount.deposit(amount, accountNum, Connectivity.resultSet);
                        } else {
                            Account currentgAccount = new CurrentAccount(Connectivity.resultSet.getInt("balance"), accountNum, pin);
                            currentgAccount.deposit(amount, accountNum, Connectivity.resultSet);
                        }
                    }
                    case 4 -> Account.showAccountInfo(Connectivity.resultSet);
                    case 5 -> {
                        if (Account.deleteAccount(Connectivity.resultSet))
                            cont = false;
                    }
                    case 6 -> Account.editAccountType(Connectivity.resultSet);
                    case 7 -> {
                        System.out.println("Enter the amount that you want to transfer:");
                        int value = input.nextInt();
                        Account.transfer(Connectivity.resultSet, value);
                    }
                    case 8 -> {System.out.println("Thanks for Using Our Banking System");cont = false;}
                    default -> System.out.println("Invalid Option");
                }
            }

        } else if (choice.equalsIgnoreCase("n")) {
            System.out.println("Do You want to create an account:(Y/N)");
            choice = input.next();
            if (choice.equalsIgnoreCase("y")) {
                CreateNewAccount();
            } else {
                System.out.println("Can't Server More");
            }
        }
    }
    public static void CreateNewAccount() throws SQLException
    {
        Connectivity.connection();
        String name, phone,  city, street,  zip,  age, pin_num, id;
        int balance=0,option;boolean cont = true;
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Enter Your balance:");
                balance = input.nextInt();
                cont = false;
            } catch (Exception e) {
                input.nextLine();
                System.out.println(e.getMessage());
            }
        }while (cont);
        System.out.println("Choose Your Account type:");
        System.out.println("1]" + "Checking" + "\n2]" + "Savings" + "\n3]" + "Current" + "\n4]" + "Trust");
        option = input.nextInt();
        System.out.println("Enter Your name:");
        name = input.next();
        System.out.println("Enter Your age:");
        age = input.next();
        do {
            System.out.println("Enter Your phone number:");
            phone = input.next();
        } while (phone.length() != 11);
        System.out.println("Enter Your city:");
        city = input.next();
        System.out.println("Enter Your street:");
        street = input.next();
        do {
            System.out.println("Enter Your zip code:");
            zip = input.next();
        } while (zip.length() != 5);
        do {
            System.out.println("Enter Your ID:");
            id = input.next();
        } while (id.length() != 14);
        System.out.println("Enter Your PIN:");
        pin_num = input.next();
        pin_num = Encryption(pin_num);
        Client client;
        switch (option) {
            case 1 -> {
                client = new Client(name, age, id, phone, new CheckingAccount(balance, String.valueOf((long) ((Math.random() * 1000000000 + balance) % 10000000)), pin_num), new Address(city, street, zip));
                Connectivity.createAccount(Connectivity.resultSet, client, "Checking");
            }
            case 2 -> {
                client = new Client(name, age, id, phone, new SavingsAccount(balance, String.valueOf((long) ((Math.random() * 1000000000 + balance) % 10000000)), pin_num), new Address(city, street, zip));
                Connectivity.createAccount(Connectivity.resultSet, client, "Savings");
            }
            case 3 -> {
                client = new Client(name, age, id, phone, new CurrentAccount(balance, String.valueOf((long) ((Math.random() * 1000000000 + balance) % 10000000)), pin_num), new Address(city, street, zip));
                Connectivity.createAccount(Connectivity.resultSet, client, "Current");
            }
            case 4 -> {
                client = new Client(name, age, id, phone, new TrustAccount(balance, String.valueOf((long) ((Math.random() * 1000000000 + balance) % 10000000)), pin_num), new Address(city, street, zip));
                Connectivity.createAccount(Connectivity.resultSet, client, "Trust");
            }
            default -> System.out.println("Invalid Option");
        }
        System.out.println("Your Account Is Successfully Created:)");
    }
    public static String Encryption(String pin){
        pin= pin.toLowerCase();
        HashMap<Character ,Integer> plain = new HashMap<>();
        plain.put('a',0);plain.put('b',1);plain.put('c',2);plain.put('d',3);plain.put('e',4);plain.put('f',5);plain.put('g',6);
        plain.put('h',7);plain.put('i',8);plain.put('j',9);plain.put('k',10);plain.put('l',11);plain.put('m',12);plain.put('n',13);
        plain.put('o',14);plain.put('p',15);plain.put('q',16);plain.put('r',17);plain.put('s',18);plain.put('t',19);plain.put('u',20);
        plain.put('v',21);plain.put('w',22);plain.put('x',23);plain.put('y',24);plain.put('z',25);


        HashMap<Integer ,Character> cipher = new HashMap<>();
        cipher.put(0,'a');cipher.put(1,'b');cipher.put(2,'c');cipher.put(3,'d');cipher.put(4,'e');cipher.put(5,'f');cipher.put(6,'g');
        cipher.put(7,'h');cipher.put(8,'i');cipher.put(9,'j');cipher.put(10,'k');cipher.put(11,'l');cipher.put(12,'m');cipher.put(13,'n');
        cipher.put(14,'o');cipher.put(15,'p');cipher.put(16,'q');cipher.put(17,'r');cipher.put(18,'s');cipher.put(19,'t');cipher.put(20,'u');
        cipher.put(21,'v');cipher.put(22,'w');cipher.put(23,'x');cipher.put(24,'y');cipher.put(25,'z');

        HashMap<Character, Character> numbersCiher = new HashMap<>();
        numbersCiher.put('0','*');numbersCiher.put('1','$');numbersCiher.put('2','%');numbersCiher.put('3','&');numbersCiher.put('4','#');
        numbersCiher.put('5','.');numbersCiher.put('6',')');numbersCiher.put('7','+');numbersCiher.put('8','=');numbersCiher.put('9','!');



        for(int i=0;i<pin.length();i++)
        {
            if(plain.containsKey(pin.charAt(i)))
                pin = pin.replace(pin.charAt(i),cipher.get((plain.get(pin.charAt(i)) + 3) % 26));
            else if(numbersCiher.containsKey(pin.charAt(i)))
            {
                pin = pin.replace(pin.charAt(i),numbersCiher.get(pin.charAt(i))); //r0
            }
        }
        return pin;
    }
}
