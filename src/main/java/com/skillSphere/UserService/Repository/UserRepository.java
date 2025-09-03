package com.skillSphere.UserService.Repository;

import com.skillSphere.UserService.Dto.UserSummaryDto;
import com.skillSphere.UserService.Entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    @Query("SELECT new com.skillSphere.UserService.Dto.UserSummaryDto(u.email, u.firstName, u.description, u.ratings) " +
            "FROM UserEntity u")
    List<UserSummaryDto> findAllProjectedBy();

    @Query("SELECT new com.skillSphere.UserService.Dto.UserSummaryDto(u.email, u.firstName, u.description, u.ratings) " +
            "FROM UserEntity u")
    Page<UserSummaryDto> findAllProjectedBy(Pageable pageable);
}
