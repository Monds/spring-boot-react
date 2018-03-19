package com.monds.demos;

import com.monds.demos.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUsername(String username);
}
