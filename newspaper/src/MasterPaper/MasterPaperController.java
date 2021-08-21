package MasterPaper;
import jdbcc.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MasterPaperController {
	 Connection con;
    @FXML
    private ResourceBundle resources;
    @FXML
    private TableView<userbean> tbl1;
    @FXML
    private URL location;
    

    @FXML
    private TextField txtcost;

    @FXML
    private ComboBox<String> combonews;

    @FXML
    private Button btnfetch;

    @FXML
    private Button doupdate;

    @FXML
    void donew(ActionEvent event) {
    	combonews.getSelectionModel().clearSelection();
    	txtcost.setText("");
    }
    @FXML
    void dofetch(ActionEvent event) {
    	try{
			pstmt=con.prepareStatement("select price from paper where paper=?");
			pstmt.setString(1,combonews.getEditor().getText());
			ResultSet table=pstmt.executeQuery();
			Float pricee=0f;
			while(table.next())
			{
				 pricee=table.getFloat("price");
			}
			txtcost.setText(pricee+"");
		}
		catch(Exception exp)
		{ 
			exp.printStackTrace();    	
		}
	}
    	
    @FXML
    void showall(ActionEvent event) {
    	TableColumn<userbean, String> paper=new TableColumn<userbean, String>("Paper Name");
    	paper.setCellValueFactory(new PropertyValueFactory<userbean,String>("paper"));
    	paper.setMinWidth(100);
    	
    	TableColumn<userbean, Float>price=new TableColumn<userbean, Float>("Price");
    	price.setCellValueFactory(new PropertyValueFactory<userbean,Float>("price"));
    	price.setMinWidth(100);
    	
    	tbl1.getColumns().addAll(paper,price);
    	ObservableList<userbean> aryList=showAllRecords();
    	System.out.println(aryList);
    	tbl1.setItems(aryList);
    	
    }

ObservableList<userbean> showAllRecords()
{
	ObservableList<userbean> aryList=FXCollections.observableArrayList();
	
	try{
	 PreparedStatement	pstmt=con.prepareStatement("select * from paper");
		ResultSet table= pstmt.executeQuery();
		
		while(table.next())
		{
			Float price=table.getFloat("price");
			String paper=table.getString("paper");
			
			userbean obj=new userbean(paper,price);
			aryList.add(obj);
		}
	}
	catch(Exception exp)
	{
		exp.printStackTrace();
	}
	return aryList;
}
    
    @FXML
    void doremove(ActionEvent event) {
    	try{
    		pstmt=con.prepareStatement("delete from paper where paper=?");
    		pstmt.setString(1,combonews.getEditor().getText());
    		int count=pstmt.executeUpdate();
    		if(count==0)
    			showMsg("Invalid newspaper");
    		else
    			{
    				showMsg(count+" Records Deleted");
    				fillnews();
    			
    			}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }
    @FXML
    void doupdate(ActionEvent event) {
    	try {
																
			pstmt=con.prepareStatement("update paper set price=? where paper=?");
			
			pstmt.setFloat(1,Float.parseFloat(txtcost.getText()));
			pstmt.setString(2,combonews.getEditor().getText());
			
			int count=pstmt.executeUpdate();
			if(count==0)
				showMsg("Invalid Newspaper");
			else
				showMsg(count+" Record Updated");
			
		} 
    	catch (Exception e) {
			
			e.printStackTrace();
		}
    }
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Error...");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    
    @FXML
    void dosave(ActionEvent event) {
    	try {
    		pstmt=con.prepareStatement("insert into paper values(?,?)");
    		pstmt.setString(1,combonews.getEditor().getText());
    		pstmt.setFloat(2,Float.parseFloat(txtcost.getText()));
    		pstmt.executeUpdate();
    		showMsg("Record Saved");
    		fillnews();
    	}
    	catch(SQLIntegrityConstraintViolationException exp)
    	{
    		showMsg("Dupliacte Entry for newspaper");
    		
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace(); 
    	}
    }

    PreparedStatement pstmt;
    void fillnews() {
    	combonews.getItems().clear();
    	ArrayList<String> newspaper=new ArrayList<String>();
    	try{
			pstmt=con.prepareStatement("select paper from paper");
			ResultSet table=pstmt.executeQuery();
		
		while(table.next())
		{
			String news=table.getString("paper");
			newspaper.add(String.valueOf(news));
			
		}
		combonews.getItems().addAll(newspaper);
		}
		catch(Exception exp)
		{ exp.printStackTrace();    			
		}
    }
   
    @FXML
    void initialize() {
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");
		fillnews();
			
			

    }
}
