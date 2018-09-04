package hola.app.us.hola.sqlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hola.app.us.hola.models.Message;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ChatHistoryDao {

    @Insert(onConflict = REPLACE)
    long insertNewChatMessage(Message message);

  /*  @Query("SELECT * FROM messages where counterPart LIKE :counterPart")
    List<Message> getMessage(String counterPart);*/
}
