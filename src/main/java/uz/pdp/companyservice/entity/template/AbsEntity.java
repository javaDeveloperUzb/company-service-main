package uz.pdp.companyservice.entity.template;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class AbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private boolean active = true;

}
