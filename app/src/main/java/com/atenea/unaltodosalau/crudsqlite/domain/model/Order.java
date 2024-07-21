package com.atenea.unaltodosalau.crudsqlite.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orders",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id_user",
                        childColumns = "id_client",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Address.class,
                        parentColumns = "id_address",
                        childColumns = "id_address",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index("id_client"), @Index("id_address")})
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_order")
    public int id;
    @ColumnInfo(name = "id_client")
    public int idClient;

    @ColumnInfo(name = "id_address")
    public int idAddress;

    @ColumnInfo(name = "status")
    public String status;

    @Ignore
    public Order(int idClient, int idAddress, String status) {
        this.idClient = idClient;
        this.idAddress = idAddress;
        this.status = status;
    }
    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
