package com.sip.demo.security_role_demo.serviceImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.demo.security_role_demo.Exception.UsernameAlreadyExistsException;
import com.sip.demo.security_role_demo.model.Role;
import com.sip.demo.security_role_demo.model.User;
import com.sip.demo.security_role_demo.repo.RoleRepository;
import com.sip.demo.security_role_demo.repo.UserRepository;
import com.sip.demo.security_role_demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void save(User user) throws Exception {
       
        if (userRepository.findOneByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists!!");
        } else {
            String encoded = encoder.encode(user.getPassword());
            user.setPassword(encoded);
            userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> listAllUsers() {
    	System.out.println("All in service before.....");
        List<User> listUsers = userRepository.findAll();
        System.out.println("All in service....."+listUsers);
        return listUsers;
    }

    @Override
    public User findUser(int id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> allRoles = roleRepository.findAll();
        return allRoles;
    }

    @Override
    public List<User> findByUsernameContainingIgnoreCase(String keyword) {
        List<User> usersList = userRepository.findAll();
        return usersList;
    }

//    @Override
//    public void updateEnabledAndRoles(Integer id, boolean enabled, Set<Role> roleSet) {
//        userRepository.updateEnabledAndRoles(id,enabled,roleSet);
//    }

}
