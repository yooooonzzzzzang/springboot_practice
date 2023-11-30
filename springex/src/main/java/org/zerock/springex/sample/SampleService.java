package org.zerock.springex.sample;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ToString
@RequiredArgsConstructor // 생성자 주입, + final
public class SampleService {

    private final SampleDAO sampleDAO;
}
