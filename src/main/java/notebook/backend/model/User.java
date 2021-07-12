package notebook.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notebook.backend.constants.EntitySize;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", length=EntitySize.USER_NAME)
	private String name;
	
	@Column(name="username", length=EntitySize.USER_USERNAME)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="role_id", referencedColumnName="id")
	private Role role;
	
	@Column(name="is_logged_in")
	private boolean isLoggedIn;
	
}
