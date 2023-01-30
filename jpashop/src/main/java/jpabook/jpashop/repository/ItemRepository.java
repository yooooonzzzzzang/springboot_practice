package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;
    public void save(Item item){
        if (item.getId() == null) {
            em.persist(item);   // 처음에 저장하기 전까진 id 가 없음
        } else {
            em.merge(item);  // 이미 디비에 적용된걸 update, 한번 persist 상태였다가 detached된 상태에서 다음 persist 상태가 됐을때
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item  i", Item.class).getResultList();
    }
}
