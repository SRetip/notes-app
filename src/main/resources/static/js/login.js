function login() {
    var data = JSON.stringify(
        {
            "login": document.getElementById('login').value,
            "password": document.getElementById('password').value
        });

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            try {
                var data = JSON.parse(this.response);
            } catch (e) {
                alert("Wrong login ar password!)")
                return;
            }
            var user = data.user;
            sessionStorage.setItem("user", JSON.stringify(user));
            sessionStorage.setItem("token", data.token);
            window.location.replace("/html/homepage.html");
        }
    });

    xhr.open("POST", "http://localhost:8888/auth/login");
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.send(data);
}