package jdbcc;
import java.sql.Connection;
import java.sql.DriverManager;

public class database_connection 
{
	public static Connection doConnect()
	{
		Connection con=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/newspaper","root","");
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return con;
		
	}
	public static void main(String args[])
	{
		Connection con=doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");;
	}

}