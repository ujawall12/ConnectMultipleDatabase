package com.PracticeSpringBoot.ConnectMultipleDatabase.modelUser;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User {

    @Id
    private Integer id;
    private String userName;
}
