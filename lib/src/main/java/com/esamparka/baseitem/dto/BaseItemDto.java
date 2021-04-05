package com.esamparka.baseitem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseItemDto {

        @NotNull
        private int id;

        @NotNull
        private String fullName;

        @Email
        @NotNull
        private String email;

        @NotNull
        private String phone;
        private long availableDate;
        private int serviceId;

        private String title;

        private String description;

        // special cases
        private boolean featured;
        private boolean hotDeal;
        private boolean published;

        private String slug;

}
