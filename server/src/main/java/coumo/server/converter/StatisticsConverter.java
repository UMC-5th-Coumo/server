package coumo.server.converter;

import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CustomerDTO;

public class StatisticsConverter {
    public static CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .gender(customer.getGender())
                .birthday(customer.getBirthday())
                .build();
    }
}
