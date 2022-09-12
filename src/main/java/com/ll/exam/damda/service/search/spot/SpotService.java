package com.ll.exam.damda.service.search.spot;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.search.Tag;
import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.repository.search.spot.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;

    private Specification<Spot> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Spot> s, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Spot, Review> r = s.join("reviewList", JoinType.LEFT);
                return cb.or(cb.like(s.get("name"), "%" + kw + "%"),
                        cb.like(s.get("city"), "%" + kw + "%"),
                        cb.like(s.get("address"), "%" + kw + "%"),
                        cb.like(r.get("content"), "%" + kw + "%"));
            }
        };
    }

    public SpotDto findById(Long spotId) {
        Optional<Spot> _spot = spotRepository.findById(spotId);

        if (_spot.isPresent()) {
            Spot spot = _spot.get();
            return new SpotDto().toDto(spot);
        } else {
            return null;
        }
    }

    public Page<SpotDto> getSpotListBy(String searchWord, List<String> checkedValue, int page) {
        Page<SpotDto> spotDtoList = null;
        Specification<Spot> spec = search(searchWord);

        if (searchWord == "" && checkedValue.size() == 0) {
            List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(Sort.Order.desc("reviewCnt"));
            Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
            Page<Spot> spotList = spotRepository.findAll(pageable);
            spotDtoList = new SpotDto().toDtoList(spotList);

            return spotDtoList;
        }
        else if (searchWord != "" && checkedValue.size() == 0) {
            List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(Sort.Order.desc("reviewCnt"));
            Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
            Page<Spot> spotList = spotRepository.findAll(spec, pageable);
            spotDtoList = new SpotDto().toDtoList(spotList);
        }
        else if (searchWord == "" && checkedValue.size() != 0) {
            List<Spot> spotList = spotRepository.findAll();
            List<SpotDto> _spotDtoList = filterAndSortByTag(spotList, checkedValue);

            Pageable pageable = PageRequest.of(page, 8);
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), _spotDtoList.size());
            spotDtoList = new PageImpl<>(_spotDtoList.subList(start, end), pageable, _spotDtoList.size());
        } else {
            List<Spot> spotList = spotRepository.findAll(spec);
            List<SpotDto> _spotDtoList = filterAndSortByTag(spotList, checkedValue);

            Pageable pageable = PageRequest.of(page, 8);
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), _spotDtoList.size());
            spotDtoList = new PageImpl<>(_spotDtoList.subList(start, end), pageable, _spotDtoList.size());
        }

        return spotDtoList;
    }

    public List<SpotDto> filterAndSortByTag(List<Spot> spotList, List<String> checkedValue) {
        List<SpotDto> _spotDtoList = new ArrayList<>(); // 필터된 여행지 리스트

        // 태그 카운트 및 필터
        for (Spot spot : spotList) {
            Map<Tag, Integer> tagInfo = spot.getTagMap();
            SpotDto spotDto = new SpotDto().toDto(spot);

            for (Tag tag : tagInfo.keySet()) { // 여행지 태그들
                if (checkedValue.contains(tag.getName())) { // 여행지 태그가 검색 조건에 포함
                    if (!_spotDtoList.contains(spotDto)) {
                        _spotDtoList.add(spotDto);
                    }
                    spotDto.setTagCnt(spotDto.getTagCnt() + tagInfo.get(tag));
                }
            }
        }

        // 태그 카운트를 기준으로 정렬
        Comparator<SpotDto> comparator = new Comparator<SpotDto>() {
            @Override
            public int compare(SpotDto a, SpotDto b) {
                return b.getTagCnt() - a.getTagCnt();
            }
        };

        Collections.sort(_spotDtoList, comparator);

        return _spotDtoList;
    }
}