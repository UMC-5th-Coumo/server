package coumo.server.converter;

import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CustomerResponseDTO;

import java.time.LocalDateTime;

public class StatisticsConverter {
    public static CustomerResponseDTO toDTO(Customer customer, CustomerStore customerStore) {
        String ageGroup = calcAgeGroup(customer.getBirthday());

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .gender(customer.getGender())
                .birthday(customer.getBirthday())
                .ageGroup(ageGroup)
                .phone(customer.getPhone())
                .createdAt(customerStore.getCreatedAt())
                .updatedAt(customerStore.getUpdatedAt())
                .build();
    }
    public static String calcAgeGroup(String birthday) {
        int birthYear = Integer.parseInt(birthday.substring(0, 4));
        int currentYear = LocalDateTime.now().getYear();
        int age = currentYear - birthYear + 1;

        if (age < 20) {
            return "10s";
        } else if (age < 30) {
            return "20s";
        } else if (age < 40) {
            return "30s";
        } else if (age < 50) {
            return "40s";
        } else if (age < 60) {
            return "50s";
        } else {
            return "60s";
        }
    }
}
