import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentAccount extends Account{
    static final double FEE = 0.33;
    static final double ANNUAL_INTEREST_RATE = .33;
    public CurrentAccount(int balance, String accountNumber, String pin)
    {
        super(balance,accountNumber,pin);
    }
    @Override
    public  void withdraw(int amount, String acc_num, ResultSet resultSet) throws SQLException {
                if(resultSet.getInt("balance")-(amount+(amount*FEE)) >= 0)
                {
                    resultSet.updateDouble("balance",(this.getBalance()-(amount+(amount*FEE))));
                    System.out.println("Withdrawal Done:)");
                    resultSet.updateRow();
                    System.out.println("Current Balance is:"+resultSet.getInt("balance"));
                }
                else {
                    System.out.println("Insufficient Withdrawal");
                }
    }
    public  void deposit(int amount,String acc_num, ResultSet resultSet) throws SQLException{
                if(amount>0)
                {
                    resultSet.updateDouble("balance",(this.getBalance()+(amount+(amount*ANNUAL_INTEREST_RATE))));
                    System.out.println("Deposit Done:)");
                    resultSet.updateRow();
                    System.out.println("Current Balance is:"+resultSet.getInt("balance"));
                }
                else {
                    System.out.println("Invalid amount");
                }
    }
}
