package com.atenea.unaltodosalau.crudsqlite.domain.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "user_has_roles", primaryKeys = {"id_user", "id_role"})
public class UserRole implements Serializable {
    @ColumnInfo(name = "id_user")
    public int userId;

    @ColumnInfo(name = "id_role")
    @NonNull
    public String roleId;
}
