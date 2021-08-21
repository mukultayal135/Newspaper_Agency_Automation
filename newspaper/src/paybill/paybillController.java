package paybill;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import jdbcc.database_connection;

public class paybillController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtmobile;
    @FXML
    private TextField txtbill;
    PreparedStatement pstmt;
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Message...");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    @FXML
    void fetch(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("select * from customer where mobile=?");
			pstmt.setString(1,txtmobile.getText());
			ResultSet table=pstmt.executeQuery();
			while(table.next())
			{
				txtbill.setText(table.getString("bill"));
				if(table.getInt("status")==0)
				{
					showMsg("Bill Already Paid Or Not Generated");
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
    }

    @FXML
    void pay(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("update customer set status='0' where mobile=?");
			pstmt.setString(1,txtmobile.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    Connection con;
    @FXML
    void initialize() {
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");
		

    }
}
