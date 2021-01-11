package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String operationId;
    private double amount;
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String categoryId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String monthId;
}
