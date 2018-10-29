package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.experimental.categories.Categories;

import beans.CategoryBean;
import beans.RegistrationBean;

public class RegistrationDAO {
	public Integer register(RegistrationBean bean) {
		Integer result=1;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "Insert into Registration (name,password,country,email) values (?,?,?,?)";
		      // create the java statement
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setString(1, bean.getName());
		      st.setString(2, bean.getPassword());
		      st.setString(3, bean.getCountry());
		      st.setString(4, bean.getEmail());
		      // execute the query, and get a java resultset
		     st.executeUpdate();
		     
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	    result=fetchLastRegisteredId();
	    insertNewRegistrationInUserActionCategory(result);
		return result;
		
	}
	
	public String fetchNameById(Integer registrationId) {
		String result="";
	    try {
	    	DBConnection connection=new DBConnection();
		    	
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "Select name from Registration where registration_id=?";
		      // create the java statement
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setInt(1, registrationId);
		      ResultSet resultSet= st.executeQuery();
			     while(resultSet.next()) {
			    	
			    	 result=resultSet.getString("name");
			     }
			      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	  
		return result;
		
	}
	
public void insertNewRegistrationInUserActionCategory(Integer registrationId) {
		CategoryDAO categoryDAO=new CategoryDAO();
		CategoryBean categoryBean=new CategoryBean();
		categoryBean=categoryDAO.fetchLastRegisteredCategory();
		String actionId="";
		for(int i=0;i<categoryBean.getCategoryId();i++) {
			actionId=actionId+"0";
		}
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "insert into user_action_category(action_id,registration_id) values (?,?)";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setString(1, actionId);
		      st.setInt(2,registrationId);
		   st.executeUpdate();
		    
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	  
		
	}
	
	public Integer fetchLastRegisteredId() {
		Integer result=0;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "select max(registration_id) registration_id from Registration";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	
		    	 result=resultSet.getInt("registration_Id");
		     }
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	    System.out.println(result);
		return result;
		
	}
}









		