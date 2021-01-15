package com.javatechie.elk;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class ElkStackExampleApplication {

	Logger logger=LoggerFactory.getLogger(ElkStackExampleApplication.class);

    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable int id) {
		List<User> users=getUsers();

		User user=users.stream().
				filter(u->u.getId()==id).findAny().orElse(null);
		if(user!=null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("User found", user.getId());
			jsonObject.put("userId", user.getId());
			logger.info(jsonObject.toString());
			return user;
		}else{
			try {
				throw new Exception();
			} catch (Exception e) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("User Not Found Id", id);
				logger.info(jsonObject.toString());
			}
			return new User();
		}
    }


    private List<User> getUsers() {
        return Stream.of(new User(1, "John"),
				new User(2, "Shyam"),
				new User(3, "Rony"),
				new User(4, "mak"))
				.collect(Collectors.toList());
    }

    public static void main(String[] args) {
				System.setProperty("java.util.logging.SocketHandler.host", "localhost");
				System.setProperty("java.util.logging.SocketHandler.port", "5044");
        SpringApplication.run(ElkStackExampleApplication.class, args);
    }
}
