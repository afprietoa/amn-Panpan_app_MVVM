package com.atenea.unaltodosalau.crudsqlite.domain.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "order_has_products",
        primaryKeys = {"id_order", "id_product"},
        foreignKeys = {
                @ForeignKey(entity = Order.class,
                        parentColumns = "id_order",
                        childColumns = "id_order",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Product.class,
                        parentColumns = "id_product",
                        childColumns = "id_product",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index("id_order"), @Index("id_product")})
public class OrderHasProducts implements Serializable {
    @ColumnInfo(name = "id_order")
    public int orderId;

    @ColumnInfo(name = "id_product")
    public int productId;

    @ColumnInfo(name = "quantity")
    public int quantity;
}
