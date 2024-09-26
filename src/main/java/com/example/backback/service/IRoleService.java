package com.example.backback.service;


import com.example.backback.domain.entity.Role;
import com.example.backback.domain.entity.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
