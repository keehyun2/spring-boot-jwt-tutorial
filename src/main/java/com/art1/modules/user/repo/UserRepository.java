package com.art1.modules.user.repo;

import com.art1.modules.user.repo.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   // 사용자 조회 권한하고 같이
   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);

   // 사용자 존재 여부
   boolean existsByUsername(String username);
}
