package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import beans.CategoryBean;

public class CategoryDAO {
	public CategoryBean fetchCategoryId(String categoryName) {
		CategoryBean categoryBean=new CategoryBean();
		
		Integer tempResult=0;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "select * from category where category_name=?";
		      // create the java statement
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setString(1, categoryName);
		      ResultSet resultSet= st.executeQuery();
			     while(resultSet.next()) {
			    	 categoryBean.setCategoryId(resultSet.getInt("category_id"));
			    	 categoryBean.setCategoryName(resultSet.getString("category_name"));
			    	 tempResult=resultSet.getInt("category_id");
			    	
			    	 
			     }
			     
//			     if(tempResult!=0) {
//			    	 System.out.println("yes category available");
//			      }
//		    	 else {
//		    		 System.out.println("no category available");
//			    	  categoryBean= createNewCategory(categoryName);
//		    	 }
//		    
			      
			      st.close();
	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	    
		return categoryBean;
		
	}
	
	public CategoryBean createNewCategory(String categoryName) {
		CategoryBean categoryBean=new CategoryBean();
		Integer result=0;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "insert into category (category_name) values (?)";
		      // create the java statement
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setString(1, categoryName);
		       st.executeUpdate();
			      st.close();
			     
			      categoryBean=fetchLastRegisteredCategory();
			      
			      System.out.println("Result in createNewCategory :"+result);
			      appendActionIdForNewCategory();
	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	    
		return categoryBean;
		
	}
	
	
	
	public CategoryBean fetchLastRegisteredCategory() {
		CategoryBean categoryBean=new CategoryBean();
		
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "select max(category_id) category_id from category";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	 categoryBean.setCategoryId(resultSet.getInt("category_id"));
		    	 
		     }
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	  
		return categoryBean;
		
	}
	
	
	public void appendActionIdForNewCategory() {
		
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "update user_action_category set action_id=concat(action_id,'0')";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		   st.executeUpdate();
		    
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	  
		
	}

	public List<CategoryBean> fetchAllCategories() {
		// TODO Auto-generated method stub
CategoryBean categoryBean=null;
List<CategoryBean> categoryBeans=new ArrayList<CategoryBean>();
		
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "select * from category";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	 categoryBean =new CategoryBean();
		    	 categoryBean.setCategoryId(resultSet.getInt("category_id"));
		    	 categoryBean.setCategoryName(resultSet.getString("category_name"));
		    	 categoryBeans.add(categoryBean);
		     }
		      st.close();
		      
	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
		return categoryBeans;
	}
	

	public List<CategoryBean> sortCategoriesByUserAction(List<CategoryBean> beans,String registrationId) {
		// TODO Auto-generated method stub

List<CategoryBean> sortedCategoryBeans=new ArrayList<CategoryBean>();
String action_id="";
		
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "select action_id from user_action_category where registration_id=?";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      st.setInt(1, Integer.parseInt(registrationId));
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	 
		    	 action_id=resultSet.getString("action_id");
		     }
		      st.close();
		      if(action_id.length()>0) {
		      Integer[] actionElements = new Integer[action_id.length()];
		      
		      for(int i=0;i<action_id.length();i++) {
		    	  actionElements[i]=Integer.parseInt(action_id.charAt(i)+"");
		      }
		      int indx[] = new int[actionElements.length];

		      for (int i = 0; i < actionElements.length; i++) {
		          indx[i] = i;
		      }

		      /*sort the actionElements array*/

		      for (int i = 0; i < actionElements.length; i++) {
		          int max = i;
		          for (int k = i; k < actionElements.length; k++) {
		              if (actionElements[k] > actionElements[max]) {
		                  max = k;
		              }
		          }

		          
		          int tmp;
		          tmp = actionElements[i];
		          actionElements[i] = actionElements[max];
		          actionElements[max] = tmp;

		          /*keep updating index as well*/
		          int temp;
		          temp = indx[i];
		          indx[i] = indx[max];
		          indx[max] = temp;
		      }
		      for(int i=0;i<indx.length;i++) {
		    	  for (CategoryBean categoryBeanTemp : beans) {
						System.out.println(indx[i]+1==categoryBeanTemp.getCategoryId());
						if(indx[i]+1==categoryBeanTemp.getCategoryId()) {
							sortedCategoryBeans.add(categoryBeanTemp);
						}
		      }
		      }
		      }
	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
		return sortedCategoryBeans;
	}
	
	
	
}
