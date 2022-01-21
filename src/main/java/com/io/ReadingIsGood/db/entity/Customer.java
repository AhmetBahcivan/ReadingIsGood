package com.io.ReadingIsGood.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

}