import { host } from "./events.js";
/* 1) Yeni selektorlar */
const courseSlideRow = document.querySelector(".course-slide-row");
const coursesContainer = document.querySelector(".courses-container"); // istəyə bağlı: bütün kurslar üçün
function createEl(tag, className, parent) {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (parent) parent.appendChild(el);
  return el;
}
function aboutSplit(text = "") {
  const words = text.trim().split(/\s+/);
  return words.length > 12 ? words.slice(0, 12).join(" ") + "..." : text;
}
/* 2) Endpoint-i dəyişirik */
fetch(`${host}/api/courses`) // ← bura dəyişdi
  .then((r) => r.json())
  .then((data) => {
    
    const firstEight = data.slice(0, 8);

    renderCourseSlides(firstEight); // funksiya adı da dəyişdi
    renderCourses(data); // istəyə bağlı: bütün kartlar
  });

/* 3) Sliders (üst sıxışdıran hissə) */
function renderCourseSlides(list) {
  list.forEach((crs) => {
    // crs = course
    const slider = createEl(
      "div",
      "slide-slider course-slide-slider",
      courseSlideRow
    );

    const imgBox = createEl("div", "course_sliderImgBox", slider);
    const img = createEl("img", "", imgBox);
    if (crs.imageUrl) {
      img.src = `${host}${crs.imageUrl}`;
      img.onload = requestUpdateThumb;
    } else {
      imgBox.style.backgroundColor = "#5b525b";
    }

    const contentBox = createEl("div", "course_sliderContetBox", slider);
    const name = createEl("p", "course_sliderName", contentBox);
    name.textContent = crs.title;


    const footer = createEl("div", "course_sliderFooter", contentBox);
    const btn = createEl("button", "", footer);
    btn.textContent = "Ətraflı məlumat";

    /* tarix əvəzinə müddət / qiymət / saat əlavə edə bilərsiniz */
    const span = createEl("span", "", footer);
    span.innerHTML = crs.duration || ""; // backend-dən gələn istənilən sahə

    btn.addEventListener("click", () => {
      window.location.href = `courseDetail.html?id=${crs.id}`; // yönləndirmə
    });
  });

  requestUpdateThumb();
}

/* 4) Bütün kurs kartları (əgər ayrı səhifədə lazımdırsa) */
function renderCourses(list) {
  list.forEach((crs) => {
    const card = createEl("div", "course-card", coursesContainer);

    const imgBox = createEl("div", "courseImgBox", card);
    const img = createEl("img", "", imgBox);
    if (crs.imageUrl) img.src = `${host}${crs.imageUrl}`;
    else imgBox.style.backgroundColor = "#5b525b";

    const contentBox = createEl("div", "courseContetBox", card);
    const name = createEl("p", "courseCardName", contentBox);
    name.textContent = crs.title;


    const footer = createEl("div", "courseCardFooter", contentBox);
    const btn = createEl("button", "", footer);
    btn.textContent = "Ətraflı məlumat";

    btn.addEventListener("click", () => {
      window.location.href = `courseDetail.html?id=${crs.id}`;
    });
  });
}

/* 5) Thumbnail yeniləmə (ehtiyac duyulan yerdə) */
let updateTimeout;
function requestUpdateThumb() {
  clearTimeout(updateTimeout);
  updateTimeout = setTimeout(() => {
    document.querySelectorAll(".course-slide-viewport").forEach((vp) => {
      const row = vp.querySelector(".course-slide-row");
      if (row) {
        row.dispatchEvent(new Event("scroll"));
        window.dispatchEvent(new Event("resize"));
      }
    });
  }, 50);
}
