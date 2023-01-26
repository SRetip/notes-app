var current_page = 0


function load() {
    clearList();
    sendRequest();
}

function nextPage() {
    current_page++;
    clearList();
    sendRequest();
}

function prevPage() {
    current_page--;
    clearList();
    sendRequest();
}

function sendRequest() {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    var re = JSON.stringify({
        id: JSON.parse(sessionStorage.getItem("user")).id
    });
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            try {
                var data = JSON.parse(this.response);
            } catch (e) {
                alert("Error!")
                return;
            }
            for (let i = 0; i < data.content.length; i++) {
                addToList(data.content[i]);
            }
            updatePage(data.first, data.last, data.number, data.totalPages);

        }
    });

    xhr.open("GET", "http://localhost:8888/notes?n=" + current_page);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer_" + sessionStorage.getItem("token"));
    // xhr.send();
    xhr.send(re);
}

function addToList(note) {
    var ul = document.getElementById("list");
    var li = document.createElement("li");
    var a = document.createElement("a");
    var p_t = document.createElement("p");
    var p_c = document.createElement("p");
    p_t.appendChild(document.createTextNode("Topic - " + note.topic))
    p_c.appendChild(document.createTextNode("Content - " + note.content))
    a.appendChild(p_t);
    a.appendChild(p_c);
    a.setAttribute("class", "product");
    li.appendChild(a);
    li.setAttribute("class", "product-wrapper");
    li.setAttribute("onClick", "toNote(" + note.id + ")");
    ul.appendChild(li);
}

function clearList() {
    document.getElementById("list").innerHTML = "";
}

function updatePage(first, last, number, totalPages) {
    document.getElementById("n").innerText = "Page - " + (number + 1) + " from " + totalPages;
    document.getElementById("prew").hidden = first;
    document.getElementById("next").hidden = last;


}

function toNote(id) {
    sessionStorage.setItem("current_note_id", id);
    window.location.replace("/html/notePage.html");
}


