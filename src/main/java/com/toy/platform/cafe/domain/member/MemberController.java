package com.toy.platform.cafe.domain.member;

import com.toy.platform.cafe.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Member", description = "회원")
@RestController
@RequestMapping(value = "/api/v1/member", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MemberController {

    // TODO: 2020-11-29 save 예시
    @ApiOperation(value = "회원 등록")
    @GetMapping
    public ApiResponse<Long> save() {
//        AdminMember adminMember = adminMemberService.addAdminMember(saveRequest);
        return ApiResponse.success(1L);
    }
}
