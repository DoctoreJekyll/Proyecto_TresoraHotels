package com.atm.buenas_practicas_java.services.templateMethod;

import lombok.Getter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Getter
public abstract class AbstractTemplateServicesEntities<ENTITIES, ID, REPOSITORY extends JpaRepository<ENTITIES,ID>>
{
    private final REPOSITORY repo;

    public AbstractTemplateServicesEntities(REPOSITORY repo) {
        this.repo = repo;
    }

    public Optional<ENTITIES> findById(ID id) {
        if (repo.findById(id).isPresent()) {
            return Optional.of(repo.findById(id).get());
        }
        return Optional.empty();
    }

    public List<ENTITIES> findAll() {
        return repo.findAll();
    }

    public Set<ENTITIES> findAllSet()
    {
        Set<ENTITIES> set = new HashSet<>(this.repo.findAll());
        return set;
    }

    public Page<ENTITIES> findAllPages(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public ENTITIES save(ENTITIES entity){
        return repo.save(entity);
    }

    public void saveEntitiesList(List<ENTITIES> list){
        repo.saveAll(list);
    }

    public void saveEntitiesSet(Set<ENTITIES> list){
        repo.saveAll(list);
    }

    public void deleteById(ID id) {
        this.repo.deleteById(id);
    }

}
