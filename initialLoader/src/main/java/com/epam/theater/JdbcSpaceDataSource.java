package com.epam.theater;

import com.epam.theater.dao.MovieDao;
import com.gigaspaces.datasource.DataIterator;
import com.gigaspaces.datasource.SpaceDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class JdbcSpaceDataSource extends SpaceDataSource {

    @Autowired
    private MovieDao movieDao;

    @Override
    public DataIterator<Object> initialDataLoad() {
        List<Object> initialObjects = getAllInitialObjects();
        DataIterator<Object> moviesDataIterator = new ListDataIterator(initialObjects);
        return moviesDataIterator;
    }

    private List<Object> getAllInitialObjects() {
        List<Object> initialObjects = new ArrayList<>();
        initialObjects.addAll(movieDao.getAll());
        return initialObjects;
    }

    static class ListDataIterator<T> implements DataIterator<T> {

        private Iterator<T> iterator;

        public ListDataIterator(List<T> list) {
            iterator = list.iterator();
        }

        @Override
        public void close() {}

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

    }

}