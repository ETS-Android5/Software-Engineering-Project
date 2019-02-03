package comp3350.breadtunes.persistence;

import java.util.List;

import comp3350.breadtunes.exception.RecordDoesNotExistException;

public interface GeneralPersistence<T> {
    List<T> getAll();

    T insert(T entry);

    T update(T entry) throws RecordDoesNotExistException;

    void delete(T entry) throws RecordDoesNotExistException;
}
