package com.esamparka.baseitem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.esamparka.baseitem.dto.BaseItemDto;
import com.esamparka.baseitem.model.BaseItem;
import com.esamparka.baseitem.repository.BaseItemRepository;
import com.esamparka.baseitem.service.BaseItemService;
import com.esamparka.baseitem.service.mapper.BaseItemMapper;
import com.esamparka.exceptions.IdMismatchException;
import com.esamparka.exceptions.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseItemServiceImpl<T extends BaseItem, D extends BaseItemDto> implements BaseItemService<T, D> {

    private final BaseItemRepository<T> itemsRepository;
    private final BaseItemMapper<T, D> itemMapper;

    @Value("${esamparka.location.miles.one_degree_constant}")
    private String milesInOneDegree;

    @Value("${esamparka.location.miles.earth_radius_constant}")
    private String earthRadiusInMiles;

    @Value("${esamparka.location.km.one_degree_constant}")
    private String kmInOneDegree;

    @Value("${esamparka.location.km.earth_radius_constant}")
    private String earthRadiusInKm;

    public BaseItemServiceImpl(BaseItemRepository<T> itemsRepository, BaseItemMapper<T, D> itemMapper) {
        this.itemsRepository = itemsRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public D addNew(D itemDto) {
        T item = itemMapper.getEntity(itemDto);
        //item.setUserId(AuthenticationUtil.getUserId());
        item.setCreatedAt(new Date().getTime());
        item.setUpdatedAt(new Date().getTime());
        return itemMapper.getDto(itemsRepository.save(item));
    }

    @Override
    public D getById(int id) {
        return itemMapper.getDto(findById(id));
    }

    @Override
    public T findById(int id) {
        return itemsRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public D updateItem(D itemDto, int id) {
        T oldItem = findById(id);
        if (itemDto.getId() == id) {
            T newItem = itemMapper.getEntity(itemDto);
            newItem.setUserId(oldItem.getUserId());
            newItem.setCreatedAt(oldItem.getCreatedAt());
            newItem.setUpdatedAt(new Date().getTime());
            newItem.setSlug(newItem.getTitle().toLowerCase(Locale.ROOT).replace(" ", "-") + "-" + newItem.getId());
            return itemMapper.getDto(itemsRepository.save(newItem));
        } else
            throw new IdMismatchException();
    }

    @Override
    public D updateFeaturedItem(int id) {
        T item = findById(id);
        item.setFeatured(!item.isFeatured());
        item.setUpdatedAt(new Date().getTime());
        itemsRepository.save(item);
        return itemMapper.getDto(item);
    }

    @Override
    public D updateHotDealItem(int id) {
        T item = findById(id);
        item.setHotDeal(!item.isHotDeal());
        item.setUpdatedAt(new Date().getTime());
        itemsRepository.save(item);
        return itemMapper.getDto(item);
    }

    @Override
    public D updatePublish(int id) {
        T item = findById(id);
        item.setPublished(!item.isPublished());
        item.setUpdatedAt(new Date().getTime());
        itemsRepository.save(item);
        return itemMapper.getDto(item);
    }

    @Override
    public D updateViewCount(int id) {
        T item = findById(id);
        int viewCount = item.getViewCount();
        item.setViewCount(viewCount + 1);
        item.setUpdatedAt(new Date().getTime());
        itemsRepository.save(item);
        return itemMapper.getDto(item);
    }

    @Override
    public List<D> getAll() {
        return itemsRepository.findAll().stream().map(itemMapper::getDto).collect(Collectors.toList());
    }

    protected Map<String, Double> getNearby(Double latitude, Double longitude, Double maxDistance, String units) {
        double ONE_DEGREE_CONSTANT, EARTH_RADIUS_CONSTANT;
        double maxLat, minLat, maxLong, minLong;

        if (units.equalsIgnoreCase("miles")) {
            ONE_DEGREE_CONSTANT = Double.parseDouble(milesInOneDegree);
            EARTH_RADIUS_CONSTANT = Double.parseDouble(earthRadiusInMiles);
        } else {
            ONE_DEGREE_CONSTANT = Double.parseDouble(kmInOneDegree);
            EARTH_RADIUS_CONSTANT = Double.parseDouble(earthRadiusInKm);
        }
        double calc = maxDistance / Math.abs(Math.cos(Math.toRadians(latitude)) * ONE_DEGREE_CONSTANT);
        maxLat = latitude + (maxDistance / ONE_DEGREE_CONSTANT);
        minLat = latitude - (maxDistance / ONE_DEGREE_CONSTANT);
        maxLong = longitude + calc;
        minLong = longitude - calc;
        Map<String, Double> location = new HashMap<>();
        location.put("maxLat", maxLat);
        location.put("minLat", minLat);
        location.put("maxLong", maxLong);
        location.put("minLong", minLong);
        location.put("radius", EARTH_RADIUS_CONSTANT);
        return location;
    }

    @Override
    public D findBySlug(String slug) {
        return itemMapper.getDto((findById(Integer.parseInt(slug.substring(slug.lastIndexOf("-") + 1)))));
    }
}
