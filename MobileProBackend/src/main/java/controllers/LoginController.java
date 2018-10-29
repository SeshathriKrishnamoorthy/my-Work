package controllers;

import java.io.IOException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.LoginBean;
import beans.RegistrationBean;
import persistence.LoginDAO;
import persistence.RegistrationDAO;

@RestController
@EnableAutoConfiguration
@RequestMapping("/LoginController")
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String login(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		LoginBean pj = new LoginBean();
		Gson gson = new GsonBuilder().create();
		LoginBean loginBean = gson.fromJson(json, LoginBean.class);
		RegistrationBean bean1=new RegistrationBean();

		String resultString=null;
        System.out.println(loginBean);
       LoginDAO dao=new LoginDAO();
      Integer result= dao.login(loginBean);
       if(result!=0) 
       {
    	       	     bean1.setRegistrationId(result);
    	        resultString= gson.toJson(bean1);
    		 
       }
       else {
    	   bean1.setRegistrationId(0);
	        resultString= gson.toJson(bean1);
		  
       }
       System.out.println("The result is : "+resultString);
       return resultString;    
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String register(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		Gson gson = new GsonBuilder().create();
		RegistrationBean bean = gson.fromJson(json, RegistrationBean.class);
		System.out.println("In Controller : "+bean.getName());
        RegistrationDAO dao=new RegistrationDAO();
     Integer registrationId=dao.register(bean);
     RegistrationBean bean1=new RegistrationBean();
     bean1.setRegistrationId(registrationId);
        String result = gson.toJson(bean1);
	  return result;
	}
	
}
