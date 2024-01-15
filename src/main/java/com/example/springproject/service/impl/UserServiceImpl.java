package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.request.UserUpdateRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.dto.response.UserUpdateResponse;
import com.example.springproject.entity.User;
import com.example.springproject.exception.base.BadRequestException;
import com.example.springproject.exception.base.DuplicateException;
import com.example.springproject.exception.base.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.security.CustomUserDetail;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.example.springproject.constant.CommonConstants.AGE_THRESHOLD;
import static com.example.springproject.constant.ExceptionCode.DUPLICATE_USERNAME_CODE;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;
  private final JwtService jwtService;

  public UserServiceImpl(UserRepository repository, JwtService jwtService) {
    super(repository);
    this.repository = repository;
    this.jwtService = jwtService;
  }

  /**
   * method that allows creating new users
   *
   * @param request UserRequest
   * @return UserResponse
   */

  @Transactional
  @Override
  public UserResponse create(UserRequest request) {
    this.checkDateOfBirth(request.getDateOfBirth());
    this.checkUsernameIfExist(request.getUsername());

    User user = new User(
          request.getUsername(),
          request.getPassword(),
          request.getEmail(),
          request.getPhone(),
          request.getDateOfBirth()
    );

    this.create(user);

    return new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getPhone(),
          user.getRole(),
          jwtService.generateToken(new CustomUserDetail(user)),
          user.getDateOfBirth()
    );
  }

  /**
   * method that allows update users
   *
   * @param request UserUpdateRequest, id
   * @return UserUpdateResponse
   */
  @Override
  @Transactional
  public UserUpdateResponse update(String id, UserUpdateRequest request) {
    this.checkDateOfBirth(request.getDateOfBirth());
    User user = this.checkUserExist(id);

    this.setValueForUpdate(user, request);
    this.update(user);

    return new UserUpdateResponse(
          user.getId(),
          user.getPassword(),
          user.getEmail(),
          user.getPhone(),
          user.getRole(),
          user.getDateOfBirth()
    );
  }

  /**
   * method that allows delete users
   *
   * @param id id
   */
  @Transactional
  @Override
  public void delete(String id) {
    User user = this.checkUserExist(id);
    repository.delete(user);
  }

  /**
   * method that allows view all users
   *
   * @param size, page UserRequest
   * @return PageResponse
   */
  @Override
  public PageResponse<UserResponse> viewAll(int size, int page) {
    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> listAllUsers = repository.findAllUser(pageable);
    return PageResponse.of(listAllUsers.getContent(), (int) listAllUsers.getTotalElements());
  }

  /**
   * method that allows check Date Of Birth of users
   *
   * @param dateOfBirth
   */
  private void checkDateOfBirth(Date dateOfBirth) {
    LocalDate currentDate = LocalDate.now();
    LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    int age = currentDate.getYear() - birthDate.getYear();

    if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
        (currentDate.getMonthValue() == birthDate.getMonthValue() &&
         currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
      age--;
    }

    if (age < AGE_THRESHOLD) {
      throw new BadRequestException();
    }
  }

  /**
   * method that allows check Username Exist
   *
   * @param username
   */
  private void checkUsernameIfExist(String username) {
    Optional<User> optionalUser = repository.findUserByUsername(username);
    if (optionalUser.isPresent()) {
      throw new DuplicateException(DUPLICATE_USERNAME_CODE);
    }
  }

  /**
   * method that allows check User Exist
   *
   * @param id
   * @return
   */
  private User checkUserExist(String id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  /**
   * method that allows set value for update
   *
   * @param user
   * @param update
   */
  private void setValueForUpdate(User user, UserUpdateRequest update) {
    user.setPassword(update.getPassword());
    user.setPhone(update.getPhone());
    user.setEmail(update.getEmail());
    user.setDateOfBirth(update.getDateOfBirth());
  }
}
