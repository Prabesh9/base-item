package com.esamparka.baseitem.repository;


import com.esamparka.baseitem.model.BaseItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseItemRepository<T extends BaseItem> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
    

}
