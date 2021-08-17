package uz.pdp.companyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.companyservice.entity.template.AbsEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worker extends AbsEntity {
   @Column(nullable = false, unique = true)
    private String phoneNumber;

   @OneToOne(optional = false)
    private Address address;

   @ManyToOne(optional = false)
    private Department department;

}
