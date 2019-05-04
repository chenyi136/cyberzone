package com.safecode.cyberzone.web.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class User implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 420L;
	private Integer userId;
	private String password;
	private String username;
	private Set<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public User() {
		super();
	}

	public User(Integer userId, String password, String username) {
		this.userId = userId;
		this.password = password;
		this.username = username;
	}

	public User(Integer userId , String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(userId , username, password, true, true, true, true, authorities);
	}

	public User(Integer userId ,String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		if (username != null && !"".equals(username) && password != null) {
			this.userId = userId;
			this.username = username;
			this.password = password;
			this.enabled = enabled;
			this.accountNonExpired = accountNonExpired;
			this.credentialsNonExpired = credentialsNonExpired;
			this.accountNonLocked = accountNonLocked;
			this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		} else {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void eraseCredentials() {
		this.password = null;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		TreeSet sortedAuthorities = new TreeSet(new User.AuthorityComparator());
		Iterator arg1 = authorities.iterator();

		while (arg1.hasNext()) {
			GrantedAuthority grantedAuthority = (GrantedAuthority) arg1.next();
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	public boolean equals(Object rhs) {
		return rhs instanceof User ? this.username.equals(((User) rhs).username) : false;
	}

	public int hashCode() {
		return this.username.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("UserId: ").append(this.userId).append("; ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
		if (!this.authorities.isEmpty()) {
			sb.append("Granted Authorities: ");
			boolean first = true;
			Iterator arg2 = this.authorities.iterator();

			while (arg2.hasNext()) {
				GrantedAuthority auth = (GrantedAuthority) arg2.next();
				if (!first) {
					sb.append(",");
				}

				first = false;
				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

	public static User.UserBuilder withUsername(String username) {
		return (new User.UserBuilder()).username(username);
	}

	public static class UserBuilder {
		private Integer userId;
		private String username;
		private String password;
		private List<GrantedAuthority> authorities;
		private boolean accountExpired;
		private boolean accountLocked;
		private boolean credentialsExpired;
		private boolean disabled;

		private UserBuilder() {
		}

		private User.UserBuilder username(String username) {
			Assert.notNull(username, "username cannot be null");
			this.username = username;
			return this;
		}

		public User.UserBuilder password(String password) {
			Assert.notNull(password, "password cannot be null");
			this.password = password;
			return this;
		}

		public User.UserBuilder roles(String... roles) {
			ArrayList authorities = new ArrayList(roles.length);
			String[] arg2 = roles;
			int arg3 = roles.length;

			for (int arg4 = 0; arg4 < arg3; ++arg4) {
				String role = arg2[arg4];
				Assert.isTrue(!role.startsWith("ROLE_"), role + " cannot start with ROLE_ (it is automatically added)");
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}

			return this.authorities((List) authorities);
		}

		public User.UserBuilder authorities(GrantedAuthority... authorities) {
			return this.authorities(Arrays.asList(authorities));
		}

		public User.UserBuilder authorities(List<? extends GrantedAuthority> authorities) {
			this.authorities = new ArrayList(authorities);
			return this;
		}

		public User.UserBuilder authorities(String... authorities) {
			return this.authorities(AuthorityUtils.createAuthorityList(authorities));
		}

		public User.UserBuilder accountExpired(boolean accountExpired) {
			this.accountExpired = accountExpired;
			return this;
		}

		public User.UserBuilder accountLocked(boolean accountLocked) {
			this.accountLocked = accountLocked;
			return this;
		}

		public User.UserBuilder credentialsExpired(boolean credentialsExpired) {
			this.credentialsExpired = credentialsExpired;
			return this;
		}

		public User.UserBuilder disabled(boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public UserDetails build() {
			return new User(this.userId , this.username, this.password, !this.disabled, !this.accountExpired,
					!this.credentialsExpired, !this.accountLocked, this.authorities);
		}
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = 420L;

		private AuthorityComparator() {
		}

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			return g2.getAuthority() == null ? -1
					: (g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority()));
		}
	}
}