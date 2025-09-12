package com.ianjali.securewithspring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ianjali.securewithspring.utility.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 50, unique = true, nullable = false)
    private Constants.RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<UserDetail> users = new HashSet<>();

}
