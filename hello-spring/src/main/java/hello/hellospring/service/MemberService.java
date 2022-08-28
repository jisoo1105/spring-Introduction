package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/**
	 * 회원가입
	 * @param member
	 * @return
	 */
	public Long join(Member member) {
		//같은 이름이 있는 중복 회원 X
		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
		
	}
	
	/**
	 * 중복 회원 검증
	 * @param member
	 */
	private void validateDuplicateMember(Member member) {
		
		//Optional을 직접적으로 반환하여 사용하는 것은 별로 좋지 않다. ↓
		//Optional<Member> result = memberRepository.findByName(member.getName());
		//result.ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
		
		memberRepository.findByName(member.getName())
						.ifPresent(m -> {
							throw new IllegalStateException("이미 존재하는 회원입니다.");
						});
	}
	
	/**
	 * 전체 회원 조회
	 * @return
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	/**
	 * 회원 조회
	 * @param memberId
	 * @return
	 */
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
	
}
