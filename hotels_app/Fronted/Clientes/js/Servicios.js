let slides = document.querySelectorAll('.slide');
let index = 0;

function showSlide() {
  slides.forEach((slide, i) => {
    slide.classList.remove('active');
    if (i === index) slide.classList.add('active');
  });
  index = (index + 1) % slides.length; // vuelve al inicio
}

setInterval(showSlide, 3000); // cambia cada 3 segundos
