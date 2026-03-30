package repository;

import models.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository {
    void save(Theatre theatre);
    Optional<Theatre> findById(String id);
    List<Theatre> findAll();
    List<Theatre> findByCityId(String cityId);
}
