package pl.kopka.summary.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "billings")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String billingId;
    @OneToMany
//    @JoinColumn(name = "monthId")
    private List<Month> months = new ArrayList<>();
    @OneToMany
//    @JoinColumn(name = "categoryId")
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category){
        this.categories.add(category);
    }

    public void addMonth(Month month){
        this.months.add(month);
    }
}
