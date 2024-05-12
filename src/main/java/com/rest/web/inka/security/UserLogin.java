package com.rest.web.inka.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rest.web.inka.models.Rol;
import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.service.IUsuarioService;


@Component
public class UserLogin implements UserDetailsService{
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioService.buscarPorCorreo(username);
				 
		if(usuario.get() == null) { 
        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Rol role: usuario.get().getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		
		return new User(usuario.get().getCorreo(), usuario.get().getPassword(), authorities);
	}

}
