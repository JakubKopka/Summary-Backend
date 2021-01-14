package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Month> months = new HashSet<>();

    @JoinColumn(name = "billingId")
    @OneToMany
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
    }

    public void addMonth(Month month){
        this.months.add(month);
    }
}
