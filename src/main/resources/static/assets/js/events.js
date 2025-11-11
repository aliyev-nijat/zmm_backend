const host = "";
const slideRow = document.querySelector(".slide-row");
fetch(`${host}/api/events`)
  .then((r) => r.json())
  .then((data) => renderSlides(data));
//   .then((data) => console.log(data[8].imageUrl));

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

    let pointerMoved = false;
    slider.addEventListener("pointerdown", () => (pointerMoved = false));
    slider.addEventListener("pointermove", () => (pointerMoved = true));

    btn.addEventListener("click", (e) => {
      console.log("salam");
      
      if (pointerMoved) return; // sürüşdürmədirsə nəticə vermə
      window.location.href = `eventDetail.html?id=${ev.id}`;
    });
    
  });
}
