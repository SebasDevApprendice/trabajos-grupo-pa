const carruselItems = document.querySelector(".carrusel_items");
        const btnPrev = document.querySelector(".carrusel_prev");
        const btnNext = document.querySelector(".carrusel_next");

        let vistaActual = 0;
        const totalItems = document.querySelectorAll(".carrusel_item").length;

        function updateCarrusel() {
            carruselItems.style.transform = `translateX(-${vistaActual * 100}%)`;
        }

        btnNext.addEventListener("click", () => {
            vistaActual = (vistaActual + 1) % totalItems;
            updateCarrusel();
        });

        btnPrev.addEventListener("click", () => {
            vistaActual = (vistaActual - 1 + totalItems) % totalItems;
            updateCarrusel();
        });

        // Scroll automático cada 5 segundos
        setInterval(() => {
            vistaActual = (vistaActual + 1) % totalItems;
            updateCarrusel();
        }, 5000);