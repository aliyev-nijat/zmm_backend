

(function () {
  document.querySelectorAll(".slide-viewport").forEach((viewport) => {
    const row = viewport.querySelector(".slide-row");
    const scrollbar = viewport.parentElement.querySelector(".slide-scrollbar");
    const track = scrollbar.querySelector(".scrollbar__track");
    const thumb = track.querySelector(".scrollbar__thumb");

    const clamp = (v, min, max) => Math.max(min, Math.min(v, max));
    const maxScroll = () => Math.max(0, row.scrollWidth - row.clientWidth);
    const trackW = () => track.clientWidth;

    const updateThumb = () => {
      const m = maxScroll();
      const ratio = m === 0 ? 1 : row.clientWidth / row.scrollWidth;
      const w = Math.max(40, trackW() * ratio);
      thumb.style.width = w + "px";
      const x = m > 0 ? (row.scrollLeft / m) * (trackW() - w) : 0;
      thumb.style.transform = `translateX(${x}px)`;
    };

    let dragging = false,
      startX = 0,
      startScroll = 0;
    let vel = 0,
      rafId = 0,
      lastT = 0,
      lastX = 0;

    const stopMomentum = () => {
      cancelAnimationFrame(rafId);
      rafId = 0;
      vel = 0;
    };

    const momentum = () => {
      stopMomentum();
      const step = () => {
        if (Math.abs(vel) < 0.15) return stopMomentum();
        row.scrollLeft = clamp(row.scrollLeft - vel, 0, maxScroll());
        vel *= 0.92;
        updateThumb();
        rafId = requestAnimationFrame(step);
      };
      rafId = requestAnimationFrame(step);
    };

    const onPointerDown = (e) => {
      viewport.setPointerCapture(e.pointerId);
      dragging = true;
      viewport.classList.add("dragging");
      stopMomentum();
      startX = e.clientX;
      startScroll = row.scrollLeft;
      lastT = performance.now();
      lastX = startX;
    };

    const onPointerMove = (e) => {
      if (!dragging) return;
      const now = performance.now();
      const dx = e.clientX - startX;
      row.scrollLeft = clamp(startScroll - dx, 0, maxScroll());
      vel = (e.clientX - lastX) / (now - lastT || 16);
      lastT = now;
      lastX = e.clientX;
      updateThumb();
    };

    const onPointerUp = () => {
      if (!dragging) return;
      dragging = false;
      viewport.classList.remove("dragging");
      vel *= 16;
      momentum();
    };

    viewport.addEventListener("pointerdown", onPointerDown);
    viewport.addEventListener("pointermove", onPointerMove, { passive: true });

    let tDragging = false,
      tStartX = 0,
      tStartLeft = 0;

    const onThumbDown = (e) => {
      const rect = track.getBoundingClientRect();
      const clickX = e.clientX - rect.left;
      const thumbRect = thumb.getBoundingClientRect();
      const w = thumbRect.width;

      if (
        clickX < thumbRect.left - rect.left ||
        clickX > thumbRect.right - rect.left
      ) {
        const nx = clamp(clickX - w / 2, 0, trackW() - w);
        row.scrollLeft = (nx / (trackW() - w)) * maxScroll();
        updateThumb();
      }

      tDragging = true;
      track.setPointerCapture(e.pointerId);
      tStartX = e.clientX;
      tStartLeft = new DOMMatrixReadOnly(getComputedStyle(thumb).transform).m41;
      stopMomentum();
    };

    const onThumbMove = (e) => {
      if (!tDragging) return;
      const w = thumb.getBoundingClientRect().width;
      const nx = clamp(tStartLeft + e.clientX - tStartX, 0, trackW() - w);
      row.scrollLeft = (nx / (trackW() - w)) * maxScroll();
      updateThumb();
    };

    const endThumb = () => (tDragging = false);
    const endContent = () => {
      if (dragging) {
        dragging = false;
        viewport.classList.remove("dragging");
        vel *= 16;
        momentum();
      }
    };

    track.addEventListener("pointerdown", onThumbDown);
    track.addEventListener("pointermove", onThumbMove, { passive: true });

    document.addEventListener("pointerup", endContent);
    document.addEventListener("pointercancel", endContent);
    document.addEventListener("pointerup", endThumb);
    document.addEventListener("pointercancel", endThumb);

    row.addEventListener("scroll", updateThumb, { passive: true });
    window.addEventListener("resize", updateThumb);
    updateThumb();

    row.querySelectorAll("img").forEach((img) => (img.draggable = false));
  });
})();
