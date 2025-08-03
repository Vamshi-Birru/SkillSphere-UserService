package com.skillSphere.UserService.Repository;

import com.skillSphere.UserService.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

}
