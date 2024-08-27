// Function to show the login form and hide the registration form
function showLogin() {
    document.getElementById('login').style.transform = 'translateX(0)';
    document.getElementById('register').style.transform = 'translateX(100%)';
}

// Function to show the registration form and hide the login form
function showRegister() {
    document.getElementById('login').style.transform = 'translateX(-100%)';
    document.getElementById('register').style.transform = 'translateX(0)';
}

// Function to handle login form submission
function handleLogin() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    if (!username || !password) {
        alert('Please fill in all fields.');
        return;
    }

    fetch('http://localhost:8080/api/auth/login', { // Update with your backend login URL
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Login successful!');
                // Redirect or perform other actions
                window.location.href = '/dashboard'; // Redirect to the dashboard or another page
            } else {
                alert('Login failed: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred during login.');
        });
}

// Function to handle registration form submission
function handleRegister() {
    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;

    if (!firstName || !lastName || !email || !password) {
        alert('Please fill in all fields.');
        return;
    }

    fetch('http://localhost:8080/api/auth/register', { // Update with your backend registration URL
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstName, lastName, email, password }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Registration successful!');
                showLogin(); // Switch to login form after registration
            } else {
                alert('Registration failed: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred during registration.');
        });
}

// Event listeners for buttons to show login or registration form
document.getElementById('loginBtn').addEventListener('click', showLogin);
document.getElementById('registerBtn').addEventListener('click', showRegister);

// Event listeners for form submission buttons
document.querySelector('.login-container .submit').addEventListener('click', handleLogin);
document.querySelector('.register-container .submit').addEventListener('click', handleRegister);
