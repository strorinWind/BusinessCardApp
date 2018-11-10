package ru.strorin.businesscardapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singleton;

    private static final String DATABASE_NAME = "NewsRoomDb.db";
    
}
