package com.esamparka.baseitem.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // user Details
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userId; // cognito user id

    private String fullName;
    private String email;
    private String phone;
    private long availableDate;

    private String title;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    // special cases
    private boolean featured;
    private boolean hotDeal;
    private boolean published;

    private long createdAt;
    private long updatedAt;
    private int viewCount;

    @Column(unique = true)
    private String slug;
}
