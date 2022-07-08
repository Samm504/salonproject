package application;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Controller implements Initializable{
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://localhost/projectsalon";
 
	static final String USER = "root";
	static final String PASS = "c5hB4PJ9zIKP3WSN3D";
	
	@FXML
	private TextField nameTF, genderTF, addressTF, emailTF, phoneTF, memidTF, productTF;
	@FXML
	private RadioButton firstYes, firstNo, haveNo, haveYes, beautician1, beautician2, beautician3;
	@FXML
	private CheckBox haircutCB, hairstyleCB, scalptreatCB, hotoiltreatCB, keratintreatCB, hairdetoxCB, manicureCB,
				pedicureCB, hardwaxCB, softwaxCB, haircolorCB, hairbleachCB;
	@FXML 
	private DatePicker apptDate;
	@FXML
	private ChoiceBox<String> timeChoiceBox;
	
	private ArrayList<String> time = new ArrayList<String>();
	
	private int customerd1=0, customerd2=0, cb_int=0, hour=0, minute=0;
	
	private String[] beautician_ids = {"1", "2", "3"};
	
	private String customer_id, appointment_id;
	
	private String[] services = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	
	private LocalDate selectedDate;
	
	@SuppressWarnings("unchecked")
	public void initialize(URL arg0, ResourceBundle arg1) {
		apptDate.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				timeChoiceBox.getItems().clear();
				timeChoiceBox.getSelectionModel().clearSelection();
				timeChoiceBox.setValue(null);
				selectedDate = apptDate.getValue();
				String myFormattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				
				Connection connection = null;
				
				try {
					Class.forName(JDBC_DRIVER);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					connection = DriverManager.getConnection(DB_URL, USER, PASS);
					PreparedStatement pst = connection.prepareStatement("SELECT appointment_time "
							+ "FROM appointment_time "
							+ "WHERE appointment_time NOT IN "
							+ "(SELECT appointment_time "
							+ "FROM appointments "
							+ "WHERE appointment_date = ? AND beautician_id = ?);");
					pst.setString(1, myFormattedDate);
					if(beautician1.isSelected()) {
						pst.setString(2, beautician_ids[0]);
					}else if(beautician2.isSelected()) {
						pst.setString(2, beautician_ids[1]);
					}else if(beautician3.isSelected()) {
						pst.setString(2, beautician_ids[2]);
					}
					time.clear();
					
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						time.add(rs.getString(1));
						System.out.println(rs.getString(1));
					}
					rs.close();
					System.out.println("--");
					String[] time_array = new String[time.size()];
					 
			        for (int i = 0; i < time.size(); i++) {
			            time_array[i] = time.get(i);
			        }
			        System.out.println(time_array);
					timeChoiceBox.getItems().addAll(time_array);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
//		apptDate.setValue(LocalDate.now());
//		timeChoiceBox.setValue("8:00 AM");
		
		
	}
	
	public void checkCheckBox(ActionEvent event) {
		if(haircolorCB.isSelected()) {
			hairbleachCB.setSelected(false);
		}else if(hairbleachCB.isSelected()) {
			haircolorCB.setSelected(false);
		}
	}
	
	public void setTimeAvailable(ActionEvent event) throws ClassNotFoundException {
		
	}
	
	public void submit(ActionEvent event) throws ClassNotFoundException {
		Connection connection = null;
		
		try {
		    
		    if(firstYes.isSelected()) {
		    	customerd1 = 1;
		    }
		    else if(firstNo.isSelected()) {
		    	customerd1 = 0;
		    }
		    
		    if(haveNo.isSelected()) {
		    	customerd2 = 0;
		    }
		    else if(haveYes.isSelected()) {
		    	customerd2 = 1;
		    }
		    
//		    cb_int = 0;
//		    
//		    if(haircutCB.isSelected()) {
//		    	cb_int+=10;
//		    }
//		    if(haircolorCB.isSelected()) {
//		    	cb_int+=30;
//		    }else if(hairbleachCB.isSelected()){
//		    	cb_int+=30;
//		    }
//		    if(hairstyleCB.isSelected()) {
//		    	cb_int+=20;
//		    }
//		    if(scalptreatCB.isSelected()) {
//		    	cb_int+=30;
//		    }
//		    if(hotoiltreatCB.isSelected()) {
//		    	cb_int+=30;
//		    }
//		    if(keratintreatCB.isSelected()) {
//		    	cb_int+=30;
//		    }
//		    if(hairdetoxCB.isSelected()) {
//		    	cb_int+=30;
//		    }
//		    if(manicureCB.isSelected()) {
//		    	cb_int+=30;
//		    }
//		    if(pedicureCB.isSelected()) {
//		    	cb_int+=20;
//		    }
//		    if(hardwaxCB.isSelected()) {
//		    	cb_int+=10;
//		    }
//		    if(softwaxCB.isSelected()) {
//		    	cb_int+=10;
//		    }
//		    
//		    hour = cb_int/60;
//		    minute = cb_int%60;
//		    
//		    System.out.println(cb_int);
//		    System.out.println(hour);
//		    System.out.println(minute);
//		    System.out.println(String.format("%d hr/s, %d mins", hour, minute));
		    
		    String myTime = timeChoiceBox.getValue();
			
			System.out.println("Attempting database connection...");
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected to Database successfully...");

			PreparedStatement pst = connection.prepareStatement("INSERT INTO customers(customer_name, customer_gender, customer_address,"
					+ "customer_email, customer_phone, customer_memid, customer_detailone, customer_detailtwo, customer_detailthree) VALUES (?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement pst1 = connection.prepareStatement("INSERT INTO appointments(appointment_date, appointment_time, customer_id,"
					+ "beautician_id) VALUES (?,?,?,?)");
			
			PreparedStatement pst2 = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			PreparedStatement pst3 = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			
			PreparedStatement pst4 = connection.prepareStatement("INSERT INTO service_duration(service_code, appointment_id, service_duration) VALUES (?,?,?)");
		    
		    pst.setString(1, nameTF.getText());
	    	pst.setString(2, genderTF.getText());
	    	pst.setString(3, addressTF.getText());
	    	pst.setString(4, emailTF.getText());
	    	pst.setString(5, phoneTF.getText());
			
			pst.setString(7, Integer.toString(customerd1));
			pst.setString(8, Integer.toString(customerd2));
			
		    LocalDate myDate = apptDate.getValue();
		    String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			pst1.setString(1, myFormattedDate);
			pst1.setString(2, myTime);
			
			if(beautician1.isSelected()) {
				pst1.setString(4, beautician_ids[0]);
			}else if(beautician2.isSelected()) {
				pst1.setString(4, beautician_ids[1]);
			}else if(beautician3.isSelected()) {
				pst1.setString(4, beautician_ids[2]);
			}
				
		    if((nameTF.getText().isEmpty()||genderTF.getText().isEmpty()||addressTF.getText().isEmpty()||emailTF.getText().isEmpty()||
		    		phoneTF.getText().isEmpty()||(customerd2==1&&productTF.getText().isEmpty())||myTime.isEmpty()||
		    		(firstNo.isSelected()&&memidTF.getText().isEmpty()))) {
		    		Alert alert = new Alert(AlertType.WARNING, "Detected discrepancies. Please check your form carefully before submitting!");
		    		Optional<ButtonType> result = alert.showAndWait();
		    		if (result.isPresent() && result.get() == ButtonType.OK) {
		    		}
		    }else {
		    	if(memidTF.getText().isEmpty()) {
		    		pst.setNull(6, java.sql.Types.INTEGER);
		    	}else {
		    		pst.setString(6, memidTF.getText());
		    	}
		    	
		    	if(customerd2==0) {
		    		productTF.setTextFormatter(null);
		    		pst.setNull(9, java.sql.Types.INTEGER);;
		    	}else if(customerd2==1) {
		    		pst.setString(9, productTF.getText());
		    	}
		    	
		    	Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to submit the appointment?");
		    	Optional<ButtonType> result = alert.showAndWait();
	    		if (result.isPresent() && result.get() == ButtonType.OK) {
	    			
	    			pst.executeUpdate();
	    			ResultSet rs = pst2.executeQuery();
	    			while(rs.next()) {
	    				customer_id = rs.getString(1);
	    			}
	    			
	    			System.out.println(customer_id);
	    			pst1.setString(3, customer_id);
	    			pst1.executeUpdate();
	    			
	    			ResultSet rs1 = pst3.executeQuery();
	    			while(rs1.next()) {
	    				appointment_id = rs1.getString(1);
	    			}
	    			
	    			System.out.println(appointment_id);

					pst4.setString(2, appointment_id);
					
					if(haircutCB.isSelected()) {
				    	pst4.setString(1, services[0]);
				    	hour = 10/60;
					    minute = 10%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(haircolorCB.isSelected()) {
				    	pst4.setString(1, services[1]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(hairbleachCB.isSelected()) {
				    	pst4.setString(1, services[2]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(hairstyleCB.isSelected()) {
				    	pst4.setString(1, services[3]);
				    	hour = 20/60;
					    minute = 20%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(scalptreatCB.isSelected()) {
				    	pst4.setString(1, services[4]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(hotoiltreatCB.isSelected()) {
				    	pst4.setString(1, services[5]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(keratintreatCB.isSelected()) {
				    	pst4.setString(1, services[6]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(hairdetoxCB.isSelected()) {
				    	pst4.setString(1, services[7]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(manicureCB.isSelected()) {
				    	pst4.setString(1, services[8]);
				    	hour = 30/60;
					    minute = 30%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(pedicureCB.isSelected()) {
				    	pst4.setString(1, services[9]);
				    	hour = 20/60;
					    minute = 20%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(hardwaxCB.isSelected()) {
				    	pst4.setString(1, services[10]);
				    	hour = 10/60;
					    minute = 10%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    if(softwaxCB.isSelected()) {
				    	pst4.setString(1, services[11]);
				    	hour = 10/60;
					    minute = 10%60;
				    	if(hour>0) {
					    	pst4.setString(3, String.format("%d hr/s, %d mins", hour, minute));
				    	}else {
				    		pst4.setString(3, String.format("%d mins", minute));
				    	}
				    	pst4.executeUpdate();
				    }
				    
				    System.out.println("Successfully added to database.");

					nameTF.setText("");
				    genderTF.setText("");
				    addressTF.setText("");
				    emailTF.setText("");
				    phoneTF.setText("");
				    memidTF.setText("");
				    productTF.setText("");
				    firstYes.setSelected(false);
				    firstNo.setSelected(false);
				    haveNo.setSelected(false);
				    haveYes.setSelected(false); 
				    beautician1.setSelected(false);
				    beautician2.setSelected(false);
				    beautician3.setSelected(false);
				    haircolorCB.setSelected(false);
				    hairbleachCB.setSelected(false);
				    haircutCB.setSelected(false);
				    hairstyleCB.setSelected(false);
				    scalptreatCB.setSelected(false);
				    hotoiltreatCB.setSelected(false);
				    keratintreatCB.setSelected(false);
				    hairdetoxCB.setSelected(false);
				    manicureCB.setSelected(false);
					pedicureCB.setSelected(false);
					hardwaxCB.setSelected(false);
					softwaxCB.setSelected(false);
					apptDate.setValue(LocalDate.now());
					timeChoiceBox.setValue(null);
					
//					Random rand = new Random();
//					Alert alert2 = new Alert(AlertType.INFORMATION, String.format("Appointment has been submitted. This is your appointment ID: %d", rand.nextInt(1000)));
//					Optional<ButtonType> result1 = alert.showAndWait();
//		    		if (result1.isPresent() && result1.get() == ButtonType.OK) {
//		    			stage.close();
//		    		}
	    		}
		    }
			
			}catch(Exception e) {
				System.out.println("There was an error.");
				System.out.println(e);
				Alert alert2 = new Alert(AlertType.ERROR, String.format("There was an erorr!"));
				Optional<ButtonType> result1 = alert2.showAndWait();
	    		if (result1.isPresent() && result1.get() == ButtonType.OK) {
	    		}
		}
	}
	
}
