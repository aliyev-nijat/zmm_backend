const host = "http://165.232.122.28:8080";
const slideRow = document.querySelector(".slide-row");
fetch(`${host}/api/events`)
  .then((r) => r.json())
  .then((data) => {
    const sorted = data.sort((a, b) => a.id - b.id);

    const firstEight = sorted.slice(0, 8);

    renderSlides(firstEight);
  });

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
function renderSlides(data) {
  data.forEach((ev) => {
    const slider = createEl("div", "slide-slider", slideRow);

    const imgBox = createEl("div", "sliderImgBox", slider);
    const img = createEl("img", "", imgBox);
    if (ev.imageUrl !== null) {
      img.src = `${host}${ev.imageUrl}`;
      // Şəkil yüklənəndə yeniləmə
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

  // BÜTÜN SLİDE-LƏR ƏLAVƏ OLDUQDAN SONRA
  requestUpdateThumb();
}

// updateThumb-u çox çağırmamaq üçün debounced funksiya
let updateTimeout;
function requestUpdateThumb() {
  clearTimeout(updateTimeout);
  updateTimeout = setTimeout(() => {
    document.querySelectorAll(".slide-viewport").forEach((viewport) => {
      const row = viewport.querySelector(".slide-row");
      if (row) {
        // slider.js-dəki updateThumb funksiyasını çağırırıq
        const event = new Event("scroll");
        row.dispatchEvent(event);
        window.dispatchEvent(new Event("resize"));
      }
    });
  }, 50);
}
