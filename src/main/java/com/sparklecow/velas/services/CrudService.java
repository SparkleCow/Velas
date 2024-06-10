package com.sparklecow.velas.services;

import com.sparklecow.velas.exceptions.NotFoundException;

import java.util.List;

public interface CrudService<C, R, U> {
    public R create(C c);
    public List<R> findAll();
    public R findById(Long id) throws NotFoundException;
    public R update(U u, Long id) throws NotFoundException;
    public void deleteById(Long id) throws NotFoundException;
}
