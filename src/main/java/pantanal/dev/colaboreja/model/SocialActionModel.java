package pantanal.dev.colaboreja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "socialActions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SocialActionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column
    private String name;

    @Basic
    @Column
    private String description;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    private Date initDateTime;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    private Date finishDateTime;

    @ManyToOne
    @JoinColumn(name = "social_action_category_id")
    private SocialActionCategoryModel socialActionCategoryId;

    public SocialActionCategoryModel getSocialActionCategoryId() {
        return socialActionCategoryId;
    }

    public void setSocialActionCategoryId(SocialActionCategoryModel socialActionCategoryId) {
        this.socialActionCategoryId = socialActionCategoryId;
    }

}
