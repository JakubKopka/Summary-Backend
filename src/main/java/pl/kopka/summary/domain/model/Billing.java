package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String billingId;

    @JoinColumn(name = "billingId")
    @OneToMany
    private List<Month> months = new ArrayList<>();

    @JoinColumn(name = "billingId")
    @OneToMany
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category){
        this.categories.add(category);
    }

    public void addMonth(Month month){
        this.months.add(month);
    }
}
