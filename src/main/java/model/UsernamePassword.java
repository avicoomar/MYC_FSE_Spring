// A POJO to simply denote the username & password format --- it's needed for @RequestBody in Controller.
package model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernamePassword {

	@Getter @Setter String username;

	@Getter @Setter String password;

}
