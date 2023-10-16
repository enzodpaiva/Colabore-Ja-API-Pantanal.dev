package pantanal.dev.colaboreja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "socialActionCategorys")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SocialActionCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(length = 45, nullable = false, unique = true)
    private String name;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<SocialActionModel> socialActions;
}
