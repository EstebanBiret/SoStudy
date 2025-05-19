function redirectSearch(event) {
    event.preventDefault();
    const input = document.getElementById("searchInputBody");
    const query = input.value.trim();
    if (query) {
        // Redirige vers l'URL avec le pseudo
        window.location.href = `/user/search/${encodeURIComponent(query)}`;
    }
    return false;
}