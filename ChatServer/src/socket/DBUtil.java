package socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;




public class DBUtil {
	public static final String Table_Chat="chatText";
	public static final String Table_Trans="transaction";
	public static final	String Table_PetInfo="pet_info";
	public static final String Table_PetHome="per_home";
	
	private static Connection connect;
	
	private static Connection getConnection(){
		if(connect == null){
			String url = "jdbc:mysql://localhost:3306/chat";
			try{
				Class.forName("com.mysql.jdbc.Driver");
				connect = (Connection)DriverManager.getConnection(url,"root","zyd2288778.");
				System.out.println("�������ݿ�����");
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
			}
		}
		return connect;
	}
	
	public static ResultSet query(String querySql)throws SQLException{
		Statement statement=(Statement) getConnection().createStatement();
		return statement.executeQuery(querySql);
	}
	
	public static int update(String insertSql)throws SQLException {
		Statement stateMent = (Statement) getConnection().createStatement();
		return stateMent.executeUpdate(insertSql);
	}
	
	public static void closeConnection() {
		if (connect != null) {
			try {
				connect.close();
				connect = null;
			} catch (SQLException e) {
				System.out.println("���ݿ�ر��쳣��[" + e.getErrorCode() + "]" + e.getMessage());
			}
		}
	}
}