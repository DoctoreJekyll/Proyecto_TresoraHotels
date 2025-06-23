// JavaScript para mostrar/ocultar el botón y manejar el desplazamiento
document.addEventListener("DOMContentLoaded", function () {
    const scrollTopBtn = document.getElementById('scroll-top-btn');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 300) {
            scrollTopBtn.classList.add('show');
        } else {
            scrollTopBtn.classList.remove('show');
        }
    });

    scrollTopBtn.addEventListener('click', () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });


    // ===== Validación de Contraseña en Registro =====
    /*
    const form = document.getElementById('registroForm');
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault(); // Previene envío real del formulario

            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const mismatch = document.getElementById('passwordMismatch');

            if (password !== confirmPassword) {
                mismatch.classList.remove('d-none'); // Muestra mensaje de error
            } else {
                mismatch.classList.add('d-none');

                // Muestra el modal de éxito
                const modal = new bootstrap.Modal(document.getElementById('modalExito'));
                modal.show();

                // Limpia el formulario
                form.reset();
            }
        });
    }*/


    // ===== Validación de Login Simulado =====
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const email = document.getElementById("loginEmail").value.trim();
            const password = document.getElementById("loginPassword").value.trim();
            const errorDiv = document.getElementById("loginError");

            if (email === "test@ejemplo.com" && password === "1234") {
                errorDiv.classList.add("d-none");
                alert("Login correcto");
            } else {
                errorDiv.classList.remove("d-none");
            }
        });
    }
});