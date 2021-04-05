package com.esamparka.baseitem.service;

import java.util.List;

public interface CrudService<T> {

    T addNew(T t);
    List<T> getAll();
    T getById(int id);

}

