//package com.ianjali.userauth.entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name="user_roles")
//public class UserRoles {
//
//    @ManyToMany(mappedBy = "userDetails")
//    @JoinColumn(name = "ur_user_id", nullable = false,
//            foreignKey = @ForeignKey(name = "fk_user_on_role"))
//    private UserDetail user;
//
//    @ManyToMany(mappedBy = "roles")
//    @JoinColumn(name = "ur_role_id", nullable = false,
//            foreignKey = @ForeignKey(name = "fk_user"))
//    private Roles roles;
//
//}