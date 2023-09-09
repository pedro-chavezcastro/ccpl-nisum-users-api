package com.nisum.ccplnisumusersapi.dataprovider.jpa.repository;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    Page<UserEntity> findByIsActive(Boolean aTrue, Pageable pageable);

    Optional<UserEntity> findByEmail(String email);

}
