package com.ll.exam.damda;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.service.search.spot.SpotService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotServiceTest {

    @Autowired
    SpotService spotService;

    @Test
    @DisplayName("Spot 여러개 조회")
    public void findAllTest() throws Exception {
        //given
        Page<SpotDto> spotList = spotService.getSpotListBy("", new ArrayList<>(), 0);

        //then
        assertThat(spotList.getNumberOfElements()).isEqualTo(8);
    }
}
