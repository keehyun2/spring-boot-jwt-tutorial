package com.art1.infra.athentication;

import com.art1.infra.exception.DuplicateMemberException;
import com.art1.modules.user.repo.AuthorityType;
import com.art1.modules.user.repo.User;
import com.art1.modules.user.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String username) {
      if(!userRepository.existsByUsername(username)){ // 없으면 생성
         User user = User.builder()
                 .username(username)
                 .authorities(Collections.singletonList(AuthorityType.ROLE_USER))
                 .activated(true)
                 .build();
         userRepository.save(user);
      }

      return userRepository.findOneWithAuthoritiesByUsername(username)
         .map(user -> {
            if (!user.isActivated()) throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
            return convertToUserDetail(user);
         })
         .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   // 사용자 존재 여부

   /**
    * db User Entity -> spring security UserDetails 객체 로 변환
    */
   private UserDetails convertToUserDetail(User user) {
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.name()))
              .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(user.getUsername(),
              "1234", /// 패스워드 null 넣으면 안돼서 임의로 넣음
              grantedAuthorities);
   }
}
