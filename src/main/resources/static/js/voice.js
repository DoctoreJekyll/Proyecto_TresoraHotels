// Estado inicial de la funcionalidad de voz
let isVoiceEnabled = false;
let lastSpokenElement = null;

// Botón de activación
const toggleVoiceBtn = document.getElementById('toggleVoiceBtn');
const synth = window.speechSynthesis;

// Función para hablar
function speak(text) {
    if (isVoiceEnabled && text) {
        synth.cancel();
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.lang = 'es-ES';
        utterance.rate = 1;
        utterance.pitch = 1;
        synth.speak(utterance);
    }
}

// Obtener texto legible
function getElementText(element) {
    return (
        element.getAttribute('aria-label') ||
        element.textContent.trim() ||
        element.getAttribute('title')
    );
}

// Añadir eventos de voz a un elemento
function addVoiceEventsToElement(element) {
    if (element.hasAttribute('data-voice-enabled')) return;
    element.setAttribute('data-voice-enabled', 'true');

    const handleSpeak = () => {
        if (!isVoiceEnabled) return;

        if (element.tagName === 'SELECT') {
            const label = document.querySelector(`label[for="${element.id}"]`);
            let announcement = '';
            if (label) announcement += label.textContent + '. ';
            announcement += element.getAttribute('aria-label') || '';

            const selectedOption = element.options[element.selectedIndex];
            if (selectedOption && selectedOption.value !== "" && !selectedOption.disabled) {
                announcement += '. Valor actual: ' + selectedOption.textContent.trim();
            }

            if (announcement && lastSpokenElement !== element) {
                speak(announcement);
                lastSpokenElement = element;
            }
        } else {
            const text = getElementText(element);
            if (text && lastSpokenElement !== element) {
                speak(text);
                lastSpokenElement = element;
            }
        }
    };

    element.addEventListener('mouseenter', handleSpeak);
    element.addEventListener('focus', handleSpeak);
    element.addEventListener('mouseleave', () => lastSpokenElement = null);
    element.addEventListener('blur', () => lastSpokenElement = null);

    // Evento para detectar cuando el usuario cambia de opción en un SELECT
    if (element.tagName === 'SELECT') {
        element.addEventListener('change', () => {
            if (!isVoiceEnabled) return;
            const selectedOption = element.options[element.selectedIndex];
            if (selectedOption && selectedOption.textContent.trim()) {
                speak('Seleccionado: ' + selectedOption.textContent.trim());
            }
        });
    }
}

// Inicializar todos los elementos actuales
function initVoiceForAll() {
    const elementsWithAria = document.querySelectorAll('[aria-label]');
    const buttons = document.querySelectorAll('button, a.btn');
    const selects = document.querySelectorAll('select');
    const allElements = new Set([...elementsWithAria, ...buttons, ...selects]);
    allElements.forEach(addVoiceEventsToElement);
}

// Observador para nuevos elementos dinámicos
const observer = new MutationObserver(mutations => {
    let hasNewElements = false;
    mutations.forEach(m => {
        if (m.type === 'childList' && m.addedNodes.length > 0) {
            hasNewElements = true;
        }
    });
    if (hasNewElements) {
        initVoiceForAll();
    }
});
observer.observe(document.body, { childList: true, subtree: true });

// Botón de activación/desactivación
toggleVoiceBtn.addEventListener('click', () => {
    isVoiceEnabled = !isVoiceEnabled;
    toggleVoiceBtn.setAttribute('aria-checked', isVoiceEnabled);
    toggleVoiceBtn.classList.toggle('active', isVoiceEnabled);
    toggleVoiceBtn.setAttribute('aria-label', isVoiceEnabled ? 'Botón lectura en voz activado' : 'Botón lectura en voz desactivado');
    toggleVoiceBtn.title = isVoiceEnabled ? 'Lectura en voz activada' : 'Lectura en voz desactivada';
    toggleVoiceBtn.innerHTML = isVoiceEnabled
        ? '<i class="bi bi-volume-up-fill"></i>'
        : '<i class="bi bi-volume-mute"></i>';
    synth.cancel();
    speak(isVoiceEnabled ? 'Lectura en voz activada' : 'Lectura en voz desactivada');
});

// Al cargar la página
document.addEventListener('DOMContentLoaded', initVoiceForAll);
