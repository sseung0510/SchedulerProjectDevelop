package com.schedulerprojectdevelop.domain.user.service;

import com.schedulerprojectdevelop.common.config.PasswordEncoder;
import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.domain.user.model.SessionUser;
import com.schedulerprojectdevelop.domain.user.model.request.DeleteUserRequest;
import com.schedulerprojectdevelop.domain.user.model.request.LoginRequest;
import com.schedulerprojectdevelop.domain.user.model.request.RegisterRequest;
import com.schedulerprojectdevelop.domain.user.model.request.UpdateUserRequest;
import com.schedulerprojectdevelop.domain.user.model.response.GetUserResponse;
import com.schedulerprojectdevelop.domain.user.model.response.RegisterResponse;
import com.schedulerprojectdevelop.domain.user.model.response.UpdateUserResponse;
import com.schedulerprojectdevelop.common.entity.User;
import com.schedulerprojectdevelop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param request
     * @return RegisterResponse
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        User user = new User(
                request.getUserName(),
                request.getUserEmail(),
                passwordEncoder.encode(request.getUserPassword())
        );

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
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
                        user.getName(),
                        user.getEmail()
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
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
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
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
        if(!passwordEncoder.matches(request.getUserPassword(), user.getPassword())) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_MISMATCH_PASSWORD);
        }
        user.updateUser(request.getUserName(), request.getUserEmail());
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    /**
     * 유저 회원탈퇴
     * @param userId
     * @param request
     */
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        if(!passwordEncoder.matches(request.getUserPassword(), user.getPassword())) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_MISMATCH_PASSWORD);
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
        User user = userRepository.findByEmail(request.getUserEmail()).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
        if(!passwordEncoder.matches(request.getUserPassword(), user.getPassword())) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_MISMATCH_PASSWORD);
        }
        return new SessionUser(
                user.getId(),
                user.getEmail()
        );
    }
}
/**
 * 회원 탈퇴 오류
 *
 */
