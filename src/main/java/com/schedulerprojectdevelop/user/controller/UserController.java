package com.schedulerprojectdevelop.user.controller;

import com.schedulerprojectdevelop.config.PasswordEncoder;
import com.schedulerprojectdevelop.user.dto.*;
import com.schedulerprojectdevelop.user.service.UserService;
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
     * @param request
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    /**
     * 유저 전체 조회
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    /**
     * 유저 단건 조회
     * @param userId
     * @return
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> findOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    /**
     * 유저 정보 수정
     * @param
     * @param request
     * @return
     */
    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name="loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser.getUserId(), request));
    }

    /**
     * 유저 탈퇴
     * @param
     * @return
     */
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name="loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody DeleteUserRequest request,
            HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
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
     * @param sessionUser
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
            , HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
