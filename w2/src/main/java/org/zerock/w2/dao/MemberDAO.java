package org.zerock.w2.dao;

import lombok.Cleanup;
import org.zerock.w2.domain.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
    public MemberVO getWithPassword(String mid, String mpw) throws Exception {
        String query = "select mid, mpw, mname from tbl_member where mid =? and mpw=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mid);
        preparedStatement.setString(2, mpw);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        MemberVO memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .build();
        return memberVO;
    }

    // rememberMe 가 true 면 tbl_member 테이블 uuid 수정
    public void updateUUID(String mid, String uuid) throws Exception {
        String sql = "update tbl_member set uuid =? where mid =?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // =? 에 넣을 값 설정
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, mid);
        preparedStatement.executeUpdate();
    }
    // 쿠키의 uuid 이용해서 해당 사용자 정보 로딩
    public MemberVO selsectUUID(String uuid) throws Exception {

        String query = "select  where uuid=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uuid);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        MemberVO memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .uuid(resultSet.getString(4))
                .build();
        return memberVO;
    }

}
