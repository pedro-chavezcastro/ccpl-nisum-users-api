package com.nisum.ccplnisumusersapi.dataprovider.jpa.repository;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPhoneRepository extends JpaRepository<PhoneEntity, UUID> {
}
