package com.example.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Tên", length = 50, nullable = false)
    @Size(min = 1, max = 50, message = "Tên từ 1 - 50 kí tự")
            @NotBlank(message = "Tên không được để trống ")
            private String name;
            @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
            @ToString.Exclude
            private List<Xe> xes = new ArrayList<>();
            @Override
            public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return getId() != null && Objects.equals(getId(),
                category.getId());

    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}