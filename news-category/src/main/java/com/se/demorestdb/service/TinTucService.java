package com.se.demorestdb.service;

import com.se.demorestdb.dto.CatogoryDTO;
import com.se.demorestdb.model.TinTuc;
import com.se.demorestdb.repo.TinTucRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TinTucService {
    @Inject
    private TinTucRepo tinTucRepo;

    public List<TinTuc> getAll(){
        return tinTucRepo.getAll();
    }

    public TinTuc getOne(int id){
        return tinTucRepo.findOne(id);
    }

    public TinTuc insert(TinTuc tinTuc){
        return tinTucRepo.insert(tinTuc);
    }

    public boolean update(TinTuc tinTuc,int id){
        return tinTucRepo.update(tinTuc,id);
    }

    public boolean delete(int id){
        return tinTucRepo.delete(id);
    }

    public List<CatogoryDTO> findByCategory(int categoryId){
        return tinTucRepo.findByCategory(categoryId);
    }

    public List<TinTuc> getNewsOfCategoryName(String categoryName){
        return tinTucRepo.getNewsOfCategoryName(categoryName);
    }

    public Map<String, Integer> countNewsOfCategory(){
        return tinTucRepo.countNewsOfCategory();
    }

    public List<Map<String, Object>> getCategoryCountNews(){
        return tinTucRepo.getCategoryCountNews();
    }

    public List<Map<String, Object>> getMaxNewsCategory(){
        return tinTucRepo.getMaxNewsCategory();
    }
}
