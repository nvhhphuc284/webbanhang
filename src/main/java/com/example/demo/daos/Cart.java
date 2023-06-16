// thong tin gio hang , luu danh sach cac san pham trong gio hang
package com.example.demo.daos;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();

    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getXeId(),
                        item.getXeId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() +
                            item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }
    //removeFromCart: Hàm này được gọi khi truy cập vào đường dẫn
    ///removeFromCart/{id} bằng phương thức GET, trong đó {id} là một số
    //nguyên duy nhất đại diện cho sản phẩm cần xóa khỏi giỏ hàng. Hàm này xóa
    //sản phẩm có id tương ứng khỏi giỏ hàng và sau đó chuyển hướng người dùng
    //đến trang /cart.
    public void removeItems(Long xeId) {
        cartItems.removeIf(item -> Objects.equals(item.getXeId(),
                xeId));
    }
    //updateCart: Hàm này được gọi khi truy cập vào đường dẫn
    ///updateCart/{id}/{quantity} bằng phương thức GET, trong đó {id} là
    //một số nguyên duy nhất đại diện cho sản phẩm trong giỏ hàng và {quantity}
    //là số lượng mới của sản phẩm đó. Hàm này cập nhật số lượng của sản phẩm
    //trong giỏ hàng và sau đó trả về tên của view xe/cart để hiển thị thông tin
    //giỏ hàng
    public void updateItems(int xeId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item
                        .getXeId(), xeId))
                .forEach(item -> item.setQuantity(quantity));
    }
}