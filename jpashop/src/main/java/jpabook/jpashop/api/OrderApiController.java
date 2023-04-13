package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    // 1. 엔티티 직접 노출 - 하면 안된다~
    // * - Hibernate5Module 모듈 등록, LAZY=null 처리
    // * - 양방향 관계 문제 발생 -> @JsonIgnor
    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();   // lazy 강제 초기화
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            /*
            for (OrderItem orderItem : orderItems){
                orderItem.getItem().getName();
            }  아래에 람다식 이용
             */
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }
    // 2. 엔티티를 DTO 변환 - 하면 안된다~
    // * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
    // * - 단점: 지연로딩으로 쿼리 N번 호출

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
}
