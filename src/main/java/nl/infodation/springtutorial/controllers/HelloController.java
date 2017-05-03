package nl.infodation.springtutorial.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.infodation.springtutorial.models.HelloModel;

//import nl.infodation.

@Controller
@EnableAutoConfiguration
public class HelloController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(path = "/hello", method = RequestMethod.GET)
	@ResponseBody
	HelloModel getHello() {		
		return new HelloModel("Hello from REST interface with GET method on /hello endpoint");
	}
	
	@RequestMapping(path = "/hello", method = RequestMethod.POST)
	@ResponseBody
	HelloModel postHello(@RequestParam(value="message") String message) {
		if(message.isEmpty()) {
			throw new IllegalArgumentException("You must provide 'message' parameter in request.");
		}
		
		return new HelloModel("You performed POST on /hello endpoint with message: '"+message+"'");
	}
}
