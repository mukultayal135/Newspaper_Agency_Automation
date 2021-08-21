package bill;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import jdbcc.database_connection;

public class billgeneratorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combomobile;

    @FXML
    private TextField totaldays;

    @FXML
    private DatePicker startdate;

    @FXML
    private DatePicker enddate;

    @FXML
    private TextField skip;

    @FXML
    private TextField total;

    @FXML
    private TextField txtbill;

    @FXML
    void bill(ActionEvent event) {
    	txtbill.setText(Float.parseFloat(total.getText())*Integer.parseInt(totaldays.getText())+"");
    }

    @FXML
    void fetch(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("select * from customer where mobile=?");
			pstmt.setString(1,combomobile.getSelectionModel().getSelectedItem());
			ResultSet table=pstmt.executeQuery();
			while(table.next())
			{
				total.setText(table.getFloat("totalprice")+"");
				startdate.setValue(LocalDate.parse(table.getString("dos")));
				
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
			
			
			
    } 
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			
    			alert.setTitle("Success");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    @FXML
    void getdays(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("select datediff(startdate,endate)");
			Period interval=Period.between(startdate.getValue(), enddate.getValue());
			totaldays.setText((interval.getDays()-Integer.parseInt(skip.getText()))+"");
		} catch (SQLException e) {
						e.printStackTrace();
		}
    }

    @FXML
    void save(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("update customer set status=?,bill=?,dos=? where mobile=?");
			pstmt.setInt(1,1);
			pstmt.setFloat(2,Float.parseFloat(txtbill.getText()));
			pstmt.setDate(3,Date.valueOf(enddate.getValue().plusDays(1)));
			pstmt.setString(4,combomobile.getSelectionModel().getSelectedItem());
			pstmt.executeUpdate();
			showMsg("Bill Updated");
		} catch (SQLException e) {
						e.printStackTrace();
		}
    }
    Connection con;
    PreparedStatement pstmt;
    @FXML
    void initialize() {
    	startdate.setDisable(true);
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");
		try {
			pstmt=con.prepareStatement("select mobile from customer where status=0");
			ResultSet table=pstmt.executeQuery();
    		ArrayList<String>mobiles=new ArrayList<String>();
    		while(table.next())
    		{
    			mobiles.add(table.getString("mobile"));
    					
    		}
    		combomobile.getItems().addAll(mobiles);
			
    		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

    }
}
