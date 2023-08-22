package org.zerock.w2.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.w2.Util.MapperUtil;
import org.zerock.w2.dao.MemberDAO;
import org.zerock.w2.domain.MemberVO;
import org.zerock.w2.dto.MemberDTO;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService(){
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO lgoin(String mid, String mpw) throws Exception {
        // db 에서 mid , mpw 로 user 찾기
        MemberVO vo = dao.getWithPassword(mid, mpw);
        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);
        return memberDTO;
    }

}
