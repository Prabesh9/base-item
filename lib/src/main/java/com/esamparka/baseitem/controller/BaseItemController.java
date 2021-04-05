package com.esamparka.baseitem.controller;

import java.util.List;

import javax.validation.Valid;

import com.esamparka.baseitem.dto.BaseItemDto;
import com.esamparka.baseitem.dto.ItemInfoDto;
import com.esamparka.baseitem.model.BaseItem;
import com.esamparka.baseitem.service.BaseItemService;
import com.esamparka.baseitem.service.mapper.BaseItemMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public class BaseItemController<T extends BaseItem, D extends BaseItemDto> {

    private final BaseItemService<T, D> itemService;
    private final BaseItemMapper<T, D> itemMapper;

    public BaseItemController(BaseItemService<T, D> itemService, BaseItemMapper<T, D> itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Operation(summary = "Get featured ads")
    @GetMapping("/featured")
    public ResponseEntity<List<D>> findFeatured() {
        return new ResponseEntity<>(itemService.findByFeatured(), HttpStatus.OK);
    }

    @Operation(summary = "Get hot ads")
    @GetMapping("/hot")
    public ResponseEntity<Iterable<D>> findHotDeals() {
        return new ResponseEntity<>(itemService.findByHotDeals(), HttpStatus.OK);
    }

    @Operation(summary = "Get ad by its id or slug")
    @GetMapping("/{id}")
    public ResponseEntity<D> findItem(@Parameter(description = "id of ad or slug to fetch it") @PathVariable String id) {
        try {
            Integer.parseInt(id);
            return new ResponseEntity<>(itemService.getById(Integer.parseInt(id)), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(itemService.findBySlug(id), HttpStatus.OK);
        }
    }

    @Operation(summary = "Get ads related to the user")
    @GetMapping("/me")
    public ResponseEntity<List<D>> findByUserId() {
        return new ResponseEntity<>(itemService.findByUserId(), HttpStatus.OK);
    }

    @Operation(summary = "Update ads by its id")
    @PutMapping("/{id}")
    public ResponseEntity<D> updateById(
            @Parameter(description = "id of ad to update it") @PathVariable(value = "id") final Integer id,
            @Valid @RequestBody D itemDto) {
        return new ResponseEntity<>(itemService.updateItem(itemDto, id), HttpStatus.OK);
    }

    @Operation(summary = "Create ad")
    @PostMapping("")
    public ResponseEntity<D> createItem(
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Provide initial information to create ad") @RequestBody ItemInfoDto item) {
        return new ResponseEntity<>(itemService.addNew(itemMapper.getDto(item)), HttpStatus.OK);
    }

    @Operation(summary = "Update featured flag by ad id")
    @PutMapping("/{id}/featured")
    public ResponseEntity<D> updateFeature(
            @Parameter(description = "id of ad to update feature flag") @PathVariable int id) {
        return new ResponseEntity<>(itemService.updateFeaturedItem(id), HttpStatus.OK);
    }

    @Operation(summary = "Update hot flag by ad id")
    @PutMapping("/{id}/hot-deal")
    public ResponseEntity<D> updateHotDeal(
            @Parameter(description = "id of ad to update hot flag") @PathVariable int id) {
        return new ResponseEntity<>(itemService.updateHotDealItem(id), HttpStatus.OK);
    }

    @Operation(summary = "Update publish flag by ad id")
    @PutMapping("/{id}/publish")
    public ResponseEntity<D> updatePublish(
            @Parameter(description = "id of ad to update publish flag") @PathVariable int id) {
        return new ResponseEntity<>(itemService.updatePublish(id), HttpStatus.OK);
    }

    @Operation(summary = "Update view count of ad")
    @PutMapping("/{id}/viewCount")
    public ResponseEntity<D> updateViewCount(
            @Parameter(description = "id of ad to increase view count") @PathVariable int id) {
        return new ResponseEntity<>(itemService.updateViewCount(id), HttpStatus.OK);
    }

}
