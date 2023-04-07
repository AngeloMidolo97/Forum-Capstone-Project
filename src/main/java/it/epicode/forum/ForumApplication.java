package it.epicode.forum;

import it.epicode.forum.entity.Role;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.RoleRepo;
import it.epicode.forum.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ForumApplication implements CommandLineRunner {

	@Autowired
	RoleRepo rp;

	@Autowired
	UserRepo ur;

	@Autowired
	PasswordEncoder pe;

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

			//List<Role> roles = initRole();
			//initUser(roles);
	}



	private List<Role> initRole() {
		List<Role> result = new ArrayList<Role>();
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		rp.save(role);
		result.add(role);
		System.out.println("Saved Role: " + role.getName());

		Role role1 = new Role();
		role1.setName("ROLE_USER");
		rp.save(role1);
		result.add(role1);
		System.out.println("Saved Role: " + role.getName());

		return result;
	}

	private User initUser(List<Role> roles) {
		User user = new User();
		user.setFullname("Angelo Midolo");
		user.setUsername("angelom97");
		user.setPassword(pe.encode("test"));
		user.setDataRegistrazione(LocalDate.now());
		user.setEmail("angelo@angelo.com");
		user.setRoleList(roles);
		user.setActive(true);
		ur.save(user);

		return user;
	}


}
