package com.sip.demo.security_role_demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sip.demo.security_role_demo.model.Role;
import com.sip.demo.security_role_demo.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
            
    @Query("SELECT u FROM User u WHERE u.username=:username")
    public User getUserByUsername(@Param("username")String username);

    public List<User> findByUsernameContainingIgnoreCase(String keyword);

    public Optional<User> findOneByUsername(String username);

//    @Query("UPDATE User u SET u.enabled=:enabled and u.roleSet=:roleSet where u.id=:id")
//    @Modifying
//    public void updateEnabledAndRoles(Integer id,Boolean enabled, Set<Role> roleSet);
}
