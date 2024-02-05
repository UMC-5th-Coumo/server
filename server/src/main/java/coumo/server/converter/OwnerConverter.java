package coumo.server.converter;

import coumo.server.domain.Owner;
import coumo.server.web.dto.OwnerRequestDTO;
import coumo.server.web.dto.OwnerResponseDTO;

import java.time.LocalDateTime;

public class OwnerConverter {

    public static OwnerResponseDTO.JoinResultDTO toJoinResultDTO(Owner owner){
        return OwnerResponseDTO.JoinResultDTO.builder()
                .id(owner.getId())
                .loginId(owner.getLoginId())
                .name(owner.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static OwnerResponseDTO.LoginResultDTO toLoginResultDTO(Owner owner, String token){
        return OwnerResponseDTO.LoginResultDTO.builder()
                .id(owner.getId())
                .loginId(owner.getLoginId())
                .password(owner.getPassword())
                .token(token)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Owner toOwner(OwnerRequestDTO.JoinDTO request){
        return Owner.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}