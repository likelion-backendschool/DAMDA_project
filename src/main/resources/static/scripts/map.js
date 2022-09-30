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

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

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
            getAllCourse(course_id);
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
            console.log('getFinalSpotAtCourse success');
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
            let html = ``;
            $(spots).each(function () {
                html += `
            <div class="m-2">
            <span class="p-2 badge rounded-pill badge-outline-primary">${this.name}
            <button type="button" class="btn btn-outline-danger" onclick="removeCourse(course_id, ${this.id}, this)" aria-label="Close">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-x" viewBox="0 0 16 16">
  <path d="M6.146 7.146a.5.5 0 0 1 .708 0L8 8.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 9l1.147 1.146a.5.5 0 0 1-.708.708L8 9.707l-1.146 1.147a.5.5 0 0 1-.708-.708L7.293 9 6.146 7.854a.5.5 0 0 1 0-.708z"/>
  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
</svg>
</button></span>
            </div>
            `;
            });
            $('.course').empty();
            console.log('course mpty');
            $('.course').append(html);
            console.log('course append');
        }

    });
    console.log('getAllCourse수행');
    // setTimeout(getAllCourse, 3000); //3초 뒤에 수행
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
        success: function () {
            console.log('엘리먼트 삭제');
        },
        error: function () {
            alert('실패');
        }
    });
    $(btn).parent().remove();
}

function getAllBusket(plan_id) {

    $.ajax({
        url: `/travel/design/getAllBusket`, //url
        data: {
            planId: plan_id
        },  //보낼 파라미터 데이터
        success: function (spots) {
            let html = ``;
            $(spots).each(function () {
                html += `
                            <div class="p-2 border-bottom border-1 my-1" style="width: 80%;">
                            <a class="font-weight-bold" style="white-space: nowrap;">${this.name}</a>
                            <br>
                            <a href="http://place.map.kakao.com/${this.urlId}" target="_blank">kakao map</a>
                            <br>
                            <span class="mb-1">${this.address}</span>
                            <br>
                            <div class="float-right">
                                 <button type="button" class="btn btn-outline-primary" onclick="insertCourse(${this.id})">
                                 <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-plus" viewBox="0 0 16 16">
                                 <path d="M8 7a.5.5 0 0 1 .5.5V9H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V10H6a.5.5 0 0 1 0-1h1.5V7.5A.5.5 0 0 1 8 7z"/>
                                 <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                                 </svg>
                                 </button>
                                 <button type="button" class="btn btn-outline-danger" onclick="removeAtBusket(${this.id}, this);">
                                 <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-x" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M6.146 8.146a.5.5 0 0 1 .708 0L8 9.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 10l1.147 1.146a.5.5 0 0 1-.708.708L8 10.707l-1.146 1.147a.5.5 0 0 1-.708-.708L7.293 10 6.146 8.854a.5.5 0 0 1 0-.708z"/>
  <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"/>
</svg>
                                 </button>
                             </div>
                            </div>
                            `;
            });
            $('.busket').empty();
            console.log('busket empty');
            $('.busket').append(html);
            console.log('busket append');
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
                        <div class="p-2 border-bottom border-1 my-1" style="width:200px;">
                        <a class="font-weight-bold" onclick=window.open("http://place.map.kakao.com/${spot.urlId}")>${spot.name}</a>
                        <br>
                        <span class="mb-1">${spot.address}</span>
                        <br>
                        <div class="float-right">
                             <button type="button" class="btn btn-outline-primary" onclick="insertCourse(${spot.id})">
                             <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-plus" viewBox="0 0 16 16">
                             <path d="M8 7a.5.5 0 0 1 .5.5V9H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V10H6a.5.5 0 0 1 0-1h1.5V7.5A.5.5 0 0 1 8 7z"/>
                             <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                             </svg>
                             </button>
                             <button type="button" class="btn btn-outline-danger" onclick="removeAtBusket(${spot.id}, this);">
                             <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-x" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M6.146 8.146a.5.5 0 0 1 .708 0L8 9.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 10l1.147 1.146a.5.5 0 0 1-.708.708L8 10.707l-1.146 1.147a.5.5 0 0 1-.708-.708L7.293 10 6.146 8.854a.5.5 0 0 1 0-.708z"/>
  <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"/>
</svg>
                             </button>
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
             '   <h5>' + places.place_name + '</h5>' +
            '<a href="http://place.map.kakao.com/' + places.id + '" target="_blank" style="text-decoration-color: #0a53be;">kakao map</a>'
    ;

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    // '  <span class="tel">' + places.phone + '</span>' +

    itemStr += '<div style="float: right;">' +
'<button class="btn btn-outline-primary p-2 mx-3 mr-1" onclick="directAddCourse(\'' + places.place_name + '\',\'' + places.address_name + '\',\'' + places.id + '\',\'' + places.x + '\',\'' + places.y + '\')">' +
        '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-plus" viewBox="0 0 16 16">' +
        '<path d="M8 7a.5.5 0 0 1 .5.5V9H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V10H6a.5.5 0 0 1 0-1h1.5V7.5A.5.5 0 0 1 8 7z"/>' +
        '<path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>' +
        '</svg> 바로 추가</button>' +
    '<button class="btn btn-outline-danger p-2 mr-1" onclick="planInsert(\'' + places.place_name + '\',\'' + places.address_name + '\',\'' + places.id + '\',\'' + places.x + '\',\'' + places.y + '\')">' +
    '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-heart-fill" viewBox="0 0 16 16">' +
      '<path d="M11.5 4v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5ZM8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1Zm0 6.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132Z"/>' +
    '</svg> 담기</button>' +
    '</div>'
    ;


    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}
function directAddCourse(place_name, place_address, place_urlId, place_x, place_y) {
    $.ajax({
        type: "post", //요청타입
        url: `/travel/design/directAddCourse`, //url
        data: {
            name: place_name,
            address: place_address,
            urlId: place_urlId,
            x: place_x,
            y: place_y,
            planId: plan_id,
            courseId: course_id
        },  //보낼 파라미터 데이터
        success: function (success) {
            console.log(success);
            getAllCourse(course_id);
        }, // ajax성공 후 실행할 함수, 서버에서 response 값을 받아서 처리할 수 있다.
        error: function (error) {
            alert('예기치 못한 오류가 발생했습니다.');
        }
    });
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
        success: function (spot) {
            console.log(spot);
            // getBusket(plan_id);
            getAllBusket(plan_id);
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