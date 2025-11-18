export const host = "http://165.232.122.28:8080";

const slideRow = document.querySelector(".slide-row");
const eventsContainer = document.querySelector(".events-container");
fetch(`${host}/api/events`)
  .then((r) => r.json())
  .then((data) => {
    const sorted = data.sort((a, b) => a.id - b.id);

    const firstEight = sorted.slice(0, 8);

    renderSlides(firstEight);
    renderEvents(sorted);
  });

function createEl(tag, className, parent) {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (parent) parent.appendChild(el);
  return el;
}
function charsPerLine() {
  const w = window.innerWidth;
  if (w <  480) return 37;   /* mobil */
  if (w <  768) return 50;   /* planşet */
  return 70;                 /* desktop */
}

function aboutSplit(text = '', maxLines = 3) {
  if (!text) return '';
  const maxChars = charsPerLine() * maxLines;
  if (text.length <= maxChars) return text;
  const cut = text.slice(0, maxChars).replace(/\s+\S*$/, ''); // son kəlməni tam kəsmə
  return cut + '…';
}
function renderSlides(data) {
  data.forEach((ev) => {
    const slider = createEl("div", "slide-slider", slideRow);

    const imgBox = createEl("div", "sliderImgBox", slider);
    const img = createEl("img", "", imgBox);
    if (ev.imageUrl !== null) {
      img.src = `${host}${ev.imageUrl}`;
      img.onload = requestUpdateThumb;
    } else {
      imgBox.style.backgroundColor = "#5b525b";
    }

    const contentBox = createEl("div", "sliderContetBox", slider);
    const name = createEl("p", "sliderName", contentBox);
    name.textContent = ev.title;

    const desc = createEl("p", "sliderContent", contentBox);
    desc.textContent = aboutSplit(ev.about);

    const footer = createEl("div", "sliderFooter", contentBox);
    const btn = createEl("button", "", footer);
    btn.textContent = "Ətraflı məlumat";

    const [datePart, timePart] = ev.dateTime.split("T");
    const span = createEl("span", "", footer);
    span.innerHTML = `${datePart}<br>${timePart}`;

    btn.addEventListener("click", (e) => {
      window.location.href = `eventDetail.html?id=${ev.id}`;
    });
  });

  requestUpdateThumb();
}
let resizeTimeout;
window.addEventListener('resize', () => {
  clearTimeout(resizeTimeout);
  resizeTimeout = setTimeout(() => {
    document.querySelectorAll('.course_sliderContent, .courseCardContent')
            .forEach(el => el.textContent = aboutSplit(el.dataset.fullText || el.textContent));
  }, 150);
});
function renderEvents(list) {
  list.forEach((ev) => {
    const card = createEl("div", "event-card", eventsContainer);

    const imgBox = createEl("div", "eventImgBox", card);
    const img = createEl("img", "", imgBox);
    if (ev.imageUrl !== null) {
      img.src = `${host}${ev.imageUrl}`;
    } else {
      imgBox.style.backgroundColor = "#5b525b";
    }

    const contentBox = createEl("div", "eventContetBox", card);

    const name = createEl("p", "eventCardName", contentBox);
    name.textContent = ev.title;

    const desc = createEl("p", "eventCardContent", contentBox);
    desc.textContent = aboutSplit(ev.about);

    const footer = createEl("div", "eventCardFooter", contentBox);
    const btn = createEl("button", "", footer);
    btn.textContent = "Ətraflı məlumat";

    const [datePart, timePart] = ev.dateTime.split("T");
    const span = createEl("span", "", footer);
    span.innerHTML = `${datePart}<br>${timePart}`;

    btn.addEventListener("click", () => {
      window.location.href = `eventDetail.html?id=${ev.id}`;
    });
  });
}

let updateTimeout;
function requestUpdateThumb() {
  clearTimeout(updateTimeout);
  updateTimeout = setTimeout(() => {
    document.querySelectorAll(".slide-viewport").forEach((viewport) => {
      const row = viewport.querySelector(".slide-row");
      if (row) {
        const event = new Event("scroll");
        row.dispatchEvent(event);
        window.dispatchEvent(new Event("resize"));
      }
    });
  }, 50);
}
