package com.springRestful.banco_api.domain.respository;

import com.springRestful.banco_api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
