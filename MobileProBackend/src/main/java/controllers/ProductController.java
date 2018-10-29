package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.CategoryBean;
import beans.ProductBean;
import persistence.CategoryDAO;
import persistence.ProductDAO;
import resource.ConvertStringToImage;

@RestController
@EnableAutoConfiguration
@RequestMapping("/ProductController")
public class ProductController {

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String addNewProduct(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Obtained Json"+json);
		Gson gson = new GsonBuilder().create();
		ProductBean productBean = gson.fromJson(json, ProductBean.class);
		if(productBean.getImageString()!=null) {
		ConvertStringToImage convertStringToImage=new ConvertStringToImage();
		productBean.setImageURL(convertStringToImage.convertStringToImageByteArray(productBean));
		}
        ProductDAO productDAO=new ProductDAO();
      Integer productId=productDAO.createNewProduct(productBean);
     
    ProductBean productBean2=new ProductBean();
    productBean2.setProductId(productId);
    String result = gson.toJson(productBean2);
	  return result;
	}
	
	@RequestMapping(value = "/fetchProduct", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String fetchProductByCategory(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(json);
		Gson gson = new GsonBuilder().create();
		CategoryBean bean = gson.fromJson(json, CategoryBean.class);
        ProductDAO dao=new ProductDAO();
        System.out.println("Controller reg id :" +bean.getRegistrationId()+bean.getIndex());
        List<ProductBean> productBeans=new ArrayList<ProductBean>();
        productBeans=dao.fetchProductsByCategory(bean);
     
        String result = gson.toJson(productBeans);
	  return result;
	}
	
	@RequestMapping(value = "/fetchCategories", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String fetchAllCategories(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(json);
		Gson gson = new GsonBuilder().create();
		CategoryBean bean = gson.fromJson(json, CategoryBean.class);
        CategoryDAO dao=new CategoryDAO();
        System.out.println("Controller reg id :" +bean.getRegistrationId());
        List<CategoryBean> categoryBeans=new ArrayList<CategoryBean>();
        //List<CategoryBean> sortedCategoryBeans=new ArrayList<CategoryBean>();
        categoryBeans=dao.fetchAllCategories();
        
      //  sortedCategoryBeans=dao.sortCategoriesByUserAction(categoryBeans,bean.getRegistrationId());
     //   System.out.println(categoryBeans.size()+" : "+sortedCategoryBeans.size());
//        if(categoryBeans.size()!=sortedCategoryBeans.size()) {
//        	sortedCategoryBeans=categoryBeans;
//        }
        String result = gson.toJson(categoryBeans);
	  return result;
	}
}
