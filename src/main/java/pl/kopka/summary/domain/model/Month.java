package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "months")
public class Month {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String monthId;
    private int monthNumber;
    private int year;
    @OneToMany
//    @JoinColumn(name = "operationId")
    private List<Operation> operationList = new ArrayList<>();
    @Transient
    private double summary = getCurrentMonthSummary();

    private double getCurrentMonthSummary() {
        System.out.println(this.getOperationList());
        return this.getOperationList().stream().mapToDouble(Operation::getAmount).sum();
    }

    public void addOperation(Operation operation){
        this.operationList.add(operation);
    }

}
