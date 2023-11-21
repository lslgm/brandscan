package com.comeandsee.brandscan.entity;

import com.comeandsee.brandscan.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static com.comeandsee.brandscan.constants.ManagementConstant.MEMBER_TABLE_NAME;

@Getter
@Entity
@Table(name = MEMBER_TABLE_NAME)
@ToString
public class MemberEntity extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //회원 id
    @Column(nullable = false)
    private String email; //회원 이메일

    @Column(nullable = false)
    private String name; //회원 이름

    @Column(nullable = false)
    private String password; //회원 암호

    private String tel; //회원 전화번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public MemberEntity(String email, String name, String password, String tel){
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
        this.memberRole = MemberRole.USER;
    }


    public MemberEntity() {

    }
}
