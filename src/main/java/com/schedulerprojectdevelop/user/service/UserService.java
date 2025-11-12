package com.schedulerprojectdevelop.user.service;

import com.schedulerprojectdevelop.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.user.dto.*;
import com.schedulerprojectdevelop.user.entity.User;
import com.schedulerprojectdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 회원가입
     * @param request
     * @return RegisterResponse
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        User user = new User(request.getUserName(), request.getUserEmail(), request.getUserPassword());
        User savedUser = userRepository.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getUserEmail()
        );
    }

    /**
     * 유저 전체 조회
     * @return GetUserResponse
     */
    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new GetUserResponse(
                        user.getId(),
                        user.getUserName(),
                        user.getUserEmail()
                )).toList();
    }

    /**
     * 유저 단건 조회
     * @param userId
     * @return GetUserResponse
     */
    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserEmail()
        );
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param request
     * @return UpdateUserResponse
     */
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        if(!user.getUserPassword().equals(request.getUserPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }
        user.updateUser(request.getUserName(), request.getUserEmail());
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserEmail()
        );
    }

    /**
     * 유저 회원탈퇴
     *
     * @param userId
     * @param request
     */
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        if(!user.getUserPassword().equals(request.getUserPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }

        scheduleRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }

    /**
     * 로그인
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {
        User user = userRepository.findByUserEmail(request.getUserEmail()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        if(!user.getUserPassword().equals(request.getUserPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return new SessionUser(
                user.getId(),
                user.getUserEmail()
        );
    }
}
