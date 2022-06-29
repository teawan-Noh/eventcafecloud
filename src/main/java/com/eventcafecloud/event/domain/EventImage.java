import com.eventcafecloud.event.domain.Event;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class EventImage extends Image {

    @Column(nullable = false)
    private String eventImageUrl;

    @Column(nullable = true)
    private LocalDateTime createdDate;    // BaseTImeEntity 상속 구현 (상속은 안되지만)

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    @Builder
    public EventImage(String eventImageUrl, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.eventImageUrl = eventImageUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
