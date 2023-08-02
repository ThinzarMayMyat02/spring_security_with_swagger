package com.sip.demo.security_role_demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sip.demo.security_role_demo.model.User;

@Service
public interface UserService {

    public void save(User user)throws Exception;

    public List<User> listAllUsers();

    public User findUser(int id);

    public void delete(int id);

    public Object getAllRoles();

    public Iterable<User> findByUsernameContainingIgnoreCase(String keyword);

    //public void updateEnabledAndRoles(Integer id, boolean enabled, Set<Role> roleSet);
}
