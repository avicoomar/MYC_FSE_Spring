package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import model.InvestorDetails;
import model.EntrepreneurDetails;

@Entity
@NoArgsConstructor
@ToString
public class User {
    
    public enum Role {
    	INVESTOR,
    	ENTREPRENEUR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long userid;
    
    @Column(nullable = false, unique = true)
    @Getter @Setter private String username;
    
    @Column(nullable = false)
    @Getter @Setter private String password;
    
    @Getter @Setter private String firstName;
    
    @Getter @Setter private String LastName;
    
    @Column(unique = true)
    @Getter @Setter private long phoneNo;
    
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy="user") //non-owning side
    @Getter @Setter private InvestorDetails investorDetails;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy="user") //non-owning side
    @Getter @Setter private EntrepreneurDetails entrepreneurDetails;
    
}

