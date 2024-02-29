package com.example.b02.repository.search;

import com.example.b02.domain.Board;
import com.example.b02.domain.QBoard;
import com.example.b02.domain.QReply;
import com.example.b02.dto.BoardListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.expression.spel.ast.Projection;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    /**
     * query dsl
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     * param domainclass must not be {@literal null}.
     */
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board; // Q 도메인 객체
        JPQLQuery<Board> query = from(board); // select from board
        query.where(board.title.contains("1")); // where title like %1
        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }

    // 제목 t, 내용 c, 작성자 w 조합으로 검색
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        // 검색 조건과 키워드가 있다면
        if ((types != null && types.length > 0) && keyword != null ){
            BooleanBuilder booleanBuilder = new BooleanBuilder(); //  '('
            for (String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;

                }
            } // end for
            query.where(booleanBuilder);
        } // end if
        // bno > 0
        query.where(board.bno.gt(0L));
        //paging
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        JPQLQuery<Board> query = from(board);
        // join
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);

        // 검색 조건과 키워드가 있다면
        if ((types != null && types.length > 0) && keyword != null ){
            BooleanBuilder booleanBuilder = new BooleanBuilder(); //  '('
            for (String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;

                }
            } // end for
            query.where(booleanBuilder);
        } // end if
        // bno > 0
        query.where(board.bno.gt(0L));

        // projection bean : jpql result -> dto
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,
                board.title,
                board.writer,
                board.regDate,
                reply.count().as("replyCount")));

        //paging
        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, count);
    }
}
