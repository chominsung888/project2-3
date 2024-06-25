// TMDB API 키
const tmdbApiKey = 'ba65bca7bd473078269aa9976068045a';

// 영화 검색 함수
function searchMovie(query) {
    const apiUrl = `https://api.themoviedb.org/3/search/movie?api_key=${tmdbApiKey}&query=${query}`;

    $.ajax({
        url: apiUrl,
        dataType: 'json',
        type: 'GET',
        success: function(result) {
            displayMovies(result.results);
        },
        error: function(error) {
            console.error('Error fetching movie data:', error);
        }
    });
}

// 영화 정보를 화면에 표시하는 함수
function displayMovies(movies) {
    const movieContainer = $('#movie-container');
    movieContainer.empty();

    movies.forEach(movie => {
        const title = movie.title;
        const releaseDate = movie.release_date;
        const posterPath = movie.poster_path ? `https://image.tmdb.org/t/p/w500/${movie.poster_path}` : 'https://via.placeholder.com/500x750';

        const movieElement = `
            <div class="movie">
                <img src="${posterPath}" alt="${title}" />
                <div class="movie-info">
                    <h3>${title}</h3>
                    <p>Released: ${releaseDate}</p>
                </div>
            </div>
        `;

        movieContainer.append(movieElement);
    });
}


$(document).ready(function() {
    // 페이지 로드 시 기본적으로 인기 있는 영화를 표시
    searchMovie('popular');

    // 검색 버튼에 대한 클릭 이벤트 핸들러 추가
    $('#search-button').click(function() {
        // 검색어 입력란에서 검색어를 가져옴
        var query = $('#search-input').val();
        // 가져온 검색어로 영화 검색
        searchMovie(query);
    });
});