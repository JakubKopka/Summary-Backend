package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Month {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String monthId;
    private int number;
    private int year;

    @JoinColumn(name = "monthId")
    @OneToMany
    private List<Operation> operationList = new ArrayList<>();

    @Transient
    private double total = 0;

    public void addOperation(Operation operation){
        this.operationList.add(operation);
    }
}
