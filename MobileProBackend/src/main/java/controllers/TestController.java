package controllers;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestController {
	 @RequestMapping(value="/test",method = RequestMethod.GET)
	    public String testMethod() {
	       System.out.println("Hiii");
	       return "App is working dude";
	    }
	 @RequestMapping(value="/login/{name}",method = RequestMethod.GET)
	    public String login(@PathVariable String name) {
	       System.out.println("Hiii"+name);
	       return "App is working dude"+name+" appended";
	    }
	 
	 @RequestMapping(value = "/getjson", method = RequestMethod.GET, produces="application/json")
	 @ResponseBody
	 public String getJson() {
	     JSONObject json = new JSONObject();
	      JSONObject subJson = new JSONObject();
	     subJson .put("key", "value");
	     json.put("key", subJson);
	     return json.toString();
	 }
	 
}
