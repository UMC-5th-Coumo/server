package coumo.server.converter;

import coumo.server.domain.Owner;
import coumo.server.domain.enums.State;
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

    public static OwnerResponseDTO.LoginResultDTO toLoginResultDTO(Owner owner, String token, boolean iswrite){
        return OwnerResponseDTO.LoginResultDTO.builder()
                .ownerId(owner.getId())
                .storeId(owner.getStore().getId())
                .token(token)
                .isWrite(iswrite)
                .createdAt(owner.getCreatedAt())
                .name(owner.getName())
                .loginId(owner.getLoginId())
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .build();
    }

    public static Owner toOwner(OwnerRequestDTO.JoinDTO request){
        return Owner.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .state(State.ACTIVE)
                .build();
    }

    public static OwnerResponseDTO.MyPageDTO toMyPageDTO(Owner owner){
        return OwnerResponseDTO.MyPageDTO.builder()
                .name(owner.getName())
                .loginId(owner.getLoginId())
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .build();
    }
}