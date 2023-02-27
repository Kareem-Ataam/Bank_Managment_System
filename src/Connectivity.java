import javax.swing.*;
import java.sql.Statement;
import java.sql.*;
public class Connectivity {
    static ResultSet resultSet;
    static Statement statement;
    public static void connection(){
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bank?serverTimezone=UTC",
                    "Kareem_Ataam",
                    "01095086737"
            );
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("select * from `client` ");
            //printRows();


        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public static void printRows() throws SQLException {
        resultSet.first();
        while (!resultSet.isAfterLast()) {
            System.out.printf("%-10s%-15s%-15s%-20s%n"
                    , resultSet.getString("Name")
                    , resultSet.getString("age")
                    , resultSet.getString("city"),
                    resultSet.getString("id")
            );
            resultSet.next();
        }
    }
    public static boolean searchAccountNumber(String acc_num ) throws SQLException{
        resultSet.first();
        while (!resultSet.isAfterLast()){
            if(resultSet.getString("Account_No").equals(acc_num)){
                return true;
            }
            resultSet.next();
        }
        return false;
    }
    public static boolean searchAccountPin(String pin,String acc_num ) throws SQLException{
        resultSet.first();
        while (!resultSet.isAfterLast()){
            if(resultSet.getString("PIN").equals(pin) && resultSet.getString("Account_No").equals(acc_num)){
                return true;
            }
            resultSet.next();
        }
        System.out.println("Incorrect PIN");
        return false;
    }
        public static void createAccount(ResultSet resultSet,Client client,String account_type) throws SQLException {
            resultSet.moveToInsertRow();
            try {
                resultSet.updateString("name", client.getName());
                resultSet.updateString("age", client.getAge());
                resultSet.updateString("Phone_Number", client.getPhone());
                resultSet.updateString("city", client.getAddress().getCity());
                resultSet.updateString("street", client.getAddress().getStreet());
                resultSet.updateString("zip_code", client.getAddress().getZip());
                resultSet.updateString("id", client.getId());
                resultSet.updateString("Account_type", account_type);
                resultSet.updateString("pin", client.getAccount().getPin());
                resultSet.updateString("Account_no", client.getAccount().getAccountNumber());
                resultSet.updateInt("balance",client.getAccount().getBalance());
                resultSet.insertRow();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"There is an account with this info");
            }


        }

}
