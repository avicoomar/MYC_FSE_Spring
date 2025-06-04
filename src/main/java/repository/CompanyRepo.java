package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Company;

public interface CompanyRepo extends JpaRepository<Company,Long>{
}
