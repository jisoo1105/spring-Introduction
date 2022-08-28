package hello.hellospring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hello.hellospring.domain.Member;

public class MemoryMemberRepository implements MemberRepository{
	
	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L; 
	
	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}
	
	@Override
	public Optional<Member> findById(Long id) {
		//요즘에는 null이 반화될 가능성이 있으면 optional로 한번 감싼다.
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}

}
