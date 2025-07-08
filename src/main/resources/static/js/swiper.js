document.addEventListener('DOMContentLoaded', () => {
    // Toggle barra de reservas
    const bookingBar = document.getElementById('bookingBar');
    const toggleBtn = document.getElementById('toggleBookingBtn');

    toggleBtn.addEventListener('click', () => {
        const isCompressed = bookingBar.classList.toggle('compressed');
        toggleBtn.setAttribute('aria-expanded', !isCompressed);
        toggleBtn.innerHTML = isCompressed ? '&#9776;' : '&times;';
    });

    // Swiper About Us - mantenemos la configuración y nombre
    const aboutUsSwiper = new Swiper('.mySwiper', {
        slidesPerView: 1.6,
        centeredSlides: false,
        loop: true,
        spaceBetween: 20,
        grabCursor: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        breakpoints: {
            768: { slidesPerView: 2.2 },
            1024: { slidesPerView: 3 }
        }
    });

    // Swiper Servicios
    const servicesSwiper = new Swiper('.servicesSwiper', {
        slidesPerView: 1,
        spaceBetween: 10,
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        },
        breakpoints: {
            768: { slidesPerView: 2 },
            1024: { slidesPerView: 3 }
        }
    });
});
