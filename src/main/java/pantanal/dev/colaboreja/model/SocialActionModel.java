package pantanal.dev.colaboreja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserModel author;

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    @Basic
    @Column
    private String location;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    @CreationTimestamp
    private Date createdAt;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    @UpdateTimestamp
    private Date updatedAt;

    @Override
    public String toString() {
        return "SocialActionModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", initDateTime='" + initDateTime + '\'' +
                ", finishDateTime='" + finishDateTime + '\'' +
                ", location='" + location + '\'' +
//                ", socialActionCategoryId='" + socialActionCategoryId + '\'' +
//                ", author='" + author + '\'' +
                // Add other fields you want to include
                '}';
    }

}
