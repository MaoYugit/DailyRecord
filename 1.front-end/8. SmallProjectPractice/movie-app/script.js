// 获取热门电影列表
const API_URL =
  "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3fd2be6f0c70a2a598f084ddfb75487c&page=1";
// 电影海报图片的基础路径。
// 从API获取到的图片信息只是一个文件名（如 /xxxxx.jpg）
// 需要和这个路径拼接才能得到完整的图片网址
const IMG_PATH = "https://image.tmdb.org/t/p/w1280";
// 用于搜索电影的基础链接。之后会把用户的搜索关键词拼接到这个链接的末尾。
const SEARCH_API =
  'https://api.themoviedb.org/3/search/movie?api_key=3fd2be6f0c70a2a598f084ddfb75487c&query="';

const main = document.getElementById("main");
const form = document.getElementById("form");
const search = document.getElementById("search");

// 程序入口，页面一加载就调用这个函数获取热门电影列表
getMovies(API_URL);

async function getMovies(url) {
  // 使用 fetch API 向传入的 url 地址发送网络请求
  // await 会暂停函数的执行，直到请求完成并收到服务器的响应（res）
  const res = await fetch(url);

  //  将服务器返回的响应（res）解析为 JSON 格式的数据
  const data = await res.json();

  // 从返回的数据 data 中，取出包含所有电影信息的 results 数组
  // 然后把它交给 showMovies 函数去处理和展示。
  showMovies(data.results);
}

// 将电影数据渲染到页面上
function showMovies(movies) {
  main.innerHTML = ""; // 清空 main 容器，准备添加新的电影元素

  movies.forEach((movie) => {
    const { title, poster_path, vote_average, overview } = movie; // 对象解构

    const movieEl = document.createElement("div");
    movieEl.classList.add("movie");

    movieEl.innerHTML = `
            <img src="${IMG_PATH + poster_path}" alt="${title}">
            <div class="movie-info">
                <h3>${title}</h3>
                <span class="${getClassByRate(
                  vote_average
                )}">${vote_average}</span>
            </div>
            <div class="overview">
                <h3>Overview</h3>
                ${overview}
            </div>
        `;
    main.appendChild(movieEl);
  });
}

function getClassByRate(vote) {
  if (vote >= 8) {
    return "green";
  } else if (vote >= 5) {
    return "orange";
  } else {
    return "red";
  }
}

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const searchTerm = search.value;

  if (searchTerm && searchTerm !== "") {
    getMovies(SEARCH_API + searchTerm);

    search.value = "";
  } else {
    window.location.reload();
  }
});
