package hawkerhistory;
import jdbcc.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import MasterPaper.userbean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class hawkerdataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboarea;

    @FXML
    private TableView<userbean1> tbl;

    @FXML
    void filter(ActionEvent event) {
    	for ( int i = 0; i<tbl.getItems().size(); i++) {
    	    tbl.getItems().clear();
    	}
    	TableColumn<userbean1, String> name=new TableColumn<userbean1, String>("Hawker Name");
    	name.setCellValueFactory(new PropertyValueFactory<userbean1,String>("name"));
    	name.setMinWidth(100);
    	
    	TableColumn<userbean1, String>address=new TableColumn<userbean1, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<userbean1,String>("address"));
    	address.setMinWidth(100);
    	
    	TableColumn<userbean1, String>mobile=new TableColumn<userbean1, String>("Mobile Number");
    	mobile.setCellValueFactory(new PropertyValueFactory<userbean1,String>("mobile"));
    	mobile.setMinWidth(100);
    	
    	TableColumn<userbean1, String>area=new TableColumn<userbean1,String>("Area Serving In");
    	area.setCellValueFactory(new PropertyValueFactory<userbean1,String>("area"));
    	area.setMinWidth(100);
    	
    	TableColumn<userbean1, Date>doj=new TableColumn<userbean1, Date>("Date Of Joining");
    	doj.setCellValueFactory(new PropertyValueFactory<userbean1,Date>("doj"));
    	doj.setMinWidth(100);
    	
    	tbl.getColumns().addAll(doj,name,address,mobile,area);
    	ObservableList<userbean1> aryList=showfilter(1);
    	tbl.setItems(aryList);
    }

    @FXML
    void show(ActionEvent event) {
    	for ( int i = 0; i<tbl.getItems().size(); i++) {
    	    tbl.getItems().clear();
    	}
    	 
    	TableColumn<userbean1, String> name=new TableColumn<userbean1, String>("Hawker Name");
    	name.setCellValueFactory(new PropertyValueFactory<userbean1,String>("name"));
    	name.setMinWidth(100);
    	
    	TableColumn<userbean1, String>address=new TableColumn<userbean1, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<userbean1,String>("address"));
    	address.setMinWidth(100);
    	
    	TableColumn<userbean1, String>mobile=new TableColumn<userbean1, String>("Mobile Number");
    	mobile.setCellValueFactory(new PropertyValueFactory<userbean1,String>("mobile"));
    	mobile.setMinWidth(100);
    	
    	TableColumn<userbean1, String>area=new TableColumn<userbean1,String>("Area Serving In");
    	area.setCellValueFactory(new PropertyValueFactory<userbean1,String>("area"));
    	area.setMinWidth(100);
    	
    	TableColumn<userbean1, Date>doj=new TableColumn<userbean1, Date>("Date Of Joining");
    	doj.setCellValueFactory(new PropertyValueFactory<userbean1,Date>("doj"));
    	doj.setMinWidth(100);
    	
    	tbl.getColumns().addAll(doj,name,address,mobile,area);
    	ObservableList<userbean1> aryList=showfilter(0);
    	tbl.setItems(aryList);
    }
    PreparedStatement pstmt;
    ObservableList<userbean1> showfilter(int flag)
    {
    	
    	ObservableList<userbean1> aryList=FXCollections.observableArrayList();
    	try
    	{
    		ResultSet  table=null;
    		String selarea=comboarea.getSelectionModel().getSelectedItem();
    		if(flag==1)
    		{
    			pstmt=con.prepareStatement("select * from hawkers where find_in_set(?,sareas)");
    			pstmt.setString(1,selarea);
    			table= pstmt.executeQuery();
    		}
    		else
    			{
    				pstmt=con.prepareStatement("select * from hawkers");
    				table= pstmt.executeQuery();
    			}
    		
    		
    		while(table.next())
    		{
    			String name=table.getString("hname");
    			String address=table.getString("address");
    			String mobile=table.getString("mobile");
    			Date doa =table.getDate("doj");
    			String area=table.getString("sareas");
    			userbean1 obj=new userbean1(doa,name,address,mobile,area);
    			aryList.add(obj);
    		}
    		
        	
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return aryList;
    }
    Connection con;
    @FXML
    void initialize() {
    	ArrayList<String>areas=new ArrayList<String>(Arrays.asList("Patel Nagar","Bharat Nagar","Green City","Model Town","Ganesh Nagar"));
    	comboarea.getItems().addAll(areas);
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");

    }
}
