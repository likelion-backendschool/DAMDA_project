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

    /*
    핵심 서비스 메서드
    */
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
        Page<SpotDto> spotDtoPages;
        Specification<Spot> spec;

        if (searchWord.equals("") && checkedValue.size() == 0) {
            System.out.println("아무런 조건 없음");
            Pageable pageable = getPageRequest(page, 8, "reviewCnt");
            Page<Spot> spotPages = spotRepository.findAllEntityGraph(pageable);
            spotDtoPages = new SpotDto().toDtoList(spotPages);
        }
        else if (!searchWord.equals("") && checkedValue.size() == 0) {
            System.out.println("검색어만 있음");
            spec = search(searchWord);
            Pageable pageable = getPageRequest(page, 8, "reviewCnt");
            Page<Spot> spotPages = spotRepository.findAll(spec, pageable);
            spotDtoPages = new SpotDto().toDtoList(spotPages);
        }
        else if (searchWord.equals("") && checkedValue.size() != 0) {
            System.out.println("검색 조건만 있음");
            List<Spot> spotList = spotRepository.findAllEntityGraph();
            spotDtoPages = filterAndSortByTag(page, spotList, checkedValue);
        } else {
            System.out.println("둘다 있음");
            spec = search(searchWord);
            List<Spot> spotList = spotRepository.findAll(spec);
            spotDtoPages = filterAndSortByTag(page, spotList, checkedValue);
        }

        return spotDtoPages;
    }

    @Transactional(readOnly = false)
    public Spot create(String name, String address, long urlId, String x, String y) {
        Optional<Spot> _spot = spotRepository.findByUrlId(urlId);

        if (_spot.isPresent()) {
            return _spot.get();
        } else {
            Spot spot = Spot.builder()
                    .name(name)
                    .address(address)
                    .urlId(urlId)
                    .x(x)
                    .y(y)
                    .selfMadeFlag("N")
                    .build();

            spotRepository.save(spot);

            return spot;
        }
    }

    public Spot getSpot(long spotId) {
        Optional<Spot> optionalSpot = spotRepository.findById(spotId);
        if(optionalSpot.isPresent()) {
            return optionalSpot.get();
        } else {
            return null;
        }
    }
    public Spot getSpotByUrlId(long urlId) {
        Optional<Spot> optionalSpot = spotRepository.findByUrlId(urlId);
        if(optionalSpot.isPresent()) {
            return optionalSpot.get();
        } else return null;

    }

    @Transactional(readOnly = false)
    public Spot cloneSpot(Spot spot) {
        Spot spotClone = new Spot();
        spotClone.setName(spot.getName());
        spotClone.setAddress(spot.getAddress());
        spotClone.setUrlId(spot.getUrlId());
        spotClone.setX(spot.getX());
        spotClone.setY(spot.getY());
        spotRepository.save(spotClone);
        return spotClone;
    }

    /*
    핵심 서비스 메서드에서 필요한 메서드
    */
    private Specification<Spot> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Spot> s, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Spot, Review> r = s.join("reviews", JoinType.LEFT);

                Predicate predicateSearchCondition = cb.or(cb.like(s.get("name"), "%" + kw + "%"),
                        cb.like(s.get("city"), "%" + kw + "%"),
                        cb.like(s.get("address"), "%" + kw + "%"),
                        cb.like(r.get("content"), "%" + kw + "%"));

                Predicate predicateSelfMadeFlag = cb.equal(s.get("selfMadeFlag"), "Y");

                return cb.and(predicateSelfMadeFlag, predicateSearchCondition);
            }
        };
    }

    public Pageable getPageRequest(int page, int pageSize, String condition) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(condition));
        return PageRequest.of(page, pageSize, Sort.by(sorts));
    }

    public Page<SpotDto> filterAndSortByTag(int page, List<Spot> spotList, List<String> checkedValue) {
        List<SpotDto> spotDtoList = new ArrayList<>(); // 필터된 여행지 리스트

        // 태그 카운트 및 필터
        for (Spot spot : spotList) {
            Map<Tag, Integer> tagInfo = spot.getTagMap();
            SpotDto spotDto = new SpotDto().toDto(spot);

            for (Tag tag : tagInfo.keySet()) { // 여행지 태그들
                if (checkedValue.contains(tag.getName())) { // 여행지 태그가 검색 조건에 포함
                    if (!spotDtoList.contains(spotDto)) {
                        spotDtoList.add(spotDto);
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

        Collections.sort(spotDtoList, comparator);

        Pageable pageable = PageRequest.of(page, 8);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), spotDtoList.size());
        Page<SpotDto> spotDtoPages = new PageImpl<>(spotDtoList.subList(start, end), pageable, spotDtoList.size());

        return spotDtoPages;
    }

    public void removeCloneSpot(long spotId) {
//        Spot spot = getSpot(spotId);
        spotRepository.deleteById(spotId);
    }
}
