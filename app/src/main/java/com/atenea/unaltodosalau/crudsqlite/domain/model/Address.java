package com.atenea.unaltodosalau.crudsqlite.domain.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "address",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id_user",
                childColumns = "id_user",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("id_user")})
public class Address implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_address")
    public int id;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "neighborhood")
    public String neighborhood;
    @NonNull
    @ColumnInfo(name = "id_user")
    public int idUser;

    @Ignore
    public Address(String address, String neighborhood, int idUser) {
        this.address = address;
        this.neighborhood = neighborhood;
        this.idUser = idUser;
    }

    public Address() {}

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
