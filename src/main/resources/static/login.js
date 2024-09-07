// Function to show login form
function showLogin() {
    document.getElementById("login").style.display = "block";
    document.getElementById("register").style.display = "none";
}

// Function to show register form
function showRegister() {
    document.getElementById("login").style.display = "none";
    document.getElementById("register").style.display = "block";
}

// Handle Login
document.getElementById("login").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;

    fetch("http://localhost:8080/api/users/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ userName: username, password: password })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Authentication failed");
            }
        })
        .then(data => {
            alert("Login successful!");
            localStorage.setItem('isAuthenticated', true);
            window.location.href = "main.html";
        })
        .catch(error => {
            alert("Invalid username or password");
            console.error("Error:", error);
        });
});

// Handle SignUp
document.getElementById("register").addEventListener("submit", function (event) {
    event.preventDefault();

    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const username = document.getElementById("register-email").value;
    const password = document.getElementById("register-password").value;

    fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: firstName,
            lastName: lastName,
            userName: username,
            password: password,
            bookName: ""
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Signup failed");
            }
        })
        .then(data => {
            alert("Registration successful! Please login.");
            showLogin();  // Switch to login form
        })
        .catch(error => {
            alert("Error during registration");
            console.error("Error:", error);
        });
});
