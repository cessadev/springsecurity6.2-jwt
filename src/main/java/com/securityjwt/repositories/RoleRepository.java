package com.securityjwt.repositories;

import com.securityjwt.models.ERoles;
import com.securityjwt.models.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERoles name);
}
