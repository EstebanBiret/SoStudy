document.addEventListener("DOMContentLoaded", function () {
    const navItems = document.querySelectorAll(".navbar-right .nav-item img");

    navItems.forEach(img => {
        const originalSrc = img.getAttribute("src");
        if (!originalSrc.includes("-grey")) return;

        const activeSrc = originalSrc.replace("-grey", "-active");

        img.addEventListener("mouseover", () => {
            img.setAttribute("src", activeSrc);
        });

        img.addEventListener("mouseout", () => {
            img.setAttribute("src", originalSrc);
        });
    });
});
