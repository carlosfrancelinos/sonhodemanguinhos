document.getElementById("year").textContent = new Date().getFullYear();
document.addEventListener("DOMContentLoaded", () => {
    const items = Array.from(document.querySelectorAll("#galeriaGrid .g-item"));
    if (!items.length) return;

    const modal = document.getElementById("sdmModal");
    const imgEl = document.getElementById("sdmImg");
    const captionEl = document.getElementById("sdmCaption");
    const counterEl = document.getElementById("sdmCounter");

    const btnClose = document.getElementById("sdmClose");
    const btnPrev = document.getElementById("sdmPrev");
    const btnNext = document.getElementById("sdmNext");
    const stage = document.getElementById("sdmStage");

    const images = items.map(a => {
        const img = a.querySelector("img");
        const title = a.dataset.title || (img ? img.alt : "") || "";
        let full = a.dataset.full || (img ? img.getAttribute("src") : "");

        // Se vier sem img/, tenta completar
        if (full && !full.startsWith("http") && !full.startsWith("img/") && !full.startsWith("/")) {
            full = "img/" + full;
        }
        // Se tiver espaço, codifica
        full = full.replace(/ /g, "%20");

        return { full, title };
    });

    let index = 0;

    // Zoom / Pan
    let scale = 1, translateX = 0, translateY = 0;

    // Swipe
    let touchStartX = 0, touchStartY = 0;

    function setTransform() {
        imgEl.style.transform = `translate(${translateX}px, ${translateY}px) scale(${scale})`;
    }

    function resetZoom() {
        scale = 1; translateX = 0; translateY = 0;
        setTransform();
    }

    function updateUI() {
        counterEl.textContent = `${index + 1} / ${images.length}`;
        captionEl.textContent = images[index].title || "";
    }

    function show(i) {
        index = (i + images.length) % images.length;
        imgEl.src = images[index].full;
        updateUI();
        resetZoom();
    }

    function open(i) {
        show(i);
        modal.classList.add("open");
        modal.setAttribute("aria-hidden", "false");
        document.body.style.overflow = "hidden";
    }

    function close() {
        modal.classList.remove("open");
        modal.setAttribute("aria-hidden", "true");
        document.body.style.overflow = "";
    }

    function prev() { show(index - 1); }
    function next() { show(index + 1); }

    // Clique nas thumbs
    items.forEach((a, i) => {
        a.addEventListener("click", (e) => {
            e.preventDefault();
            open(i);
        });
    });

    // Fechar: X, clique fora, ESC
    btnClose.addEventListener("click", close);
    modal.addEventListener("click", (e) => { if (e.target === modal) close(); });

    document.addEventListener("keydown", (e) => {
        if (!modal.classList.contains("open")) return;
        if (e.key === "Escape") close();
        if (e.key === "ArrowLeft") prev();
        if (e.key === "ArrowRight") next();
    });

    btnPrev.addEventListener("click", (e) => { e.stopPropagation(); prev(); });
    btnNext.addEventListener("click", (e) => { e.stopPropagation(); next(); });

    // Zoom scroll (desktop)
    imgEl.addEventListener("wheel", (e) => {
        e.preventDefault();
        const delta = Math.sign(e.deltaY);
        const step = 0.15;
        scale = Math.min(4, Math.max(1, scale - delta * step));
        if (scale === 1) { translateX = 0; translateY = 0; }
        setTransform();
    }, { passive: false });

    // Double click zoom
    imgEl.addEventListener("dblclick", () => {
        scale = (scale === 1) ? 2 : 1;
        if (scale === 1) { translateX = 0; translateY = 0; }
        setTransform();
    });

    // Pan (arrastar) quando zoom > 1
    let dragging = false, startX = 0, startY = 0, startTX = 0, startTY = 0;

    imgEl.addEventListener("mousedown", (e) => {
        if (scale === 1) return;
        dragging = true;
        startX = e.clientX; startY = e.clientY;
        startTX = translateX; startTY = translateY;
    });

    window.addEventListener("mousemove", (e) => {
        if (!dragging) return;
        translateX = startTX + (e.clientX - startX);
        translateY = startTY + (e.clientY - startY);
        setTransform();
    });

    window.addEventListener("mouseup", () => dragging = false);

    // Swipe no celular (trocar imagem)
    stage.addEventListener("touchstart", (e) => {
        const t = e.touches[0];
        touchStartX = t.clientX;
        touchStartY = t.clientY;
    }, { passive: true });

    stage.addEventListener("touchend", (e) => {
        const t = e.changedTouches[0];
        const dx = t.clientX - touchStartX;
        const dy = t.clientY - touchStartY;

        if (scale > 1) return;

        if (Math.abs(dx) > 50 && Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) prev(); else next();
        }
    }, { passive: true });
});
