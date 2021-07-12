package notebook.backend.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import notebook.backend.model.User;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897953722110308008L;

	private Long id;
	private String name;
	private String username;
	private String password;
	private String email;
	private boolean isLoggedIn;
	private GrantedAuthority authority;

	public static UserDetailsImpl build(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

		return new UserDetailsImpl(
				user.getId(), user.getName(), user.getUsername(), user.getPassword(), 
				user.getEmail(), user.isLoggedIn(), authority);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(authority);
	}
	
	public GrantedAuthority getAuthority() {
		return authority;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isLoggedIn;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isLoggedIn;	
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isLoggedIn;
	}

	@Override
	public boolean isEnabled() {
		return isLoggedIn;
	}

}
