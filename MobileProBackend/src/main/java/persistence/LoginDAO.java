package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import beans.LoginBean;
import beans.RegistrationBean;


public class LoginDAO {

	public Integer login(LoginBean loginBean) {
		Integer result=0;
		String query=null;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
			if(loginBean.getCheck().equals("N")) {
		       query = "select * from Registration where email=? and password=?";   
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setString(1, loginBean.getEmail());
		      st.setString(2, loginBean.getPassword());
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	 result=resultSet.getInt("registration_Id");
		     }
		     st.close();
			}
			
			else {
				query = "select * from Registration where email=?";   
			      PreparedStatement st =  connection.connect().prepareStatement(query);
			      st.setString(1, loginBean.getEmail());
			     ResultSet resultSet= st.executeQuery();
			     while(resultSet.next()) {
			    	 result=resultSet.getInt("registration_Id");
			     }
			     if(result==0 && !loginBean.getCheck().equals("N")) {
			    	  RegistrationDAO registrationDAO=new RegistrationDAO();
			    	  RegistrationBean registrationBean=new RegistrationBean();
			    	  registrationBean.setEmail(loginBean.getEmail());
			    	  registrationBean.setName(loginBean.getName());
			    	  result=registrationDAO.register(registrationBean);
			     }
			     st.close();
			}
		      

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
		return result;
		
	}
	
}
