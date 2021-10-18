import javax.swing.*;
import java.sql.*;
import java.util.Properties;

public class Main {
    private static Properties dbProperties;
    private static Connection connection;
    private static final String url = "jdbc:postgresql://databanken.ucll.be:62122/2TX33";

    public static void main(String[] args) {
        connect();
        add();
        showOverview();
        disconect();
    }

    private static void connect(){
        dbProperties = new Properties();
        dbProperties.setProperty("ssl", "true");
        dbProperties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        dbProperties.setProperty("sslmode", "prefer");
        SecreteData data = new LoginData();
        data.setLoginData(dbProperties);
        try {
            connection = DriverManager.getConnection(url, dbProperties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void disconect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void showOverview(){
        try {
            String querry = "select * from groep3_team17.user";
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String out = "";
                out += resultSet.getString("firstname");
                out += " ";
                out += resultSet.getString("lastname");
                out += " ";
                out += resultSet.getString("id");
                out += " ";
                out += resultSet.getString("email");
                System.out.println(out);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void add(){
        String firstname = JOptionPane.showInputDialog("firstname?");
        String lastname = JOptionPane.showInputDialog("lastname?");
        String ID = JOptionPane.showInputDialog("userID?");
        String email = JOptionPane.showInputDialog("email?");
        String password = JOptionPane.showInputDialog("password?");
        String group = JOptionPane.showInputDialog("group?");
        String role = JOptionPane.showInputDialog("role?");
        String querry = "insert into groep3_team17.user (\"id\", \"firstname\", \"lastname\", \"email\", \"password\", \"group\", \"role\") " +
                "values (" + ID + "," + firstname + "," + lastname + "," + email + "," + password + "," + group + "," + role + ")";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(querry);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
