package hello.hellospring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

@SpringBootTest //스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional //테스트 케이스에 이 어노테이션을 사용하면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
public class MemberServiceIntegrationTest {
	
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	// ↓@Transactional가 있기때문에 필요 X
//	@AfterEach
//	public void afterEach() {
//		memberRepository.clearStore();
//	}
	
	@Test
	void 회원가입() {
		
		//given
		Member member = new Member();
		member.setName("hello");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		Member findMember = memberService.findOne(saveId).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		
		//given
		Member member1 = new Member();
		member1.setName("spring");
		
		//when
		Member member2 = new Member();
		member2.setName("spring");
		
		//then
		memberService.join(member1);
		IllegalStateException e = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		
		Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
}
