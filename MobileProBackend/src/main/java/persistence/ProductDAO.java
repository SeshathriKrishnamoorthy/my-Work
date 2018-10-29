package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.CategoryBean;
import beans.ProductBean;
import beans.UserActionCategory;
import beans.UserActionProduct;

public class ProductDAO {

	public Integer createNewProduct(ProductBean productBean) {
		Integer result=0;
	    try {
	    	DBConnection connection=new DBConnection();
	    	CategoryDAO categoryDAO=new CategoryDAO();
	    	CategoryBean categoryBean=new CategoryBean();
		    	 categoryBean=categoryDAO.fetchCategoryId(productBean.getCategoryName());
		    	System.out.println("Category id in create product : "+categoryBean.getCategoryId());
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "insert into product (category_id,product_name,description,price,imageurl,address,no_of_likes,no_of_dislikes) values (?,?,?,?,?,?,?,?)";
		      // create the java statement
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		      System.out.println("Before : " + query.toString());
		     
		  
		      st.setInt(1, categoryBean.getCategoryId());
		      st.setString(2, productBean.getProductName());
		      st.setString(3, productBean.getDescription());
		      st.setString(4, productBean.getPrice());
		      st.setString(5, productBean.getImageURL());
		      st.setString(6, productBean.getAddress());
		      st.setInt(7, 0);
		      st.setInt(8, 0);
		      
		      System.out.println("After : " + query.toString());
		      int resultSet= st.executeUpdate();
			   System.out.println(resultSet);  
			      st.close();
			     
			    	  result=fetchLastRegisteredId();
			      

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	    
		return result;
		
	}

	public Integer fetchLastRegisteredId() {
		// TODO Auto-generated method stub
		Integer result=0;
	    try {
	    	DBConnection connection=new DBConnection();
		    	
		      String query = "select max(product_id) product_id from product";
		      PreparedStatement st =  connection.connect().prepareStatement(query);
		     ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	
		    	 result=resultSet.getInt("product_id");
		     }
		      st.close();

	     } catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	  
		return result;
		
	}
	

public List<ProductBean> fetchProductsByCategory(CategoryBean categoryBean) {
	Integer result=0;
	List<ProductBean> productBeans=new ArrayList<ProductBean>();
	ProductBean productBean=null;
	String registrationId=categoryBean.getRegistrationId();
	Integer index=categoryBean.getIndex();
    try {
    	DBConnection connection=new DBConnection();
    	CategoryDAO categoryDAO=new CategoryDAO();
	    
    	categoryBean=categoryDAO.fetchCategoryId(categoryBean.getCategoryName());
    	
//    	System.out.println("Received Reg id :"+categoryBean.getRegistrationId());
//    	Integer categoryPosition=categoryBean.getCategoryId()-1;
//    	System.out.println("Updated Reg Position :"+categoryPosition);
//    	updateUserActionForCategory(registrationId,categoryPosition.toString());
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = "select * from  product where category_id=? order by no_of_likes desc,no_of_dislikes asc limit ?,5";
	      // create the java statement
	      PreparedStatement st =  connection.connect().prepareStatement(query);
	      st.setInt(1, categoryBean.getCategoryId());
	      st.setInt(2, index);
	      ResultSet resultSet= st.executeQuery();
		     while(resultSet.next()) {
		    	 productBean=new ProductBean();
		    	productBean.setAddress(resultSet.getString("Address"));
		    	productBean.setProductId(resultSet.getInt("Product_Id"));
		    	productBean.setCategoryId(resultSet.getInt("Category_Id"));
		    	productBean.setProductName(resultSet.getString("Product_Name"));
		    	productBean.setDescription(resultSet.getString("Description"));
		    	productBean.setPrice(resultSet.getString("Price"));
		    	productBean.setImageURL(resultSet.getString("imageurl"));
		    	productBean.setLikes(resultSet.getInt("no_of_likes"));
		    	productBean.setDislikes(resultSet.getInt("no_of_dislikes"));
		    	UserActionProduct actionProduct=new UserActionProduct();
			    actionProduct=fetchUserActionForProduct(registrationId, productBean.getProductId().toString());
		    	productBean.setIsLiked(actionProduct.getIsLiked());
		    	productBean.setIsDisliked(actionProduct.getIsDisliked());
		    	//productBean.setName(new RegistrationDAO().fetchNameById(Integer.parseInt(categoryBean.getRegistrationId())));
		    	productBeans.add(productBean);
		    }
		     System.out.println(productBeans.size());
		     
		      st.close();
		     
		    	  result=fetchLastRegisteredId();
		      System.out.println("Result in createNewCategory :"+result);

     } catch (Exception e)
     {
         e.printStackTrace();
     }
	return productBeans;
    
}	

public UserActionProduct fetchUserActionForProduct(String registrationId,String productId) {
	// TODO Auto-generated method stub
	UserActionProduct actionProduct=new UserActionProduct();
	//Setting the default Value for the user_action_product
	
    try {
    	DBConnection connection=new DBConnection();
	    	
	      String query = "select * from user_action_product where registration_id=? and product_id=?";
	      PreparedStatement st =  connection.connect().prepareStatement(query);
	      st.setInt(1, Integer.parseInt(registrationId));
	      st.setInt(2, Integer.parseInt(productId));
	     ResultSet resultSet= st.executeQuery();
	     
	     while(resultSet.next()) {
	    	actionProduct=new UserActionProduct();
	    	 actionProduct.setIsLiked(resultSet.getInt("is_liked"));
	    	 actionProduct.setIsDisliked(resultSet.getInt("is_disliked"));
	     }
	     System.out.println("IsLiked :"+actionProduct.getIsLiked().toString()+".... IsDisliked :"+actionProduct.getIsDisliked().toString()+"... productId :"+productId+"...regId :"+registrationId);
	      st.close();

     } catch (Exception e)
     {
         e.printStackTrace();
     }
  
	return actionProduct;
	
}

public UserActionCategory updateUserActionForCategory(String registrationId,String categoryPosition) {
	// TODO Auto-generated method stub
	UserActionCategory actionCategory=new UserActionCategory();
	//Setting the default Value for the user_action_product
	
    try {
    	DBConnection connection=new DBConnection();
	    	
	      String query = "select action_id from user_action_category where registration_id=?";
	      PreparedStatement st =  connection.connect().prepareStatement(query);
	      System.out.println("DAO reg ID :"+registrationId);
	      st.setInt(1, Integer.parseInt(registrationId));
	      
	     ResultSet resultSet= st.executeQuery();
	     
	     while(resultSet.next()) {
	    	 actionCategory=new UserActionCategory();
	    	 System.out.println("cat Id"+categoryPosition);
	    	 String actionId=resultSet.getString("action_id");
	    	 Integer actionElementId=Integer.parseInt(""+actionId.charAt(Integer.parseInt(categoryPosition)));
	    	 Integer actionElementIdUpdated=actionElementId+1;
	    	 final int RADIX = 10;
	    	 char[] myNameChars = actionId.toCharArray();
	    	 myNameChars[Integer.parseInt(categoryPosition)] = Character.forDigit(actionElementIdUpdated, RADIX);
	    	 String actionIdUpdated = String.valueOf(myNameChars);
	    	 System.out.println("ActionId :"+actionId+" actionElementId :"+actionElementId+" actionElementIdUpdated :"+actionElementIdUpdated+" actionIdUpdated :"+actionIdUpdated);
	    	query="update user_action_category set action_id=? where registration_id=?";
	    	
		      PreparedStatement st1 =  connection.connect().prepareStatement(query);
		      st1.setString(1, actionIdUpdated);
		      st1.setInt(2, Integer.parseInt(registrationId));
		      st1.executeUpdate();
		      actionCategory.setActionId(actionIdUpdated);
		      actionCategory.setRegistrationId(Integer.parseInt(registrationId));
		      st1.close();
	     }
	    
	      st.close();

     } catch (Exception e)
     {
         e.printStackTrace();
     }
  
	return actionCategory;
	
}
	
}
	

