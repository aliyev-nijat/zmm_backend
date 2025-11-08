const navMenu = document.querySelector('.navMenuMobil');
const openBtn = document.querySelector('.hamburgerMenu'); // menyunu açan düymə
const closeBtn = document.querySelector('.navClose'); // menyunu bağlayan düymə

openBtn.addEventListener('click', () => {
  navMenu.style.display = 'flex';
  document.body.style.overflow = 'hidden'; // scroll gizlə
});

closeBtn.addEventListener('click', () => {
  navMenu.style.display = 'none';
  document.body.style.overflow = ''; // scroll bərpa et
});
