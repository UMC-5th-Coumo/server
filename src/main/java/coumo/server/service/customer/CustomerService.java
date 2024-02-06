package coumo.server.service.customer;

import coumo.server.domain.Customer;
import coumo.server.web.dto.CustomerRequestDTO;

public interface CustomerService {

    Customer findByLoginId(String loginId);

    //회원가입
    Customer joinCustomer(CustomerRequestDTO.CustomerJoinDTO request);

    //로그인
    Customer loginCustomer(CustomerRequestDTO.CustomerLoginDTO request);

    //로그인 중복확인
    boolean isLoginIdAvailable(String loginId);
}
