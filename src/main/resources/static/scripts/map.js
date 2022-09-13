window.onload = function () {
    console.log('getAllBusket');
    // getAllBusket(plan_id);
    getAllCourse(course_id);
}
var plan_id = $('input[name=plan_id]').val();
var course_id = $('input[name=course_id]').val();
// var courseId =
// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex: 1});

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch(keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);

        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        alert('검색 결과가 존재하지 않습니다.');
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {

        alert('검색 결과 중 오류가 발생했습니다.');
        return;

    }
}

function insertCourse(spot_id) {
    $.ajax({
        url: `/travel/design/insertCourse`,
        data: {
            courseId: course_id,
            spotId: spot_id
        },
        datatype: "text",
        success: function (messages) {
            console.log('insertCourse success');
            getFinalSpotAtCourse(course_id);
        }
    });
}
function getFinalSpotAtCourse(course_id) {
    $.ajax({
        url: `/travel/design/getFinalSpotAtCourse`,
        data: {
            courseId: course_id,
        },
        success: function (spot) {
            console.log('insertCourse success');
            const html = `
            <div class="m-2">
            <span class="p-2 badge rounded-pill badge-secondary">${spot.name}
            <button type="button" class="btn-close mx-1" onclick="removeCourse(course_id, ${spot.id}, this)" aria-label="Close">X</button></span>
            </div>
            `;
            $('.course').append(html);
        }
    });
}

function getAllCourse(course_id) {
    $.ajax({
        url: `/travel/design/getAllCourse`,
        data: {
            courseId: course_id
        },
        success: function (spots) {
            $(spots).each(function () {
                const html = `
            <div class="m-2">
            <span class="p-2 badge rounded-pill badge-secondary">${this.name}
            <button type="button" class="btn-close" onclick="removeCourse(course_id, ${this.id}, this)" aria-label="Close">X</button></span>
            </div>

            `;
                $('.course').append(html);
            });
        }
    });
}
function removeCourse(course_id, spot_id, btn) {
    $.ajax({
        url: `/travel/design/removeCourse`,
        data: {
            planId: plan_id,
            courseId: course_id,
            spotId: spot_id
        },
        datatype: "text",
        success: function (btn) {
            console.log('엘리먼트 삭제');
        },
        error: function () {
            alert('실패');
        }
    });
    $(btn).parent().remove();
}

function getAllBusket(plan_id) {
    // fetch(`/travel/design/getAllBusket?planId=${plan_id}`)
    //     .then(responseData => {
    //         for (const spot in responseData.data) {
    //             console.log(spot);
    //             // $('.busket').append(`
    //             //
    //             // `);
    //         }
    //     });
    $.ajax({
        url: `/travel/design/getAllBusket`, //url
        data: {
            planId: plan_id
        },  //보낼 파라미터 데이터
        success: function (spots) {
            console.log(spots);
            console.log('실행');
            $(spots).each(function () {
                const html = `
                            <div class="p-2 border-bottom border-1 my-1">
                            <a class="font-weight-bold" onclick=window.open("http://place.map.kakao.com/${this.urlId}")>${this.name}</a>
                            <br>
                            <span class="mb-1">${this.address}</span>
                            <br>
                            <div class="btn-group float-right" role="group" aria-label="Basic outlined example">
                                 <button type="button" class="btn btn-outline-primary" onclick="insertCourse(${this.id})">+</button>
                                 <button type="button" class="btn btn-outline-primary" onclick="removeAtBusket(${this.id}, this);">x</button>
                             </div>
                            </div>
                            `;
                $('.busket').append(html);
            });
        }
    });
}

function getBusket(plan_id) {
    console.log('getBusket');
    $.ajax({
        url: `/travel/design/getBusket`, //url
        data: {
            planId: plan_id
        },  //보낼 파라미터 데이터
        dataType: "json", //http통신 후 응답 데이터 타입
        success: function (spot) {
            console.log(spot);
            const html = `
                        <div class="p-2 border-bottom border-1 my-1">
                        <a class="font-weight-bold" onclick=window.open("http://place.map.kakao.com/${spot.urlId}")>${spot.name}</a>
                        <br>
                        <span class="mb-1">${spot.address}</span>
                        <br>
                        <div class="btn-group float-right" role="group" aria-label="Basic outlined example">
                             <button type="button" class="btn btn-outline-primary" onclick="insertCourse(${spot.id})">+</button>
                             <button type="button" class="btn btn-outline-primary" onclick="removeAtBusket(${spot.id}, this);">x</button>
                         </div>
                        </div>
                        `;
            $('.busket').append(html);

        }, // ajax성공 후 실행할 함수, 서버에서 response 값을 받아서 처리할 수 있다.
    });
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (var i = 0; i < places.length; i++) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function (marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function () {
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function () {
                infowindow.close();
            });

            itemEl.onmouseover = function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout = function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

    console.log(places);

    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' +
        '<button class="btn btn-primary p-2 mx-3" style="float:right" onclick="planInsert(\'' + places.place_name + '\',\'' + places.address_name + '\',\'' + places.id + '\',\'' + places.x + '\',\'' + places.y + '\')">담기</input>'
    '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

function planInsert(place_name, place_address, place_urlId, place_x, place_y) {
    console.log("name : " + place_name, "address : " + place_address, "urlId : " + place_urlId);
    console.log(plan_id);
    // fetch(`/search/design/insertSpot/${course.id}`)
    $.ajax({
        type: "post", //요청타입
        url: `/travel/design/insertSpot`, //url
        data: {
            name: place_name,
            address: place_address,
            urlId: place_urlId,
            x: place_x,
            y: place_y,
            planId: plan_id
        },  //보낼 파라미터 데이터
        dataType: "json", //http통신 후 응답 데이터 타입
        success: function (spot) {
            console.log(spot);
            getBusket(plan_id);
            // const html = `
            // <div class="p-2 border-bottom border-1 my-1">
            // <a class="font-weight-bold" onclick=window.open("http://place.map.kakao.com/${spot.urlId}")>${spot.name}</a>
            // <br>
            // <span class="mb-1">${spot.address}</span>
            // <br>
            // <div class="btn-group float-right" role="group" aria-label="Basic outlined example">
            //      <button type="button" class="btn btn-outline-primary">+</button>
            //      <button type="button" class="btn btn-outline-primary">x</button>
            //  </div>
            // </div>
            // `;
            // $('.busket').append(html);
        }, // ajax성공 후 실행할 함수, 서버에서 response 값을 받아서 처리할 수 있다.
        error: function (error) {
            alert('이미 바구니에 있습니다.');
        }
    });
}

function removeAtBusket(spot_id, btn) {
    $.ajax({
        url: `/travel/design/removeSpot`,
        data: {
            planId: plan_id,
            spotId: spot_id
        },
        datatype: "text",
        success: function (btn) {
            console.log('엘리먼트 삭제');
        },
        error: function () {
            alert('실패');
        }
    });
    $(btn).parent().parent().remove();
}


// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function (i) {
                return function () {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}