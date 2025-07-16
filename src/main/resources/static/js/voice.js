// Estado inicial de la funcionalidad de voz
let isVoiceEnabled = false;

// Referencia al botón de activación/desactivación
const toggleVoiceBtn = document.getElementById('toggleVoiceBtn');

// Crear una instancia de SpeechSynthesis
const synth = window.speechSynthesis;

// Función para leer texto en voz alta
function speak(text) {
    if (isVoiceEnabled && text) {
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.lang = 'es-ES'; // Configurar idioma a español
        utterance.rate = 1; // Velocidad de la voz
        utterance.pitch = 1; // Tono de la voz
        synth.speak(utterance);
    }
}

// Función para obtener el texto a leer de un botón
function getButtonText(button) {
    return (
        button.getAttribute('aria-label') ||
         button.textContent.trim() //||
        // button.getAttribute('title') ||
        // 'Botón sin descripción'
    );
}

// Seleccionar todos los botones en la página
const buttons = document.querySelectorAll('button, a.btn');

// Añadir evento mouseover a cada botón
buttons.forEach(button => {
    button.addEventListener('mouseover', () => {
        if (isVoiceEnabled) {
            const text = getButtonText(button);
            speak(text);
        }
    });
});

// Manejar el botón de activar/desactivar voz
toggleVoiceBtn.addEventListener('click', () => {
    isVoiceEnabled = !isVoiceEnabled;
    toggleVoiceBtn.setAttribute('aria-checked', isVoiceEnabled);
    toggleVoiceBtn.classList.toggle('active', isVoiceEnabled);
    toggleVoiceBtn.setAttribute('aria-label', isVoiceEnabled ? 'Desactivar lectura en voz alta' : 'Activar lectura en voz alta');
    toggleVoiceBtn.title = isVoiceEnabled ? 'Desactivar lectura en voz alta' : 'Activar lectura en voz alta';

    // Cambiar el icono según el estado
    toggleVoiceBtn.innerHTML = isVoiceEnabled
        ? '<i class="bi bi-volume-up-fill"></i>'
        : '<i class="bi bi-volume-mute"></i>';

    // Anunciar el cambio de estado
    speak(isVoiceEnabled ? 'Lectura en voz alta activada' : 'Lectura en voz alta desactivada');
});