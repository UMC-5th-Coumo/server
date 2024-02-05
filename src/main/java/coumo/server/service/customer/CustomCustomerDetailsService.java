package coumo.server.service.customer;

import coumo.server.domain.Customer;
import coumo.server.repository.CustomerRepository;
import coumo.server.web.dto.CustomCustomerDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomCustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomCustomerDetailsService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        //DB조회
        Customer customerData = customerRepository.findByLoginId(loginId);

        if(customerData != null){
            //UserDetails에 담아서 return 하면 AuthenticationManager가 검증
            return new CustomCustomerDetails(customerData);
        }

        throw new UsernameNotFoundException("User not found with loginId: " + loginId); //아이디 못찾은 경우
    }
}
