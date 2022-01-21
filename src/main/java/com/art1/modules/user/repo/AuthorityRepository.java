package com.art1.modules.user.repo;

import com.art1.modules.user.repo.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
