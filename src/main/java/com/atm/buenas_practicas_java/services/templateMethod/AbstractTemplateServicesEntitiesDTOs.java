package com.atm.buenas_practicas_java.services.templateMethod;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Getter
public abstract class AbstractTemplateServicesEntitiesDTOs<
        ENT, DTO, ID,
        REPO extends JpaRepository<ENT, ID>,
        MAPPER extends AbstractTemplateMapper<ENT, DTO>> {

    // Repositorio que permite operaciones CRUD sobre la entidad (findAll, save, delete, etc.)
    private final REPO repository;

    // Mapper que convierte entre la entidad (ENT) y su DTO (DTO)
    @Getter
    private final MAPPER mapper;

    // Constructor que recibe el repositorio y el mapper.
    // Estos serán inyectados en las subclases.
    public AbstractTemplateServicesEntitiesDTOs(REPO repository, MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ============================
    // ======= LECTURA ============
    // ============================

    // Busca una entidad por su ID. Devuelve un Optional con la entidad si existe.
    public Optional<ENT> findEntity(ID id) {
        return repository.findById(id);
    }

    // Busca una entidad por su ID y la transforma a DTO. Devuelve un Optional con el DTO si existe.
    public Optional<DTO> findDto(ID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    // Devuelve una lista con todas las entidades.
    public List<ENT> findAllEntities() {
        return repository.findAll();
    }

    // Devuelve una lista con todos los DTOs convertidos desde las entidades.
    public List<DTO> findAllDtos() {
        return mapper.toDto(repository.findAll());
    }

    // Devuelve un set (sin elementos duplicados) de todas las entidades.
    public Set<ENT> findAllEntitySet() {
        return new HashSet<>(repository.findAll());
    }

    // Devuelve un set de todos los DTOs convertidos desde las entidades.
    public Set<DTO> findAllDtoSet() {
        return mapper.toDtoSet(new HashSet<>(repository.findAll()));
    }

    // Devuelve una página de entidades según el `Pageable` proporcionado.
    public Page<ENT> findAllEntities(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Devuelve una página de DTOs. Transforma las entidades paginadas usando el mapper.
    public Page<DTO> findAllDtos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    // ============================
    // ======= ESCRITURA ==========
    // ============================

    // Guarda un DTO convirtiéndolo primero en entidad, luego lo persiste, y devuelve el DTO actualizado.
    public DTO saveDto(DTO dto) {
        ENT entity = mapper.toEntity(dto);
        ENT saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    // Guarda directamente una entidad y devuelve su DTO correspondiente.
    public DTO saveAndReturnDto(ENT entity) {
        return mapper.toDto(repository.save(entity));
    }

    // Guarda una entidad y devuelve la entidad resultante (por si la BD genera valores como ID autoincremental).
    public ENT saveEntity(ENT entity) {
        return repository.save(entity);
    }

    // Guarda una lista de DTOs: convierte cada DTO en entidad, guarda todas, no devuelve nada.
    public void saveDtos(List<DTO> dtos) {
        List<ENT> entities = mapper.toEntity(dtos);
        repository.saveAll(entities);
    }

    // Guarda una colección de entidades directamente (útil para sets o listas).
    public void saveEntities(Collection<ENT> entities) {
        repository.saveAll(entities);
    }

    // ============================
    // ======= ELIMINACIÓN ========
    // ============================

    // Elimina una entidad directamente.
    public void deleteEntity(ENT entity) {
        repository.delete(entity);
    }

    // Elimina una entidad por su ID.
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
