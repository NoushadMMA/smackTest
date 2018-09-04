package hola.app.us.hola.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import hola.app.us.hola.models.Message;

@Database(entities = {Message.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase instance;
    public abstract ChatHistoryDao chatHistory() ;


    public static AppDatabase getInstance(Context context) {
        if (instance==null){
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context, AppDatabase.class, "chat_history")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return instance;
    }
}
