package com.esamparka.baseitem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoDto {

    @NotNull
    String fullName;

    @NotNull
    @Email
    String email;

    @NotNull
    String phone;

    int typeId;
    int subCategoryId;
    int categoryId;

}

