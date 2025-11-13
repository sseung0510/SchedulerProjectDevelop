package com.schedulerprojectdevelop.domain.user.controller;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.user.model.SessionUser;
import com.schedulerprojectdevelop.domain.user.model.request.DeleteUserRequest;
import com.schedulerprojectdevelop.domain.user.model.request.LoginRequest;
import com.schedulerprojectdevelop.domain.user.model.request.RegisterRequest;
import com.schedulerprojectdevelop.domain.user.model.request.UpdateUserRequest;
import com.schedulerprojectdevelop.domain.user.model.response.GetUserResponse;
import com.schedulerprojectdevelop.domain.user.model.response.RegisterResponse;
import com.schedulerprojectdevelop.domain.user.model.response.UpdateUserResponse;
import com.schedulerprojectdevelop.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    /**
     * 유저 전체 조회
     */
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    /**
     * 유저 단건 조회
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> findOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    /**
     * 유저 정보 수정
     */
    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name="loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        checkedLogin(sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser.getUserId(), request));
    }

    /**
     * 유저 탈퇴
     */
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name="loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody DeleteUserRequest request,
            HttpSession session
    ) {
        checkedLogin(sessionUser);
        userService.delete(sessionUser.getUserId(), request);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ){
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        checkedLogin(sessionUser);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인 체크
    public void checkedLogin(SessionUser sessionUser) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
    }
}