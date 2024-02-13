package coumo.server.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QRRequestDTO {

    @NotBlank
    Long customerId;
    @NotBlank
    Long storeId;
    @NotBlank
    Long ownerId;
    @NotBlank
    Integer stampCnt;
}
