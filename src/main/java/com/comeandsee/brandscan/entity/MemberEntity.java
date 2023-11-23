package com.comeandsee.brandscan.entity;

import com.comeandsee.brandscan.converter.MemberRoleConverter;
import com.comeandsee.brandscan.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.comeandsee.brandscan.constants.ManagementConstant.MEMBER_TABLE_NAME;

@Getter
@Entity
@Table(name = MEMBER_TABLE_NAME)
@NoArgsConstructor
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //회원 id

    @Column(nullable = false)
    private String email; //회원 이메일

    @Column(nullable = false)
    private String name; //회원 이름

    @Column(nullable = false)
    private String password; //회원 암호
    private String tel; //회원 전화번호

    @Convert(converter = MemberRoleConverter.class)
    @Column(nullable = false)
    private MemberRole role;

    @Builder
    public MemberEntity(String email, String name, String password, String tel, MemberRole role){
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
        this.role = role;
    }
}
