package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.InvestorDetails;

public interface InvestorRepo extends JpaRepository<InvestorDetails,Long>{
}
