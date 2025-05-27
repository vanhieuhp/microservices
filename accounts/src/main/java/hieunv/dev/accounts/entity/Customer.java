package hieunv.dev.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;
}