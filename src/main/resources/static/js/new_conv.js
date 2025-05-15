const checkboxes = document.querySelectorAll('.checkbox');
const submitBtn = document.getElementById('submitBtn');

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', () => {
        const isChecked = Array.from(checkboxes).some(cb => cb.checked);
        submitBtn.disabled = !isChecked;
    });
});
