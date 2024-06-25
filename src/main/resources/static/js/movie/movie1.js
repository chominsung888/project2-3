$(document).ready(function() {
    fetchMovies();
});

// TMDB API 키
const tmdbApiKey = 'ba65bca7bd473078269aa9976068045a';
// 영화진흥위원회 API 키
const koficApiKey = 'a00c456adf2ded3c39df93f44cf40503';

// 어제의 날짜 구하기
const today = new Date();
const yesterday = new Date(today);
yesterday.setDate(today.getDate() - 1); // 어제 날짜로 설정

const year = yesterday.getFullYear();
let month = yesterday.getMonth() + 1;
let day = yesterday.getDate();

// 월과 일이 한 자리 숫자인 경우 두 자리로 변경
month = month < 10 ? '0' + month : month;
day = day < 10 ? '0' + day : day;

const targetDate = `${year}${month}${day}`; // YYYYMMDD 형식의 문자열

// 어제의 박스오피스를 가져오는 서버 API URL
const koficApiUrl = `/api/movieList?key=${koficApiKey}&targetDt=${targetDate}`;

// 영화 목록을 가져오고 처리하는 함수
function fetchMovies() {
    // 서버 측 엔드포인트를 사용하여 영화 목록 가져오기
    $.ajax({
        url: koficApiUrl,
        type: 'GET',
        dataType: 'json',
        success: function(result) {
            try {
                // 서버에서 데이터를 가져온 후 처리
                const movieData = JSON.parse(result.movie); // 문자열로 된 JSON을 객체로 파싱
                if (movieData && movieData.boxOfficeResult && movieData.boxOfficeResult.dailyBoxOfficeList) {
                    let dailyBoxOfficeList = movieData.boxOfficeResult.dailyBoxOfficeList;
                     let DayShowRange = movieData.boxOfficeResult.showRange;
                    console.log("DayShowRange: ", DayShowRange);
                    console.log("Daily Box Office List: ", dailyBoxOfficeList);

                    if (Array.isArray(dailyBoxOfficeList)) {
                        dailyBoxOfficeList.forEach(movie => {
                            const movieTitle = movie.movieNm; // 영화 제목
                            const releaseDate = movie.openDt; // 개봉일
                            const movieCode = movie.movieCd; // 영화 코드
                            const audiAcc = movie.audiAcc; // 누적관객수
                            const rank = movie.rank; // 순위

                            // 영화 제목을 기반으로 TMDB API를 이용하여 영화의 상세 정보 가져오기
                            fetchMovieDetails(movieTitle, releaseDate, movieCode, audiAcc, rank,DayShowRange);
                        });
                    } else {
                        console.error('Daily Box Office List is not an array: ', dailyBoxOfficeList,DayShowRange);
                    }
                } else {
                    console.error('Missing dailyBoxOfficeList in response:', movieData);
                }
            } catch (error) {
                console.error('Error parsing JSON response:', error);
            }
        },
        error: function(error) {
            console.error('Error fetching movie data from server:', error);
        }
    });
}

// 영화 상세 정보를 가져오고 처리하는 함수
function fetchMovieDetails(movieTitle, releaseDate, movieCode, audiAcc, rank,DayShowRange) {
    // TMDB API 검색 엔드포인트
    const tmdbSearchUrl = `https://api.themoviedb.org/3/search/movie?api_key=${tmdbApiKey}&query=${encodeURIComponent(movieTitle)}&year=${releaseDate.substring(0, 4)}`;

    // TMDB API를 사용하여 영화 상세 정보 가져오기
    $.ajax({
        url: tmdbSearchUrl,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // 영화 상세 정보를 가져온 후 처리
            const movieDetails = data.results[0];
            if (movieDetails) {
                const posterPath = movieDetails.poster_path;
                const movieOverview = movieDetails.overview; // 영화 줄거리
                const movieOriginalLanguage = movieDetails.original_language; // 영화 언어

                // 한글로 번역된 줄거리 가져오기
                if (movieOriginalLanguage !== 'ko') {
                    fetchKoreanOverview(movieDetails.id, movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange);
                } else {
                    // 영화 정보 화면에 표시
                    displayMovieInfo(movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange);
                }
            }
        },
        error: function(error) {
            console.error('Error fetching movie details from TMDB:', error);
        }
    });
}

// 영화의 한글 줄거리를 가져오는 함수
function fetchKoreanOverview(movieId, movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange) {
    const koreanOverviewUrl = `https://api.themoviedb.org/3/movie/${movieId}?api_key=${tmdbApiKey}&append_to_response=translations`;
    $.ajax({
        url: koreanOverviewUrl,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            const koreanOverview = data.translations.translations.find(translation => translation.iso_639_1 === 'ko');
            if (koreanOverview) {
                // 한글로 번역된 줄거리가 있을 경우 사용
                displayMovieInfo(movieTitle, posterPath, koreanOverview.data.overview, releaseDate, audiAcc, movieCode, rank,DayShowRange);
            } else {
                // 한글 번역이 없을 경우 영화 정보 화면에 표시 (원본 영어 줄거리 사용)
                displayMovieInfo(movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange);
            }
        },
        error: function(error) {
            console.error('Error fetching Korean overview from TMDB:', error);
            // 영화 정보 화면에 표시 (원본 영어 줄거리 사용)
            displayMovieInfo(movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange);
        }
    });
}

// 영화 정보를 화면에 표시하는 함수
function displayMovieInfo(movieTitle, posterPath, movieOverview, releaseDate, audiAcc, movieCode, rank,DayShowRange) {
    const posterUrl = `https://image.tmdb.org/t/p/w500/${posterPath}`;
      const showRangeData =`
        <div class="title">
            <h2>일일 박스오피스</h2>
              <p  class="showRange">단위 기간: ${DayShowRange}<p>
        </div>`;
    const movieHtml = `
        <div class="movie">
            <img src="${posterUrl}" alt="Movie Poster">
            <p>순위: ${rank}위 </p>
            <p>영화코드: ${movieCode}</p>
            <h2 class="movieTitle" data-moviecode="${movieCode}" data-title="${movieTitle}" data-poster="${posterUrl}" data-overview="${movieOverview}" data-release="${releaseDate}">${movieTitle}</h2>
            <p>개봉일: ${releaseDate}</p>
            <p>누적관객수: ${audiAcc}명</p>
        </div>
    `;
    // 현재 추가하는 영화의 순위와 비교하여 적절한 위치에 추가 -> 순위 정렬
    let inserted = false;
    $('.movie').each(function() {
        const currentRank = parseInt($(this).find('p:eq(0)').text().replace('순위: ', ''));
        if (rank < currentRank) {
            $(this).before(movieHtml);
            inserted = true;
            return false; // break each loop
        }
    });
    if (!inserted) {
        $('#movie-container').append(movieHtml); // 맨 마지막에 추가
    }
     // 한 번만 추가되도록 조건을 설정
           if (!$('.title').length) {
               $('#title').append(showRangeData); // 맨 마지막에 추가
           }
    console.log("movieCode displayed: " + movieCode);

    // 영화 제목을 클릭했을 때 실행될 함수를 설정합니다.
    $('.movieTitle').off('click').on('click', function() {
        const movieCodeClicked = $(this).data('moviecode');  // 'moviecode'를 정확히 사용
         const clickedMovieTitle = $(this).data('title');
         const clickedMovieOverview = $(this).data('overview');
        console.log('Movie clicked:', movieCodeClicked);
        fetchMovieInfo(movieCodeClicked,clickedMovieTitle,clickedMovieOverview);
    });
}

// 영화 이름 클릭 시 영화 코드를 가져옴
function fetchMovieInfo(movieCode,movieTitle,movieOverview) {
    const apiUrl = `/api/movieListId?movieCode=${movieCode}`;
    console.log("Fetching movie info for movieCode: " + movieCode);

    fetch(apiUrl)
        .then(response => response.json())
        .then(function(result) {
            const movieInfo = JSON.parse(result.movie).movieInfoResult.movieInfo;

            const container = document.getElementById('movie-info');

            // actors, directors, companies, genres, showTypes 처리
            const actors = movieInfo.actors.map(actor => actor.peopleNm).join(', ');
            const directors = movieInfo.directors.map(director => director.peopleNm).join(', ');
            const companies = movieInfo.companys.map(company => company.companyNm).join(', ');
            const genres = movieInfo.genres.map(genre => genre.genreNm).join(', ');
            const showTypes = movieInfo.showTypes.map(showType => showType.showTypeNm).join(', ');

            console.log("movieInfo fetched: ", movieInfo);
            console.log("container: " + container);
            container.innerHTML = '';

            // 영화 상세 정보를 가져와서 표시
            let div = document.createElement('div');
            div.className = 'movie-info';

            let movieNm = document.createElement('p');
            let movieNmEn = document.createElement('p');
            let openDt = document.createElement('p');
            let prdtStatNm = document.createElement('p');
            let prdtYear = document.createElement('p');
            let showTm = document.createElement('p');
            let typeNm = document.createElement('p');
            let overview = document.createElement('p');
            let directorsEl = document.createElement('p');
            let actorsEl = document.createElement('p');
            let companiesEl = document.createElement('p');
            let genresEl = document.createElement('p');
            let showTypesEl = document.createElement('p');

            movieNm.innerText = '영화명: ' + movieInfo.movieNm;
            movieNmEn.innerText = '영어 제목: ' + movieInfo.movieNmEn;
            openDt.innerText = '개봉일: ' + movieInfo.openDt;
            prdtStatNm.innerText = '제작 상태: ' + movieInfo.prdtStatNm;
            prdtYear.innerText = '제작년도: ' + movieInfo.prdtYear + '년';
            showTm.innerText = '상영 시간: ' + movieInfo.showTm + '분';
            typeNm.innerText = '타입: ' + movieInfo.typeNm;
            overview.innerText = '줄거리: ' + movieOverview;

            directorsEl.innerText = '감독: ' + directors;
            actorsEl.innerText = '배우: ' + actors;
            companiesEl.innerText = '배급사: ' + companies;
            genresEl.innerText = '장르: ' + genres;
            showTypesEl.innerText = '종류: ' + showTypes;

            div.appendChild(movieNm);
            div.appendChild(movieNmEn);
            div.appendChild(openDt);
            div.appendChild(prdtStatNm);
            div.appendChild(prdtYear);
            div.appendChild(showTm);
            div.appendChild(typeNm);
            div.appendChild(overview);
            div.appendChild(directorsEl);
            div.appendChild(actorsEl);
            div.appendChild(companiesEl);
            div.appendChild(genresEl);
            div.appendChild(showTypesEl);
            container.appendChild(div);

            // 모달 창을 표시
            document.getElementById('movieModal').style.display = 'block';
            console.log('Modal should be displayed');
        })
        .catch(error => {
            console.error('Error fetching movie details from server:', error);
        });
}


// 모달 창 닫기
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.close').addEventListener('click', function() {
        document.getElementById('movieModal').style.display = 'none';
    });

    // 모달 창 외부 클릭 시 닫기
    window.addEventListener('click', function(event) {
        if (event.target === document.getElementById('movieModal')) {
            document.getElementById('movieModal').style.display = 'none';
        }
    });
});
