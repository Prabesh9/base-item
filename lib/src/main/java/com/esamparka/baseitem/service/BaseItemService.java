package com.esamparka.baseitem.service;

import com.esamparka.baseitem.dto.BaseItemDto;
import com.esamparka.baseitem.model.BaseItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BaseItemService<T extends BaseItem, D extends BaseItemDto> extends CrudService<D>{

    D updateItem(D itemDto, int id);
    List<D> findByUserId();
    List<D> findByHotDeals();
    List<D> findByFeatured();
    D updateFeaturedItem(int id);
    D updateHotDealItem(int id);
    D updatePublish(int id);
    D updateViewCount(int id);
    T findById(int id);
    D updateImage(MultipartFile[] files, int id);
    D findBySlug(String slug);
}
