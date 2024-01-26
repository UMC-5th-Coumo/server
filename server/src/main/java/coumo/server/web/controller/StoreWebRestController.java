package coumo.server.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.StoreConverter;
import coumo.server.domain.Store;
import coumo.server.service.store.StoreCommandService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.web.dto.StoreRequestDTO;
import coumo.server.web.dto.StoreResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner/store")
@Slf4j
public class StoreWebRestController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{storeId}/basic")
    @Operation(summary = "사장님이 작성한 가게 정보(기본 정보) 조회 API",
            description = "사장님이 가게 정보를 저장한 정보(이제 막 회원가입한 유저하면 빈 값)를 조회하는 API입니다. 가게Id를 알려주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게 아이디, path variable 입니다!"),
    })
    public ApiResponse<StoreResponseDTO.StoreBasicDTO> getBasic(
            @PathVariable("storeId") Long storeId){

        Store store = storeQueryService.findStore(storeId).orElseThrow();

        return ApiResponse.onSuccess(StoreConverter.toResultBasicDTO(store));
    }

    @GetMapping("/{storeId}/detail")
    @Operation(summary = "사장님이 작성한 가게 정보(매장 설명) 조회 API",
            description = "사장님이 가게 정보를 저장한 정보(이제 막 회원가입한 유저하면 빈 값)를 조회하는 API입니다. 가게Id를 알려주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게 아이디, path variable 입니다!"),
    })
    public ApiResponse<StoreResponseDTO.StoreDetailDTO> getDetail(
            @PathVariable("storeId") Long storeId){

        Store store = storeQueryService.findStore(storeId).orElseThrow();

        return ApiResponse.onSuccess(StoreConverter.toResultDetailDTO(store ));
    }

    @PatchMapping("/{storeId}/basic")
    @Operation(summary = "사장님이 작성한 가게 정보(기본 정보) 저장 API",
            description = "사장님이 가게 정보를 저장한 정보를 저장하는 API입니다. 가게에 대한 정보를 json으로 보내주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "updateBasicDTO", description = "가게에 대한 정보가 담긴 json"),
    })
    public ApiResponse<Long> updateBasic(@PathVariable("storeId") Long storeId, @RequestBody StoreRequestDTO.UpdateBasicDTO updateBasicDTO){
        storeCommandService.updateStore(storeId, updateBasicDTO);
        return ApiResponse.onSuccess(storeId);
    }

    @PatchMapping(value = "/{storeId}/detail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사장님이 작성한 가게 정보(매장 설명) 저장 API",
            description = "사장님이 가게 정보를 저장한 정보를 저장하는 API입니다. 가게에 대한 정보를 FormData으로 보내주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게 아이디, path variable 입니다!"),
            @Parameter(name = "storeImages", description = "가게 메인 이미지들 입니다!"),
            @Parameter(name = "menuImages", description = "메뉴의 이미지들입니다!"),
            @Parameter(name = "description", description = "가게 설명입니다!!"),
            @Parameter(name = "menuDetail", description = "가게 메뉴 이름과 설명(가격)이 담긴 객체 배열입니다!"),
    })
    public ApiResponse<Long> updateDetail(
            @PathVariable("storeId") Long storeId,
            @RequestPart("storeImages")MultipartFile[] storeImages,
            @RequestPart("menuImages")MultipartFile[] menuImages,
            @RequestPart("description")String description,
            @RequestPart("menuDetail") String menuDetailJson
            ) throws JsonProcessingException {

        //<수정 필요>
        String[] storeImageUrl = {"", ""};
        String[] menuImageUrl = {"", ""};

        //[   {"name": "메뉴1", "description": "설명1"},   {"name": "메뉴2", "description": "설명2"} ]
        StoreRequestDTO.MenuDetail[] menuDetails = objectMapper.readValue(menuDetailJson, StoreRequestDTO.MenuDetail[].class);

        storeCommandService.updateStore(storeId, description, storeImageUrl, menuImageUrl, menuDetails);
        return ApiResponse.onSuccess(storeId);
    }

}
