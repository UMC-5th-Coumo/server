package coumo.server.web.dto;

import coumo.server.domain.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomCustomerDetails implements UserDetails{
    private final Customer customer;

    public CustomCustomerDetails(Customer customer){
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return null;
            }
        });

        return collection;
    }

    @Override
    public String getPassword(){

        return customer.getPassword();
    }

    @Override
    public String getUsername(){
        return customer.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}



