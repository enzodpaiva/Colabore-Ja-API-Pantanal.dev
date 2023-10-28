package pantanal.dev.colaboreja.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;

import java.util.Date;

@Entity
@Table(name = "socialActionContracts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SocialActionContractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(length = 255, nullable = true)
    private String keyProcess;

    @Basic
    @Column(length = 255, nullable = true)
    private String keyDocument;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialActionContractStatusEnum statusContract;

    @Basic
    @Column(length = 255, nullable = true)
    private String codeDocumentPdsign;

    @ManyToOne
//    @JoinColumn(name = "social_action_id")
    private SocialActionModel socialActionId;

    public SocialActionModel getSocialActionId() {
        return socialActionId;
    }

    public void setSocialActionId(SocialActionModel socialActionId) {
        this.socialActionId = socialActionId;
    }

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


    @ManyToOne
//    @JoinColumn(name = "colaborator_id")
    private UserModel colaborator;

    public UserModel getColaborator() {
        return colaborator;
    }

    public void setColaborator(UserModel colaborator) {
        this.colaborator = colaborator;
    }

}
