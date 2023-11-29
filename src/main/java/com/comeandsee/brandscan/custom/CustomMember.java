package com.comeandsee.brandscan.custom;

import com.comeandsee.brandscan.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
public class CustomMember extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private String email;
    private LocalDateTime createdAt;

    public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomMember(MemberEntity member, List<GrantedAuthority> authorities) {
        super(member.getName(), member.getPassword(), authorities);

        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
    }
}
