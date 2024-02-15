package coumo.server.service.customer;

import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.web.dto.CustomerRequestDTO;
import coumo.server.web.dto.LoginIdDTO;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findCustomerById(Long customerId);

    Customer findByLoginId(String loginId);

    //회원가입
    Customer joinCustomer(CustomerRequestDTO.CustomerJoinDTO request);

    //로그인
    Customer loginCustomer(CustomerRequestDTO.CustomerLoginDTO request);

    //로그인 중복확인
    boolean isLoginIdAvailable(String loginId);

    //인증번호
    Optional<Customer> findCustomerByNameAndPhone(String name, String phone);

    Optional<Customer> findCustomerByLoginIdAndPhone(String loginId, String phone);

    void resetPassword(String loginId, String newPassword);
    //인증번호 검증
    Optional<LoginIdDTO> findLoginIdByPhone(String phone);

    Customer saveCustomer(Customer customer);

}
