package com.atenea.unaltodosalau.crudsqlite.domain.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "roles")
public class Role implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_role")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "route")
    public String route;
}
