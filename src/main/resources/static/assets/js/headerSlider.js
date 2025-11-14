const tempData = [
  {
    title: "ZiMODA2025",
    subtitle: "ZiRaDa ZOVQ Va YARADICILIQ MaRKazi",
    date: "30 AVQUST 2025",
    warning: "QEYDiYYAT UCUN SON TARiX 15 AVQUST",
  },
  {
    title: "Növbəti Slayd",
    subtitle: "Alt başlıq 2",
    date: "25 SENTYABR 2025",
    warning: "SON TARİX 10 SENTYABR",
  },
];

fetch(`${host}/api/slider`)
  .then((r) => r.json())
  .then((data) => {
    buildSlides(data);
  });

function buildSlides(list) {
  const slider = document.getElementById("slider");
  const dotsContainer = document.querySelector(".dots-container");
  slider.innerHTML = "";
  dotsContainer.innerHTML = "";

  list.forEach((it, idx) => {
    const s = document.createElement("div");
    s.className = "slide" + (idx === 0 ? " active" : "");

    s.style.backgroundImage = `url(${host}${it.imageUrl})`;
    s.style.backgroundSize = "cover";
    s.style.backgroundPosition = "center";

 

    slider.appendChild(s);
  });

  const dots = document.createElement("div");
  dots.className = "dots-wrapper";

  const left = document.createElement("span");
  const leftimg = document.createElement("img");
  leftimg.src = "./assets/images/icon/ArrowLeft.svg";
  left.onclick = () => goToSlide(current - 1);
  left.append(leftimg);
  dots.appendChild(left);

  const dotsInner = document.createElement("div");
  dotsInner.className = "dots";
  list.forEach((_, i) => {
    const d = document.createElement("span");
    d.className = "dot" + (i === 0 ? " active" : "");
    d.onclick = () => goToSlide(i);
    dotsInner.appendChild(d);
  });
  dots.appendChild(dotsInner);

  const right = document.createElement("span");
  const rightimg = document.createElement("img");
  right.className = "arrow-dot right-dot";
  rightimg.src = "./assets/images/icon/ArrowRight.svg";

  right.onclick = () => nextSlide();
  right.append(rightimg);
  dots.appendChild(right);

  dotsContainer.appendChild(dots);
}

let current = 0;
function goToSlide(n) {
  const slides = document.querySelectorAll(".slide");
  const dots = document.querySelectorAll(".dot");
  slides[current].classList.remove("active");
  dots[current].classList.remove("active");
  current = (n + slides.length) % slides.length;
  slides[current].classList.add("active");
  dots[current].classList.add("active");
}
function nextSlide() {
  goToSlide(current + 1);
}

const slider = document.getElementById("slider");
let startX = 0,
  endX = 0;
slider.addEventListener("touchstart", (e) => (startX = e.touches[0].clientX));
slider.addEventListener("touchend", (e) => {
  endX = e.changedTouches[0].clientX;
  handleSwipe();
});
slider.addEventListener("mousedown", (e) => (startX = e.clientX));
slider.addEventListener("mouseup", (e) => {
  endX = e.clientX;
  handleSwipe();
});
function handleSwipe() {
  if (endX < startX - 40) nextSlide();
  if (endX > startX + 40) goToSlide(current - 1);
}

buildSlides(tempData);
setInterval(nextSlide, 5000);
