package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

// 저장의 경우 JPA 정석 코드

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 생성, 파라미터로 넘어가는 값은 persistence.xml에서 persistence-unit의 name 값을 넘겨주면 된다.
        // emf의 경우 애플리케이션 로딩 시점에 딱 하나만 만들면 된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            // 연관관계의 주인에게만 값을 저장!
            member.changeTeam(team);
            // 역방향(주인이 아닌 방향) 만 연관관계 설정
//            team.getMembers().add(member);
            em.persist(member);

            //영속성 초기화 - db에서 가져옴
            em.flush();
            em.clear();


            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members){
                System.out.println("m = " + m.getUsername());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // 엔티티 매니저 종료
            em.close();
        }
        // 엔티티 매니저 팩토리 종료
        emf.close();
    }
}