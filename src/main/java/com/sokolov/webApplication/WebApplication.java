package com.sokolov.webApplication;

import com.sokolov.webApplication.models.User;
import com.sokolov.webApplication.repository.Repository;
import com.sokolov.webApplication.repository.RepositoryImpl;
import com.sokolov.webApplication.utils.DummyObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext cxt = SpringApplication.run(WebApplication.class, args);
		Repository<User,String> rep = cxt.getBean(RepositoryImpl.class);
		cxt.getBean(DummyObjectFactory.class).createData();

		rep.selectAll((User.class)).forEach(u-> {
			System.out.println(u.getLogin());
			System.out.println(u.getName());
			System.out.println(u.getPassword());
		});
	}

}
