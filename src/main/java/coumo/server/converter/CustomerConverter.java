package coumo.server.converter;

import coumo.server.domain.Customer;
import coumo.server.domain.enums.Gender;
import coumo.server.domain.enums.State;
import coumo.server.web.dto.CustomerRequestDTO;
import coumo.server.web.dto.CustomerResponseDTO;

import java.time.LocalDateTime;

public class CustomerConverter {

    public static CustomerResponseDTO.CustomerJoinResultDTO toJoinResultDTO(Customer customer){
        return CustomerResponseDTO.CustomerJoinResultDTO.builder()
                .id(customer.getId())
                .loginId(customer.getLoginId())
                .name(customer.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CustomerResponseDTO.CustomerLoginResultDTO toCustomerLoginResultDTO(Customer customer, String token){
        return CustomerResponseDTO.CustomerLoginResultDTO.builder()
                .customerId(customer.getId())
                .token(token)
                .build();
    }
    public static Customer toCustomer(CustomerRequestDTO.CustomerJoinDTO request){

        Gender gender = null;

        return Customer.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .email(request.getEmail())
                .phone(request.getPhone())
                .state(State.ACTIVE)
                .build();
    }

    public static CustomerResponseDTO.CustomerMyPageDTO toCustomerMyPageDTO(Customer customer) {
        return new CustomerResponseDTO.CustomerMyPageDTO(
                customer.getName(),
                customer.getBirthday(),
                customer.getGender().toString(),
                customer.getPhone()
        );
    }
}
