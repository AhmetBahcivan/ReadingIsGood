package com.io.ReadingIsGood.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@Entity
@Setter
@Getter
@Table(name="customer", schema = "public")
@NoArgsConstructor
public class Customer {

    /*
    @Id
    @Column(unique = true)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
     */

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    private String name;

    @Column(unique = true)
    @NotBlank
    @Size(min = 3, max = 50)
    @NaturalId
    private String username;

    @Column(unique = true)
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String phoneNumber;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 100)
    private String hashedPassword;

    @JsonProperty("joinedOn")
    private Timestamp joined_on;

    @JsonProperty("lastLogin")
    private Timestamp last_login;

    @JsonProperty("orderCount")
    private Integer order_count;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    public Customer(String name, String username, String email, String hashedPassword) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.order_count= 0;
        this.joined_on = new Timestamp(System.currentTimeMillis());
    }

}