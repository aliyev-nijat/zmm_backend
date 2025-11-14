let container = document.querySelector(".container");

class Util {
    static connectInputToImg(input, img, defaultUrl) {
        input.addEventListener('change', () => {
            const file = input.files && input.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = e => {
                    img.src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
            else {
                img.src = defaultUrl;
            }
        })
    }
}

class Api {
    static getSlider() {
        return fetch(`/api/slider`);
    }

    static uploadImage(id, body) {
        return fetch(`/api/slider/${id}`, {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken")
            },
            body
        });
    }

    static getUser() {
        return fetch("/api/auth/user", {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken")
            }
        })
    }
}

function loadPage() {
    container.innerHTML = "";
    Api.getSlider()
        .then(r => {
            if (r.status == 200) {

                return r.json();
            }
            else {
                throw Error();
            }
        })
        .then(sliders => sliders
            .map(slide => {
                let img = document.createElement('img');
                img.setAttribute('height', "200px");
                img.setAttribute('src', slide.imageUrl == null ? "" : slide.imageUrl);
                return {
                    slide,
                    imgElement: img
                }
            })
            .map(set => {
                let imgInput = document.createElement('input');
                imgInput.setAttribute("type", "file");
                imgInput.setAttribute("name", "image");
                imgInput.setAttribute("accept", "image/");
                set.imgInputElement = imgInput;
                Util.connectInputToImg(imgInput, set.imgElement,
                    set.slide.imageUrl == null ? "" : set.slide.imageUrl
                )

                return set;
            })
            .map(set => {
                let button = document.createElement('button');
                button.setAttribute("type", "submit");
                button.innerText = "Yükıə";
                set.submitButton = button;

                return set;
            })
            .map(set => {
                let form = document.createElement('form');
                form.appendChild(set.imgInputElement);
                form.appendChild(set.submitButton);
                form.addEventListener('submit', e => {
                    e.preventDefault();
                    const fileInput = e.target.querySelector("input");
                    if (fileInput.files.length != 0) {
                        const image = fileInput.files[0];
                        const imageFormData = new FormData();
                        imageFormData.append('image', image);
                        Api.uploadImage(set.slide.id, imageFormData)
                            .then(r => {
                                if (r.status != 204) throw Error();
                                else {
                                    alert("Şəkil yükləndi");
                                    loadPage();
                                }
                            })
                            .catch(() => alert("Xəta: Şəkil yüklənmədi"))
                    }
                    else {
                        alert("Yeni şəkil yükləyin");
                    }

                })
                let slideContainer = document.createElement("div");
                slideContainer.appendChild(form);
                slideContainer.appendChild(set.imgElement);

                return slideContainer
            })
            .forEach(slideContainer => container.appendChild(slideContainer))
        )
        .catch(() => alert("Xəta: Şəkillər yüklənmədi"));
}

if (localStorage.getItem("zmmtoken") == null) {
    window.location.href = "./login.html";
}
else {
    Api.getUser()
        .then(r => {
            if (r.status != 200) throw Error();
            return r.json();
        })
        .then(data => {
            console.log(data)
            loadPage();
        })
        .catch(() => window.location.href = "./login.html");
}


