package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.EntrepreneurDetails;

public interface EntrepreneurRepo extends JpaRepository<EntrepreneurDetails,Long>{
}
