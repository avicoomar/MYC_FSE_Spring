package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long>{
	List<User> findByUsername(String username);
	List<User> findByRefreshToken(String refreshToken);
}
