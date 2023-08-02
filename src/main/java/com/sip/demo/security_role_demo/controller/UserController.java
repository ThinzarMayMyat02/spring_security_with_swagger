package com.sip.demo.security_role_demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.demo.security_role_demo.Exception.UsernameAlreadyExistsException;
import com.sip.demo.security_role_demo.model.ChgPasswordForm;
import com.sip.demo.security_role_demo.model.Role;
import com.sip.demo.security_role_demo.model.User;
import com.sip.demo.security_role_demo.service.UserService;

@Controller
@RequestMapping(path = "/admin")
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(path = "/listUser")
    private String listUsers(Model model, @Param("keyword") String keyword) {
    	System.out.println(userService==null);
    	System.out.println("listUser..........");
//        try {
            List<User> listUsers = new ArrayList<User>();
            
            if (keyword == null) {
            	System.out.println("Before if..........");
            	System.out.println("All data...."+userService.listAllUsers());
            	//userService.listAllUsers().forEach(listUsers::add);
                System.out.println("After if..........");
            } else {
            	System.out.println("Before else..........");
                userService.findByUsernameContainingIgnoreCase(keyword).forEach(listUsers::add);
                System.out.println("After else..........");
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("listUsers", listUsers);
            System.out.println("all......"+listUsers);
//        } catch (Exception e) {
//        	System.out.println("exception................"+e.getMessage());
//            model.addAttribute("message", e.getMessage());
//        }
        return "list_users";
    }

    @RequestMapping(path = "/newUser")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @RequestMapping(path = "/saveUser")
    public String saveUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult result)
            throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", userService.getAllRoles());
            return "user_form";
        }
        try {
            userService.save(user);
            return "redirect:/admin/listUser";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("roles", userService.getAllRoles());
            return "user_form";
        }
    }

    @RequestMapping(path = "/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        User user = userService.findUser(id);
        model.addAttribute("pageTitle", "Editing User");
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        return "edit_user";
    }

    @Transactional
    @RequestMapping(path = "/update/{user}")
    public String updateUser(Model model, @RequestParam(value = "user") User user){
        User u=userService.findUser(user.getId());
        Set<Role> objRoles=user.getRoleSet().stream().collect(Collectors.toSet());
        System.out.println(objRoles.toString());
        System.out.println(user.getId()+"  "+ user.isEnabled()+"  "+user.getRoleSet());
       // userService.updateEnabledAndRoles(user.getId(),user.isEnabled(),user.getRoleSet());
        return "";
    }

    @RequestMapping(path = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/listUser";
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(String currentPassword, String newPassword, String confirmPassword) {
        System.out.println("--------------------------------------------------");
        ChgPasswordForm chgPasswordForm = new ChgPasswordForm();
        chgPasswordForm.setCurrentPassword(currentPassword);
        chgPasswordForm.setNewPassword(newPassword);
        chgPasswordForm.setConfirmPassword(confirmPassword);
        System.out.println(chgPasswordForm.toString());
        return "admin/list_users";
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        System.out.println("user testing............................");
        return "success";
    }
}