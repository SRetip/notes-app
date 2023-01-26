function load() {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            try {
                var data = JSON.parse(this.response);
            } catch (e) {
                alert("Error!")
                return;
            }
            document.getElementById("topic").value = data.topic;
            console.log(data.date);
            console.log(data.date.split("T"));
            document.getElementById("date").value = data.date.split("T")[0];
            document.getElementById("content").value = data.content;
        }
    });

    xhr.open("GET", "http://localhost:8888/notes/" + sessionStorage.getItem("current_note_id"));
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer_" + sessionStorage.getItem("token"));
    let re;
    xhr.send(re);
}

function update() {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    var re = JSON.stringify({
            topic: document.getElementById("topic").value,
            content: document.getElementById("content").value,
            date: document.getElementById("date").value,
        }
    );
    console.log(JSON.parse(sessionStorage.getItem("user")).id);
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            try {
                var data = JSON.parse(this.response);
            } catch (e) {
                alert("Error!")
                return;
            }

            document.getElementById("topic").value = data.topic;
            document.getElementById("date").value = data.date.split("T")[0];
            document.getElementById("content").value = data.content;
        }
    });

    xhr.open("PUT", "http://localhost:8888/notes/" + sessionStorage.getItem("current_note_id"));
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer_" + sessionStorage.getItem("token"));
    xhr.send(re);
}

function del() {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    var re;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            window.location.replace("/html/homepage.html");
        }
    });

    xhr.open("DELETE", "http://localhost:8888/notes/" + sessionStorage.getItem("current_note_id"));
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer_" + sessionStorage.getItem("token"));
    xhr.send(re);
}


function addNote() {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    var re = JSON.stringify({
            user_id: JSON.parse(sessionStorage.getItem("user")).id,
            topic: document.getElementById("topic").value,
            content: document.getElementById("content").value,
            date: document.getElementById("date").value,
        }
    );
    console.log(JSON.parse(sessionStorage.getItem("user")).id);
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            try {
                JSON.parse(this.response);
            } catch (e) {
                alert("Error!")
                return;
            }
            window.location.replace("/html/homepage.html");
        }
    });

    xhr.open("POST", "http://localhost:8888/notes");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer_" + sessionStorage.getItem("token"));
    xhr.send(re);
}


