package hola.app.us.hola.models;

import android.content.ContentValues;

public abstract class AbstractEntity {

    public static final String UUID = "uuid";

    protected String uuid;

    public String getUuid() {
        return this.uuid;
    }

    public abstract ContentValues getContentValues();

    public boolean equals(AbstractEntity entity) {
        return this.getUuid().equals(entity.getUuid());
    }
}
