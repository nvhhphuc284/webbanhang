package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import com.example.demo.validation.annotations.ValidCategoryId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import java.util.Objects;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Xe")
public class Xe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Tên ", length = 50, nullable = false)
    @Size(min = 1, max = 50, message = "Tên phải từ 1 đến 50 Ký tự")
            @NotBlank(message = "Tên không được để trống")
            private String title;
            @Column(name = "Nơi sản xuất", length = 50, nullable = false)
            @Size(min = 1,max = 50, message = "Nơi sản xuất từ 1- 50 kí tự ")
            @NotBlank(message = "Nơi sản xuất không được để trống")
            private String author;
            @Column(name = "Giá tiền")
            @Positive(message = "Số tiền tính bằng $ và khác 0")
            private Double price;
            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    @ToString.Exclude
    private Category category;
    @OneToMany(mappedBy = "xe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ItemInvoice> itemInvoices = new ArrayList<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Xe xe = (Xe) o;
        return getId() != null && Objects.equals(getId(),
                xe.getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

