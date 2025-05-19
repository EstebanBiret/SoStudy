const checkboxes = document.querySelectorAll('.checkbox');
const submitBtn = document.getElementById('submitBtn');

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', () => {
        const isChecked = Array.from(checkboxes).some(cb => cb.checked);
        submitBtn.disabled = !isChecked;
    });
});

let sendIcon = document.querySelector('.send-icon');
let sendIconHover = document.querySelector('.send-icon-hover');
sendIcon.addEventListener('mouseover', function() {
    sendIcon.style.display = 'none';
    sendIconHover.style.display = 'block';
});
sendIconHover.addEventListener('mouseout', function() {
    sendIcon.style.display = 'block';
    sendIconHover.style.display = 'none';
});