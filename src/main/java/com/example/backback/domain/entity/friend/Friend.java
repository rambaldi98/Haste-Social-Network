package com.example.backback.domain.entity.friend;

import com.example.backback.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userone;

    @ManyToOne(fetch = FetchType.EAGER)
    private User usertwo;

    private int status; // 1 Gui yeu cau , 2 da ket ban , 3 la huy yeu cau

    public Friend(User userone, User usertwo) {
        this.userone = userone;
        this.usertwo = usertwo;
        this.status = 0;
    }

    public Friend() {

    }
}
