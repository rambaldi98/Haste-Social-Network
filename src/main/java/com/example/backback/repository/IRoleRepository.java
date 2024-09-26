package com.example.backback.repository;

import com.example.backback.domain.entity.Role;
import com.example.backback.domain.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(RoleName name);
        }
