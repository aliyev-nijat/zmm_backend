
import { host } from "./events.js"


const params = new URLSearchParams(window.location.search);
const id = params.get("id");

if (!id) console.error("ID tapılmadı!");

function createEl(tag, className, parent) {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (parent) parent.appendChild(el);
  return el;
}

const detailBox = document.querySelector(".eventDetail");
const imgBox = document.querySelector(".eventDetailImgBox");
const contentBox = document.querySelector(".eventDetailContetBox");

fetch(`${host}/api/events/${id}`)
  .then((r) => r.json())
  .then((data) => fillDetail(data))
  .catch((err) => console.error("API ERROR:", err));

function fillDetail(ev) {
  const img = createEl("img", "", imgBox);
  img.src = `${host}${ev.imageUrl}`;
  img.alt = ev.title;

  let date = "";
  let time = "";

  if (ev.dateTime) {
    const [d, t] = ev.dateTime.split("T");
    date = d;
    time = t;
  }

  const dateBox = createEl("div", "eventDetailDate", contentBox);

  const dateIcon = createEl("img", "", dateBox);
  dateIcon.src = "./assets/images/icon/dateIcon.svg";
  dateIcon.alt = "date icon";

  const dateText = createEl("span", "", dateBox);
  dateText.innerHTML = `${date} saat ${time}`;

  const name = createEl("p", "eventDetailName", contentBox);
  name.innerHTML = ev.title;

  const about = createEl("p", "eventDetailContent", contentBox);
  about.innerHTML = ev.about;

  const btn = createEl("button", "", contentBox);
  btn.innerHTML = "Bilet əldə edin";

  btn.addEventListener("click", () => {
    console.log("Ətraflı məlumat klik edildi!");
  });
}
