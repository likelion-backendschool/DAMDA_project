package com.ll.exam.damda;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.service.search.spot.SpotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotServiceTest {

    @Autowired
    SpotService spotService;

    @Test
    public void findAllTest1() throws Exception {
        //given
        Page<SpotDto> spotList = spotService.getSpotListBy("", new ArrayList<>(), 0);

        //then
        assertThat(spotList.getNumberOfElements()).isEqualTo(8);
    }

    @Test
    public void findAllTest2() throws Exception {
        //given
        Page<SpotDto> spotList = spotService.getSpotListBy("", new ArrayList<>(Arrays.asList("인스타", "친구끼리")), 0);

        //then
        assertThat(spotList.getNumberOfElements()).isEqualTo(8);
    }
}
