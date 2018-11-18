package ru.strorin.businesscardapp.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NewsEntity")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    NewsEntity getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

}
