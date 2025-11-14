let pageMode = "initial";
let state = {};
let container = document.querySelector("#container");

class Util {
    static escapeForAttribute(str) {
        return str.split("")
            .map(char => {
                switch (char) {
                    case '"':
                        return "&quot;";
                    case "&":
                        return "&amp;";
                    case '<':
                        return "&lt;";
                    case '>':
                        return "&gt;";
                    default:
                        return char;
                }
            })
            .join("");
    }

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
    static create(body) {
        return fetch('/api/events', {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken"),
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
    }

    static getAll() {
        return fetch('/api/events');
    }

    static getById(id) {
        return fetch(`/api/events/${id}`);
    }

    static update(id, body) {
        return fetch(`/api/events/${id}`, {
            method: 'PUT',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken"),
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
    }

    static delete(id) {
        return fetch(`/api/events/${id}`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken")
            }
        });
    }

    static uploadImage(id, body) {
        return fetch(`/api/events/${id}/image`, {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken")
            },
            body
        });
    }

    static deleteImage(id) {
        return fetch(`/api/events/${id}/image`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("zmmtoken")
            }
        })
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

class Creation {
    static getCreatForm() {
        let form = document.createElement('form');
        form.setAttribute("id", "create-form");
        form.innerHTML = `<label for="order">Sıra</label>
        <input type="number" name="order" id="order">
        <label for="title">Başlıq (title)</label>
        <input type="text" name="title" id="title">
        <label for="about">Haqqında</label>
        <textarea name="about" id="about"></textarea>
        <label for="dateTime">Tarix</label>
        <input type="datetime-local" name="dateTime" id="dateTime">
        <button type="submit">Əlavə et</button>
        `;
        form.addEventListener("submit", Creation.submitEventListener);

        return form;
    }

    static submitEventListener(e) {
        e.preventDefault();
        let form = document.querySelector("#create-form");
        let formData = new FormData(e.target);
        const requestBody = Object.fromEntries(formData.entries());
        const keyMap = {
            title: "Başlıq",
            about: "Haqqında",
            dateTime: "Tarix"
        };
        if (
            Object.keys(requestBody)
                .filter(key => !requestBody[key])
                .map(key => keyMap[key] || key)
                .map(key => `${key} boş qoyula bilməz`)
                .map(message => alert(message))
                .length != 0
        ) return;

        Api.create(requestBody)
            .then(r => {
                if (r.status != 200) throw new Error();
                return r.json();
            }
            )
            .then(data => {
                form.reset();
                alert(`Əlavə edildi!\n${Object.keys(data)
                    .map(key => `${keyMap[key] || key}: ${data[key]}`)
                    .join("\n")
                    }`);
            })
            .catch(() => alert("Xəta: Yeni tədbir yaradılmadı"))

    }
}

class Updating {
    static getUpdateForm(id) {
        let form = document.createElement('form');
        return Api.getById(id).then(r => {
            if (r.status != 200) throw new Error();
            return r.json();
        })
            .then(event => {
                form.setAttribute("id", "update-form");
                form.innerHTML = `
            <label for="id">ID</label>
            <input type="number" name="id" id="id" disabled value="${event.id}">
            <label for="order">Sıra</label>
            <input type="number" name="order" id="order" value="${event.order}">
            <label for="title">Başlıq (title)</label>
            <input type="text" name="title" id="title" value="${Util.escapeForAttribute(event.title)}">
            <label for="about">Haqqında</label>
            <textarea name="about" id="about">${Util.escapeForAttribute(event.about)}</textarea>
            <label for="dateTime">Tarix</label>
            <input type="datetime-local" name="dateTime" id="dateTime" value="${event.dateTime}">
            <label for="update-file">Şəkil</label>
            <input type="file" name="image" id="update-file" accept="image/">
                <img src="${event.imageId ? `/api/images/${event.imageId}` : ""}"> 
            <button type="submit">Redaktə et</button>
            `;
                Util.connectInputToImg(
                    form.querySelector("#update-file"),
                    form.querySelector("img"),
                    event.imageUrl == null ? "" : event.imageUrl
                );
                form.addEventListener("submit", e => Updating.submitEventListener(e, id));

                return form;
            })
            .catch(() => alert("Xəta: Tədbir məlumatları əldə edilə bilmədi."))

    }

    static submitEventListener(e, id) {
        e.preventDefault();
        let formData = new FormData(e.target);
        const requestBody = Object.fromEntries(formData.entries());
        const keyMap = {
            title: "Başlıq",
            about: "Haqqında",
            dateTime: "Tarix"
        };
        if (
            Object.keys(requestBody)
                .filter(key => !requestBody[key])
                .map(key => keyMap[key] || key)
                .map(key => `${key} boş qoyula bilməz`)
                .map(message => alert(message))
                .length != 0
        ) return;
        delete requestBody.file;

        Api.update(id, requestBody).then(r => {
            if (r.status != 200) throw new Error();
            return r.json();
        })
            .then(data => {
                const fileInput = e.target.querySelector("#update-file");
                if (fileInput.files.length != 0) {
                    const image = fileInput.files[0];
                    const imageFormData = new FormData();
                    imageFormData.append('image', image);
                    Api.uploadImage(id, imageFormData)
                        .then(r => {
                            if (r.status != 200) throw Error();
                        })
                        .catch(() => alert("Xəta: Şəkil yüklənmədi"))
                }
                alert(`Redaktə edildi!\n${Object.keys(data)
                    .map(key => `${keyMap[key] || key}: ${data[key]}`)
                    .join("\n")
                    }`);
                pageMode = "initial";
                Page.loadPage();
            })
            .catch(() => alert("Xəta: Redaktə mümkün olmadı"));
    }

    static getEventFromServer(id) {
        return fetch(`/api/events/${id}`)
            .then(r => r.json());
    }
}

class Page {
    static getTable() {
        let table = document.createElement("table");
        table.innerHTML = `<thead>
        <tr>
        <th>ID</th>
        <th>Sıra</th>
        <th>Başlıq</th>
        <th>Haqqında</th>
        <th>Tarix</th>
        <th></th>
        <th></th>
        <th colspan="2">Şəkil</th>
        </thead>`;
        let tbody = document.createElement('tbody');
        table.appendChild(tbody);
        return Api.getAll()
            .then(r => {
                if (r.status != 200) throw new Error();

                return r.json()
            })
            .then(events => {
                events
                    .map(event => ({
                        event,
                        element: document.createElement('tr')
                    }))
                    .map(set => {
                        set.element
                            .innerHTML = `
                <td>${set.event.id}</td>
                <td>${set.event.order}</td>
                <td>${set.event.title}</td>
                <td>${Util.escapeForAttribute(set.event.about)}</td>
                <td>${`Tarix: ${set.event.dateTime.split("T")[0]}, 
                    Saat: ${set.event.dateTime.split("T")[1]}`}
                    </td>`;

                        return set;
                    })
                    .map(set => {
                        let td = document.createElement('td');
                        let button = document.createElement('button');
                        td.appendChild(button);
                        button.innerHTML = "Sil";
                        button.addEventListener("click", e => {
                            e.preventDefault();
                            if (confirm("Tədbir silinsin?")) {
                                Api.delete(set.event.id)
                                    .then(r => {
                                        if (r.status != 204) throw Error();
                                    })
                                    .catch(() => alert("Xəta: Tədbirin silinməsi mümkün olmadı"))
                                    .finally(() => Page.loadPage())
                            }
                        })
                        set.element.appendChild(td);

                        return set;
                    })
                    .map(set => {
                        let td = document.createElement('td');
                        let button = document.createElement('button');
                        td.appendChild(button);
                        button.innerHTML = "Redaktə et";
                        button.addEventListener('click', e => {
                            e.preventDefault();
                            pageMode = "update";
                            state.eventId = set.event.id;
                            Page.loadPage();
                        });
                        set.element.appendChild(td);

                        return set;
                    })
                    .map(set => {
                        let td = document.createElement('td');
                        if (set.event.imageUrl == null) td.setAttribute("colspan", "2");
                        if (set.event.imageUrl) {
                            let img = document.createElement('img');
                            img.setAttribute("width", "200px");
                            img.setAttribute("src", set.event.imageUrl);
                            td.appendChild(img);
                        }
                        else {
                            td.innerHTML = `<span style="color: red;">Şəkil əlavə edilməyib</span>`;
                        }
                        set.element.appendChild(td);

                        return set;
                    })
                    .map(set => {
                        if (set.event.imageUrl == null) return set;
                        let td = document.createElement('td');
                        let button = document.createElement('button');
                        td.appendChild(button);
                        button.innerHTML = "Şəkili sil";
                        button.addEventListener('click', e => {
                            e.preventDefault();
                            Api.deleteImage(set.event.id)
                                .then(r => {
                                    if (r.status != 204) throw new Error();
                                })
                                .then(() => alert("Şəkil silindi"))
                                .catch(() => alert("Xəta: Şəkil silinmədi"))
                                .finally(() => Page.loadPage());
                        });
                        set.element.appendChild(td);

                        return set;
                    })
                    .map(set => set.element)
                    .forEach(element => tbody.appendChild(element));

                return table;
            })
            .catch(() => alert("Xəta: Tədbirlərin yüklənməsi mümkün olmadı"));
    }

    static loadPage() {
        container.innerHTML = "";
        switch (pageMode) {
            case 'initial':
                container.appendChild(Page.getNewEventButton());
                Page.getTable().then(table => container.appendChild(table));
                break;
            case 'create-new':
                container.appendChild(Page.getBackButton());
                container.appendChild(Creation.getCreatForm());
                break;
            case 'update':
                Updating
                    .getUpdateForm(state.eventId)
                    .then(form => container.appendChild(form));
        }
    }

    static getBackButton() {
        let button = document.createElement('button');
        button.innerHTML = "Geri";
        button.addEventListener('click', e => {
            e.preventDefault();
            pageMode = "initial";
            Page.loadPage();
        });

        return button;
    }

    static getNewEventButton() {
        let button = document.createElement('button');
        button.innerHTML = "Yeni";
        button.addEventListener('click', e => {
            e.preventDefault();
            pageMode = "create-new";
            Page.loadPage();
        });

        return button;
    }
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
        .then(() => {
            Page.loadPage();
        })
        .catch(() => window.location.href = "./login.html");
}
