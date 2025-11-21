import { host } from "./events.js";

const about_contentBox = document.querySelector(".about-contentBox");
const about_imgBox = document.querySelector(".about-imgBox");
const contact_infoText = document.querySelectorAll(".contact-infoText");
const contact_infoSocial = document.querySelectorAll(".contact-infoSocial");

fetch(`${host}/api/settings`)
  .then((r) => r.json())
  .then((data) => {
    getDataAbout(data);
  });
function createEl(tag, className, parent) {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (parent) parent.appendChild(el);
  return el;
}
function getDataAbout(data) {
  const aboutText = createEl("p", "", about_contentBox);
  aboutText.innerHTML = data.about;

  const aboutImg = createEl("img", "", about_imgBox);
  aboutImg.src = `${host}${data.imageUrl}`;

  contact_infoText.forEach((container) => {
    // Əgər içində artıq məlumat varsa təmizləmək istəyirsənsə (tövsiyə olunur)
    container.innerHTML = "";



    const phoneP = createEl("p", "", container);
    phoneP.innerHTML = `Telefon: ${data.contactPhone}`;

    const daysP = createEl("p", "", container);
    daysP.innerHTML = `İş günləri: ${data.workDays}`;

    const hoursP = createEl("p", "", container);
    hoursP.innerHTML = `İş saatları: ${data.workHours}`;
  });

  contact_infoSocial.forEach((container) => {
    container.innerHTML = "";
    const instaUrl = createEl("a", "", container);
    instaUrl.href = data.instagramUrl;
    const whassapUrl = createEl("a", "", container);

    whassapUrl.href = data.whatsappUrl;

    const instaIcon = createEl("img", "", instaUrl);
    instaIcon.src = "./assets/images/social/insta.svg";
    const whassapIcon = createEl("img", "", whassapUrl);
    whassapIcon.src = "./assets/images/social/whatsapp.svg";
  });
}
