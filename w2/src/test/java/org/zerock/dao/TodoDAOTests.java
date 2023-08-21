package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.w2.dao.TodoDAO;
import org.zerock.w2.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
    private TodoDAO todoDAO;

    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception {
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testInsert() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title ...")
                .dueDate(LocalDate.now())
                .build();
        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception {
        List<TodoVO> list = todoDAO.selectAll();
        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testOne() throws Exception {
        TodoVO vo = todoDAO.selectOne(2L);
        System.out.println(vo);
    }

    @Test
    public void testDelete() throws Exception {
        todoDAO.deleteOne(2L);
    }

    @Test
    public void testUpdateOne() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("update test ... ")
                .tno(1L)
                .dueDate(LocalDate.of(1998,6,7))
                .finished(true)
                .build();
        todoDAO.updateOne(todoVO);
    }
}
