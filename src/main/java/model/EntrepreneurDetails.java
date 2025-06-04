package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class EntrepreneurDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long entrepreneurid;

    @OneToOne(cascade = CascadeType.ALL)	//owning side
    @JoinColumn(referencedColumnName = "userid", nullable=false)
    private User user;
    
    @OneToMany(mappedBy="entrepreneur", cascade=CascadeType.ALL, orphanRemoval=true)
    @Getter @Setter private List<Company> companies;
    
}

