package hola.app.us.hola.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "messages")
public class Message  {

  /*  public static int MESSAGE_TYPE_AUDIO = 0x100;
    public static int MESSAGE_TYPE_VIDEO = 0x101;
    public static int MESSAGE_TYPE_FILE = 0x102;
    public static int MESSAGE_TYPE_GPS = 0x103;
    public static int MESSAGE_TYPE_VOICE_NOTE = 0x104;
    public static int MESSAGE_STATUS_READ = 0;
    public static int MESSAGE_STATUS_UNREAD = 1;
    public static int MESSAGE_STATUS_WAITING = 2;
    public static int MESSAGE_STATUS_SENT = 3;
    public static int MESSAGE_STATUS_TYPING = 4;*/

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int messageId;
    @ColumnInfo(name = "timeSent")
    private long timeSent;
    @ColumnInfo(name = "messagebody")
    private String messagebody;
    @ColumnInfo(name = "counterPart")
    private String counterPart;
    @ColumnInfo(name = "timeReceived")
    private long timeReceived;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name = "messageType")
    private int messageType;
    @ColumnInfo(name = "type")
    private String type;
    public Message() {
    }

    public Message(long timeSent, String messagebody, String counterPart, long timeReceived, int status, int type, String chatType) {
        this.timeSent = timeSent;
        this.messagebody = messagebody;
        this.counterPart = counterPart;
        this.timeReceived = timeReceived;
        this.status = status;
        this.messageType = type;
        this.type = chatType;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(long timeSent) {
        this.timeSent = timeSent;
    }

    public String getMessagebody() {
        return messagebody;
    }

    public void setMessagebody(String messagebody) {
        this.messagebody = messagebody;
    }

    public String getCounterPart() {
        return counterPart;
    }

    public void setCounterPart(String counterPart) {
        this.counterPart = counterPart;
    }

    public long getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(long timeReceived) {
        this.timeReceived = timeReceived;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
