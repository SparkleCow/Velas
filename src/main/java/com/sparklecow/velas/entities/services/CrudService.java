package com.sparklecow.velas.entities.services;

import java.util.List;

public interface CrudService<C, R, U> {
    public R create(C c);
    public List<R> findAll();
    public R findById(Long id);
    public R update(U u, Long id);
    public void deleteById(Long id);
}
