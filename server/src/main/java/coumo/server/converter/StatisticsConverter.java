package coumo.server.converter;

import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CustomerDTO;

public class StatisticsConverter {
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setGender(customer.getGender());
        customerDTO.setBirthday(customer.getBirthday());

        return customerDTO;
    }
}
