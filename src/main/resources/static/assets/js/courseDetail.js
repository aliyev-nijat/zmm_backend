    import { host } from "./events.js"

const courseId = new URLSearchParams(window.location.search).get("id");

// Helper - element yaratmaq
function createEl(tag, className, parent, text) {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (text) el.innerHTML = text;
  if (parent) parent.appendChild(el);
  return el;
}

fetch(`${host}/api/courses/${courseId}`)
  .then(res => res.json())
  .then(data => renderCourseDetail(data))
  .catch(err => console.log("Error:", err));

function renderCourseDetail(data) {

  // ==========================
  //  1) ŞƏKİL
  // ==========================
  const imgBox = document.querySelector(".courseDetailImgBox");
  imgBox.innerHTML = ""; // təmizlə

  const img = createEl("img", "courseDetailImg", imgBox);
  img.src = `${host}${data.imageUrl}`;
  img.alt = data.title;

  // ==========================
  //  2) COURSE INFORMASIYALARI
  // ==========================
  const contetBox = document.querySelector(".courseDetailContetBox");
  contetBox.innerHTML = "";

  createEl("p", "courseDetailName", contetBox, data.title);
  createEl("p", "courseDetailCoontent", contetBox, data.about);

  const btnBox = createEl("div", "courseDetailBtnBox", contetBox);
  const btn = createEl("button", "", btnBox, "Müraciət et");
  btn.addEventListener("click", () => {
    window.location.href = `https://wa.me/994501234567?text=Kurs: ${data.title}`;
  });

  // ==========================
  //  3) MÜƏLLİM MƏLUMATI
  // ==========================
  const teacherBox = document.querySelector(".courseTeacher");
  teacherBox.innerHTML = "";

  const teacherImgBox = createEl("div", "courseTeacherImg", teacherBox);
  const tImg = createEl("img", "teacherImg", teacherImgBox);
  tImg.src = `${host}${data.teacherImageUrl}` ;
  tImg.alt = data.teacher;

  const teacherAbout = createEl("div", "courseTeacherAbout", teacherBox);
  createEl("p", "teacherAboutName", teacherAbout, `${data.teacherFirstName} ${data.teacherLastName}`  );
  createEl("p", "teacherAboutContent", teacherAbout, data.teacherAbout);
}
