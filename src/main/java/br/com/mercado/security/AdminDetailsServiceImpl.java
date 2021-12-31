package br.com.mercado.security;

import br.com.mercado.model.entity.Admin;
import br.com.mercado.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional <Admin> adm = adminRepository.findByLogin(login);
        if(adm.isEmpty()){
            throw new UsernameNotFoundException(login);
        }

        return adm.map(AdminDetailsImpl::new).get();
    }
}
