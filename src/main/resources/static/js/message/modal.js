function openModal() {
    const selectedCheckboxes = document.querySelectorAll('.checkbox:checked');
    const selectedIds = Array.from(selectedCheckboxes).map(cb => cb.value);

    document.getElementById('selectedUsersInput').value = selectedIds.join(',');

    const channelNameWrapper = document.getElementById('channelNameWrapper');
    const channelImageWrapper = document.getElementById('channelImageWrapper');
    if (selectedIds.length > 1) {
        channelNameWrapper.style.display = 'block';
        channelImageWrapper.style.display = 'block';
    } else {
        channelNameWrapper.style.display = 'none';
        channelImageWrapper.style.display = 'none';
    }


    document.getElementById('myModal').style.display = 'flex';
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

function copyLink() {
    const input = document.querySelector('.share-link input');
    input.select();
    document.execCommand('copy');
}

window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

document.getElementById('channelImage').addEventListener('change', function(event) {
    const input = event.target;
    const preview = document.getElementById('preview');

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        preview.style.display = 'none';
    }
});