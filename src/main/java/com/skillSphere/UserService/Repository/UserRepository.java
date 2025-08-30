package com.skillSphere.UserService.Repository;

import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Projection.UserSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    List<UserSummary> findAllProjectedBy();

    Page<UserSummary> findAllProjectedBy(Pageable pageable);
}
