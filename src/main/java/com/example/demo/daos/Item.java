//CHUa thong tin san pham trong gio hang
package com.example.demo.daos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long xeId;
    private String xeName;
    private Double price;
    private int quantity;
}