package com.comeandsee.brandscan.runner;

import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.MemberRole;
import com.comeandsee.brandscan.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class InitData implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public InitData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 관리자 계정 생성 코드
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> sourceArgs = List.of(args.getSourceArgs());
        String email;
        String password;

        if (sourceArgs.size() == 2) {
            email = sourceArgs.get(0);
            password = sourceArgs.get(1);
        } else {
            email = "brandscan@gmail.com";
            password = "admin1234!";
        }

        log.info(sourceArgs.toString());
        MemberEntity member = memberRepository.findByEmail(email);
        if (member == null) {
            // 없으면 생성
            MemberEntity newMember = MemberEntity.builder()
                    .email(email)
                    .name("관리자")
                    .password(passwordEncoder.encode(password))
                    .role(MemberRole.ADMIN)
                    .build();

            memberRepository.save(newMember);
        } else {
            log.info("관리자 계정이 존재 합니다 : " + email);
        }
    }
}
