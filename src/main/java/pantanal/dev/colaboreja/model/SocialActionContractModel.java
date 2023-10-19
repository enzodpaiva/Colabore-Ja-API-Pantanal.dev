package pantanal.dev.colaboreja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;

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
    @Column(length = 255, nullable = true, unique = true)
    private String keyContract;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialActionContractStatusEnum statusContract;

    @ManyToOne
    @JoinColumn(name = "social_action_id")
    private SocialActionModel socialActionId;

    public SocialActionModel getSocialActionId() {
        return socialActionId;
    }

    public void setSocialActionId(SocialActionModel socialActionId) {
        this.socialActionId = socialActionId;
    }

}
