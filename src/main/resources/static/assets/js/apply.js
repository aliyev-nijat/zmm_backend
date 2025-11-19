const courseId = new URLSearchParams(window.location.search).get("id");

const form = document.querySelector("form");

form.addEventListener("submit", function (e) {
  e.preventDefault(); // Formun default göndərməsini bloklayırıq

  // Input dəyərlərini götürək
  const firstName = document.getElementById("ad").value.trim();
  const lastName = document.getElementById("soyad").value.trim();
  const birthYear = document.getElementById("dogum_ili").value;
  const contactNumber = document.getElementById("telefon").value.trim();

  // Cinsiyyət (radio)
  const gender = document.querySelector(
    'input[name="cinsiyyet"]:checked'
  )?.value;

  // API-yə göndərilən JSON
  const bodyData = {
    firstName,
    lastName,
    birthYear: Number(birthYear),
    contactNumber,
    gender,
  };

  // POST sorğusu
  fetch(`/api/courses/apply/${courseId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bodyData),
  })
    .then(async (res) => {
      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || `Server xətası: ${res.status}`);
      }

      // server cavabı boş ola bilər – JSON oxumuruq
      return null;
    })
    .then((data) => {
      console.log("Göndərildi:", data);
      alert("Müraciət uğurla göndərildi!");
      form.reset();
    })
    .catch((err) => {
      console.error("Xəta:", err);
      alert("Xəta baş verdi!");
    });
});
