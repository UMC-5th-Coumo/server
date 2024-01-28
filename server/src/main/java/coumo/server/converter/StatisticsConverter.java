package coumo.server.converter;

import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CustomerDTO;

public class StatisticsConverter {
    public static CustomerDTO toDTO(Customer customer, CustomerStore customerStore) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .gender(customer.getGender())
                .birthday(customer.getBirthday())
                .createdAt(customerStore.getCreatedAt())
                .updatedAt(customerStore.getUpdatedAt())
                .build();
    }
}
