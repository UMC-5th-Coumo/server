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
    private static String calcAgeGroup(String birthday) {
        int birthYear = Integer.parseInt(birthday.substring(0, 4));
        int currentYear = LocalDateTime.now().getYear();
        int age = currentYear - birthYear + 1;

        if (age < 20) {
            return "10대";
        } else if (age < 30) {
            return "20대";
        } else if (age < 40) {
            return "30대";
        } else if (age < 50) {
            return "40대";
        } else if (age < 60) {
            return "50대";
        } else {
            return "60대 이상";
        }
    }
}
