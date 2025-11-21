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

  const socials = [
    { url: data.instagramUrl, icon: "./assets/images/social/insta.svg" },
    { url: data.whatsappUrl, icon: "./assets/images/social/whatsapp.svg" },
    { url: data.whatsappChannelUrl, icon: "./assets/images/social/bullhorn.svg" },
    { url: data.tiktokUrl, icon: "./assets/images/social/tiktok.svg" },
    { url: data.youtubeUrl, icon: "./assets/images/social/youtube.svg" }
  ];

  socials.forEach((item) => {
    if (!item.url) return; // boş olanları keç
    const a = createEl("a", "", container);
    a.href = item.url;

    const img = createEl("img", "", a);
    img.src = item.icon;
  });
});

}
