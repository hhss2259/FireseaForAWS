package firesea.testserver.domain.entity;

import firesea.testserver.domain.basic.JpaBaseEntity;
import firesea.testserver.domain.basic.JpaBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @NotNull
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tmId" ,updatable = false)
    @NotNull
    TextMessage textMessage;

    @NotNull
    String commentBody;

    public Comment(Member member, TextMessage tm, String commentBody) {
        this.member = member;
        this.textMessage = tm;
        this.commentBody = commentBody;
    }
}
